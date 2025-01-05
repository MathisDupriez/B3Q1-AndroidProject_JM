package be.com.learn.adminsys.b3q1_androidproject_jm.Database.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Evaluation;

@Dao
public interface EvaluationDao {

    @Insert
    long insert(Evaluation evaluation);

    @Query("SELECT * FROM evaluations WHERE parent_id = :parentId AND parent_type = :parentType")
    List<Evaluation> getEvaluationsByParent(int parentId, String parentType);

    @Query("SELECT * FROM evaluations WHERE type = :type")
    List<Evaluation> getEvaluationsByType(String type);

    @Query("SELECT * FROM evaluations")
    List<Evaluation> getAllEvaluations();

    @Query("SELECT * FROM evaluations WHERE id = :evaluationId")
    Evaluation getEvaluationById(int evaluationId); // Corrigée pour récupérer une évaluation par ID
}