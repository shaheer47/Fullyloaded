package com.app.fullyloaded.Models;

public class PreviousWinnersModel {

    private String UserName, ProfileImage, WinningDate, ProductID, Description,
            CompetitionID, CompetitionName, CompetitionImage, CompetitionSpecification;

    /*public PreviousWinnersModel(String UserName, String ProfileImage, String WinningDate, String ProductID) {
        this.UserName = UserName;
        this.ProfileImage = ProfileImage;
        this.WinningDate = WinningDate;
        this.ProductID = ProductID;
    }*/

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }

    public String getWinningDate() {
        return WinningDate;
    }

    public void setWinningDate(String winningDate) {
        WinningDate = winningDate;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCompetitionID() {
        return CompetitionID;
    }

    public void setCompetitionID(String competitionID) {
        CompetitionID = competitionID;
    }

    public String getCompetitionName() {
        return CompetitionName;
    }

    public void setCompetitionName(String competitionName) {
        CompetitionName = competitionName;
    }

    public String getCompetitionImage() {
        return CompetitionImage;
    }

    public void setCompetitionImage(String competitionImage) {
        CompetitionImage = competitionImage;
    }

    public String getCompetitionSpecification() {
        return CompetitionSpecification;
    }

    public void setCompetitionSpecification(String competitionSpecification) {
        CompetitionSpecification = competitionSpecification;
    }
}
