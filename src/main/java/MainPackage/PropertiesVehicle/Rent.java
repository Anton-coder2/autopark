package MainPackage.PropertiesVehicle;

public class Rent {

    private int id;
    private String Date;
    private Double cost;

    public Rent(int id,String date, Double cost) {
        this.id = id;
        this.Date = date;
        this.cost = cost;
    }

    public Rent() {
    }

    public String getDate() {
        return Date;
    }

    public int getId() {
        return id;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return
                "Date= " + Date +   " cost= " + cost
                ;
    }
}
