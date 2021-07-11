package com.example.spectrumtask2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Dashboard extends AppCompatActivity {


    private RecyclerView recyclerView;
    private static MyAdapter adapter;
    private static ArrayList<TaskList> taskList= new ArrayList<> ();
    private Button add;
    private EditText task;
    private TextView fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_dashboard);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        add=findViewById (R.id.right);
        task=findViewById (R.id.taskEntryD);
        fullName=findViewById (R.id.nameD);
       // String sName=getIntent ().getStringExtra ("name");
        //fullName.setText (sName);

        add.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getdata ();
            }

        });

        recyclerView= findViewById (R.id.recycler1);
        recyclerView.setLayoutManager (new LinearLayoutManager (this));

        adapter=new MyAdapter (getApplicationContext (),taskList);

        recyclerView.setAdapter (adapter);

    }

    private void getdata() {

        Map<String,Object> map=new HashMap<> ();
        map.put ("task",task.getText ().toString ());
        String s1 = task.getText ().toString ();
        TaskList s=new TaskList (s1);
        Dashboard.taskList.add (s);
        Dashboard.adapter.notifyDataSetChanged ();

        FirebaseDatabase.getInstance ().getReference ().child ("taskList").push ()
                .setValue (map)
                .addOnSuccessListener (new OnSuccessListener<Void> () {
                    @Override
                    public void onSuccess(Void aVoid) {
                        task.setText ("");

                        Toast.makeText (getApplicationContext (), "Inserted SuccessFully", Toast.LENGTH_SHORT).show ();
                    }
                })
                .addOnFailureListener (new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText (getApplicationContext (), "Could not inserted", Toast.LENGTH_SHORT).show ();
                    }
                });

        getTimer(5);




    }

    private void getTimer(int number) {
        AlarmManager am = (AlarmManager) getSystemService (Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance ();
        List<Calendar> calendarList = new ArrayList<> ();
        for (int i = 1; i <= number; i++) {
            calendarList.add (calendar);
        }

        for (Calendar calandarItem : calendarList) {
            calandarItem.add (Calendar.SECOND, 10);

            int requestCode=(int) calendar.getTimeInMillis () / 1000;
            Intent intent = new Intent (this, MyAlaramReceiver.class);
            intent.putExtra ("REQUEST_CODE",requestCode);

            intent.addFlags (Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            intent.addFlags (Intent.FLAG_RECEIVER_FOREGROUND);
            PendingIntent pi = PendingIntent.getBroadcast (this,requestCode, intent, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                am.setExactAndAllowWhileIdle (AlarmManager.RTC_WAKEUP,calandarItem.getTimeInMillis (),pi);

            }
            else {
                am.setExact (AlarmManager.RTC_WAKEUP,calandarItem.getTimeInMillis (),pi);
            }

        }
        //  Toast.makeText (this, "", Toast.LENGTH_SHORT).show ();


    }


}
