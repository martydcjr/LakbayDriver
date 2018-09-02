package com.example.hp_pc.lakbaydriver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shashank.sony.fancytoastlib.FancyToast;


public class MainActivity extends AppCompatActivity {



    public Button signin;
    public EditText cEmail, cPassword;
    public TextView reg;
    public String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    public FirebaseAuth nAuth;
    public FirebaseAuth.AuthStateListener firebaseAuthListener;

    private ProgressDialog progressDialog;

    private CheckBox sPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        progressDialog = new ProgressDialog(this);

        nAuth = FirebaseAuth.getInstance();




        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
//                    try{
//                        if(user.isEmailVerified()){
//                            progressDialog.setMessage("Signing In Your Account");
//                            progressDialog.show();
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                public void run() {
//                                    progressDialog.dismiss();
//                                }
//                            }, 2000);
                            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finish();
                            return;
//                        }else{
//                            progressDialog.setMessage("Please Verify Your Email");
//                            progressDialog.show();
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                public void run() {
//                                    progressDialog.dismiss();
//                                }
//                            }, 2000);
//                            nAuth.signOut();
//                        }
//                    }catch (NullPointerException e){
//                        Toast.makeText(MainActivity.this, "NullPointerException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
//                    startActivity(intent);

                }
            }
        };

        cEmail =findViewById(R.id.clientEmail);
        cPassword =findViewById(R.id.clientPass);
        signin = findViewById(R.id.btn_signin);
//        register = findViewById(R.id.reg);
        sPass = findViewById(R.id.showPass);


        sPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    cPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    cPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });




        signin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                final String email = cEmail.getText().toString();
                final String password = cPassword.getText().toString();

                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                    FancyToast.makeText(getApplicationContext(),"Enter Email and Password.",FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    FancyToast.makeText(getApplicationContext(),"Enter Email Address.",FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                    return;
                }
                if (!email.matches(emailPattern) && email.length() > 0) {
                    FancyToast.makeText(getApplicationContext(),"Invalid Email Address.",FancyToast.LENGTH_SHORT, FancyToast.WARNING,false).show();

                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    FancyToast.makeText(getApplicationContext(),"Enter Password.",FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();

                    return;
                }else {
                    nAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = nAuth.getCurrentUser();

                            if (!task.isSuccessful()) {
                                progressDialog.setMessage("Sign In Error!");
                                progressDialog.show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        progressDialog.dismiss();
                                    }
                                }, 2000);
                                Toast.makeText(MainActivity.this, "signInWithEmail:failed",
                                        Toast.LENGTH_SHORT).show();

                            }
                            else{
//                                    if(user.isEmailVerified()){
                                        progressDialog.setMessage("Signing In Your Account");
                                        progressDialog.show();
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                progressDialog.dismiss();
                                                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                                startActivity(intent);
                                            }
                                        }, 2000);

//                                    }else{
////                                        Toast.makeText(MainActivity.this, "Email is not verified \n check your email inbox.", Toast.LENGTH_SHORT).show();
//                                        progressDialog.setMessage("Please Verify Your Email");
//                                        progressDialog.show();
//                                        Handler handler = new Handler();
//                                        handler.postDelayed(new Runnable() {
//                                            public void run() {
//                                                progressDialog.dismiss();
//                                            }
//                                        }, 2000);
//                                        nAuth.signOut();
//                                    }
                            }

                        }
                    });
                }

            }
        });


    }

//    public void reg(View view){
//        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
//            startActivity(intent);
//    }




    @Override
    protected void onStart() {
        super.onStart();
        nAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        nAuth.removeAuthStateListener(firebaseAuthListener);
    }

}


