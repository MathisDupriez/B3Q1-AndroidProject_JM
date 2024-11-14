package be.com.learn.adminsys.b3q1_androidproject_jm.newModels;

import java.io.Serializable;

public class NewStudent implements Serializable {
    private String matricule;
    private String firstName;
    private String lastName;
    private NewBloc bloc;

    // Constructor
    public NewStudent(String matricule, String firstName, String lastName, NewBloc bloc) {
        this.matricule = matricule;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bloc = bloc;
    }

    // Getters et Setters
    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public NewBloc getBloc() {
        return bloc;
    }

    public void setBloc(NewBloc bloc) {
        this.bloc = bloc;
    }

    @Override
    public String toString() {
        return matricule + " - " + firstName + " " + lastName;
    }
}

