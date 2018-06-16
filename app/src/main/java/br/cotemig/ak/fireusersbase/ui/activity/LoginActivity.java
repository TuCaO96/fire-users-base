package br.cotemig.ak.fireusersbase.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.cotemig.ak.fireusersbase.R;
import br.cotemig.ak.fireusersbase.model.UserDetails;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference database;

    @BindView(R.id.email)
    AutoCompleteTextView email;

    @BindView(R.id.password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        database = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check auth on Activity start
        if (auth != null && auth.getCurrentUser() != null) {
            onAuthSuccess(auth.getCurrentUser());
        }
    }

    @OnClick(R.id.email_sign_in_button)
    public void loginClick(){
        String e = email.getText().toString();
        String p = password.getText().toString();
        logar(e, p);
    }

    @OnClick(R.id.email_sign_up_button)
    public void signupClick(){
        cadastrar();
    }

    protected void logar(String email, String password){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser());
                } else {
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void cadastrar(){
        String e = email.getText().toString();
        String p = password.getText().toString();
        auth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser());
                } else {
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void onAuthSuccess(FirebaseUser user){
        UserDetails u = new UserDetails(user.getEmail(), user.getEmail());
        // [START basic_write]
        database.child("users").child(user.getUid()).setValue(u);
        // [END basic_write]
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}

