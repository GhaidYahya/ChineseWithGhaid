package com.example.learnchinese;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button1;
    Button button2;
    TextView questionsTxtV;
    TextView totalQuestionsN;
    Button Submit_button;
    int score =0;
    int totalQuestions=QuestionsANDanswers.Question.length;
    int CurrentQuestionIndex=0;
    String SelectedAnswer="";
    int my_color ;
    int my_yel ;
    private Animation top, bottom;
    private TextView txt;
    private ImageView img;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         my_color = ContextCompat.getColor(this, R.color.pink2);
          my_yel = ContextCompat.getColor(this, R.color.yellow);
        questionsTxtV = findViewById(R.id.questionsTxtV);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        Submit_button = findViewById(R.id.Submit_button);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        Submit_button.setOnClickListener(this);
        totalQuestionsN = findViewById(R.id.totalQuestionsN);
        totalQuestionsN.setText(String.valueOf(totalQuestions));
        loadNewQuestion();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this, splash.class);
                startActivity(intent);
                finish();
            }
        },5000);
    }
    @Override
    public void onClick(View v) {

        button1.setBackgroundColor(my_color);
        button2.setBackgroundColor(my_color);
        Button clickedButton = (Button) v;

        if(clickedButton.getId()==R.id.Submit_button){
            if(SelectedAnswer.equals(QuestionsANDanswers.CorrAnswers[CurrentQuestionIndex])){
                score++;
            }
            CurrentQuestionIndex++;
            loadNewQuestion();



        }else {
            //other choices
            SelectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(my_yel);
        }

    }
    void loadNewQuestion(){
        if(CurrentQuestionIndex == totalQuestions){
            ENDQuiz();
            return;
        }
        questionsTxtV.setText(QuestionsANDanswers.Question[CurrentQuestionIndex]);
        button1.setText(QuestionsANDanswers.Choices[CurrentQuestionIndex][0]);
        button2.setText(QuestionsANDanswers.Choices[CurrentQuestionIndex][1]);
    }
    void ENDQuiz(){
        String passG="";
        if(score > 0.60*totalQuestions){
            passG="PASSED :)";
        }
        else{
            passG="FAILED :(";
        }

        new AlertDialog.Builder(this).setTitle(passG).setMessage("Your Score is "+score +"/" + totalQuestions).setPositiveButton("RESTART",(dialogInterface, i) -> restartQuiz() )
                .setCancelable(false)
                .show();

    }
    void restartQuiz(){
        score = 0;
        CurrentQuestionIndex=0;
        loadNewQuestion();
    }
}
