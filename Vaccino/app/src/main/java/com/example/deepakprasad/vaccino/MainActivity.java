package com.example.deepakprasad.vaccino;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    //firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Button scan_qr_btn;
    TextView uid_TV;

    EditText mothers_name_ET,fathers_name_ET,mobile_number_ET,adhar_id_ET,childs_name_ET;
    DatePicker birth_date_CV;
    Spinner gender_spinner;

    EditText hepb,opv0,bcg,penta1,opv1,ipv1,penta2,opv2,penta3,opv3,ipv2,
        measles1,measles2,opvbooster,dptbooster1,dptbooster2,next;

    String uid,mothersName,fathersName,mobileNumber,adharID,childsName,gender,birthDate;

    private IntentIntegrator qrScan;

    int year;int month;int day;
    String age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.floatingActionButton);



        //initialize with the id
        scan_qr_btn=(Button)findViewById(R.id.scan_qr_btn);
        uid_TV=(TextView)findViewById(R.id.uid_TV);
        mothers_name_ET=(EditText)findViewById(R.id.mothers_name_ET);
        fathers_name_ET=(EditText)findViewById(R.id.fathers_name_ET);
        mobile_number_ET=(EditText)findViewById(R.id.mobile_number_ET);
        adhar_id_ET=(EditText)findViewById(R.id.adhar_id_ET);
        childs_name_ET=(EditText)findViewById(R.id.childs_name_ET);
        birth_date_CV=(DatePicker) findViewById(R.id.birth_date_CV);
        gender_spinner=(Spinner)findViewById(R.id.gender_spinner);

        opv0=(EditText)findViewById(R.id.OPV0_ET);
        hepb=(EditText)findViewById(R.id.hepb_ET);
        bcg=(EditText)findViewById(R.id.BCG);
        penta1=(EditText)findViewById(R.id.Penta1);
        penta2=(EditText)findViewById(R.id.Penta2);
        penta3=(EditText)findViewById(R.id.Penta3);
        opv2=(EditText)findViewById(R.id.OPV2);
        opv1=(EditText)findViewById(R.id.OPV1);
        opv3=(EditText)findViewById(R.id.OPV3);
        ipv1=(EditText)findViewById(R.id.IPV1);
        ipv2=(EditText)findViewById(R.id.IPV2);
        measles1=(EditText)findViewById(R.id.Measles1);
        measles2=(EditText)findViewById(R.id.Measles2);
        opvbooster=(EditText)findViewById(R.id.OPV_Booster);
        dptbooster1=(EditText)findViewById(R.id.DPT_Booster1);
        dptbooster2=(EditText)findViewById(R.id.DPT_Booster2);
        next=(EditText)findViewById(R.id.next);



        //int year=birth_date_CV.getYear();
        //int month=birth_date_CV.getMonth();
        //int day=birth_date_CV.getDayOfMonth();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        birth_date_CV.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int y, int m, int dayOfMonth) {
                Log.d("Date", "Year=" + year + " Month=" + (month + 1) + " day=" + dayOfMonth);
                year=y; month=m;day=dayOfMonth;

            }
        });







        qrScan = new IntentIntegrator(this);
        scan_qr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.initiateScan();
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadToDatabase();


            }
        });


        //Populate the spinner
        List<String> genderList=new ArrayList<>();
        genderList.add("Other");
        genderList.add("Male");
        genderList.add("Female");


        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,genderList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_spinner.setAdapter(adapter);
        gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender=adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                gender="Other";

            }
        });
    }

    public void printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();
        Log.e("Different","different: "+different);


        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long yearsInMilli=daysInMilli * 365;

        long elapsedYear=0;

        //long elapsedYears=different/yearsInMilli;
        //
        //different=different%yearsInMilli;

        long elapsedDays = different / daysInMilli;
        Log.e("Different","elapsedDays: "+elapsedDays);
        different = different % daysInMilli;
        if(elapsedDays>365) {
            elapsedYear = (long) elapsedDays / 365;
            elapsedDays = elapsedDays % (elapsedYear*365);
        }
        Log.e("Different","Elapsed Yr: "+elapsedYear);

        Log.e("Different","elapsedDays: "+elapsedDays);
        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        age=elapsedYear+" years "+elapsedDays+" days ";
        Log.e("Age ","age : "+age);

    }

    private void uploadToDatabase() {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH,month);
        cal.set(Calendar.YEAR,year);
        long birthD=cal.getTimeInMillis();
        Log.e("Time","btime: "+birthD);




        long timestamp =System.currentTimeMillis();
        Log.e("Time","cTime: "+timestamp);

        Date currDate=new Date(timestamp);
        Date bDate=new Date(birthD);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate=sdf.format(currDate);
        printDifference(bDate,currDate);
        birthDate = day+"/"+month+"/"+year;

        uid=uid_TV.getText().toString();
        mothersName=mothers_name_ET.getText().toString();
        fathersName=fathers_name_ET.getText().toString();
        mobileNumber=mobile_number_ET.getText().toString();
        adharID=adhar_id_ET.getText().toString();
        childsName=childs_name_ET.getText().toString();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Patients");
        String newPostKey=databaseReference.push().getKey();
        Patient patient=new Patient(uid,mothersName,fathersName,mobileNumber,adharID,childsName,gender,birthDate,
                hepb.getText().toString(),opv0.getText().toString(),bcg.getText().toString(),penta1.getText().toString(),opv1.getText().toString(),
                ipv1.getText().toString(),penta2.getText().toString(),opv2.getText().toString(),
                penta3.getText().toString(),opv3.getText().toString(),ipv2.getText().toString(),measles1.getText().toString(),measles2.getText().toString()
                ,opvbooster.getText().toString(),dptbooster1.getText().toString(),dptbooster2.getText().toString()
                ,next.getText().toString());
        databaseReference.child(newPostKey).setValue(patient);
        Toast.makeText(this, "Updated Successfully. Age: "+age, Toast.LENGTH_LONG).show();
        //startActivity(new Intent(MainActivity.this,MainActivity.class));
        //finish();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                String uid=result.getContents();
                uid_TV.setText(uid.toString());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
