package com.example.g16_listtrip.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g16_listtrip.DoiTuong.Accounts;
import com.example.g16_listtrip.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 1102 ;
    private static final String TAG = "Lỗi";
    TextView txtdangky, txtqmk;
    ImageView showpass;
    EditText edttk,edtmk;
    DatabaseReference mData;
    LoginButton linkfb;
    ImageButton linkgg;
    Button btnLog;
    private FirebaseAuth mAuth;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();

        try {
            getView();
            SignUpReceive();
        }catch (Exception e)
        {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        txtdangky.setOnClickListener(this);
        txtqmk.setOnClickListener(this);
        showpass.setOnClickListener(this);
    }
    public void getView() {
        txtdangky = (TextView) findViewById(R.id.signin);
        txtqmk = (TextView) findViewById(R.id.quenmk);
        showpass = (ImageView) findViewById(R.id.showpass);
        edttk = (EditText) findViewById(R.id.edttk);
        edtmk = (EditText) findViewById(R.id.edtmk);
        btnLog = (Button) findViewById(R.id.btnlogin);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin:
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
                break;
            case R.id.quenmk:
                Intent intent2 = new Intent(SignIn.this, ForgetPassword.class);
                startActivity(intent2);
                break;
            case R.id.showpass:

        }
    }
    public void  SignUpReceive() {
        Intent iSu = getIntent();
        Bundle bunrcv = iSu.getExtras();
        if(bunrcv != null)
        {
            edttk.setText(""+bunrcv.getString("tentk"));
            edtmk.setText(""+bunrcv.getString("mkdk"));
        }
    }


    public void signin(View view) {
        mData = FirebaseDatabase.getInstance().getReference("USER").child(edttk.getText().toString().trim());
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() == null) {
                    Toast.makeText(SignIn.this, "Tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Accounts accounts = snapshot.getValue(Accounts.class);
                    if(edtmk.getText().toString().equals(accounts.password)){
                        startActivity(new Intent(SignIn.this, MainActivity.class));
                    }
                    else {
                        Toast.makeText(SignIn.this, "Mật khẩu sai!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void fbsignin(View view)
    {
       callbackManager = CallbackManager.Factory.create();


        linkfb = (LoginButton) findViewById(R.id.linkfb);
        linkfb.setReadPermissions("email");
        // Callback registration
        linkfb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                startActivity(new Intent(SignIn.this, MainActivity.class));
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
        //

    }

    private void firebaseAuthWithGoogle(String idToken) {
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

    }

    private void handleFacebookAccessToken(AccessToken token) {


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


}