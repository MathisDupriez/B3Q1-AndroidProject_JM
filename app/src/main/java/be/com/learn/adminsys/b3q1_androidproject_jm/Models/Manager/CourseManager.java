package be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager;

import java.util.List;

import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.DAOs.CourseDao;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Course;

public class CourseManager {

    private CourseDao courseDao;

    public CourseManager(AppDatabase db) {
        this.courseDao = db.courseDao();
    }

    // Récupérer les cours par BlocId
    public List<Course> getCoursesByBlocId(int blocId) {
        return courseDao.getCoursesByBlocId(blocId);
    }

    // Ajouter un nouveau cours
    public void addCourse(String courseName, int blocId) {
        Course course = new Course(courseName, blocId);
        courseDao.insert(course);
    }
    public Course getCourseById(int courseId){
        return courseDao.getCourseById(courseId);
    }
}
