package com.example.phosphor.idecided.Model;

public class LeadersListModel {
    String image, firstname, secondname, surname, post, manifesto, office, party, awards, areaofjurisdiction,
                education;

    public LeadersListModel(){

    }
    public LeadersListModel(String image, String firstname, String secondname, String surname, String post, String manifesto,
                            String office, String party, String awards, String areaofjurisdiction, String education) {
        this.image = image;
        this.firstname = firstname;
        this.secondname = secondname;
        this.surname = surname;
        this.post = post;
        this.manifesto = manifesto;
        this.office = office;
        this.party = party;
        this.awards = awards;
        this.areaofjurisdiction = areaofjurisdiction;
        this.education = education;
    }

    public String getImage() {
        return image;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public String getSurname() {
        return surname;
    }

    public String getPost() {
        return post;
    }

    public String getManifesto() {
        return manifesto;
    }

    public String getOffice() {
        return office;
    }

    public String getParty() {
        return party;
    }

    public String getAwards() {
        return awards;
    }

    public String getAreaofjurisdiction() {
        return areaofjurisdiction;
    }

    public String getEducation() {
        return education;
    }
}
