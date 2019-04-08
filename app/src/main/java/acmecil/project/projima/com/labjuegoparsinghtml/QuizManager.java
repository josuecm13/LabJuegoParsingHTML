package acmecil.project.projima.com.labjuegoparsinghtml;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuizManager {

    String[] imagePath;
    String[] standNames;
    String currentAnswer;
    int currentAnswerIndex;
    String currentImagePath;
    Random random;

    public QuizManager(String[] imagePath, String[] movieTitles){
        this.imagePath = imagePath;
        this.standNames = movieTitles;
    }

    public Bundle getNextQuestion(){
        Bundle bundle = new Bundle();
        ArrayList<String> movieTitlesArray = new ArrayList<>();
        assignNewAnswer();
        movieTitlesArray.add(currentAnswer);
        random = new Random();
        for (int i = 0; i < 3; i++) {
            int option = random.nextInt(standNames.length);
            while (movieTitlesArray.contains(standNames[option])){
                option = random.nextInt(standNames.length);
            }
            movieTitlesArray.add(standNames[option]);
        }
        currentImagePath = imagePath[currentAnswerIndex];
        bundle.putString("imagePath",currentImagePath);
        Collections.shuffle(movieTitlesArray);
        bundle.putStringArrayList("standOptions", movieTitlesArray);
        return bundle;
    }

    private void assignNewAnswer(){
        random = new Random();
        int temporal = random.nextInt(standNames.length);
        while (temporal == currentAnswerIndex){
            temporal = random.nextInt(standNames.length);
        }
        currentAnswerIndex = temporal;
        currentAnswer = standNames[currentAnswerIndex];
    }


    public boolean validateAnswer(String answer){
        return answer.equals(currentAnswer);
    }

}
