package be.com.learn.adminsys.b3q1_androidproject_jm.Models.evaluation;

import java.io.Serializable;
import java.util.Map;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Student;

public abstract class NewEvaluation implements Serializable {
    private String name;
    private int maxPoint;
    private Map<String, Student> students;

    public NewEvaluation(String name, int maxPoint, Map<String, Student> students) {
        this.name = name;
        this.maxPoint = maxPoint;
        this.students = students;
    }
    public abstract double GetAverage();
    public Map<String, Student> getStudents() {
        return students;
    }
    public String getName() {
        return name;
    }
    public int getMaxPoint() {
        return maxPoint;
    }
}
