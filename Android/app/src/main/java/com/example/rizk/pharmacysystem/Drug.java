package com.example.rizk.pharmacysystem;

import java.io.Serializable;

public class Drug implements Serializable {
    private String drugID;
    private String drugName;
    private String classification;
    private String concentration;
    private String price;
    private String amount;
    private String type;

    public Drug(String drugID, String drugName, String classification, String concentration, String price, String amount, String type) {

        this.drugID = drugID;
        this.drugName = drugName;
        this.classification = classification;
        this.concentration = concentration;
        this.price = price;
        this.amount = amount;
        this.type = type;
    }

    public String getDrugID() {
        return drugID;
    }

    public void setDrugID(String drugID) {
        this.drugID = drugID;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
