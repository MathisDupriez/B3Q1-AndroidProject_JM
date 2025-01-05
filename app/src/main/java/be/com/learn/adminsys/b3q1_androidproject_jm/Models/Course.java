package be.com.learn.adminsys.b3q1_androidproject_jm.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        tableName = "courses",
        foreignKeys = @ForeignKey(
                entity = Bloc.class,
                parentColumns = "id",
                childColumns = "blocId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Course implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private int blocId; // Reference to the parent BlocDB

    // Constructor
    public Course(String name, int blocId) {
        this.name = name;
        this.blocId = blocId;
    }

    public Course() {}

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

    public int getBlocId() {
        return blocId;
    }

    public void setBlocId(int blocId) {
        this.blocId = blocId;
    }
}
