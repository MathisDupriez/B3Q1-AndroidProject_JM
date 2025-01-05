package be.com.learn.adminsys.b3q1_androidproject_jm.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import be.com.learn.adminsys.b3q1_androidproject_jm.Database.DAOs.EvaluationDao;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.DAOs.GradeDao;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Bloc;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Course;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Evaluation;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Grade;
import be.com.learn.adminsys.b3q1_androidproject_jm.Models.Student;

import be.com.learn.adminsys.b3q1_androidproject_jm.Database.DAOs.BlocDao;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.DAOs.CourseDao;
import be.com.learn.adminsys.b3q1_androidproject_jm.Database.DAOs.StudentDao;

@Database(entities = {Bloc.class, Course.class, Student.class, Evaluation.class, Grade.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BlocDao blocDao();

    public abstract CourseDao courseDao();

    public abstract StudentDao studentDao();

    public abstract EvaluationDao evaluationDao();

    public abstract GradeDao gradeDao();

    // Singleton de la base de données
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .fallbackToDestructiveMigration() // Gère les migrations automatiquement
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
