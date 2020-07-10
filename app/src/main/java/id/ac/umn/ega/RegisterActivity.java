package id.ac.umn.ega;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;



public class RegisterActivity extends AppCompatActivity {

    EditText editTextUserName, editTextPassword;
    TextInputLayout textInputLayoutUserName, textInputLayoutPassword;
    ImageButton buttonRegister, buttonLogin, buttonAbout;
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sqliteHelper = new SqliteHelper(this);
        initViews();

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Marvel.ttf");
        editTextPassword.setTypeface(typeface);
        editTextUserName.setTypeface(typeface);
        textInputLayoutPassword.setTypeface(typeface);
        textInputLayoutUserName.setTypeface(typeface);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String UserName = editTextUserName.getText().toString();
                    String Password = editTextPassword.getText().toString();

                    //Cek email cocok/tidak
                    if (!sqliteHelper.isUsernameExists(UserName)) {
                        //Gaada? Buat baru
                        sqliteHelper.addUser(new User(null, UserName, Password));
                        Snackbar.make(buttonRegister, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                    }else {
                        //Klo udh ada
                        Snackbar.make(buttonRegister, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                    }


                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutUserName = (TextInputLayout) findViewById(R.id.textInputLayoutUserName);
        buttonRegister = (ImageButton) findViewById(R.id.buttonRegister);
        buttonLogin = (ImageButton) findViewById(R.id.buttonLogin);
        buttonAbout = (ImageButton) findViewById(R.id.buttonAbout);
    }

    //validasi input user
    public boolean validate() {
        boolean valid = false;

        String UserName = editTextUserName.getText().toString();
        String Password = editTextPassword.getText().toString();

        //username
        if (UserName.isEmpty() || Password.isEmpty()) {
            valid = false;
            textInputLayoutUserName.setError("Please enter username!");
        } else {
            if (UserName.length() > 0) {
                valid = true;
                textInputLayoutUserName.setError(null);
            } else {
                valid = false;
                textInputLayoutUserName.setError("Username is to short!");
            }
        }

        //pw
        if (Password.isEmpty()) {
            valid = false;
            textInputLayoutPassword.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                textInputLayoutPassword.setError(null);
            } else {
                valid = false;
                textInputLayoutPassword.setError("Password is to short!");
            }
        }
        return valid;
    }
}
