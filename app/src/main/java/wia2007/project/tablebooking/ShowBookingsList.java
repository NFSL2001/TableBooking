package wia2007.project.tablebooking;

import java.sql.Time;

public class ShowBookingsList {

    private Integer booking_id;
    private Time start_time;
    private Time end_time;
    private String Remark;
    private String CustName;
    private String Email;
    private String Mobile_number;
    private String TableName;
    private String status;

    public ShowBookingsList(Integer booking_id, Time start_time, Time end_time, String TableName, String CustName, String Remark, String Email, String Mobile_number,String status) {
        this.setRemark(Remark);
        this.setEmail(Email);
        this.setMobile_number(Mobile_number);
        this.setBooking_id(booking_id);
        this.setStart_time(start_time);
        this.setEnd_time(end_time);
        this.setTableName(TableName);
        this.setCustName(CustName);
        this.setStatus(status);
    }

    public Integer getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(Integer booking_id) {
        this.booking_id = booking_id;
    }

    public Time getStart_time() {
        return start_time;
    }

    public void setStart_time(Time start_time) {
        this.start_time = start_time;
    }

    public Time getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Time end_time) {
        this.end_time = end_time;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String CustName) {
        this.CustName = CustName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getMobile_number() {
        return Mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.Mobile_number = mobile_number;
    }

    public String getTable_name() {
        return TableName;
    }

    public void setTableName(String TableName) {
        this.TableName = TableName;
    }

    public String getTableName() {
        return TableName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
