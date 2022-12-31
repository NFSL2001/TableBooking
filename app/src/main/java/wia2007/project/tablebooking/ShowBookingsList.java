package wia2007.project.tablebooking;

import java.sql.Time;

public class ShowBookingsList {

    private Integer booking_id;
    private Time startTime;
    private Time endTime;
    private String remark;
    private String custName;
    private String custEmail;
    private String custMobile;
    private String tableName;
    //    private String status;
    public static String SHOW_BOOKINGS_LIST =  "Show Bookings List";

    public ShowBookingsList(Integer booking_id, Time startTime,Time endTime, String tableName, String custName, String remark, String custEmail, String custMobile) {
        this.setRemark(remark);
        this.setCustEmail(custEmail);
        this.setCustMobile(custMobile);
        this.setBooking_id(booking_id);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setTableName(tableName);
        this.setCustName(custName);
//        this.setStatus(status);
    }

    public Integer getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(Integer booking_id) {
        this.booking_id = booking_id;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCustMobile() {
        return custMobile;
    }

    public void setCustMobile(String custMobile) {
        this.custMobile = custMobile;
    }

    public String getTable_name() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
