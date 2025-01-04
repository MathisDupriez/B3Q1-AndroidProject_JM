package be.com.learn.adminsys.b3q1_androidproject_jm.Models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Bloc implements Serializable {
    private Map<String, Student> students;
    private List<Course> courses;
    String name;
    public Bloc(String name, Map<String, Student> students, List<Course> courses) {
        this.name = name;
        this.students = students;
        this.courses = courses;
    }
    // Getters et Setters
    // Add student to the bloc
    public void addStudent(Student student) {
        students.put(student.getMatricule(), student);
    }
    // Add course to the bloc
    public void addCourse(Course course) {
        courses.add(course);
    }
    // return the list of courses in the bloc
    public List<Course> getCourses() {
        return courses;
    }
    // return the name of the bloc
    public String getName() {
        return name;
    }
    // return the reférence of list of students in the bloc
    public Map<String, Student> getStudents() {
        return students;
    }
}
