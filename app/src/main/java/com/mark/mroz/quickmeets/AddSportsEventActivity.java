package com.mark.mroz.quickmeets;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mark.mroz.quickmeets.enums.SportEnum;
import com.mark.mroz.quickmeets.models.SportsEvent;
import com.mark.mroz.quickmeets.models.User;
import com.mark.mroz.quickmeets.shared.GlobalSharedManager;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddSportsEventActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    SimpleDateFormat dfDate = new SimpleDateFormat("dd-MM-yyyy h:mm a");

    GlobalSharedManager manager;

    // Layouts

    // Spinner
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    // Buttons
    Button plusButton, minusButton, saveButton, cancelButton, editLocationButton;

    //Edit Text
    EditText playersEditText, startTimeEditText, startDateEditText, endDateEditText, endTimeEditText, locationEditText, descriptionEditText;

    //Checkbox
    CheckBox equipmentCheckBox;

    //Seek bar
    SeekBar intensitySeekBar;

    //Text View
    TextView intensityTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sports_event);

        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("Lat", 0L);
        double lng = intent.getDoubleExtra("Lng", 0L);

        manager = new GlobalSharedManager(getApplicationContext());
        getSupportActionBar().hide();

        initSubviews();

        Geocoder geo = new Geocoder(this, Locale.getDefault());



        try {
            Address address = geo.getFromLocation(lat,lng,1).get(0);
            String locationString = address.getAddressLine(0);

            locationEditText.setText(locationString);
        } catch (IOException e) {
            locationEditText.setText("Ottawa");
            e.printStackTrace();
        }

        Intent currentIntent = getIntent();
        String sort = currentIntent.getStringExtra("sport");
        if (sort != null) {
            List<String> sports = Arrays.asList(getResources().getStringArray(R.array.sports_names));
            spinner.setSelection(sports.indexOf(sort));
        }
        String players = currentIntent.getStringExtra("players");
        if (players != null) {
            playersEditText.setText(players);
        }
        String startDate = currentIntent.getStringExtra("start_date");
        if (startDate != null) {
            startDateEditText.setText(startDate);
        }
        String startTime = currentIntent.getStringExtra("start_time");
        if (startTime != null) {
            startTimeEditText.setText(startTime);
        }
        String endDate = currentIntent.getStringExtra("end_date");
        if (endDate != null) {
            endDateEditText.setText(endDate);
        }
        String endTime = currentIntent.getStringExtra("end_time");
        if (endTime != null) {
            endTimeEditText.setText(endTime);
        }
        Boolean equipment = currentIntent.getBooleanExtra("equipment", false);
        if (equipment != null) {
            equipmentCheckBox.setChecked(equipment);
        }
        String description = currentIntent.getStringExtra("description");
        if (description != null) {
            descriptionEditText.setText(description);
        }
    }



    // Init

    private void initSubviews() {
        initTextViews();
        initButtons();
        initEditTexts();
        initCheckBoxes();
        initSeekBar();
        initSpinner();

    }

    // Init text views

    private void initTextViews() {
        intensityTextView = (TextView) findViewById(R.id.intensityTextView);
    }

    // Init Spinner

    private void initSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.sports_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getBaseContext(),parent.getItemIdAtPosition(position) + "", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // Init Buttons

    private  void initButtons() {
        plusButton = (Button) findViewById(R.id.plusButton);
        plusButton.setOnClickListener(this);
        minusButton = (Button) findViewById(R.id.minusButton);
        minusButton.setOnClickListener(this);
        saveButton = (Button) findViewById(R.id.doneButton);
        saveButton.setOnClickListener(this);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this);
        editLocationButton = (Button) findViewById(R.id.editButton);
        editLocationButton.setOnClickListener(this);
    }

    private void initEditTexts() {

        playersEditText = (EditText) findViewById(R.id.editPlayers);
        startTimeEditText = (EditText) findViewById(R.id.startTimeEditText);
        startTimeEditText.setOnClickListener(this);
        startTimeEditText.setFocusable(false);
        startTimeEditText.setClickable(true);
        endTimeEditText = (EditText) findViewById(R.id.endTimeEditText);
        endTimeEditText.setOnClickListener(this);
        endTimeEditText.setFocusable(false);
        endTimeEditText.setClickable(true);
        startDateEditText = (EditText) findViewById(R.id.startDateEditText);
        startDateEditText.setOnClickListener(this);
        startDateEditText.setFocusable(false);
        startDateEditText.setClickable(true);
        endDateEditText = (EditText) findViewById(R.id.endDateEditText);
        endDateEditText.setOnClickListener(this);
        endDateEditText.setFocusable(false);
        endDateEditText.setClickable(true);
        locationEditText = (EditText) findViewById(R.id.locationEditText);
        locationEditText.setFocusable(false);
        locationEditText.setOnClickListener(this);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
    }

    private void initCheckBoxes() {
        equipmentCheckBox = (CheckBox) findViewById(R.id.equipmentCheckBox);
    }

    private void initSeekBar() {
        intensitySeekBar = (SeekBar) findViewById(R.id.intensitySeekBar);
        intensitySeekBar.setOnSeekBarChangeListener(this);
        intensitySeekBar.incrementProgressBy(1);
        intensitySeekBar.setMax(5);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plusButton:
                if (playersEditText.getText().toString().equals("")) {
                    playersEditText.setText("1");
                } else if (Integer.parseInt(playersEditText.getText().toString()) == 0) {
                    playersEditText.setText("1");
                }else {
                    int currentAmount = Integer.parseInt(playersEditText.getText().toString());
                    currentAmount = currentAmount + 1;
                    playersEditText.setText(currentAmount + "");
                }
                break;
            case R.id.minusButton:
                if (playersEditText.getText().toString().equals("")) {
                } else if (Integer.parseInt(playersEditText.getText().toString()) == 1) {
                }else {
                    int currentAmount = Integer.parseInt(playersEditText.getText().toString());
                    currentAmount = currentAmount - 1;
                    playersEditText.setText(currentAmount + "");
                }
                break;
            case R.id.doneButton:

                if (performValidation()) {

                    Long id = 0L;
                    SportEnum sport = SportEnum.getEnumFromString(spinner.getSelectedItem().toString());
                    int players = Integer.parseInt(playersEditText.getText().toString());
                    int intensity = Integer.parseInt(intensityTextView.getText().toString());
                    User creator = manager.getCurrentUser();
                    List<User> participants = new ArrayList<User>();

                    Intent intent = getIntent();
                    double lat = intent.getDoubleExtra("Lat", 0L);
                    double lng = intent.getDoubleExtra("Lng", 0L);

                    Boolean equipment = equipmentCheckBox.isChecked();
                    String description = descriptionEditText.getText().toString();

                    try {
                        Date start = dfDate.parse(startDateEditText.getText().toString() + " " + startTimeEditText.getText().toString());
                        Date end = dfDate.parse(endDateEditText.getText().toString() + " " + endTimeEditText.getText().toString());

                        SportsEvent event = new SportsEvent(id, sport, players, intensity, start, end, creator, participants, lat, lng, equipment, description);

                        manager.saveSportsEvent(event);

                        Intent mapIntent = new Intent(this, MainActivity.class);
                        startActivity(mapIntent);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.cancelButton:

                Intent mapIntent = new Intent(this, MainActivity.class);
                mapIntent.putExtra("SETUP_OPTION","CANCEL");
                startActivity(mapIntent);

                break;
            case R.id.editButton:

                Intent intent = getIntent();
                double lat = intent.getDoubleExtra("Lat", 0L);
                double lng = intent.getDoubleExtra("Lng", 0L);

                Intent editLocationIntent = new Intent(this, MainActivity.class);
                editLocationIntent.putExtra("SETUP_OPTION","EDIT");
                editLocationIntent.putExtra("sport",spinner.getSelectedItem().toString());
                editLocationIntent.putExtra("players",playersEditText.getText().toString());
                editLocationIntent.putExtra("start_date",startDateEditText.getText().toString());
                editLocationIntent.putExtra("start_time",startTimeEditText.getText().toString());
                editLocationIntent.putExtra("end_date",endDateEditText.getText().toString());
                editLocationIntent.putExtra("end_time",endTimeEditText.getText().toString());
                editLocationIntent.putExtra("equipment",equipmentCheckBox.isChecked());
                editLocationIntent.putExtra("description", descriptionEditText.getText().toString());
                editLocationIntent.putExtra("Lat",lat);
                editLocationIntent.putExtra("Lng",lng);
                startActivity(editLocationIntent);

                break;
            case R.id.locationEditText:
                Intent thisIntent = getIntent();
                double lat1 = thisIntent.getDoubleExtra("Lat", 0L);
                double lng1 = thisIntent.getDoubleExtra("Lng", 0L);

                Intent editLocationTextViewIntent = new Intent(this, MainActivity.class);
                editLocationTextViewIntent.putExtra("SETUP_OPTION","EDIT");
                editLocationTextViewIntent.putExtra("sport",spinner.getSelectedItem().toString());
                editLocationTextViewIntent.putExtra("players",playersEditText.getText().toString());
                editLocationTextViewIntent.putExtra("start_date",startDateEditText.getText().toString());
                editLocationTextViewIntent.putExtra("start_time",startTimeEditText.getText().toString());
                editLocationTextViewIntent.putExtra("end_date",endDateEditText.getText().toString());
                editLocationTextViewIntent.putExtra("end_time",endTimeEditText.getText().toString());
                editLocationTextViewIntent.putExtra("equipment",equipmentCheckBox.isChecked());
                editLocationTextViewIntent.putExtra("description", descriptionEditText.getText().toString());
                editLocationTextViewIntent.putExtra("Lat",lat1);
                editLocationTextViewIntent.putExtra("Lng",lng1);
                startActivity(editLocationTextViewIntent);

                break;
            case R.id.startTimeEditText:
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String pm_am = "AM";
                        if(selectedHour < 12) {
                            pm_am = "AM";
                        } else {
                            pm_am = "PM";
                        }

                        String minutePart;
                        if (selectedMinute < 10) {
                            minutePart = "0" + selectedMinute;
                        } else {
                            minutePart = "" + selectedMinute;
                        }

                        startTimeEditText.setText( selectedHour + ":" + minutePart  + " " + pm_am);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Start Time");
                mTimePicker.show();

                break;
            case R.id.endTimeEditText:

                Calendar mcurrentTime1 = Calendar.getInstance();
                int hour1 = mcurrentTime1.get(Calendar.HOUR_OF_DAY);
                int minute1 = mcurrentTime1.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker1;
                mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String pm_am;
                        if(selectedHour < 12) {
                            pm_am = "AM";
                        } else {
                            pm_am = "PM";
                        }

                        String minutePart;
                        if (selectedMinute < 10) {
                            minutePart = "0" + selectedMinute;
                        } else {
                            minutePart = "" + selectedMinute;
                        }

                        endTimeEditText.setText( selectedHour + ":" + minutePart + " " + pm_am);
                    }
                }, hour1, minute1, true);//Yes 24 hour time
                mTimePicker.setTitle("Select End Time");
                mTimePicker.show();

                break;
            case R.id.startDateEditText:
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                startDateEditText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                endDateEditText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

                break;
            case R.id.endDateEditText:

                // Get Current Date
                final Calendar c1 = Calendar.getInstance();
                int mYear1 = c1.get(Calendar.YEAR);
                int mMonth1 = c1.get(Calendar.MONTH);
                int mDay1 = c1.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog1 = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                endDateEditText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear1, mMonth1, mDay1);
                datePickerDialog1.show();
                break;
        }
    }

    private Boolean performValidation() {

        Boolean validated = true;

       if (playersEditText.getText().toString().equals("")) {
           playersEditText.setError("Players cannot be blank");
           validated = false;
       } else if (Integer.parseInt(playersEditText.getText().toString()) == 0) {
           playersEditText.setError("Players cannot be 0");
           validated = false;
        } else {
           playersEditText.setError(null);
       }

        if (!startDateEditText.getText().toString().equals("") && !startTimeEditText.getText().toString().equals("") && !endDateEditText.getText().toString().equals("") && !endTimeEditText.getText().toString().equals("")) {


            endTimeEditText.setError(null);
            endDateEditText.setError(null);
            startTimeEditText.setError(null);
            startDateEditText.setError(null);

            String startDateString = startDateEditText.getText().toString() + " " + startTimeEditText.getText().toString();
            String endDateString = endDateEditText.getText().toString() + " " + endTimeEditText.getText().toString();

            try {
                Date startDate = dfDate.parse(startDateString);
                Date endDate = dfDate.parse(endDateString);

                if (endDate.before(startDate)) {
                    endDateEditText.setError("Cannot be before Start date");
                    endTimeEditText.setError("Cannot be before Start date");
                    validated = false;
                } else if (startDate.before(new Date()) || endDate.before(new Date())){
                    if (startDate.before(new Date())) {
                        startDateEditText.setError("Cannot be before today");
                        startTimeEditText.setError("Cannot be before today");
                    }
                    if (endDate.before(new Date())) {
                        endDateEditText.setError("Cannot be before today");
                        endTimeEditText.setError("Cannot be before today");
                    }
                } else {
                    endDateEditText.setError(null);
                    endTimeEditText.setError(null);
                }

            } catch (Exception e) {

            }
        } else {
            if (startDateEditText.getText().toString().equals("")) {
                startDateEditText.setError("Cannot be blank");
                validated = false;
            } else {
                startDateEditText.setError(null);
            }
            if (startTimeEditText.getText().toString().equals("")) {
                startTimeEditText.setError("Cannot be blank");
                validated = false;
            } else {
                startTimeEditText.setError(null);

            }

            if (endDateEditText.getText().toString().equals("")) {
                endDateEditText.setError("Cannot be blank");
                validated = false;
            } else {
                endDateEditText.setError(null);
            }

            if (endTimeEditText.getText().toString().equals("")) {
                endTimeEditText.setError("Cannot be blank");
                validated = false;
            } else {
                endTimeEditText.setError(null);
            }
        }


        return validated;
    }

    // Seek Bar delegate

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        intensityTextView.setText(progress + "");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
