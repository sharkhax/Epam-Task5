package com.drobot.module3.entity;

public class Coordinate {

    private String latitude;
    private String longitude;

    public Coordinate(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
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
        Coordinate that = (Coordinate) o;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) {
            return false;
        }
        return longitude != null ? longitude.equals(that.longitude) : that.longitude == null;
    }

    @Override
    public int hashCode() {
        int result = latitude != null ? latitude.hashCode() : 0;
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Coordinate{");
        sb.append("latitude='").append(latitude).append('\'');
        sb.append(", longitude='").append(longitude).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
