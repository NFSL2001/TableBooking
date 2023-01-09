package wia2007.project.tablebooking;

public class ShowFoodOrderList {
    private Integer booking_id,quantity;
    private double totalcost;
    private String menuName;

    public ShowFoodOrderList(Integer booking_id, String menuName, Integer quantity, double totalcost){
        this.setBooking_id(booking_id);
        this.setMenuName(menuName);
        this.setQuantity(quantity);
        this.setTotalcost(totalcost);
    }

    public Integer getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(double totalcost) {
        this.totalcost = totalcost;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

}
