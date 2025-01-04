package be.com.learn.adminsys.b3q1_androidproject_jm.Models;

import java.io.Serializable;

public class Grade implements Serializable {
    private Student student;
    private double Point;

    public Grade(Student student, double Point) {
        this.student = student;
        this.Point = Point;
    }

    public Student getStudent() {
        return student;
    }

    public double getGrade() {
        return Point;
    }

}
