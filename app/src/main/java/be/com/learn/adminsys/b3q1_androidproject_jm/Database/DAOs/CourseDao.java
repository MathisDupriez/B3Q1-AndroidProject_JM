package be.com.learn.adminsys.b3q1_androidproject_jm.Database.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Course;


@Dao
public interface CourseDao {

    // Insérer un cours
    @Insert
    void insert(Course course);

    // Insérer plusieurs cours
    @Insert
    void insertAll(List<Course> courses);

    // Mettre à jour un cours
    @Update
    void update(Course course);

    // Supprimer un cours
    @Delete
    void delete(Course course);

    // Récupérer tous les cours
    @Query("SELECT * FROM courses")
    List<Course> getAllCourses();

    // Récupérer un cours par son ID
    @Query("SELECT * FROM courses WHERE id = :courseId")
    Course getCourseById(int courseId);

    // Récupérer tous les cours d'un bloc spécifique
    @Query("SELECT * FROM courses WHERE blocId = :blocId")
    List<Course> getCoursesByBlocId(int blocId);
}
