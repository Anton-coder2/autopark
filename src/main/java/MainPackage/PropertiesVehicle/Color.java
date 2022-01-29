package MainPackage.PropertiesVehicle;

public enum Color {

    BLUE("Color.BLUE"),
    GREEN("Color.GREEN"),
    YELLOW("Color.YELLOW"),
    GRAY("Color.GRAY"),
    RED("Color.RED"),
    WHITE("Color.WHITE"),
    ORANGE("Color.ORANGE"),
    BLACK("Color.BLACK");


    private String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String toString(){
    return "Color." + name();
 }
}
