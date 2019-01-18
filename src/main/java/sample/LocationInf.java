package sample;

public class LocationInf implements Comparable<LocationInf> {
    private City city;
    private double distance;

    public LocationInf(City city, double distance) {
        this.city = city;
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public int compareTo(LocationInf o) {
        return Double.compare(distance, o.distance);
    }
}
