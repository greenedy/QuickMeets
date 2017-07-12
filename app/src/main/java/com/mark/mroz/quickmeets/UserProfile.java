package com.mark.mroz.quickmeets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mark.mroz.quickmeets.models.User;
import com.mark.mroz.quickmeets.shared.GlobalSharedManager;

import org.w3c.dom.Text;

import java.util.List;

public class UserProfile extends AppCompatActivity {
    GlobalSharedManager manager;
    int currentUserID;
    User currentUser;

    private TextView userName, userAge, userBio, favourites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        currentUserID = 101;
        manager = new GlobalSharedManager(this);
        currentUser = manager.getCurrentUser();

        userName = (TextView) findViewById(R.id.lblName);
        userAge = (TextView) findViewById(R.id.lblAge);
        userBio = (TextView) findViewById(R.id.txtBio);
        favourites =(TextView) findViewById(R.id.lblFavSports);

        System.out.println();
        userName.setText(currentUser.getName());
        if(currentUser.getAge()!=0) {
            userAge.setText("Age: " + Integer.toString(currentUser.getAge()));
        }
        else{
            userAge.setText("Age: Hidden");
        }
        userBio.setText(currentUser.getBio());
       // favourites.setText(currentUser);


    }

    public void onClickEdit(View view) {
        Intent intent = new Intent(this, UserProfileEdit.class);
        startActivity(intent);


    }

    public void onClickLogout(View view) {

        manager.setCurrentUser(new User());
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();

    }
}
