package com.example.starquizz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class QuestionActivity extends AppCompatActivity {

    String userName;
    int score, questionCount, numberOfQuestion;
    TextView textViewQuestionNumber, textViewScore, testQuestion, textViewPourTester;
    RadioGroup radioGroup;
    RadioButton radio1Element, radio2Element, radio3Element;
    Button buttonValidate;
    Intent questionsPage, resultIntent, startQuizzIntent;
    String name;
    int CharacterID;
    String goodChoice;
    String[] choiceList;
    private boolean questionValidated;
    enumCrit [] criteriaTab = {enumCrit.HAIR, enumCrit.SKIN, enumCrit.EYE};
    enumCrit criteriaChoosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        getSupportActionBar().hide();

        textViewQuestionNumber = (TextView) findViewById(R.id.textViewQuestionNumber);
        textViewScore = (TextView) findViewById(R.id.textViewScore);
        testQuestion = (TextView) findViewById(R.id.textViewQuestionAsked);
        buttonValidate = findViewById(R.id.buttonValidate);
        radioGroup = findViewById(R.id.radioGroup);
        radio1Element = findViewById(R.id.radioButton1);
        radio2Element = findViewById(R.id.radioButton2);
        radio3Element = findViewById(R.id.radioButton3);


        numberOfQuestion = 5;

        //on recupere le pseudo, le counter et le score de la page d'accueil
        startQuizzIntent = getIntent();
        userName = startQuizzIntent.getStringExtra("userName");
        score = startQuizzIntent.getIntExtra("score", -404);
        questionCount = startQuizzIntent.getIntExtra("questionCount", -404);

        //on met la question a poser dans la textView
        textViewQuestionNumber.setText("Question: " + questionCount + "/" + numberOfQuestion);
        textViewScore.setText("Score: " + score);

        //On sélectionne le personnage, le critère et les 3 caractèristiques
        CharacterID = randomNum(80)+1;
        criteriaChoosen = criteriaTab[new Random().nextInt(3)];
        name = getFeature( CharacterID, criteriaChoosen).first;
        choiceList = new String[3];
        choiceList[0] = getFeature( CharacterID, criteriaChoosen).second;
        choiceList[1] = secondChoice(choiceList[0], criteriaChoosen);
        choiceList[2] = thirdChoice(choiceList[0], choiceList[1], criteriaChoosen);
        goodChoice = choiceList[0];
        Collections.shuffle(Arrays.asList(choiceList)); //pour mettre les réponses au hasard

        //on met la question à poser à l'utilisateur dans la TextView
        testQuestion.setText(criteriaChoosen.getPhrasePage() + name);

        //on met les caractéristiques dans les radiobutton
        radio1Element.setText(choiceList[0]);
        radio2Element.setText(choiceList[1]);
        radio3Element.setText(choiceList[2]);

        //bool pour pouvoir passer à la question suivante
        questionValidated = false;

        buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonValidateFonction(userName, score, questionCount, goodChoice);
            }
        });
    }

    //FONCTIONS:

    //requete API pour avoir une caractéristique associée à un critère
    public Pair <String,String> getFeature (int id, enumCrit choosenCriteria){
        try {
            JSONObject json = new JSONObject(new JsonDowload().execute("https://swapi.co/api/people/"+
                    id +"/?format=json").get());
            String namefc = json.getString("name");
            String feature = json.getString(choosenCriteria.getJsonParam());

            return new Pair<String,String>(namefc, feature);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getFeature(randomNum(80)+1, choosenCriteria);
    }

    public int randomNum(int number){
        number = new Random().nextInt(number);
        if (number == 17){
            return randomNum(80)+1;
        }
        return number;
    }

    public String secondChoice(String choice1, enumCrit criteriaChoosen){
        int CharacterID;
        String choice2;
        do{
            //on prend un autre perso au hasard
            CharacterID = randomNum(80)+1;
            choice2 = getFeature( CharacterID, criteriaChoosen).second;
        }while(choice1.equals(choice2));
        return choice2;
    }

    public String thirdChoice(String choice1, String choice2, enumCrit criteriaChoosen){
        int CharacterID;
        String choice3;
        do{
            CharacterID = randomNum(80)+1;
            choice3 = getFeature( CharacterID, criteriaChoosen).second;
        }while((choice1.equals(choice3)) || (choice2.equals(choice3)));
        return choice3;
    }

    //bouton pour valider et passer à la prochaine question
    public void buttonValidateFonction(String userName, int score, int questionCount, String goodChoice){
        if (questionValidated == false){
            if(!oneAnwserCheked()){
                Toast.makeText(QuestionActivity.this,"Choose an answer",Toast.LENGTH_SHORT).show();
                return;
            }
            confirmAction(goodChoice);
            questionValidated = true;
            if (questionCount>numberOfQuestion-1){
                buttonValidate.setText("Finish");
            }
            else{
                buttonValidate.setText("Next question");
            }
        }
        else{
            if(questionCount<numberOfQuestion){
                nextQuestionPage(userName, questionCount+1, score);
            }
            else{
                resultPage (userName, questionCount, score);
            }
        }
    }

    public void nextQuestionPage(String userName, int questionCount, int score){
        questionsPage = new Intent(getApplicationContext(), QuestionActivity.class);
        questionsPage.putExtra("userName", userName);
        questionsPage.putExtra("questionCount", questionCount);
        questionsPage.putExtra("score", score);
        startActivity(questionsPage);
    }

    public void resultPage (String userName, int questionCount, int score){
        resultIntent = new Intent(getApplicationContext(), ResultActivity.class);
        resultIntent.putExtra("userName", userName);
        resultIntent.putExtra("questionCount", questionCount);
        resultIntent.putExtra("score", score);
        startActivity(resultIntent);
    }

    public void disableRadioButtons(){
        radio1Element.setEnabled(false);
        radio2Element.setEnabled(false);
        radio3Element.setEnabled(false);
    }

    //Pour mettre à jour le score et afficher la correction
    public void confirmAction(String goodChoice) {

        if ((radio1Element.isChecked()) && (radio1Element.getText().toString() == goodChoice)){
            score ++;
        }
        else if ((radio2Element.isChecked()) && (radio2Element.getText().toString() == goodChoice)) {
            score ++;
        }
        else if ((radio3Element.isChecked()) && (radio3Element.getText().toString() == goodChoice)){
            score ++;
        }
        if (radio1Element.getText().toString() == goodChoice){
            radio1Element.setTextColor(Color.GREEN);
            radio2Element.setTextColor(Color.RED);
            radio3Element.setTextColor(Color.RED);
        }
        else if (radio2Element.getText().toString() == goodChoice){
            radio1Element.setTextColor(Color.RED);
            radio2Element.setTextColor(Color.GREEN);
            radio3Element.setTextColor(Color.RED);
        }
        else if (radio3Element.getText().toString() == goodChoice){
            radio1Element.setTextColor(Color.RED);
            radio2Element.setTextColor(Color.RED);
            radio3Element.setTextColor(Color.GREEN);
        }
        disableRadioButtons();
        textViewScore.setText("Score: " + score);
    }

    public boolean oneAnwserCheked(){
        if ((radio1Element.isChecked()) || (radio2Element.isChecked()) || (radio3Element.isChecked())){
            return true;
        }
        else {
            return false;
        }
    }
}
 enum enumCrit {

    HAIR ("hair_color", "How are the hairs of "),
    SKIN ("skin_color", "How is the skin of "),
    EYE ("eye_color", "What's the color of the eyes of ");

    private String jsonParam ="";

     public String getJsonParam() {
         return jsonParam;
     }

     public String getPhrasePage() {
         return phrasePage;
     }

     private String phrasePage ="";

     enumCrit(String jsonParam, String phrasePage) {
         this.jsonParam = jsonParam;
         this.phrasePage = phrasePage;
     }
 }
