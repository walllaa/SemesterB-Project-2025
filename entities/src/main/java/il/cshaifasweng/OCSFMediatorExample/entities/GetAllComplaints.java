package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class GetAllComplaints implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Complaint> complaintsList;

    // ————————————————————
    // Constructors
    // ————————————————————

    public GetAllComplaints() { }

    public GetAllComplaints(List<Complaint> complaints) {
        this.complaintsList = complaints;
    }

    // ————————————————————
    // Getters & Setters
    // ————————————————————

    public List<Complaint> getComplaintsList() {
        return complaintsList;
    }

    public void setComplaintsList(List<Complaint> compList) {
        this.complaintsList = compList;
    }

    // ————————————————————
    // Utils
    // ————————————————————

    @Override
    public String toString() {
        return "GetAllComplaints{" +
                "complaintsList size=" + (complaintsList != null ? complaintsList.size() : 0) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetAllComplaints)) return false;
        GetAllComplaints that = (GetAllComplaints) o;
        return Objects.equals(complaintsList, that.complaintsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(complaintsList);
    }
}
