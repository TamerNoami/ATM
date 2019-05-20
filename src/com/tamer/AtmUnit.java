package com.tamer;

public class AtmUnit implements java.io.Serializable {
    private int atmId;
    private String location;
    private int atmBalnce;
    private boolean status;

    public AtmUnit(int atmId, String location, int atmBalnce, boolean status) {
        this.atmId = atmId;
        this.location = location;
        this.atmBalnce = atmBalnce;
        this.status = status;
    }

    public int getAtmId() {
        return atmId;
    }

    public void setAtmId(int atmId) {
        this.atmId = atmId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAtmBalnce() {
        return atmBalnce;
    }

    public void setAtmBalnce(int atmBalnce) {
        this.atmBalnce = atmBalnce;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AtmUnit{" +
                "atmId=" + atmId +
                ", location='" + location + '\'' +
                ", atmBalnce=" + atmBalnce +
                ", status=" + status +
                '}';
    }
}
