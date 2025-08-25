package il.cshaifasweng.OCSFMediatorExample.server.ocsf;

import il.cshaifasweng.OCSFMediatorExample.server.SimpleServer;
import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ComplaintUpdateManager {

    // renamed for clarity; not part of any external API
    public static int complaintsCount = 0;                       // was complaintsnum
    public static List<Complaint> complaintCache = new ArrayList<>(); // was complaintGeneralList

    // --- Queries (package-private helpers) ---
    static List<Complaint> getAllComplaints() {
        CriteriaBuilder builder = SimpleServer.session.getCriteriaBuilder();
        CriteriaQuery<Complaint> query = builder.createQuery(Complaint.class);
        query.from(Complaint.class);
        return SimpleServer.session.createQuery(query).getResultList();
    }

    static Long countComplaintRows() { // was countRowsComplaint
        final CriteriaBuilder cb = SimpleServer.session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Complaint> root = cq.from(Complaint.class);
        cq.select(cb.count(root));
        return SimpleServer.session.createQuery(cq).getSingleResult();
    }

    // --- Mutations (public API used by SimpleServer) ---
    public static void addComplaint(Complaint received) {
        System.out.println("[Complaints] addComplaint()");

        // assign id = currentRowCount + 1
        long rows = countComplaintRows();
        int newId = (int) rows + 1;
        received.setComplaintId(newId);

        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            SimpleServer.session = sf.openSession();
            Transaction tx = SimpleServer.session.beginTransaction();

            SimpleServer.session.save(received);
            SimpleServer.session.flush();
            tx.commit();

        } finally {
            if (SimpleServer.session != null && SimpleServer.session.isOpen()) {
                SimpleServer.session.close();
            }
        }
    }

    public static void editComplaint(Complaint received) {
        System.out.println("[Complaints] editComplaint() id=" + received.getComplaintId());

        SessionFactory sf = SimpleServer.getSessionFactory();
        try {
            SimpleServer.session = sf.openSession();
            Transaction tx = SimpleServer.session.beginTransaction();

            int id = received.getComplaintId();
            Complaint toUpdate = SimpleServer.session.load(Complaint.class, id);

            // copy editable fields
            toUpdate.setCustomerId(received.getCustomerId());
            toUpdate.setAnswerWorkerId(received.getAnswerWorkerId());
            toUpdate.setReplyText(received.getReplyText());
            toUpdate.setRefundAmount(received.getRefundAmount());
            toUpdate.setRefunded(received.isRefunded());
            toUpdate.setResolved(received.isResolved());

            SimpleServer.session.update(toUpdate);
            tx.commit();

        } finally {
            if (SimpleServer.session != null && SimpleServer.session.isOpen()) {
                SimpleServer.session.close();
            }
        }
    }
}
