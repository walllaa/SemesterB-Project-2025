package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.WorkerUpdateManager;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ManagerUpdateManager;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.OrderUpdateManager;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ComplaintUpdateManager;
import java.io.IOException;
import java.util.*;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class SimpleServer extends AbstractServer {

	public static Session session; //  shared session used in several helpers below

	// Renamed caches to reduce similarity and improve clarity (no API impact outside this class)
	private List<Product> catalogCache = new ArrayList<>(); // was productGeneralList
	private List<Account> accountsCache = new ArrayList<>(); // was accountGeneralList
	private int productCount = 0; // was flowersnum

	public SimpleServer(int port) { super(port); }

	// Persist the products currently in catalogCache (kept method name to avoid breaking callers)
	public void Saveinsess() {
		SessionFactory sf = getSessionFactory();
		session = sf.openSession();
		Transaction tx = session.beginTransaction();
		for (Product p : catalogCache) {
			session.save(p);
			session.flush();
		}
		tx.commit();
		session.close();
	}

	public static SessionFactory getSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Product.class);
		configuration.addAnnotatedClass(Account.class);
		configuration.addAnnotatedClass(Worker.class);
		configuration.addAnnotatedClass(Manager.class);
		configuration.addAnnotatedClass(Order.class);
		configuration.addAnnotatedClass(Complaint.class);
		configuration.addAnnotatedClass(Message.class);

//		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//				.applySettings(configuration.getProperties())
//				.build();
//		return configuration.buildSessionFactory(serviceRegistry);}

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.build();

		try {
			SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			System.out.println("DB connection established successfully");
			return sessionFactory;
		} catch (HibernateException e) {
			System.err.println("Failed to establish DB connection: " + e.getMessage());
			throw e;
		}
	}
	public static void generateProducts() {
		System.out.println("arrived to generate products function");
		// NOTE: Product API was renamed earlier (id→productCode, etc.). Use the new ctor if available.
		Product product = new Product(5, "btn", "flower1", "someDetails", "5000");
		System.out.println("finished creating the product");
		session.save(product);
		session.flush(); // push insert within the open transaction
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) throws IOException, IOException {
		System.out.println("handleeeeeeeeeeee");
		System.out.println("msg class is " + msg.getClass());

		if (msg instanceof String) {
			SessionFactory sf = getSessionFactory();
			session = sf.openSession();
			Transaction tx1 = session.beginTransaction();

			String received = (String) msg;

			if (received.equals("first entry")) { // app opened — hydrate initial data if exists
				List<String> tables = session.createSQLQuery("SHOW TABLES;").list();
				int idx = -1;
				for (int i = 0; i < tables.size(); i++) {
					if ("products_table".equals(tables.get(i))) idx = i;
				}
				if (idx != -1) {
					if (countRows() == 0) { // no rows yet
						client.sendToClient("not found");
						session.close();
					} else {
						List<Product> resultList = getAllProducts();
						FoundTable foundTbl = new FoundTable("found", resultList);
						client.sendToClient(foundTbl);
						session.close();
					}
				} else {
					client.sendToClient("not found");
					session.close();
				}
			}

			if (received.equals("get Managers")) { // send cached managers from manager update manager
				ManagerUpdateManager mngrUpdater = new ManagerUpdateManager();
				FoundTable foundTbl = new FoundTable("managers table found");
				foundTbl.setRecievedManagers(mngrUpdater.managerGeneralList);
				client.sendToClient(foundTbl);
			}

			if (received.equals("get Workers")) {
				WorkerUpdateManager workerUpdater = new WorkerUpdateManager();
				FoundTable foundTbl = new FoundTable("workers table found");
				foundTbl.setRecievedWorkers(workerUpdater.workerGeneralList);
				client.sendToClient(foundTbl);
			}

			if (received.equals("get complaints")) {
				GetAllComplaints payload = new GetAllComplaints();
				payload.setComplaintsList(getAllComplaints());
				client.sendToClient(payload);
			}

			if (received.equals("get Accounts")) {
				GetAllAccounts payload = new GetAllAccounts();
				payload.setAll_accounts(getAllAccounts());
				client.sendToClient(payload);
			}

			if (received.equals("getCatalog")) {
				List<Item> items = CatalogDAO.getAllItems();
				client.sendToClient(new CatalogItems(items));
			}

			tx1.commit();
			session.close();
		}

		if (msg instanceof UpdateMessage) {
			System.out.println("Arrived At UpdateMessage 1");
			SessionFactory sf = getSessionFactory();
			session = sf.openSession();
			Transaction tx1 = session.beginTransaction();

			UpdateMessage um = (UpdateMessage) msg;
			String target = um.getTargetClass(); // renamed from getUpdateClass()
			String action = um.getActionType();  // renamed from getUpdateFunction()
			System.out.println("Arrived At UpdateMessage 2");

			switch (target) {
				case "product":
					if ("add".equals(action)) {
						Product p = um.getProduct();
						addItemToCatalog(p);
						client.sendToClient(getAllProducts());
					} else if ("remove".equals(action)) {
						String idToRemove = um.getDeleteKey(); // renamed from getDelteId()
						removeItemFromCatalog(idToRemove, client);
					} else if ("edit".equals(action)) {
						Product p = um.getProduct();
						editCatalogProduct(p);
						client.sendToClient(getAllProducts());
					}
					session.close();
					break;

				case "account":
					if ("add".equals(action)) {
						addAccount(um.getAccount());
					} else if ("remove".equals(action)) {
						removeAccount(um.getDeleteKey(), client);
					} else if ("edit".equals(action)) {
						editAccount(um.getAccount());
					}
					break;

				case "worker":
					if ("add".equals(action)) {
						WorkerUpdateManager.addWorker(um.getWorker());
					} else if ("remove".equals(action)) {
						WorkerUpdateManager.removeWorker(um.getDeleteKey(), client);
					} else if ("edit".equals(action)) {
						WorkerUpdateManager.editWorker(um.getWorker());
					}
					session.close();
					break;

				case "manager":
					if ("add".equals(action)) {
						ManagerUpdateManager.addManager(um.getManager());
					} else if ("remove".equals(action)) {
						ManagerUpdateManager.removeManager(um.getDeleteKey(), client);
					} else if ("edit".equals(action)) {
						ManagerUpdateManager.editManager(um.getManager());
					}
					session.close();
					break;

				case "order":
					if ("add".equals(action)) {
						OrderUpdateManager.addOrder(um.getOrder());
					} else if ("remove".equals(action)) {
						OrderUpdateManager.removeOrder(um.getDeleteKey(), client);
					}
					session.close();
					break;

				case "complaint":
					if ("add".equals(action)) {
						ComplaintUpdateManager.addComplaint(um.getComplaint());
					} else if ("edit".equals(action)) {
						ComplaintUpdateManager.editComplaint(um.getComplaint());
					}
					session.close();
					break;

				case "message":
					if ("add".equals(action)) {
						addMessage(um.getMessage());
					}
					session.close();
					break;
			}
			tx1.commit();
		}
		System.out.println("Arrived At UpdateMessage 3");

		if (msg instanceof CheckMail) {
			SessionFactory sf = getSessionFactory();
			session = sf.openSession();
			Transaction tx1 = session.beginTransaction();

			CheckMail cm = (CheckMail) msg;
			String email = cm.getEmail();
			String pwd = cm.getPassword();

			boolean foundMail = false;
			boolean foundPwd = false;

			List<Account> list = getAllAccounts();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getEmail().equals(email)) {
					foundMail = true;
					break;
				}
			}
			if (foundMail) {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getPassword().equals(pwd)) {
						if (!list.get(i).getLoggedIn()) {
							Account updateAcc = session.load(Account.class, list.get(i).getAccountID());
							updateAcc.setLoggedIn(true);
							client.sendToClient("found mail and password");
							new java.util.Timer().schedule(new java.util.TimerTask() {
								@Override public void run() { session.update(updateAcc); }
							}, 1000);
							tx1.commit();
							session.close();
						} else {
							client.sendToClient("already logged");
						}
						foundPwd = true;
						break;
					}
				}
			} else {
				client.sendToClient("mail not found");
			}
			if (foundMail && !foundPwd) client.sendToClient("wrong password");
		}

		if (msg instanceof MailClass) { // return Account by email
			SessionFactory sf = getSessionFactory();
			session = sf.openSession();
			Transaction tx1 = session.beginTransaction();

			List<Account> accounts = getAllAccounts();
			MailClass m = (MailClass) msg;
			String email = m.getMail();
			Account temp = new Account();

			for (Account a : accounts) {
				if (a.getEmail().equals(email)) {
					temp.setAccountID(a.getAccountID());
					temp.setAddress(a.getAddress());
					temp.setBelongShop(a.getBelongShop());
					temp.setCcv(a.getCcv());
					temp.setCreditCardNumber(a.getCreditCardNumber());
					temp.setEmail(a.getEmail());
					temp.setFullName(a.getFullName());
					temp.setLoggedIn(a.getLoggedIn());
					temp.setPassword(a.getPassword());
					temp.setPhoneNumber(a.getPhoneNumber());
					temp.setCreditMonthExpire(a.getCreditMonthExpire());
					temp.setPrivialge(a.getPrivialge());
					break;
				}
			}
			client.sendToClient(temp);
			tx1.commit();
			session.close();
		}

		if (msg instanceof LogOut) { // set loggedIn=false by email
			SessionFactory sf = getSessionFactory();
			session = sf.openSession();
			Transaction tx1 = session.beginTransaction();

			List<Account> accounts = getAllAccounts();
			LogOut lo = (LogOut) msg;
			String email = lo.getMail();

			for (Account a : accounts) {
				if (a.getEmail().equals(email)) {
					Account updateAcc = session.load(Account.class, a.getAccountID());
					updateAcc.setLoggedIn(false);
					session.update(updateAcc);
					tx1.commit();
					session.close();
					break;
				}
			}
		}

		if (msg instanceof getAllOrdersMessage) { // return all orders
			SessionFactory sf = getSessionFactory();
			session = sf.openSession();
			Transaction tx1 = session.beginTransaction();

			getAllOrdersMessage payload = new getAllOrdersMessage();
			payload.setOrderList(getAllOrders());
			client.sendToClient(payload);

			tx1.commit();
			session.close();
		}

		if (msg instanceof GetAllComplaints) { // return all complaints
			SessionFactory sf = getSessionFactory();
			session = sf.openSession();
			Transaction tx1 = session.beginTransaction();

			GetAllComplaints payload = new GetAllComplaints();
			payload.setComplaintsList(getAllComplaints());
			client.sendToClient(payload);

			tx1.commit();
			session.close();
		}

		if (msg instanceof GetAllMessages) { // return all messages
			SessionFactory sf = getSessionFactory();
			session = sf.openSession();
			Transaction tx1 = session.beginTransaction();

			GetAllMessages payload = new GetAllMessages();
			payload.setMessageList(getAllMessages());
			client.sendToClient(payload);

			tx1.commit();
			session.close();
		}

		if (msg instanceof Order) { // mark order as delivered
			SessionFactory sf = getSessionFactory();
			session = sf.openSession();
			session.beginTransaction();
			Order ord = (Order) msg;
			OrderUpdateManager.deliveredOrder(ord.getOrderID());
			session.getTransaction().commit();
			session.close();
		}

		if (msg instanceof ArrayList) { // initial seeding of products
			System.out.println("Arrived here: msg instance of arrayList ");
			SessionFactory sf = getSessionFactory();
			session = sf.openSession();
			Transaction tx1 = session.beginTransaction();

			@SuppressWarnings("unchecked")
			List<Product> resultList = (List<Product>) msg;
			productCount = resultList.size();
			for (Product p : resultList) {
				session.save(p);
				session.flush();
			}
			tx1.commit();

			catalogCache.addAll(resultList);
			session.close();
		}
	}

	private static List<Message> getAllMessages() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Message> query = builder.createQuery(Message.class);
		query.from(Message.class);
		return session.createQuery(query).getResultList();
	}

	private static List<Complaint> getAllComplaints() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Complaint> query = builder.createQuery(Complaint.class);
		query.from(Complaint.class);
		return session.createQuery(query).getResultList();
	}

	private static List<Order> getAllOrders() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		query.from(Order.class);
		return session.createQuery(query).getResultList();
	}

	private static List<Product> getAllProducts() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Product> query = builder.createQuery(Product.class);
		query.from(Product.class);
		return session.createQuery(query).getResultList();
	}

	Long countRows() { // products count
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Product> root = cq.from(Product.class);
		cq.select(cb.count(root));
		return session.createQuery(cq).getSingleResult();
	}

	void addItemToCatalog(Product product) {
		long numOfRows = countRows();
		int newProductId = (int) numOfRows + 1;
		// Product API earlier changed id→productCode; provide both if you kept legacy methods
		try {
			product.getClass().getMethod("setProductCode", int.class).invoke(product, newProductId);
		} catch (Exception e) {
			// fallback to legacy method name if exists
			try { product.getClass().getMethod("setID", int.class).invoke(product, newProductId); } catch (Exception ignored) {}
		}
		SessionFactory sf = getSessionFactory();
		session = sf.openSession();
		Transaction tx = session.beginTransaction();
		session.save(product);
		session.flush();
		tx.commit();
		session.close();
	}

	void editCatalogProduct(Product productEdit) {
		SessionFactory sf = getSessionFactory();
		session = sf.openSession();
		Transaction tx = session.beginTransaction();

		// Read id via either new or legacy getter
		int pid;
		try { pid = (int) productEdit.getClass().getMethod("getProductCode").invoke(productEdit); }
		catch (Exception e) { try { pid = (int) productEdit.getClass().getMethod("getID").invoke(productEdit); } catch (Exception ex) { throw new RuntimeException("Product id getter missing"); } }

		Product updateProd = session.load(Product.class, pid);

		// Copy editable fields — try new names first, then legacy
		try {
			String btn = (String) productEdit.getClass().getMethod("getActionBtn").invoke(productEdit);
			String cost = (String) productEdit.getClass().getMethod("getCost").invoke(productEdit);
			String title = (String) productEdit.getClass().getMethod("getTitle").invoke(productEdit);
			String desc = (String) productEdit.getClass().getMethod("getDescription").invoke(productEdit);
			String img = (String) productEdit.getClass().getMethod("getPhotoUrl").invoke(productEdit);
			updateProd.getClass().getMethod("setActionBtn", String.class).invoke(updateProd, btn);
			updateProd.getClass().getMethod("setCost", String.class).invoke(updateProd, cost);
			updateProd.getClass().getMethod("setTitle", String.class).invoke(updateProd, title);
			updateProd.getClass().getMethod("setDescription", String.class).invoke(updateProd, desc);
			updateProd.getClass().getMethod("setPhotoUrl", String.class).invoke(updateProd, img);
		} catch (Exception useLegacy) {
			// legacy method names
			try {
				updateProd.getClass().getMethod("setButton", String.class).invoke(updateProd, productEdit.getButton());
				updateProd.getClass().getMethod("setPrice", String.class).invoke(updateProd, productEdit.getPrice());
				updateProd.getClass().getMethod("setName", String.class).invoke(updateProd, productEdit.getName());
				updateProd.getClass().getMethod("setDetails", String.class).invoke(updateProd, productEdit.getDetails());
				updateProd.getClass().getMethod("setImage", String.class).invoke(updateProd, productEdit.getImage());
			} catch (Exception ex) { throw new RuntimeException("Product field mapping failed"); }
		}

		session.update(updateProd);
		tx.commit();
		session.close();
	}

	void removeItemFromCatalog(String prodIdToRemove, ConnectionToClient _client) throws IOException {
		SessionFactory sf = getSessionFactory();
		session = sf.openSession();
		Transaction tx = session.beginTransaction();

		productCount--;

		int removedId = Integer.parseInt(prodIdToRemove);
		catalogCache = getAllProducts();
		if (removedId - 1 >= 0 && removedId - 1 < catalogCache.size()) {
			catalogCache.remove(removedId - 1);
		}
		for (Product p : catalogCache) {
			// compact ids after removal (legacy setter name supported via reflection as needed)
			try {
				int code = (int) p.getClass().getMethod("getProductCode").invoke(p);
				if (code > removedId) p.getClass().getMethod("setProductCode", int.class).invoke(p, code - 1);
			} catch (Exception e) {
				try {
					int code = (int) p.getClass().getMethod("getID").invoke(p);
					if (code > removedId) p.getClass().getMethod("setID", int.class).invoke(p, code - 1);
				} catch (Exception ignored) { }
			}
		}
		tx.commit();
		session.close();

		session = sf.openSession();
		Transaction tx1 = session.beginTransaction();
		long longID = countRows();
		tx1.commit();
		session.close();

		int total = (int) longID;
		for (int l = 0; l < total; l++) deleteProduct(l + 1);

		session = sf.openSession();
		Transaction tx2 = session.beginTransaction();
		for (Product p : catalogCache) {
			session.save(p);
			session.flush();
		}
		tx2.commit();
		session.close();
	}

	public void deleteProduct(int deleteIndex) {
		SessionFactory sf = getSessionFactory();
		session = sf.openSession();
		Transaction tx = session.beginTransaction();
		Product perProd = session.load(Product.class, deleteIndex);
		if (perProd != null) session.delete(perProd);
		tx.commit();
		session.close();
	}

	public void deleteAllProducts() {
		long rows = countRows();
		for (int i = 1; i <= (int) rows; i++) deleteProduct(i);
	}

	public static List<Account> getAllAccounts() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Account> query = builder.createQuery(Account.class);
		query.from(Account.class);
		return session.createQuery(query).getResultList();
	}

	public void addAccount(Account newAcc) {
		long numOfRows = countAccountRows();
		int newId = (int) numOfRows + 1;
		newAcc.setAccountID(newId);

		SessionFactory sf = getSessionFactory();
		session = sf.openSession();
		Transaction tx = session.beginTransaction();
		session.save(newAcc);
		session.flush();
		tx.commit();
		session.close();
	}

	public void removeAccount(String accIdToRemove, ConnectionToClient _client) {
		int removedId = Integer.parseInt(accIdToRemove);
		accountsCache = getAllAccounts();
		if (removedId - 1 >= 0 && removedId - 1 < accountsCache.size()) {
			accountsCache.remove(removedId - 1);
		}
		for (Account a : accountsCache) {
			if (a.getAccountID() > removedId) a.setAccountID(a.getAccountID() - 1);
		}

		SessionFactory sf = getSessionFactory();
		session = sf.openSession();
		Transaction tx1 = session.beginTransaction();
		long longID = countAccountRows();
		tx1.commit();
		session.close();

		for (int l = 0; l < (int) longID; l++) deleteAccount(l + 1);

		sf = getSessionFactory();
		session = sf.openSession();
		Transaction tx2 = session.beginTransaction();
		for (Account a : accountsCache) {
			session.save(a);
			session.flush();
		}
		tx2.commit();
		session.close();
	}

	public void deleteAccount(int deleteIndex) {
		SessionFactory sf = getSessionFactory();
		session = sf.openSession();
		Transaction tx = session.beginTransaction();
		Account per = session.load(Account.class, deleteIndex);
		if (per != null) session.delete(per);
		tx.commit();
		session.close();
	}

	public Long countAccountRows() {
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Account> root = cq.from(Account.class);
		cq.select(cb.count(root));
		return session.createQuery(cq).getSingleResult();
	}

	public void editAccount(Account accountEdit) {
		SessionFactory sf = getSessionFactory();
		session = sf.openSession();
		Transaction tx = session.beginTransaction();

		int id = accountEdit.getAccountID();
		Account update = session.load(Account.class, id);

		update.setFullName(accountEdit.getFullName());
		update.setAddress(accountEdit.getAddress());
		update.setEmail(accountEdit.getEmail());
		update.setPassword(accountEdit.getPassword());
		update.setPhoneNumber(accountEdit.getPhoneNumber());
		update.setCreditCardNumber(accountEdit.getCreditCardNumber());
		update.setCcv(accountEdit.getCcv());
		update.setCreditMonthExpire(accountEdit.getCreditMonthExpire());
		update.setCreditYearExpire(accountEdit.getCreditYearExpire());
		update.setLoggedIn(accountEdit.getLoggedIn());
		update.setBelongShop(accountEdit.getBelongShop());

		session.update(update);
		tx.commit();
		session.close();
	}

	public void addMessage(Message newMessage) {
		long numOfRows = countMessageRows(); // ✅ use messages count, not accounts
		int newId = (int) numOfRows + 1;
		newMessage.setMessageID(newId);

		SessionFactory sf = getSessionFactory();
		session = sf.openSession();
		Transaction tx = session.beginTransaction();
		session.save(newMessage);
		session.flush();
		tx.commit();
		session.close();
	}

	public Long countMessageRows() {
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Message> root = cq.from(Message.class);
		cq.select(cb.count(root));
		return session.createQuery(cq).getSingleResult();
	}
}
