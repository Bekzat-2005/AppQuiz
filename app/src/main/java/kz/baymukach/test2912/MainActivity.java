package kz.baymukach.test2912;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private TextView tvTime, tvCorrect, tvWrong, tvQuestion, tvA, tvB, tvC, tvD;
    private Button buttonNext;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Question");
    private String quizQuestion;
    private String quizQuestionA;
    private String quizQuestionB;
    private String quizQuestionC;
    private String quizQuestionD;
    private String correctAnswer;
    private int questionCount;
    private int questionNumber = 1;
    private int userCorrect = 0;
    private int userWrong = 0;
    private String userAnswer;
    private CountDownTimer countDownTimer;
    private static final long TOTAL_TIME = 15000;
    Boolean timerContinue;
    long leftTime = TOTAL_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTime = findViewById(R.id.tvTime);
        tvCorrect = findViewById(R.id.tvCorrect);
        tvWrong = findViewById(R.id.tvWrong);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvA = findViewById(R.id.tvA);
        tvB = findViewById(R.id.tvB);
        tvC = findViewById(R.id.tvC);
        tvD = findViewById(R.id.tvD);
        buttonNext = findViewById(R.id.buttonNext);

        quiz();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTime();
                quiz();
            }
        });

        tvA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer = "a";
                if (correctAnswer.equals(userAnswer)) {
                    tvA.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    tvCorrect.setText(""+ userCorrect);
                }else{
                    tvA.setBackgroundColor(Color.RED);
                    userWrong++;
                    tvWrong.setText(""+ userWrong);
                    findAnswer();
                }
            }
        });
        tvB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer = "b";
                if (correctAnswer.equals(userAnswer)) {
                    tvB.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    tvCorrect.setText(""+ userCorrect);
                }else{
                    tvB.setBackgroundColor(Color.RED);
                    userWrong++;
                    tvWrong.setText(""+ userWrong);
                    findAnswer();
                }

            }
        });
        tvC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer = "c";
                if (correctAnswer.equals(userAnswer)) {
                    tvC.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    tvCorrect.setText(""+ userCorrect);
                }else{
                    tvC.setBackgroundColor(Color.RED);
                    userWrong++;
                    tvWrong.setText(""+ userWrong);
                    findAnswer();
                }

            }
        });
        tvD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer = "d";
                if (correctAnswer.equals(userAnswer)) {
                    tvD.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    tvCorrect.setText(""+ userCorrect);
                }else{
                    tvD.setBackgroundColor(Color.RED);
                    userWrong++;
                    tvWrong.setText(""+ userWrong);
                    findAnswer();
                }

            }
        });

    }

    public void finish(View view) {
    }

    public void quiz(){
        startTime();
        tvA.setBackgroundColor(Color.WHITE);
        tvB.setBackgroundColor(Color.WHITE);
        tvC.setBackgroundColor(Color.WHITE);
        tvD.setBackgroundColor(Color.WHITE);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                questionCount = (int) snapshot.getChildrenCount();
                quizQuestion = snapshot.child(String.valueOf(questionNumber)).child("q").getValue().toString();
                quizQuestionA = snapshot.child(String.valueOf(questionNumber)).child("a").getValue().toString();
                quizQuestionB = snapshot.child(String.valueOf(questionNumber)).child("b").getValue().toString();
                quizQuestionC = snapshot.child(String.valueOf(questionNumber)).child("c").getValue().toString();
                quizQuestionD = snapshot.child(String.valueOf(questionNumber)).child("d").getValue().toString();
                correctAnswer = snapshot.child(String.valueOf(questionNumber)).child("answer").getValue().toString();

                tvQuestion.setText(quizQuestion);
                tvA.setText(quizQuestionA);
                tvB.setText(quizQuestionB);
                tvC.setText(quizQuestionC);
                tvD.setText(quizQuestionD);

                if(questionNumber < questionCount){
                    questionNumber++;
                }else{
                    Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void findAnswer(){
        if(correctAnswer.equals("a")){
            tvA.setBackgroundColor(Color.GREEN);
        } else if (correctAnswer.equals("b")) {
            tvB.setBackgroundColor(Color.GREEN);
        }
        else if (correctAnswer.equals("c")) {
            tvC.setBackgroundColor(Color.GREEN);
        }
        else if (correctAnswer.equals("d")) {
            tvD.setBackgroundColor(Color.GREEN);
        }
    }

    private void startTime() {
        countDownTimer = new CountDownTimer(leftTime, 1000) {
            @Override
            public void onTick(long l) {
                leftTime = l;
                updateCountDownText();
            }


            @Override
            public void onFinish() {
                timerContinue = false;
                pauseTimer();
                tvQuestion.setText("Uakyt Bitti");

            }
        }.start();
        timerContinue = true;
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerContinue = false;
    }
    private void updateCountDownText() {
        int second = (int) (leftTime / 1000) % 60;
        tvTime.setText("" + second);
    }
    public void  resetTime(){
        leftTime = TOTAL_TIME;
        updateCountDownText();
    }

}