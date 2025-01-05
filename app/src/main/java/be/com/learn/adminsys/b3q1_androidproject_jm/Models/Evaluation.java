package be.com.learn.adminsys.b3q1_androidproject_jm.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "evaluations")
public class Evaluation {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "max_points")
    private int maxPoints;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "parent_type")
    private String parentType; // Type du parent (e.g., "Course", "CompositeEvaluation")

    @ColumnInfo(name = "parent_id")
    private int parentId; // Référence vers le parent (e.g., id d'un Course ou d'une autre Evaluation)

    @ColumnInfo(name = "type")
    private String type; // Type d'évaluation (e.g., "Final", "Composite")

    // Constructeurs
    public Evaluation() {}

    public Evaluation(int maxPoints, String name, String parentType, int parentId, String type) {
        this.maxPoints = maxPoints;
        this.name = name;
        this.parentType = parentType;
        this.parentId = parentId;
        this.type = type;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
