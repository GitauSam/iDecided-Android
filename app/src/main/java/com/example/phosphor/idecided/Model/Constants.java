package com.example.phosphor.idecided.Model;

public class Constants {

    public static String selectedCounty;
    public static String selectedChild;
    public static String firstname;
    public static String secondname;
    public static String surname;
    public static String image;
    private static String comment_id=selectedChild+selectedCounty;
    public static String post;
    public static String party;
    public static String areaofjurisdiction;
    public static String education, awards, office, manifesto;


    public static String getFirstname() {
        return firstname;
    }

    public static String getSecondname() {
        return secondname;
    }

    public static String getSurname() {
        return surname;
    }

    public static String getImage() {
        return image;
    }

    public static String getComment_id() {
        return comment_id;
    }

    public String getSelectedCounty() {
        return selectedCounty;
    }

    public String getSelectedChild() {
        return selectedChild;
    }

    public static String getPost() {
        return post;
    }

    public static String getParty() {
        return party;
    }

    public static String getAreaofjurisdiction() {
        return areaofjurisdiction;
    }

    public static String getEducation() {
        return education;
    }

    public static String getAwards() {
        return awards;
    }

    public static String getOffice() {
        return office;
    }

    public static String getManifesto() {
        return manifesto;
    }
}
