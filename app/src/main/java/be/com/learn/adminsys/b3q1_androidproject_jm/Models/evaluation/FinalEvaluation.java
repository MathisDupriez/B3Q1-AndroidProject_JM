package be.com.learn.adminsys.b3q1_androidproject_jm.Models.evaluation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Grade;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Student;

public class FinalEvaluation extends NewEvaluation implements Serializable {
    private List<Grade> grades;
    public FinalEvaluation(String name, int maxPoint, Map<String, Student> students, List<Grade> grades) {
        super(name, maxPoint, students);
        this.grades = grades;
    }
    public FinalEvaluation(String name, int maxPoint, Map<String, Student> students) {
        super(name, maxPoint, students);
        grades = new ArrayList<>();
    }
    public void AddGrade(Grade grade) {
        grades.add(grade);
    }
    public List<Grade> getGrades() {
        return grades;
    }
    @Override
    public double GetAverage() {
        double average = 0;
        for (Grade grade : grades) {
            average += grade.getGrade();
        }
        return average / grades.size();
    }

}
