package com.example.timelogger;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView reservedTimeTextView;
    TextView resetTextView;
    TextView checkInTimeTextView;

    Button checkInButton;
    Button checkOutButton;

    Context context;

    public void setCheckInTime(Timestamp checkInTime) {
        SharedPreferences sharedPreferencesCheckIn = context.getSharedPreferences(
                "checkInTime", Context.MODE_PRIVATE
        );

        SharedPreferences.Editor editorCheckIn = sharedPreferencesCheckIn.edit();
        editorCheckIn.putString("checkInTime", checkInTime.toString());
        editorCheckIn.commit();
    }

    public void setSupposedCheckOutTime(Timestamp supposedCheckOutTime) {
        SharedPreferences sharedPreferencesSupposedCheckOut = context.getSharedPreferences(
                "supposedCheckOutTime", Context.MODE_PRIVATE
        );

        SharedPreferences.Editor editorSupposedCheckOut = sharedPreferencesSupposedCheckOut.edit();
        editorSupposedCheckOut.putString("supposedCheckOutTime", supposedCheckOutTime.toString());
        editorSupposedCheckOut.commit();
    }

    public void setReservedTime(long minutes) {
        SharedPreferences sharedPreferencesReservedTime = context.getSharedPreferences(
                "reservedTime", Context.MODE_PRIVATE
        );

        SharedPreferences.Editor editorReservedTime = sharedPreferencesReservedTime.edit();
        editorReservedTime.putString("reservedTime", String.valueOf(minutes));
        editorReservedTime.commit();
    }

    public void setHasCheckedIn(Boolean hasCheckedIn) {
        SharedPreferences sharedPreferencesHasCheckedIn = context.getSharedPreferences(
                "hasCheckedIn", Context.MODE_PRIVATE
        );

        SharedPreferences.Editor editorHasCheckedIn = sharedPreferencesHasCheckedIn.edit();
        editorHasCheckedIn.putBoolean("hasCheckedIn", hasCheckedIn);
        editorHasCheckedIn.commit();
    }

    public String getCheckInTime() {
        SharedPreferences sharedPreferencesCheckIn = context.getSharedPreferences(
                "checkInTime", Context.MODE_PRIVATE
        );

        return sharedPreferencesCheckIn.getString("checkInTime", "0");
    }

    public String getSupposedCheckOutTime() {
        SharedPreferences sharedPreferencesSupposedCheckOut = context.getSharedPreferences(
                "supposedCheckOutTime", Context.MODE_PRIVATE
        );

        return sharedPreferencesSupposedCheckOut.getString("supposedCheckOutTime", "0");
    }

    public String getReservedTime() {
        SharedPreferences sharedPreferencesReservedTime = context.getSharedPreferences(
                "reservedTime", Context.MODE_PRIVATE
        );

        return sharedPreferencesReservedTime.getString("reservedTime", "0");
    }

    public Boolean getHasCheckedIn() {
        SharedPreferences sharedPreferencesHasCheckedIn = context.getSharedPreferences(
                "hasCheckedIn", Context.MODE_PRIVATE
        );

        return sharedPreferencesHasCheckedIn.getBoolean("hasCheckedIn", false);
    }

    public void resetCheckInTime() {
        SharedPreferences sharedPreferencesCheckIn = context.getSharedPreferences(
                "checkInTime", Context.MODE_PRIVATE
        );

        SharedPreferences.Editor editorCheckIn = sharedPreferencesCheckIn.edit();
        editorCheckIn.putString("checkInTime", "0");
        editorCheckIn.commit();
    }

    public void resetSupposedCheckOutTime() {
        SharedPreferences sharedPreferencesSupposedCheckOut = context.getSharedPreferences(
                "supposedCheckOutTime", Context.MODE_PRIVATE
        );

        SharedPreferences.Editor editorSupposedCheckOut = sharedPreferencesSupposedCheckOut.edit();
        editorSupposedCheckOut.putString("supposedCheckOutTime", "0");
        editorSupposedCheckOut.commit();
    }

    public void resetReservedTime() {
        SharedPreferences sharedPreferencesReservedTime = context.getSharedPreferences(
                "reservedTime", Context.MODE_PRIVATE
        );

        SharedPreferences.Editor editorReservedTime = sharedPreferencesReservedTime.edit();
        editorReservedTime.putString("reservedTime", "0");
        editorReservedTime.commit();
    }

    public void resetHasCheckedIn() {
        SharedPreferences sharedPreferencesHasCheckedIn = context.getSharedPreferences(
                "hasCheckedIn", Context.MODE_PRIVATE
        );

        SharedPreferences.Editor editorHasCheckedIn = sharedPreferencesHasCheckedIn.edit();
        editorHasCheckedIn.putBoolean("hasCheckedIn", false);
        editorHasCheckedIn.commit();
    }

    public void processCheckIn() {
        Calendar checkInCalendar = Calendar.getInstance();
        checkInCalendar.setTime(new Date());
        Timestamp checkInTime = new Timestamp(checkInCalendar.getTimeInMillis());

        setCheckInTime(checkInTime);

        checkInTimeTextView.setText(checkInTime.toString().substring(11, 19));

        Calendar supposedCheckOutCalendar = Calendar.getInstance();
        supposedCheckOutCalendar.setTime(new Date());
        supposedCheckOutCalendar.add(Calendar.HOUR, 9);
        Timestamp supposedCheckOutTime = new Timestamp(supposedCheckOutCalendar.getTimeInMillis());

        setSupposedCheckOutTime(supposedCheckOutTime);

        setHasCheckedIn(true);

        Toast.makeText(MainActivity.this, "Check in success!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getBaseContext();

        reservedTimeTextView = (TextView) findViewById(R.id.reservedTimeTextView);
        resetTextView = (TextView) findViewById(R.id.resetTextView);
        checkInTimeTextView = (TextView) findViewById(R.id.checkInTimeTextView);

        checkInButton = (Button) findViewById(R.id.checkInButton);
        checkOutButton = (Button) findViewById(R.id.checkOutButton);

        reservedTimeTextView.setText(getReservedTime());

        if(Integer.valueOf(getReservedTime()) >= 0) {
            reservedTimeTextView.setTextColor(Color.GREEN);
        } else {
            reservedTimeTextView.setTextColor(Color.RED);
        }

        String checkInTime = getCheckInTime();

        if(Boolean.parseBoolean(checkInTime)) {
            checkInTimeTextView.setText(checkInTime.substring(11, 19));
        }

        if(getHasCheckedIn()) {
            checkInButton.setEnabled(false);
        } else {
            checkInButton.setEnabled(true);
        }

        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processCheckIn();

                checkInButton.setEnabled(false);
            }
        });

        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                alertDialogBuilder.setMessage("Check Out?");

                alertDialogBuilder.setPositiveButton("Check Out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Date date = new Date();
                        Timestamp time = new Timestamp(date.getTime());

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

                        Date parsedDate = null;

                        try {
                            String supposedCheckOutTime = getSupposedCheckOutTime();

                            parsedDate = dateFormat.parse(supposedCheckOutTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

                        long diff = time.getTime() - timestamp.getTime();
                        long diffMinutes = diff / (60 * 1000);

                        String reservedTime = getReservedTime();

                        if(reservedTime.equals("0")) {
                            setReservedTime(diffMinutes);
                        } else {
                            long newReservedTime = Long.valueOf(reservedTime) + diffMinutes;

                            setReservedTime(newReservedTime);
                        }

                        String minutes = getReservedTime();

                        reservedTimeTextView.setText(minutes);

                        if(Integer.valueOf(getReservedTime()) < 0) {
                            reservedTimeTextView.setTextColor(Color.RED);
                        } else {
                            reservedTimeTextView.setTextColor(Color.GREEN);
                        }

                        setHasCheckedIn(false);

                        checkInButton.setEnabled(true);
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = alertDialogBuilder.create();

                dialog.show();
            }
        });

        resetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                alertDialogBuilder.setMessage("Reset all data?");

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetCheckInTime();

                        resetSupposedCheckOutTime();

                        resetReservedTime();

                        resetHasCheckedIn();

                        checkInButton.setEnabled(true);

                        checkInTimeTextView.setText("");
                        reservedTimeTextView.setText("");

                        Toast.makeText(MainActivity.this, "Reset success!", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = alertDialogBuilder.create();

                dialog.show();
            }
        });
    }
}
