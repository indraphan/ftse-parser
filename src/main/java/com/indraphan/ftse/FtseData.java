package com.indraphan.ftse;

import java.math.BigDecimal;

public class FtseData {
    private String name;
    private Integer noOfCons;
    private Long netCapitalInMillion;
    private BigDecimal weightPercentage;

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

    public BigDecimal getWeightPercentage() {
        return weightPercentage;
    }

    public void setWeightPercentage(BigDecimal weightPercentage) {
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
