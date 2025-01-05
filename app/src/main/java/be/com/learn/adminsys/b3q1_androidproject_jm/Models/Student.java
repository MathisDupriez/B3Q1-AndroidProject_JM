package be.com.learn.adminsys.b3q1_androidproject_jm.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "students",
        foreignKeys = @ForeignKey(
                entity = Bloc.class,
                parentColumns = "id",
                childColumns = "blocId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Student {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String matricule;

    private String firstName;

    private String lastName;

    private int blocId; // Reference to the parent BlocDB

    // Constructor
    public Student(String matricule, String firstName, String lastName, int blocId) {
        this.matricule = matricule;
        this.firstName = firstName;
        this.lastName = lastName;
        this.blocId = blocId;
    }

    public Student() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getBlocId() {
        return blocId;
    }

    public void setBlocId(int blocId) {
        this.blocId = blocId;
    }
}
