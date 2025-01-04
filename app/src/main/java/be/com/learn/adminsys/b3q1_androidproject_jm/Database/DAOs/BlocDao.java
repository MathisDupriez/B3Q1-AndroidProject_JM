package be.com.learn.adminsys.b3q1_androidproject_jm.Database.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Bloc;

@Dao
public interface BlocDao {
    @Insert
    void insert(Bloc bloc);

    @Query("SELECT * FROM blocs")
    List<Bloc> getAllBlocs();
}
