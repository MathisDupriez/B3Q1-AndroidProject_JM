package be.com.learn.adminsys.b3q1_androidproject_jm.Database.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Student;


@Dao
public interface StudentDao {

    // Insérer un étudiant
    @Insert
    void insert(Student student);

    // Insérer plusieurs étudiants
    @Insert
    void insertAll(List<Student> students);

    // Mettre à jour un étudiant
    @Update
    void update(Student student);

    // Supprimer un étudiant
    @Delete
    void delete(Student student);

    // Récupérer tous les étudiants
    @Query("SELECT * FROM students")
    List<Student> getAllStudents();

    // Récupérer un étudiant par son ID
    @Query("SELECT * FROM students WHERE id = :studentId")
    Student getStudentById(int studentId);

    // Récupérer tous les étudiants d'un bloc spécifique
    @Query("SELECT * FROM students WHERE blocId = :blocId")
    List<Student> getStudentsByBlocId(int blocId);

    // Rechercher un étudiant par son matricule
    @Query("SELECT * FROM students WHERE matricule = :matricule")
    Student getStudentByMatricule(String matricule);
}
