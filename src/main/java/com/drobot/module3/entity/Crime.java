package com.drobot.module3.entity;

public class Crime extends Entity {

    private CrimeCategory crimeCategory;
    private LocationType locationType;
    private Location location;
    private String context;
    private OutcomeStatus outcomeStatus;
    private String persistentId;
    private String locationSubtype;
    private String month;

    public Crime() {
    }

    public CrimeCategory getCrimeCategory() {
        return crimeCategory;
    }

    public void setCrimeCategory(CrimeCategory crimeCategory) {
        this.crimeCategory = crimeCategory;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public OutcomeStatus getOutcomeStatus() {
        return outcomeStatus;
    }

    public void setOutcomeStatus(OutcomeStatus outcomeStatus) {
        this.outcomeStatus = outcomeStatus;
    }

    public String getPersistentId() {
        return persistentId;
    }

    public void setPersistentId(String persistentId) {
        this.persistentId = persistentId;
    }

    public String getLocationSubtype() {
        return locationSubtype;
    }

    public void setLocationSubtype(String locationSubtype) {
        this.locationSubtype = locationSubtype;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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
        Crime crime = (Crime) o;
        if (month != null ? !month.equals(crime.month) : crime.month != null) {
            return false;
        }
        if (crimeCategory != null ? !crimeCategory.equals(crime.crimeCategory) : crime.crimeCategory != null) {
            return false;
        }
        if (locationType != null ? !locationType.equals(crime.locationType) : crime.locationType != null) {
            return false;
        }
        if (location != null ? !location.equals(crime.location) : crime.location != null) {
            return false;
        }
        if (context != null ? !context.equals(crime.context) : crime.context != null) {
            return false;
        }
        if (outcomeStatus != null ? !outcomeStatus.equals(crime.outcomeStatus) : crime.outcomeStatus != null) {
            return false;
        }
        if (persistentId != null ? !persistentId.equals(crime.persistentId) : crime.persistentId != null) {
            return false;
        }
        return locationSubtype != null
                ? locationSubtype.equals(crime.locationSubtype)
                : crime.locationSubtype == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (crimeCategory != null ? crimeCategory.hashCode() : 0);
        result = 31 * result + (locationType != null ? locationType.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (context != null ? context.hashCode() : 0);
        result = 31 * result + (outcomeStatus != null ? outcomeStatus.hashCode() : 0);
        result = 31 * result + (persistentId != null ? persistentId.hashCode() : 0);
        result = 31 * result + (locationSubtype != null ? locationSubtype.hashCode() : 0);
        result = 31 * result + (month != null ? month.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Crime{");
        sb.append("id=").append(getId());
        sb.append(", crimeCategory=").append(crimeCategory);
        sb.append(", locationType='").append(locationType).append('\'');
        sb.append(", location=").append(location);
        sb.append(", context='").append(context).append('\'');
        sb.append(", outcomeStatus=").append(outcomeStatus);
        sb.append(", persistent_id='").append(persistentId).append('\'');
        sb.append(", locationSubtype='").append(locationSubtype).append('\'');
        sb.append(", month=").append(month);
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {

        private final Crime crime;

        public Builder(Crime crime) {
            this.crime = crime;
        }

        public Crime build() {
            return crime;
        }

        public Builder buildId(long crimeId) {
            crime.setId(crimeId);
            return this;
        }

        public Builder buildCategory(CrimeCategory category) {
            crime.crimeCategory = category;
            return this;
        }

        public Builder buildLocationType(LocationType locationType) {
            crime.locationType = locationType;
            return this;
        }

        public Builder buildLocation(Location location) {
            crime.location = location;
            return this;
        }

        public Builder buildContext(String context) {
            crime.context = context;
            return this;
        }

        public Builder buildOutcomeStatus(OutcomeStatus outcomeStatus) {
            crime.outcomeStatus = outcomeStatus;
            return this;
        }

        public Builder buildPersistentId(String persistentId) {
            crime.persistentId = persistentId;
            return this;
        }

        public Builder buildLocationSubtype(String locationSubtype) {
            crime.locationSubtype = locationSubtype;
            return this;
        }

        public Builder buildMonth(String month) {
            crime.month = month;
            return this;
        }
    }
}
