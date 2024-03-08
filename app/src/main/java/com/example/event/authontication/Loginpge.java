package com.example.event.authontication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.event.start.Homepage;
import com.example.event.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Loginpge extends AppCompatActivity {
   private Button loginbtn,createbtn;
   private EditText emaillogin,passwordlogin ;
  private  FirebaseAuth auth;
  private FirebaseUser user;
  private ProgressBar progressBar;


   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loginpge);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        auth = FirebaseAuth.getInstance();

        progressBar=findViewById(R.id.loginprogress);
        loginbtn=findViewById(R.id.loginbtn);
        createbtn=findViewById(R.id.regisbtn);
        emaillogin=findViewById(R.id.emailtext);
        passwordlogin=findViewById(R.id.passwordtext);


        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Loginpge.this, CreateAccountActivity.class));
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((!TextUtils.isEmpty(emaillogin.getText().toString())) && (!TextUtils.isEmpty(passwordlogin.getText().toString()))){
                    String email=emaillogin.getText().toString().trim();
                    String password=passwordlogin.getText().toString().trim();

                    loginuser(email,password);
                } else {
                    Toast.makeText(Loginpge.this,"Please enter email or Password",Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private void loginuser(String email,String password){
        if((!TextUtils.isEmpty(email)) && (!TextUtils.isEmpty(password))){
            progressBar.setVisibility(View.VISIBLE);
            auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                user=FirebaseAuth.getInstance().getCurrentUser();

                                assert user != null;
                                String currentuserid=user.getUid();

                                progressBar.setVisibility(View.INVISIBLE);
                                Intent intent=new Intent(Loginpge.this, Homepage.class);
                                intent.putExtra("userid",currentuserid);
                                startActivity(intent);


                            }else{
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(Loginpge.this, "Authentication failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        user = auth.getCurrentUser();
    }





}