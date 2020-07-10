package id.ac.umn.ega;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText editTxtUsername, editTxtPassword;
    TextInputLayout txtInputLayoutUsername, txtInputLayoutPassword;
    ImageButton btnLogin, btnAbout, btnRegis;
    SqliteHelper sqliteHelper;

    SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManagement = new SessionManagement(getApplicationContext());

        sqliteHelper = new SqliteHelper(this);
        initViews();

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Marvel.ttf");
        editTxtUsername.setTypeface(typeface);
        editTxtPassword.setTypeface(typeface);
        txtInputLayoutUsername.setTypeface(typeface);
        txtInputLayoutPassword.setTypeface(typeface);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cek inputan user
                if(validate()) {
                    String Username = editTxtUsername.getText().toString();
                    String Password = editTxtPassword.getText().toString();

                    //Authentication user
                    User currentUser = sqliteHelper.Authenticate(new User(null, Username, Password));

                    //Cek auth sukses/ga
                    if (currentUser != null) {
                        Snackbar.make(btnLogin, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();

                        sessionManagement.createLoginSession(Username, Password);

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Snackbar.make(btnLogin, "Failed to Log in, please try again", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        editTxtUsername = (EditText) findViewById(R.id.editTextUsername);
        editTxtPassword = (EditText) findViewById(R.id.editTextPassword);
        txtInputLayoutUsername = (TextInputLayout) findViewById(R.id.textInputLayoutUsername);
        txtInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        btnLogin = (ImageButton) findViewById(R.id.buttonLogin);
        btnAbout = (ImageButton) findViewById(R.id.buttonAbout);
        btnRegis = (ImageButton) findViewById(R.id.buttonRegister);
    }

    //This method is for handling fromHtml method deprecation
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    //Validasi input user
    public boolean validate() {
        boolean valid = false;

        String Username = editTxtUsername.getText().toString();
        String Password = editTxtPassword.getText().toString();

        //username
        if (Username.isEmpty()) {
            valid = false;
            txtInputLayoutUsername.setError("Please enter valid username!");
        } else {
            if (Username.length() > 0) {
                valid = true;
                txtInputLayoutUsername.setError(null);
            } else {
                valid = false;
                txtInputLayoutUsername.setError("Username is to short!");
            }
        }

        //Password input
        if (Password.isEmpty()) {
            valid = false;
            txtInputLayoutPassword.setError("Please enter password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                txtInputLayoutPassword.setError(null);
            } else {
                valid = false;
                txtInputLayoutPassword.setError("Password is to short!");
            }
        }
        return valid;
    }
}
