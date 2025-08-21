package il.cshaifasweng.OCSFMediatorExample.server.ocsf;

import il.cshaifasweng.OCSFMediatorExample.server.SimpleServer;
import il.cshaifasweng.OCSFMediatorExample.entities.Manager;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ManagerUpdateManager {

    public static int managersnum = 0; // إبقاء الاسم للتوافق
    public static List<Manager> managerGeneralList = new ArrayList<>(); // مستخدم من الخارج

    public ManagerUpdateManager() {
        // حضّر الكاش من الداتابيز
        managerGeneralList = getAllManagers();
    }

    // ===== Helpers (قراءة) =====
    private static List<Manager> getAllManagers() {
        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            SimpleServer.session = sf.openSession();
            CriteriaBuilder builder = SimpleServer.session.getCriteriaBuilder();
            CriteriaQuery<Manager> query = builder.createQuery(Manager.class);
            query.from(Manager.class);
            return SimpleServer.session.createQuery(query).getResultList();
        } finally {
            if (SimpleServer.session != null && SimpleServer.session.isOpen()) {
                SimpleServer.session.close();
            }
        }
    }

    static Long countRowsManager() {
        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            SimpleServer.session = sf.openSession();
            CriteriaBuilder cb = SimpleServer.session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Manager> root = cq.from(Manager.class);
            cq.select(cb.count(root));
            return SimpleServer.session.createQuery(cq).getSingleResult();
        } finally {
            if (SimpleServer.session != null && SimpleServer.session.isOpen()) {
                SimpleServer.session.close();
            }
        }
    }

    // ===== Mutations (مستخدمة من السيرفر) =====
    public static void addManager(Manager recievedManager) {
        // احسب ID جديد = عدد الصفوف + 1
        long rows = countRowsManager();
        int newId = (int) rows + 1;
        recievedManager.setPersonID(newId);

        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            SimpleServer.session = sf.openSession();
            Transaction tx = SimpleServer.session.beginTransaction();

            SimpleServer.session.save(recievedManager);
            SimpleServer.session.flush();

            tx.commit();
        } finally {
            if (SimpleServer.session != null && SimpleServer.session.isOpen()) {
                SimpleServer.session.close();
            }
        }
    }

    public static void removeManager(String managerIdToRemove, ConnectionToClient _client) {
        // حمّل القائمة الحالية من الداتابيز
        List<Manager> current = getAllManagers();

        int removedId = Integer.parseInt(managerIdToRemove);
        if (removedId - 1 >= 0 && removedId - 1 < current.size()) {
            current.remove(removedId - 1);
        }

        // ضغط الـ IDs المتبقية
        for (Manager m : current) {
            if (m.getPersonID() > removedId) {
                m.setPersonID(m.getPersonID() - 1);
            }
        }

        // امسح الكل وأعد الإدخال حسب القائمة المحدثة
        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            // 1) احذف الموجود
            Long totalBefore = countRowsManager();
            for (int i = 1; i <= totalBefore; i++) {
                deleteManager(i);
            }

            // 2) أعد الحفظ
            SimpleServer.session = sf.openSession();
            Transaction tx2 = SimpleServer.session.beginTransaction();
            for (Manager m : current) {
                SimpleServer.session.save(m);
                SimpleServer.session.flush();
            }
            tx2.commit();

            // 3) حدّث الكاش العام
            managerGeneralList = new ArrayList<>(current);
        } finally {
            if (SimpleServer.session != null && SimpleServer.session.isOpen()) {
                SimpleServer.session.close();
            }
        }
    }

    public static void deleteManager(int deleteIndex) {
        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            SimpleServer.session = sf.openSession();
            Transaction tx = SimpleServer.session.beginTransaction();

            Manager persistent = SimpleServer.session.load(Manager.class, deleteIndex);
            if (persistent != null) {
                SimpleServer.session.delete(persistent);
            }

            tx.commit();
        } finally {
            if (SimpleServer.session != null && SimpleServer.session.isOpen()) {
                SimpleServer.session.close();
            }
        }
    }

    public static void editManager(Manager managerEdit) {
        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            SimpleServer.session = sf.openSession();
            Transaction tx = SimpleServer.session.beginTransaction();

            int id = managerEdit.getPersonID();
            Manager update = SimpleServer.session.load(Manager.class, id);

            update.setPersonID(managerEdit.getPersonID());
            update.setFullName(managerEdit.getFullName());
            update.setEmail(managerEdit.getEmail());
            update.setPassword(managerEdit.getPassword());
            update.setLoggedIn(managerEdit.isLoggedIn());

            SimpleServer.session.update(update);
            tx.commit();
        } finally {
            if (SimpleServer.session != null && SimpleServer.session.isOpen()) {
                SimpleServer.session.close();
            }
        }
    }
}
