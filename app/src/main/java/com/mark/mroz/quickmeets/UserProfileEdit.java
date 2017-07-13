package com.mark.mroz.quickmeets;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.mark.mroz.quickmeets.models.User;
import com.mark.mroz.quickmeets.shared.GlobalSharedManager;

public class UserProfileEdit extends AppCompatActivity {
    GlobalSharedManager manager;
    private EditText inputName, inputAge, inputBio;
    private TextInputLayout inputLayoutName, inputLayoutAge, inputLayoutBio;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_edit);

        manager = new GlobalSharedManager(this);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_Name);
        inputLayoutAge = (TextInputLayout) findViewById(R.id.input_layout_Age);
        inputLayoutBio = (TextInputLayout) findViewById(R.id.input_layout_Bio);

        inputName = (EditText) findViewById(R.id.input_name);
        inputAge = (EditText) findViewById(R.id.input_age);
        inputBio = (EditText) findViewById(R.id.input_bio);


        inputName.setText(manager.getCurrentUser().getName());
        inputAge.setText( Integer.toString(manager.getCurrentUser().getAge()));
        inputBio.setText(manager.getCurrentUser().getBio());


        btnSave = (Button) findViewById(R.id.btnRegister);

        inputName.addTextChangedListener(new UserProfileEdit.MyTextWatcher(inputName));
        inputAge.addTextChangedListener(new UserProfileEdit.MyTextWatcher(inputAge));
        inputBio.addTextChangedListener(new UserProfileEdit.MyTextWatcher(inputBio));




    }


    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.error_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAge() {
        int age = Integer.parseInt(inputAge.getText().toString().trim());

        if (inputAge.getText().toString().equals("") || age>120) {
            inputLayoutAge.setError(getString(R.string.error_age));
            requestFocus(inputAge);
            return false;
        } else {
            inputLayoutAge.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateBio() {
        if (inputBio.getText().toString().trim().length()>=30) {
            inputLayoutBio.setError(getString(R.string.error_bio));
            requestFocus(inputBio);
            return false;
        } else {
            inputLayoutBio.setErrorEnabled(false);
        }

        return true;
    }



    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void onClickSave(View view) {

        if (!validateName()) {
            return;
        }

        if (!validateAge()) {
            return;
        }

        if (!validateBio()) {
            return;
        }

        User cUser = manager.getCurrentUser();
        cUser.setName(inputName.getText().toString());
        cUser.setAge(Integer.parseInt(inputAge.getText().toString()));
        cUser.setBio(inputBio.getText().toString());
        manager.setCurrentUser(cUser);
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
        finish();


    }
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_age:
                    validateAge();
                    break;
                case R.id.input_bio:
                    validateBio();
                    break;
            }
        }
    }



    public void onClickLogout(View view) {

        manager.setCurrentUser(new User());
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();

    }
}
