package com.drobot.module3.entity;

public class OutcomeStatus extends Entity {

    private String category;
    private String date;

    public OutcomeStatus() {
    }

    public OutcomeStatus(long id, String category, String date) {
        super(id);
        this.category = category;
        this.date = date;
    }

    public OutcomeStatus(String category, String date) {
        this.category = category;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
        OutcomeStatus that = (OutcomeStatus) o;
        if (date != null ? !date.equals(that.date) : that.date != null) {
            return false;
        }
        return category != null ? category.equals(that.category) : that.category == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OutcomeStatus{");
        sb.append("id=").append(getId());
        sb.append(", category='").append(category).append('\'');
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }
}
