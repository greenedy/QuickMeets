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
        manager.saveSportsEvent(new SportsEvent(0L, SportEnum.SOCCER,8, 3, null, null, new User(), testSubscribedUsers, 45.420604, (-75.676977),false,"A fun Event"));
        manager.saveSportsEvent(new SportsEvent(0L, SportEnum.TENNIS,8, 3, null, null, new User(), testSubscribedUsers, 45.420600, (-75.676977),false,"A fun Event"));
        List<SportsEvent> testJoinedEvents = new ArrayList<SportsEvent>();
        testJoinedEvents.add(new SportsEvent(0L, SportEnum.SOCCER,8, 3, null, null, new User(), testSubscribedUsers, 45.420600, (-75.676872),false,"A fun Event"));
        testJoinedEvents.add(new SportsEvent(0L, SportEnum.FOOTBALL,8, 3, null, null, new User(), testSubscribedUsers, 45.15, (-79.39),false,"A fun Event"));
        List<SportsEvent> testCreatedEvents = new ArrayList<SportsEvent>();
        testCreatedEvents.add(new SportsEvent(0L, SportEnum.SOCCER,8, 3, null, null, new User(), testSubscribedUsers, 45.420600, (-75.676872),false,"A fun Event"));
        testCreatedEvents.add(new SportsEvent(0L, SportEnum.DANCING,8, 3, null, null, new User(), testSubscribedUsers, 45.15, (-79.39),false,"A fun Event"));

        List< SportEnum > testFavSports = new ArrayList<SportEnum>();
        testFavSports.add(SportEnum.SOCCER);
        manager.saveUser(new User(101, "Nathan", 22, "nathansemail@gmail.com", "password", testJoinedEvents, testCreatedEvents, testFavSports,"Hi I'm Nathan I like Soccer!"));
        userList= manager.getAllUsers();
        System.out.println(manager.getCurrentUser().getName());
        if(manager.getCurrentUser().getName().equals("Test User")){

        }
        else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // init constraintLayout
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        // initializing animation drawable by getting background from constraint layout
        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();

        // setting enter fade animation duration to 5 seconds
        animationDrawable.setEnterFadeDuration(5000);

        // setting exit fade animation duration to 2 seconds
        animationDrawable.setExitFadeDuration(2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            // start the animation
            animationDrawable.start();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            // stop the animation
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
