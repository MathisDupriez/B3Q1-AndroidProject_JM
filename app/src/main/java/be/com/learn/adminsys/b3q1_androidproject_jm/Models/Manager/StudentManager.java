package be.com.learn.adminsys.b3q1_androidproject_jm.Models.Manager;

import java.util.List;

import be.com.learn.adminsys.b3q1_androidproject_jm.Database.AppDatabase;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.DAOs.StudentDao;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Student;

public class StudentManager {

    private StudentDao studentDao;

    public StudentManager(AppDatabase db) {
        this.studentDao = db.studentDao();
    }

    // Récupérer les étudiants par BlocId
    public List<Student> getStudentsByBlocId(int blocId) {
        return studentDao.getStudentsByBlocId(blocId);
    }

    // Ajouter un nouvel étudiant
    public void addStudent(String matricule, String firstName, String lastName, int blocId) {
        Student student = new Student(matricule, firstName, lastName, blocId);
        studentDao.insert(student);
    }
}
