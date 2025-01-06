package be.com.learn.adminsys.b3q1_androidproject_jm.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

import java.io.Serializable;

@Entity(
        tableName = "grades",
        foreignKeys = {
                @ForeignKey(
                        entity = Student.class,
                        parentColumns = "id",
                        childColumns = "studentId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Evaluation.class,
                        parentColumns = "id",
                        childColumns = "evaluationId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Grade implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int studentId; // Référence à l'étudiant
    private int evaluationId; // Référence à l'évaluation

    private boolean forced; // 0 = non, 1 = oui

    private double point;

    // Constructeurs
    public Grade(double point, int studentId, int evaluationId) {
        this.point = point;
        this.studentId = studentId;
        this.evaluationId = evaluationId;
    }

    public Grade() {}

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(int evaluationId) {
        this.evaluationId = evaluationId;
    }

    public void setForced(boolean forced) {
        this.forced = forced;
    }

    public boolean isForced() {
        return forced;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = Math.round(point * 100) / 100.0; // pour garder la précision au .01
    }

    public static double roundToNearestHalf(double value) {
        return Math.round(value * 2) / 2.0;
    }

    public double getDisplayPoint() {
        return roundToNearestHalf(point);
    }

}
