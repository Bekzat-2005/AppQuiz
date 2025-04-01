package kz.baymukach.test2912;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private EditText regName, regPhoneNumber, regEmail, regPassword, regConfirmPassword;
    private Button btnReg;
    private TextView txtGoLogin;
    private FirebaseFirestore firestore;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regName = findViewById(R.id.regName);
        regPhoneNumber = findViewById(R.id.regPhoneNumber);
        regEmail = findViewById(R.id.regEmail);
        regPassword = findViewById(R.id.regPassword);
        regConfirmPassword = findViewById(R.id.regConfirmPassword);

        btnReg = findViewById(R.id.btnReg);

        txtGoLogin = findViewById(R.id.txtGoLogin);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        txtGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }

        });



    }

    private void registerUser() {
        String name = regName.getText().toString();
        String email = regEmail.getText().toString();
        String phone_number = regPhoneNumber.getText().toString();
        String password = regPassword.getText().toString();
        String password_confirm = regConfirmPassword.getText().toString();

        if(!name.isEmpty() && !email.isEmpty() && !phone_number.isEmpty()){
            firebaseAuth.createUserWithEmailAndPassword(email, password_confirm)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                if(user != null) {
                                    Map<String, Object> userData = new HashMap<>();
                                    userData.put("display_name", name);
                                    userData.put("email", email);
                                    userData.put("password", password);
                                    userData.put("phone_number", phone_number);

                                    firestore.collection("users")
                                            .document(user.getUid())
                                            .set(userData)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                                        finish();
                                                    }
                                                }
                                            });

                                }

                            }
                        }
                    });

        }else{
            Toast.makeText(getApplicationContext(), "Toltyr", Toast.LENGTH_SHORT).show();
        }

    }
}