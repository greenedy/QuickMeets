package com.mark.mroz.quickmeets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mark.mroz.quickmeets.models.User;
import com.mark.mroz.quickmeets.shared.GlobalSharedManager;

import java.util.List;

public class UserProfile extends AppCompatActivity {
    GlobalSharedManager manager;
    int currentUserID;
    User currentUser;

    private TextView userName, userAge, userBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        currentUserID = 101;
        manager = new GlobalSharedManager(this);
        currentUser = manager.getUser(currentUserID);

        userName = (TextView) findViewById(R.id.lblName);
        userAge = (TextView) findViewById(R.id.lblAge);
        userBio = (TextView) findViewById(R.id.txtBio);

        userName.setText(currentUser.getName());
        userAge.setText(Integer.toString(currentUser.getAge()));
        userBio.setText(currentUser.getBio());

    }

    public void onClickEdit(View view) {
        Intent intent = new Intent(this, UserProfileEdit.class);
        startActivity(intent);


    }
}
