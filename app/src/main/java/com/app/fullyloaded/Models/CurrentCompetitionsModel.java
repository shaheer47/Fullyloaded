package com.app.fullyloaded.Models;

public class CurrentCompetitionsModel {

    private String CurrentCompetitionID, CurrentCompetitionImage, CurrentCompetitionName, CurrentCompetitionPrice, CurrentCompetitionType;

    /*public CurrentCompetitionsModel(String CurrentCompetitionID, String CurrentCompetitionImage, String CurrentCompetitionName, String CurrentCompetitionPrice, String CurrentCompetitionType) {
        this.CurrentCompetitionID = CurrentCompetitionID;
        this.CurrentCompetitionImage = CurrentCompetitionImage;
        this.CurrentCompetitionName = CurrentCompetitionName;
        this.CurrentCompetitionPrice = CurrentCompetitionPrice;
        this.CurrentCompetitionType = CurrentCompetitionType;
    }*/

    public String getCurrentCompetitionID() {
        return CurrentCompetitionID;
    }

    public void setCurrentCompetitionID(String currentCompetitionID) {
        CurrentCompetitionID = currentCompetitionID;
    }

    public String getCurrentCompetitionImage() {
        return CurrentCompetitionImage;
    }

    public void setCurrentCompetitionImage(String currentCompetitionImage) {
        CurrentCompetitionImage = currentCompetitionImage;
    }

    public String getCurrentCompetitionName() {
        return CurrentCompetitionName;
    }

    public void setCurrentCompetitionName(String currentCompetitionName) {
        CurrentCompetitionName = currentCompetitionName;
    }

    public String getCurrentCompetitionPrice() {
        return CurrentCompetitionPrice;
    }

    public void setCurrentCompetitionPrice(String currentCompetitionPrice) {
        CurrentCompetitionPrice = currentCompetitionPrice;
    }

    public String getCurrentCompetitionType() {
        return CurrentCompetitionType;
    }

    public void setCurrentCompetitionType(String currentCompetitionType) {
        CurrentCompetitionType = currentCompetitionType;
    }
}
