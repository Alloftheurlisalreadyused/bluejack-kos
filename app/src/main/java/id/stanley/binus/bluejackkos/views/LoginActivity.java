package id.stanley.binus.bluejackkos.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import id.stanley.binus.bluejackkos.R;
import id.stanley.binus.bluejackkos.models.UserModel;
import id.stanley.binus.bluejackkos.utils.DataStore;

public class LoginActivity extends AppCompatActivity {

    private TextView textSignUp;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private EditText usernameTextField;
    private EditText passwordTextField;
    private MaterialButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textSignUp = findViewById(R.id.textSignUp);
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.titleText);
        usernameTextField = findViewById(R.id.usernameTextField);
        passwordTextField = findViewById(R.id.passwordTextField);
        loginButton = findViewById(R.id.loginButton);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        textSignUp.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));

        loginButton.setOnClickListener(view -> {
           if(validateInput()) {
               // validation passed, proceed to login
               DataStore dataStore = DataStore.getInstance();
               ArrayList usersData = dataStore.getUsersArrayList();

               String username = usernameTextField.getText().toString().toLowerCase();
               String password = passwordTextField.getText().toString();

               if (usersData.size() == 0) Toast.makeText(this, getString(R.string.no_users_registered), Toast.LENGTH_LONG).show();
               else {
                   for (int x = 0; x < usersData.size() + 1; ++x) {
                       if (x == usersData.size()) {
                           Toast.makeText(this, getString(R.string.username_or_password_wrong), Toast.LENGTH_LONG).show();
                       } else {
                           UserModel userModel = (UserModel) usersData.get(x);
                           if (userModel.getUsername().toLowerCase().equals(username) && userModel.getPassword().equals(password)) {
                               // login successful
                               Intent intent = new Intent(LoginActivity.this, MainActivity.class)
                                       .putExtra("userId", userModel.getUserId())
                                       .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                               startActivity(intent);
                               finish();
                               break;
                           }
                       }
                   }
               }
           }
        });
    }

    private boolean validateInput() {
        Boolean valid = true;

        String username = usernameTextField.getText().toString();
        String password = passwordTextField.getText().toString();

        if (password.isEmpty()) {
            passwordTextField.setError(getString(R.string.must_be_filled));
            passwordTextField.requestFocus();
            valid = false;
        }

        if (username.isEmpty()) {
            usernameTextField.setError(getString(R.string.must_be_filled));
            usernameTextField.requestFocus();
            valid = false;
        }

        return valid;
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        toolbarTitle.setText(title);
    }
}
