package be.com.learn.adminsys.b3q1_androidproject_jm.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "blocs")
public class Bloc implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    // Constructor
    public Bloc(String name) {
        this.name = name;
    }

    public Bloc() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
