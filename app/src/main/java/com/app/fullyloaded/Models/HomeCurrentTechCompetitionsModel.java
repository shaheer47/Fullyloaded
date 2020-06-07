package com.app.fullyloaded.Models;

public class HomeCurrentTechCompetitionsModel {

    private String CurrentTechCompetitionID, CurrentTechCompetitionImage, CurrentTechCompetitionName, CurrentTechCompetitionPrice, CurrentTechCompetitionSalePrice = "";

    /*public HomeCurrentTechCompetitionsModel(String CurrentTechCompetitionID, String CurrentTechCompetitionImage, String CurrentTechCompetitionName, String CurrentTechCompetitionPrice, ) {
        this.CurrentTechCompetitionID = CurrentTechCompetitionID;
        this.CurrentTechCompetitionImage = CurrentTechCompetitionImage;
        this.CurrentTechCompetitionName = CurrentTechCompetitionName;
        this.CurrentTechCompetitionPrice = CurrentTechCompetitionPrice;
    }*/

    public String getCurrentTechCompetitionSalePrice() {
        return CurrentTechCompetitionSalePrice;
    }

    public void setCurrentTechCompetitionSalePrice(String currentTechCompetitionSalePrice) {
        CurrentTechCompetitionSalePrice = currentTechCompetitionSalePrice;
    }

    public String getCurrentTechCompetitionID() {
        return CurrentTechCompetitionID;
    }

    public void setCurrentTechCompetitionID(String currentTechCompetitionID) {
        CurrentTechCompetitionID = currentTechCompetitionID;
    }

    public String getCurrentTechCompetitionImage() {
        return CurrentTechCompetitionImage;
    }

    public void setCurrentTechCompetitionImage(String currentTechCompetitionImage) {
        CurrentTechCompetitionImage = currentTechCompetitionImage;
    }

    public String getCurrentTechCompetitionName() {
        return CurrentTechCompetitionName;
    }

    public void setCurrentTechCompetitionName(String currentTechCompetitionName) {
        CurrentTechCompetitionName = currentTechCompetitionName;
    }

    public String getCurrentTechCompetitionPrice() {
        return CurrentTechCompetitionPrice;
    }

    public void setCurrentTechCompetitionPrice(String currentTechCompetitionPrice) {
        CurrentTechCompetitionPrice = currentTechCompetitionPrice;
    }
}
