package id.stanley.binus.bluejackkos.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;

import id.stanley.binus.bluejackkos.R;
import id.stanley.binus.bluejackkos.models.UserModel;
import id.stanley.binus.bluejackkos.utils.DataStore;
import id.stanley.binus.bluejackkos.utils.SendSMS;

public class SignUpActivity extends AppCompatActivity {

    private EditText dobTextField;
    private EditText passwordTextField;
    private EditText verifyPasswordTextField;
    private EditText usernameTextField;
    private EditText phoneTextField;
    private CheckBox tosCheckbox;
    private TextView toolbarTitle;
    private DatePickerDialog picker;
    private Toolbar toolbar;
    private MaterialButton signUpButton;
    private RadioGroup genderGroup;
    private RadioButton genderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        DataStore dataStore = new DataStore(this);

        toolbarTitle = findViewById(R.id.titleText);
        dobTextField = findViewById(R.id.dobTextField);
        toolbar = findViewById(R.id.toolbar);
        signUpButton = findViewById(R.id.loginButton);
        phoneTextField = findViewById(R.id.phoneTextField);
        usernameTextField = findViewById(R.id.usernameTextField);
        passwordTextField = findViewById(R.id.passwordTextField);
        verifyPasswordTextField = findViewById(R.id.confirmPasswordTextField);
        tosCheckbox = findViewById(R.id.tosCheckbox);
        genderGroup = findViewById(R.id.genderGroup);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.sign_up));

        dobTextField.setOnClickListener(view -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            picker = new DatePickerDialog(SignUpActivity.this,
                    (view1, year1, monthOfYear, dayOfMonth) -> dobTextField.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
            picker.show();
        });

        signUpButton.setOnClickListener(view -> {
            if (validateInput()) {
                // validation passed, insert data into datastore
                String dob = dobTextField.getText().toString();
                String password = passwordTextField.getText().toString();
                String username = usernameTextField.getText().toString();
                String phone = phoneTextField.getText().toString();
                genderButton = findViewById(genderGroup.getCheckedRadioButtonId());
                String gender = genderButton.getText().toString();
                ArrayList<UserModel> usersArrayList = dataStore.getUsersArrayList();
                String userId = "US" + String.format("%03d", usersArrayList.size() + 1);

                UserModel userModel = new UserModel(userId, username, password, phone, gender, dob);
                Log.d("CAT", userModel.getUserId() + userModel.getPassword());

//                usersArrayList.add(userModel);
//
//                dataStore.setUsersArrayList(usersArrayList);

                dataStore.insertUser(userModel);

                SendSMS sendSMS = new SendSMS();
                sendSMS.SendSMS(this, "+62"+ phone.substring(1));

                Toast.makeText(this, getString(R.string.sign_up_successful), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private Boolean validateInput() {
        String dob = dobTextField.getText().toString();
        String password = passwordTextField.getText().toString();
        String verifyPass = verifyPasswordTextField.getText().toString();
        String username = usernameTextField.getText().toString();
        String phone = phoneTextField.getText().toString();
        Boolean tosChecked = tosCheckbox.isChecked();
        Boolean valid = true;
        DataStore dataStore = new DataStore(this);
        ArrayList<UserModel> usersArrayList = dataStore.getUsersArrayList();

        // reset errors
        dobTextField.setError(null);
        verifyPasswordTextField.setError(null);
        passwordTextField.setError(null);
        phoneTextField.setError(null);
        usernameTextField.setError(null);
        tosCheckbox.setError(null);

        // check if password not match
        if (!password.equals(verifyPass)) {
            verifyPasswordTextField.setError(getString(R.string.password_does_not_match));
            verifyPasswordTextField.requestFocus();
            passwordTextField.setError(getString(R.string.password_does_not_match));
            passwordTextField.requestFocus();
            valid = false;
        }

        // check password requirement
        // 1 uppercase, 1 lowercase, 1 number, minimum 7 length
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{7,}$")) {
            passwordTextField.setError(getString(R.string.password_requirement_not_met));
            passwordTextField.requestFocus();
            valid = false;
        }

        // check username requirement
        if (username.length()+1 < 3 || username.length()+1 > 25) {
            usernameTextField.setError(getString(R.string.username_requirement_not_met));
            usernameTextField.requestFocus();
            valid = false;
        }

        // check if username exists
        for (int x = 0; x < usersArrayList.size(); ++x) {
            if (usersArrayList.get(x).getUsername().toLowerCase().equals(username)) {
                usernameTextField.setError(getString(R.string.username_exists));
                usernameTextField.requestFocus();
                valid = false;
                break;
            }
        }

        // check if empty
        if (!tosChecked) {
            tosCheckbox.setError(getString(R.string.tos_error));
            tosCheckbox.requestFocus();
            valid = false;
        }

        if (dob.isEmpty()) {
            dobTextField.setError(getString(R.string.must_be_filled));
            dobTextField.requestFocus();
            valid = false;
        }

        if (verifyPass.isEmpty()) {
            verifyPasswordTextField.setError(getString(R.string.must_be_filled));
            verifyPasswordTextField.requestFocus();
            valid = false;
        }

        if (password.isEmpty()) {
            passwordTextField.setError(getString(R.string.must_be_filled));
            passwordTextField.requestFocus();
            valid = false;
        }

        if (phone.isEmpty()) {
            phoneTextField.setError(getString(R.string.must_be_filled));
            phoneTextField.requestFocus();
            valid = false;
        }

        if (phone.length()+1 < 10 || phone.length()+1 > 12) {
            phoneTextField.setError(getString(R.string.phone_length_error));
            phoneTextField.requestFocus();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
