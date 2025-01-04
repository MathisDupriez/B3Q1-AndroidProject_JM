package be.com.learn.adminsys.b3q1_androidproject_jm.Models;

import java.io.Serializable;

public class Student implements Serializable {
    private String matricule;
    private String firstName;
    private String lastName;

    public Student(String matricule, String firstName, String lastName) {
        this.matricule = matricule;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters et Setters
    public String getMatricule() {
        return matricule;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}

