package be.com.learn.adminsys.b3q1_androidproject_jm.Models.evaluation;

import java.io.Serializable;
import java.util.Map;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.NewStudent;

public abstract class NewEvaluation implements Serializable {
    private String name;
    private int maxPoint;
    private Map<String, NewStudent> students;

    public NewEvaluation(String name, int maxPoint, Map<String, NewStudent> students) {
        this.name = name;
        this.maxPoint = maxPoint;
        this.students = students;
    }
    public abstract double GetAverage();

    public Map<String, NewStudent> getStudents() {
        return students;
    }
    public String getName() {
        return name;
    }
    public int getMaxPoint() {
        return maxPoint;
    }
}
