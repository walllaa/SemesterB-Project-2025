package il.cshaifasweng.OCSFMediatorExample.server.ocsf;

import il.cshaifasweng.OCSFMediatorExample.server.SimpleServer;
import il.cshaifasweng.OCSFMediatorExample.entities.Order;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class OrderUpdateManager {

    public static int ordersnum = 0; // إبقاء الاسم للتوافق مع باقي الأجزاء
    public static List<Order> orderGeneralList = new ArrayList<>();

    // ===== Read helpers =====
    private static List<Order> getAllOrders() {
        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            SimpleServer.session = sf.openSession();
            CriteriaBuilder builder = SimpleServer.session.getCriteriaBuilder();
            CriteriaQuery<Order> query = builder.createQuery(Order.class);
            query.from(Order.class);
            return SimpleServer.session.createQuery(query).getResultList();
        } finally {
            if (SimpleServer.session != null && SimpleServer.session.isOpen()) {
                SimpleServer.session.close();
            }
        }
    }

    static Long countRowsOrder() {
        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            SimpleServer.session = sf.openSession();
            CriteriaBuilder cb = SimpleServer.session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Order> root = cq.from(Order.class);
            cq.select(cb.count(root));
            return SimpleServer.session.createQuery(cq).getSingleResult();
        } finally {
            if (SimpleServer.session != null && SimpleServer.session.isOpen()) {
                SimpleServer.session.close();
            }
        }
    }

    // ===== Mutations =====
    public static void addOrder(Order recievedOrder) {
        // id جديد = عدد الصفوف + 1
        long rows = countRowsOrder();
        int newOrderId = (int) rows + 1;
        recievedOrder.setPurchaseId(newOrderId); // بدل setOrderID

        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            SimpleServer.session = sf.openSession();
            Transaction tx = SimpleServer.session.beginTransaction();

            SimpleServer.session.save(recievedOrder);
            SimpleServer.session.flush();
            tx.commit();
        } finally {
            if (SimpleServer.session != null && SimpleServer.session.isOpen()) {
                SimpleServer.session.close();
            }
        }
    }

    public static void removeOrder(String orderIdToRemove, ConnectionToClient _client) {
        // حمل القائمة الحالية
        List<Order> current = getAllOrders();

        int removedId = Integer.parseInt(orderIdToRemove);
        if (removedId - 1 >= 0 && removedId - 1 < current.size()) {
            current.remove(removedId - 1);
        }

        // اضغط الـ IDs المتبقية (باستخدام الـ API الجديد)
        for (Order o : current) {
            if (o.getPurchaseId() > removedId) {
                o.setPurchaseId(o.getPurchaseId() - 1);
            }
        }

        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            // 1) احذف كل الموجود
            Long totalBefore = countRowsOrder();
            for (int i = 1; i <= totalBefore; i++) {
                deleteOrder(i);
            }

            // 2) أعد الإدخال بالترتيب الجديد
            SimpleServer.session = sf.openSession();
            Transaction tx2 = SimpleServer.session.beginTransaction();
            for (Order o : current) {
                SimpleServer.session.save(o);
                SimpleServer.session.flush();
            }
            tx2.commit();

            // 3) حدّث الكاش
            orderGeneralList = new ArrayList<>(current);
        } finally {
            if (SimpleServer.session != null && SimpleServer.session.isOpen()) {
                SimpleServer.session.close();
            }
        }
    }

    public static void deleteOrder(int deleteIndex) {
        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            SimpleServer.session = sf.openSession();
            Transaction tx = SimpleServer.session.beginTransaction();

            Order perOrder = SimpleServer.session.load(Order.class, deleteIndex);
            if (perOrder != null) {
                SimpleServer.session.delete(perOrder);
            }

            tx.commit();
        } finally {
            if (SimpleServer.session != null && SimpleServer.session.isOpen()) {
                SimpleServer.session.close();
            }
        }
    }

    public static void deliveredOrder(int orderID) {
        // نحدّث العلم مباشرة عبر HQL لتفادي الحاجة لـ setDelivered()
        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            SimpleServer.session = sf.openSession();
            Transaction tx = SimpleServer.session.beginTransaction();

            SimpleServer.session.createQuery(
                    "update Order set isDelivered = true where purchaseId = :id"
            ).setParameter("id", orderID).executeUpdate();

            tx.commit();
        } finally {
            if (SimpleServer.session != null && SimpleServer.session.isOpen()) {
                SimpleServer.session.close();
            }
        }
    }
}
