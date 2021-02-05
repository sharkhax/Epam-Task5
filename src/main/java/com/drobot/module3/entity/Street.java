package com.drobot.module3.entity;

public class Street extends Entity {

    private String name;

    public Street() {
    }

    public Street(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        Street street = (Street) o;
        return name != null ? name.equals(street.name) : street.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Street{");
        sb.append("id=").append(getId());
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
