package be.com.learn.adminsys.b3q1_androidproject_jm.models;

import java.util.ArrayList;
import java.util.List;

public class DataCollector {

    // Méthode pour initialiser et retourner la liste complète des blocs avec leurs sous-blocs
    public List<Bloc> collectAllBlocs() {
        List<Bloc> allBlocs = new ArrayList<>();

        // Création des trois blocs principaux
        for (int i = 1; i <= 3; i++) {
            Bloc bloc = new Bloc("bloc" + i, "Bloc " + i, null, new ArrayList<>(), null);

            // Ajout de 10 cours dans chaque bloc
            for (int j = 1; j <= 10; j++) {
                Course course = new Course("course" + i + "-" + j, "Course " + j + " - Bloc " + i, null, new ArrayList<>(), bloc);

                // Ajout de 15 évaluations dans chaque cours
                for (int k = 1; k <= 15; k++) {
                    Evaluation evaluation = new Evaluation("evaluation" + i + "-" + j + "-" + k, "Evaluation " + k + " - Course " + j, null, new ArrayList<>(), course);

                    // Ajout de 10 sous-évaluations dans chaque évaluation
                    for (int l = 1; l <= 10; l++) {
                        Evaluation subEvaluation = new Evaluation("sub-evaluation" + i + "-" + j + "-" + k + "-" + l,
                                "Sous-Evaluation " + l + " - Eval " + k, null, new ArrayList<>(), evaluation);
                        evaluation.getSubBlocs().add(subEvaluation);
                    }

                    course.getSubBlocs().add(evaluation);
                }

                bloc.getSubBlocs().add(course);
            }

            allBlocs.add(bloc);
        }

        return allBlocs;
    }
}
