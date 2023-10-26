package database;

public class AudTransSummaryRow {
    private String date;
    private String newTransTotal;
    private String completedTransTotal;
    private String exceptionTransTotal;
    private String newComplaints;

    // Implement getters and setters for the fields
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNewTransTotal() {
        return newTransTotal;
    }

    public void setNewTransTotal(String newTransTotal) {
        this.newTransTotal = newTransTotal;
    }

    public String getCompletedTransTotal() {
        return completedTransTotal;
    }

    public void setCompletedTransTotal(String completedTransTotal) {
        this.completedTransTotal = completedTransTotal;
    }

    public String getExceptionTransTotal() {
        return exceptionTransTotal;
    }

    public void setExceptionTransTotal(String exceptionTransTotal) {
        this.exceptionTransTotal = exceptionTransTotal;
    }

    public String getNewComplaints() {
        return newComplaints;
    }

    public void setNewComplaints(String newComplaints) {
        this.newComplaints = newComplaints;
    }
}
