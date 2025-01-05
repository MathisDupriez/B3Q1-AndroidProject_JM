package be.com.learn.adminsys.b3q1_androidproject_jm.Database.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Grade;

@Dao
public interface GradeDao {

    @Insert
    void insert(Grade grade);

    @Query("SELECT * FROM grades WHERE studentId = :studentId")
    List<Grade> getGradesByStudentId(int studentId);

    @Query("SELECT * FROM grades WHERE evaluationId = :evaluationId")
    List<Grade> getGradesByEvaluationId(int evaluationId);

    @Query("SELECT * FROM grades WHERE id = :gradeId")
    Grade getGradeById(int gradeId);

    @Delete
    void delete(Grade grade);
    @Update
    void update(Grade grade);
}

