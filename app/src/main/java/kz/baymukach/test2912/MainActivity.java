package kz.baymukach.test2912;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private int userWrong = 1;
    private String userAnswer;
    private CountDownTimer countDownTimer;
    private static final long TOTAL_TIME = 15000;
    Boolean timerContinue;
    long leftTime = TOTAL_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}