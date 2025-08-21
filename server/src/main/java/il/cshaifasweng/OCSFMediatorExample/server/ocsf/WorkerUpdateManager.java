package il.cshaifasweng.OCSFMediatorExample.server.ocsf;

import il.cshaifasweng.OCSFMediatorExample.entities.Worker;
import il.cshaifasweng.OCSFMediatorExample.server.SimpleServer;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class WorkerUpdateManager {

    public static int workersnum = 0; // إبقاء الاسم للتوافق مع بقية الكود
    public static List<Worker> workerGeneralList = new ArrayList<>();

    public WorkerUpdateManager() {
        workerGeneralList = getAllWorkers(); // تهيئة الكاش عند الإنشاء
    }

    // ===== Helpers (قراءة) =====
    private static List<Worker> getAllWorkers() {
        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            SimpleServer.session = sf.openSession();
            CriteriaBuilder builder = SimpleServer.session.getCriteriaBuilder();
            CriteriaQuery<Worker> query = builder.createQuery(Worker.class);
            query.from(Worker.class);
            return SimpleServer.session.createQuery(query).getResultList();
        } finally {
            // مهم: لا نترك جلسة مفتوحة
            if (SimpleServer.session != null && SimpleServer.session.isOpen()) {
                SimpleServer.session.close();
            }
        }
    }

    static Long countRowsWorker() {
        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            SimpleServer.session = sf.openSession();
            CriteriaBuilder cb = SimpleServer.session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Worker> root = cq.from(Worker.class);
            cq.select(cb.count(root));
            return SimpleServer.session.createQuery(cq).getSingleResult();
        } finally {
            if (SimpleServer.session != null && SimpleServer.session.isOpen()) {
                SimpleServer.session.close();
            }
        }
    }

    // ===== Mutations (مستخدمة من السيرفر) =====
    public static void addWorker(Worker recievedWorker) {
        // تعيين ID جديد = عدد الصفوف + 1
        long rows = countRowsWorker();
        int newId = (int) rows + 1;
        recievedWorker.setPersonID(newId);

        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            SimpleServer.session = sf.openSession();
            Transaction tx = SimpleServer.session.beginTransaction();

            SimpleServer.session.save(recievedWorker);
            SimpleServer.session.flush(); // دفع التغييرات داخل الترنزكشن
            tx.commit();
        } finally {
            if (SimpleServer.session != null && SimpleServer.session.isOpen()) {
                SimpleServer.session.close();
            }
        }
    }

    public static void removeWorker(String workerIdToRemove, ConnectionToClient _client) {
        // 1) حمّل القائمة الحالية
        List<Worker> current = getAllWorkers();

        // 2) احذف العنصر المطلوب (قائمة مرتبة حسب الـ id القديم)
        int removedId = Integer.parseInt(workerIdToRemove);
        if (removedId - 1 >= 0 && removedId - 1 < current.size()) {
            current.remove(removedId - 1);
        }

        // 3) اضغط IDs المتبقية (حتى تظل متسلسلة)
        for (Worker w : current) {
            if (w.getPersonID() > removedId) {
                w.setPersonID(w.getPersonID() - 1);
            }
        }

        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            // 4) احذف كل السجلات القديمة من الجدول
            Long totalBefore = countRowsWorker();
            for (int i = 1; i <= totalBefore; i++) {
                deleteWorker(i);
            }

            // 5) أعد الإدخال بالقيم المحدثة
            SimpleServer.session = sf.openSession();
            Transaction tx2 = SimpleServer.session.beginTransaction();
            for (Worker w : current) {
                SimpleServer.session.save(w);
                SimpleServer.session.flush();
            }
            tx2.commit();

            // 6) حدّث الكاش العام
            workerGeneralList = new ArrayList<>(current);

        } finally {
            if (SimpleServer.session != null && SimpleServer.session.isOpen()) {
                SimpleServer.session.close();
            }
        }
    }

    public static void deleteWorker(int deleteIndex) {
        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            SimpleServer.session = sf.openSession();
            Transaction tx = SimpleServer.session.beginTransaction();

            Worker persistent = SimpleServer.session.load(Worker.class, deleteIndex);
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

    public static void editWorker(Worker workerEdit) {
        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            SimpleServer.session = sf.openSession();
            Transaction tx = SimpleServer.session.beginTransaction();

            int id = workerEdit.getPersonID();
            Worker update = SimpleServer.session.load(Worker.class, id);

            // تحديث الحقول الأساسية (نفس أسماء الدوال الحالية في Worker)
            update.setPersonID(workerEdit.getPersonID());
            update.setFullName(workerEdit.getFullName());
            update.setEmail(workerEdit.getEmail());
            update.setPassword(workerEdit.getPassword());
            update.setLoggedIn(workerEdit.isLoggedIn());

            SimpleServer.session.update(update);
            tx.commit();
        } finally {
            if (SimpleServer.session != null && SimpleServer.session.isOpen()) {
                SimpleServer.session.close();
            }
        }
    }
}
