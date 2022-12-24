package wia2007.project.tablebooking;

public class Bookings {
    private int booking_id, customer_id,table_id;
    private String time, remarks;

    public Bookings(int booking_id, String time, String remarks, int table_id, int customer_id){
        this.setBooking_id(booking_id);
        this.setTime(time);
        this.setRemarks(remarks);
        this.setTable_id(table_id);
        this.setCustomer_id(customer_id);
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

}
