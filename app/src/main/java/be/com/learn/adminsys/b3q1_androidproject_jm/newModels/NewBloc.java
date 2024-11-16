package be.com.learn.adminsys.b3q1_androidproject_jm.newModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewBloc implements Serializable {
    private Map<String,NewStudent> students;
    private List<NewCourse> courses;
    String name;
    public NewBloc(String name,Map<String,NewStudent> students, List<NewCourse> courses) {
        this.name = name;
        this.students = students;
        this.courses = courses;
    }
    // Getters et Setters
    // Add student to the bloc
    public void addStudent(NewStudent student) {
        students.put(student.getMatricule(), student);
    }
    // Add course to the bloc
    public void addCourse(NewCourse course) {
        courses.add(course);
    }
    // return the list of courses in the bloc
    public List<NewCourse> getCourses() {
        return courses;
    }
    // return the name of the bloc
    public String getName() {
        return name;
    }
    // return the reférence of list of students in the bloc
    public Map<String, NewStudent> getStudents() {
        return students;
    }
}
