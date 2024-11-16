package be.com.learn.adminsys.b3q1_androidproject_jm.newModels.evaluation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import be.com.learn.adminsys.b3q1_androidproject_jm.newModels.NewGrade;
import be.com.learn.adminsys.b3q1_androidproject_jm.newModels.NewStudent;

public class FinalEvaluation extends NewEvaluation implements Serializable {
    private List<NewGrade> grades;
    public FinalEvaluation(String name, int maxPoint, Map<String, NewStudent> students, List<NewGrade> grades) {
        super(name, maxPoint, students);
        this.grades = grades;
    }
    public FinalEvaluation(String name, int maxPoint, Map<String, NewStudent> students) {
        super(name, maxPoint, students);
        grades = new ArrayList<>();
    }
    public void AddGrade(NewGrade grade) {
        grades.add(grade);
    }
    public List<NewGrade> getGrades() {
        return grades;
    }
    @Override
    public double GetAverage() {
        double average = 0;
        for (NewGrade grade : grades) {
            average += grade.getGrade();
        }
        return average / grades.size();
    }

}
