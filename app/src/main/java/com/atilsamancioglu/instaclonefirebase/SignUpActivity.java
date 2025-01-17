package com.atilsamancioglu.instaclonefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    /* /Firebase'in productlarını kullanmadan önce her zaman bir defaya mahsus bir initialization yapmak lazım
 Kullqnıcı kaydetmek için birsürü seçenek var Bunları firebase panelinden seçiyorsun.Biz bu 
 projede sadece mail adresi ile kayıt gerçekleştiriyoruz.Hangi yolla kayıt olacağını kodlarla değil
kullanıcı panelinden yapıyoruz.
*/

    private FirebaseAuth firebaseAuth;
    EditText emailText, passwordText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        /* Tek seferlik initialization işlemini gerçekleştirdik şimdi bu firebaseauth nesnesi ile işlmeleri
 gerçekleştireceğiz */
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {

            Intent intent = new Intent(SignUpActivity.this,FeedActivity.class);
            startActivity(intent);
            finish();

        }


    }

    public void signInClicked (View view) {

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();


        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                Intent intent = new Intent(SignUpActivity.this, FeedActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });


    }

    public void signUpClicked (View view) {
/* Biz email ve passwordu bir değişkene atamayıp direkt createuser metodunun içinde de bu işlemi yapabilirdik
ama o zaman da ben değişkenlere bir atama yapmadığımdan adam e mailini girmemişse if email is not null
diyemezdim bu yüzden değişkene atadım*/
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();


        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                Toast.makeText(SignUpActivity.this,"User Created",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(SignUpActivity.this,FeedActivity.class);
                startActivity(intent);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });



    }



}
