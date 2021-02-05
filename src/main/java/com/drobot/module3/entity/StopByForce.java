package com.drobot.module3.entity;

import java.time.OffsetDateTime;

public class StopByForce extends Entity {

    private String ageRage;
    private String selfDefinedEthnicity;
    private Boolean outcomeObjectLinkedToObjectOfSearch;
    private OffsetDateTime dateTime;
    private Boolean removalOfMoreThanOuterClothing;
    private Boolean operation;
    private String officerDefinedEthnicity;
    private String objectOfSearch;
    private boolean involvedPerson;
    private String gender;
    private String legislation;
    private Location location;
    private StopType type;
    private String operationName;
    private OutcomeObject outcomeObject;

    public StopByForce() {
    }

    public Builder builder() {
        return new Builder(this);
    }

    public String getAgeRage() {
        return ageRage;
    }

    public void setAgeRage(String ageRage) {
        this.ageRage = ageRage;
    }

    public String getSelfDefinedEthnicity() {
        return selfDefinedEthnicity;
    }

    public void setSelfDefinedEthnicity(String selfDefinedEthnicity) {
        this.selfDefinedEthnicity = selfDefinedEthnicity;
    }

    public Boolean getOutcomeObjectLinkedToObjectOfSearch() {
        return outcomeObjectLinkedToObjectOfSearch;
    }

    public void setOutcomeObjectLinkedToObjectOfSearch(Boolean outcomeObjectLinkedToObjectOfSearch) {
        this.outcomeObjectLinkedToObjectOfSearch = outcomeObjectLinkedToObjectOfSearch;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Boolean getRemovalOfMoreThanOuterClothing() {
        return removalOfMoreThanOuterClothing;
    }

    public void setRemovalOfMoreThanOuterClothing(Boolean removalOfMoreThanOuterClothing) {
        this.removalOfMoreThanOuterClothing = removalOfMoreThanOuterClothing;
    }

    public Boolean getOperation() {
        return operation;
    }

    public void setOperation(Boolean operation) {
        this.operation = operation;
    }

    public String getOfficerDefinedEthnicity() {
        return officerDefinedEthnicity;
    }

    public void setOfficerDefinedEthnicity(String officerDefinedEthnicity) {
        this.officerDefinedEthnicity = officerDefinedEthnicity;
    }

    public String getObjectOfSearch() {
        return objectOfSearch;
    }

    public void setObjectOfSearch(String objectOfSearch) {
        this.objectOfSearch = objectOfSearch;
    }

    public boolean getInvolvedPerson() {
        return involvedPerson;
    }

    public void setInvolvedPerson(boolean involvedPerson) {
        this.involvedPerson = involvedPerson;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLegislation() {
        return legislation;
    }

    public void setLegislation(String legislation) {
        this.legislation = legislation;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public StopType getType() {
        return type;
    }

    public void setType(StopType type) {
        this.type = type;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public OutcomeObject getOutcomeObject() {
        return outcomeObject;
    }

    public void setOutcomeObject(OutcomeObject outcomeObject) {
        this.outcomeObject = outcomeObject;
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
        StopByForce that = (StopByForce) o;
        if (ageRage != null ? !ageRage.equals(that.ageRage) : that.ageRage != null) {
            return false;
        }
        if (selfDefinedEthnicity != null
                ? !selfDefinedEthnicity.equals(that.selfDefinedEthnicity) : that.selfDefinedEthnicity != null) {
            return false;
        }
        if (outcomeObjectLinkedToObjectOfSearch != null
                ? !outcomeObjectLinkedToObjectOfSearch.equals(that.outcomeObjectLinkedToObjectOfSearch)
                : that.outcomeObjectLinkedToObjectOfSearch != null) {
            return false;
        }
        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) {
            return false;
        }
        if (removalOfMoreThanOuterClothing != null
                ? !removalOfMoreThanOuterClothing.equals(that.removalOfMoreThanOuterClothing)
                : that.removalOfMoreThanOuterClothing != null) {
            return false;
        }
        if (operation != null ? !operation.equals(that.operation) : that.operation != null) {
            return false;
        }
        if (officerDefinedEthnicity != null
                ? !officerDefinedEthnicity.equals(that.officerDefinedEthnicity)
                : that.officerDefinedEthnicity != null) {
            return false;
        }
        if (objectOfSearch != null ? !objectOfSearch.equals(that.objectOfSearch) : that.objectOfSearch != null) {
            return false;
        }
        if (involvedPerson != that.involvedPerson) {
            return false;
        }
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) {
            return false;
        }
        if (legislation != null ? !legislation.equals(that.legislation) : that.legislation != null) {
            return false;
        }
        if (location != null ? !location.equals(that.location) : that.location != null) {
            return false;
        }
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        if (operationName != null ? !operationName.equals(that.operationName) : that.operationName != null) {
            return false;
        }
        return outcomeObject != null ? outcomeObject.equals(that.outcomeObject) : that.outcomeObject == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (ageRage != null ? ageRage.hashCode() : 0);
        result = 31 * result + (selfDefinedEthnicity != null ? selfDefinedEthnicity.hashCode() : 0);
        result = 31 * result + (outcomeObjectLinkedToObjectOfSearch != null ? outcomeObjectLinkedToObjectOfSearch.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        result = 31 * result + (removalOfMoreThanOuterClothing != null ? removalOfMoreThanOuterClothing.hashCode() : 0);
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        result = 31 * result + (officerDefinedEthnicity != null ? officerDefinedEthnicity.hashCode() : 0);
        result = 31 * result + (objectOfSearch != null ? objectOfSearch.hashCode() : 0);
        result = 31 * result + (involvedPerson ? 1 : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (legislation != null ? legislation.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (operationName != null ? operationName.hashCode() : 0);
        result = 31 * result + (outcomeObject != null ? outcomeObject.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StopByForce{");
        sb.append("id=").append(getId());
        sb.append(", ageRage='").append(ageRage).append('\'');
        sb.append(", selfDefinedEthnicity='").append(selfDefinedEthnicity).append('\'');
        sb.append(", outcomeObjectLinkedToObjectOfSearch=").append(outcomeObjectLinkedToObjectOfSearch);
        sb.append(", dateTime=").append(dateTime);
        sb.append(", removalOfMoreThanOuterClothing=").append(removalOfMoreThanOuterClothing);
        sb.append(", operation=").append(operation);
        sb.append(", officerDefinedEthnicity='").append(officerDefinedEthnicity).append('\'');
        sb.append(", objectOfSearch='").append(objectOfSearch).append('\'');
        sb.append(", involvedPerson=").append(involvedPerson);
        sb.append(", gender='").append(gender).append('\'');
        sb.append(", legislation='").append(legislation).append('\'');
        sb.append(", location=").append(location);
        sb.append(", type=").append(type);
        sb.append(", operationName='").append(operationName).append('\'');
        sb.append(", outcomeObject=").append(outcomeObject);
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {

        private StopByForce stopByForce;

        private Builder(StopByForce stopByForce) {
            this.stopByForce = stopByForce;
        }

        public Builder buildId(long id) {
            stopByForce.setId(id);
            return this;
        }

        public Builder buildAgeRange(String ageRange) {
            stopByForce.ageRage = ageRange;
            return this;
        }

        public Builder buildSelfDefinedEthnicity(String selfDefinedEthnicity) {
            stopByForce.selfDefinedEthnicity = selfDefinedEthnicity;
            return this;
        }

        public Builder buildOutcomeObjectLinkedToObjectOfSearch(Boolean outcomeObjectLinkedToObjectOfSearch) {
            stopByForce.outcomeObjectLinkedToObjectOfSearch = outcomeObjectLinkedToObjectOfSearch;
            return this;
        }

        public Builder buildDateTime(OffsetDateTime dateTime) {
            stopByForce.dateTime = dateTime;
            return this;
        }

        public Builder buildRemovalOfMoreThanOuterClothing(Boolean removalOfMoreThanOuterClothing) {
            stopByForce.removalOfMoreThanOuterClothing = removalOfMoreThanOuterClothing;
            return this;
        }

        public Builder buildOperation(Boolean operation) {
            stopByForce.operation = operation;
            return this;
        }

        public Builder buildOfficerDefinedEthnicity(String officerDefinedEthnicity) {
            stopByForce.officerDefinedEthnicity = officerDefinedEthnicity;
            return this;
        }

        public Builder buildObjectOfSearch(String objectOfSearch) {
            stopByForce.objectOfSearch = objectOfSearch;
            return this;
        }

        public Builder buildInvolvedPerson(Boolean involvedPerson) {
            stopByForce.involvedPerson = involvedPerson;
            return this;
        }

        public Builder buildGender(String gender) {
            stopByForce.gender = gender;
            return this;
        }

        public Builder buildLegislation(String legislation) {
            stopByForce.legislation = legislation;
            return this;
        }

        public Builder buildLocation(Location location) {
            stopByForce.location = location;
            return this;
        }

        public Builder buildType(StopType stopType) {
            stopByForce.type = stopType;
            return this;
        }

        public Builder buildOperationName(String operationName) {
            stopByForce.operationName = operationName;
            return this;
        }

        public Builder buildOutcomeObject(OutcomeObject outcomeObject) {
            stopByForce.outcomeObject = outcomeObject;
            return this;
        }

        public StopByForce build() {
            return stopByForce;
        }
    }
}
