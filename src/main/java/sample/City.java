package sample;

public class City {
    public double x;
    public double y;

    City(String x, String y) {
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
    }

    double getX() {
        return x;
    }

    public void setX(String x) {
        this.x = Double.parseDouble(x);
    }

    double getY() {
        return y;
    }

    public void setY(String y) {
        this.y = Double.parseDouble(y);
    }
}
