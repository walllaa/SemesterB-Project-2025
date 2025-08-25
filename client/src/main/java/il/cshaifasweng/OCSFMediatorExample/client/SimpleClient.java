package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class SimpleClient extends AbstractClient {

	private static SimpleClient client = null;

	private static final long DEFAULT_POST_DELAY_MS = 4000L;

	private Item baseCatalog;
	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		System.out.println("handle start");

		// 1) Catalog items
		if (msg instanceof CatalogItems) {
			CatalogItems catalog = (CatalogItems) msg;
			EventBus.getDefault().post(new CatalogItemsEvent(catalog.getItems()));
			return;
		}

		// 2) Legacy lists (Product etc.)
		if (msg instanceof List) {
			@SuppressWarnings("unchecked")
			List<Product> products = (List<Product>) msg;
			for (int i = 0; i < products.size(); i++) {
				System.out.println(products.get(i).getName());
			}
			EventBus.getDefault().post(new UpdateGuiEvent(products));
			return;


			// 2) Simple textual status strings
		if (msg instanceof String) {
			handleStatusString((String) msg);
			return;
		}

		// 3) FoundTable payload
		if (msg instanceof FoundTable) {
			handleFoundTable((FoundTable) msg);
			return;
		}

		// 4) Account types
		if (msg instanceof Account) {
			handleAccount((Account) msg);
			return;
		}
		if (msg instanceof Manager) {
			handleAccount((Manager) msg);
			return;
		}
		if (msg instanceof Worker) {
			handleAccount((Worker) msg);
			return;
		}

		// 5) Orders / Complaints / Messages / Accounts
		if (msg instanceof getAllOrdersMessage) {
			handleOrders((getAllOrdersMessage) msg);
			return;
		}
		if (msg instanceof GetAllComplaints) {
			handleComplaints((GetAllComplaints) msg);
			return;
		}
		if (msg instanceof GetAllMessages) {
			handleMessages((GetAllMessages) msg);
			return;
		}
		if (msg instanceof GetAllAccounts) {
			handleAllAccounts((GetAllAccounts) msg);
		}
	}

	// ---------- Helpers ----------

	private void handleStatusString(String s) {
		// نفس المنطق تبعك بدون تغييرات بالـ strings
		if ("not found".equals(s)) {
			EventBus.getDefault().post(new InitDatabaseEvent(true));
			return;
		}

		if ("mail not found".equals(s)) {
			MailChecker ev = new MailChecker(false);
			EventBus.getDefault().post(ev);
			return;
		}

		if ("wrong password".equals(s)) {
			MailChecker ev = new MailChecker(true);
			ev.setPasswordExists(false);
			EventBus.getDefault().post(ev);
			return;
		}

		if ("found mail and password".equals(s)) {
			MailChecker ev = new MailChecker(true);
			ev.setPasswordExists(true);
			ev.setLoggedIn(false);
			EventBus.getDefault().post(ev);
			return;
		}

		if ("already logged".equals(s)) {
			MailChecker ev = new MailChecker(true);
			ev.setPasswordExists(true);
			ev.setLoggedIn(true);
			EventBus.getDefault().post(ev);
		}
	}

	private void handleFoundTable(FoundTable ft) {
		String m = ft.getMessage();
		if ("managers table found".equals(m) || "workers table found".equals(m)) {
			EventBus.getDefault().post(ft);
		} else {
			List<Product> list = ft.getRecievedProducts();
			EventBus.getDefault().post(new RetrieveDataBaseEvent(list));
		}
	}

	// overload لكل نوع حتى ما يصير compile error على Java 15
	private void handleAccount(Account acc) {
		PassAccountEvent ev = new PassAccountEvent(acc);
		postLater(ev, DEFAULT_POST_DELAY_MS);
	}

	private void handleAccount(Manager mgr) {
		PassAccountEvent ev = new PassAccountEvent(mgr);
		postLater(ev, DEFAULT_POST_DELAY_MS);
	}

	private void handleAccount(Worker wrk) {
		PassAccountEvent ev = new PassAccountEvent(wrk);
		postLater(ev, DEFAULT_POST_DELAY_MS);
	}

	private void handleOrders(getAllOrdersMessage m) {
		PassOrdersFromServer ev = new PassOrdersFromServer();
		ev.setRecievedOrders(m.getOrderList());
		postLater(ev, DEFAULT_POST_DELAY_MS);
	}

	private void handleComplaints(GetAllComplaints m) {
		PassAllComplaintsEvent ev = new PassAllComplaintsEvent();
		ev.setComplaintsToPass(m.getComplaintsList());
		EventBus.getDefault().post(ev);
	}

	private void handleMessages(GetAllMessages m) {
		passAllMessagesEvent ev = new passAllMessagesEvent();
		ev.setMessagesToPasssToPass(m.getMessageList());
		EventBus.getDefault().post(ev);
	}

	private void handleAllAccounts(GetAllAccounts m) {
		// نفس التأخير اللي كان عندك
		postLater(m.getAll_accounts(), DEFAULT_POST_DELAY_MS);
	}

	private void postLater(Object event, long delayMs) {
		new java.util.Timer().schedule(
				new java.util.TimerTask() {
					@Override
					public void run() {
						EventBus.getDefault().post(event);
					}
				},
				delayMs
		);
	}

	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}
}
