package com.mark.mroz.quickmeets;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mark.mroz.quickmeets.enums.SportEnum;
import com.mark.mroz.quickmeets.models.SportsEvent;
import com.mark.mroz.quickmeets.models.User;
import com.mark.mroz.quickmeets.shared.GlobalSharedManager;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Login extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private AnimationDrawable animationDrawable;
    GlobalSharedManager manager;
    List<User> userList;

    //private Toolbar toolbar;
    private EditText inputEmail, inputPassword;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //   getSupportActionBar().hide();

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_Email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_Password);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        List<User> testSubscribedUsers = new ArrayList<User>();
        testSubscribedUsers.add(new User());
        testSubscribedUsers.add(new User());
        manager = new GlobalSharedManager(this);
        //Default Events to populate map
        if(manager.getDefaults()==false) {

            manager.saveSportsEvent(new SportsEvent(0L, SportEnum.SOCCER, 8, 3, new Date(117, 7, 20, 8, 30), new Date(117, 7, 20, 10, 30), new User("Tom"), testSubscribedUsers, 45.420604, (-75.676977), false, "A fun Soccer match!"));
            manager.saveSportsEvent(new SportsEvent(0L, SportEnum.TENNIS, 6, 2, new Date(117, 7, 25, 13, 30), new Date(117, 7, 25, 15, 30), new User("Sally"), testSubscribedUsers, 45.421282, (-75.690207), false, "Newbies at Tennis"));
            manager.saveSportsEvent(new SportsEvent(0L, SportEnum.FOOTBALL, 16, 5, new Date(117, 7, 29, 12, 00), new Date(117, 7, 29, 17, 30), new User("Frank"), testSubscribedUsers, 45.428083, (-75.672777), false, "INTENSE Football Game!!!"));
            manager.saveSportsEvent(new SportsEvent(0L, SportEnum.DANCING, 4, 4, new Date(117, 7, 20, 11, 30), new Date(117, 7, 20, 14, 00), new User("Britney"), testSubscribedUsers, 45.424913, -75.685544, false, "Looking for Partners"));

            List<SportsEvent> testJoinedEvents = new ArrayList<SportsEvent>();
            testJoinedEvents.add(manager.getAllSportsEvents().get(0));
            testJoinedEvents.add(manager.getAllSportsEvents().get(3));
            List<SportsEvent> testCreatedEvents = new ArrayList<SportsEvent>();
            testCreatedEvents.add(manager.getAllSportsEvents().get(3));

            List<SportEnum> testFavSports = new ArrayList<SportEnum>();
            testFavSports.add(SportEnum.SOCCER);
            //Preset User
            manager.saveUser(new User(101, "Bob", 22, "bob@gmail.com", "password", testJoinedEvents, testCreatedEvents, testFavSports, "Hi I'm Bob I like Soccer!"));

            manager.setDefaults();
        }
            userList= manager.getAllUsers();
        //System.out.println(manager.getCurrentUser().getName());
        if(manager.getCurrentUser().getName().equals("Test User")){

        }
        else{
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
            finish();
        }

        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            animationDrawable.start();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
    }

    public void onClickLogin(View view) {
        boolean loginSuccess=false;
        for (int x=0; x<userList.size(); x++) {
            System.out.println(userList.get(x));
            User user = userList.get(x);
            //Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
            if(user.getEmail().equals(inputEmail.getText().toString()) && user.getPassword().equals(inputPassword.getText().toString())){
                loginSuccess=true;
                manager.setCurrentUser(user);
            }
        }


        if(loginSuccess) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Login info Incorrect", Toast.LENGTH_SHORT).show();
        }

    }
    public void onClickRegister(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);


    }

}
