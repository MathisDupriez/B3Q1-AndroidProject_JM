package be.com.learn.adminsys.b3q1_androidproject_jm.newModels;

import java.io.Serializable;

import be.com.learn.adminsys.b3q1_androidproject_jm.newModels.evaluation.NewEvaluation;

public class NewGrade implements Serializable {
    private NewStudent student;
    private double Point;

    public NewGrade(NewStudent student, double Point) {
        this.student = student;
        this.Point = Point;
    }

    public NewStudent getStudent() {
        return student;
    }

    public double getGrade() {
        return Point;
    }

}
