package com.example.notesmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends AppCompatActivity {
private EditText etEmail,etPassword,etCpassword;
private Button btnCreateAccount;
private TextView tvlogin;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etCpassword=findViewById(R.id.etCpassword);
        btnCreateAccount=findViewById(R.id.btnCreateAccount);
        tvlogin=findViewById(R.id.tvlogin);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
                Toast.makeText(CreateAccountActivity.this, "create account", Toast.LENGTH_SHORT).show();
            }

        });
    }
    // create account
    private void createAccount() {
        String email=etEmail.getText().toString();
        String password=etPassword.getText().toString();
        String cpassword=etCpassword.getText().toString();
       boolean isValidated= validate(email,password,cpassword);
       if(isValidated){
           createAccountInFirebase(email,password);
           progressDialog=new ProgressDialog(CreateAccountActivity.this);
           progressDialog.setTitle("Creating your Account");
           progressDialog.setMessage("Please wait a while......");
       }
    }
    //create Account in firebase
    void createAccountInFirebase(String email, String password){
        FirebaseAuth fauth=FirebaseAuth.getInstance();
        progressDialog.show();
        fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(CreateAccountActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(CreateAccountActivity.this, "Successfully created Account, Please check email", Toast.LENGTH_SHORT).show();
                fauth.getCurrentUser().sendEmailVerification();
                fauth.signOut();
                }
                else{
                    Toast.makeText(CreateAccountActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // Validation check function
    public boolean validate(String email,String password,String cpassword){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(!email.matches(emailPattern)){
            etEmail.setError("not valid email");
            return false;
        }
        if(password.length()<6){
            etPassword.setError("password should be greater 0r equal to 6");
            return false;
        }
        if(!cpassword.equals(password)){
            etCpassword.setError("password not matches");
            return false;
        }
        return true;
}
}
