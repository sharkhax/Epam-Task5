package com.drobot.module3.entity;

public class Location extends Entity {

    private float latitude;
    private Street street;
    private float longitude;

    public Location() {
    }

    public Location(long id, float latitude, Street street, float longitude) {
        super(id);
        this.latitude = latitude;
        this.street = street;
        this.longitude = longitude;
    }

    public Location(float latitude, Street street, float longitude) {
        this.latitude = latitude;
        this.street = street;
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Location location = (Location) o;
        if (Double.compare(location.latitude, latitude) != 0) {
            return false;
        }
        if (Double.compare(location.longitude, longitude) != 0) {
            return false;
        }
        return street != null ? street.equals(location.street) : location.street == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (street != null ? street.hashCode() : 0);
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Location{");
        sb.append("id=").append(getId());
        sb.append(", latitude=").append(latitude);
        sb.append(", street=").append(street);
        sb.append(", longitude=").append(longitude);
        sb.append('}');
        return sb.toString();
    }
}
