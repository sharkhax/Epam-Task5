package com.drobot.module3.entity;

public class CrimeCategory extends Entity {

    private String url;
    private String name;

    public CrimeCategory() {
    }

    public CrimeCategory(long id, String url, String name) {
        super(id);
        this.url = url;
        this.name = name;
    }

    public CrimeCategory(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public CrimeCategory(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        CrimeCategory that = (CrimeCategory) o;
        if (url != null ? !url.equals(that.url) : that.url != null) {
            return false;
        }
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CrimeCategory{");
        sb.append("id=").append(getId());
        sb.append(", url='").append(url).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
