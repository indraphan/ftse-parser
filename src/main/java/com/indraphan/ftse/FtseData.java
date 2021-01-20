package com.indraphan.ftse;

public class FtseData {
    private String name;
    private Integer noOfCons;
    private Long netCapitalInMillion;
    private Double weightPercentage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNoOfCons() {
        return noOfCons;
    }

    public void setNoOfCons(Integer noOfCons) {
        this.noOfCons = noOfCons;
    }

    public Long getNetCapitalInMillion() {
        return netCapitalInMillion;
    }

    public void setNetCapitalInMillion(Long netCapitalInMillion) {
        this.netCapitalInMillion = netCapitalInMillion;
    }

    public Double getWeightPercentage() {
        return weightPercentage;
    }

    public void setWeightPercentage(Double weightPercentage) {
        this.weightPercentage = weightPercentage;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", noOfCons=" + noOfCons +
                ", netCapitalInMillion=" + netCapitalInMillion +
                ", weightPercentage=" + weightPercentage +
                '}';
    }
}
