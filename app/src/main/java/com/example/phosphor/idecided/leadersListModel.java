package com.example.phosphor.idecided;

public class leadersListModel {
    String image, firstname, secondname, surname, post;

    public leadersListModel(){

    }
    public leadersListModel(String image, String firstname, String secondname, String surname, String post) {
        this.image = image;
        this.firstname = firstname;
        this.secondname = secondname;
        this.surname = surname;
        this.post = post;
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

}
