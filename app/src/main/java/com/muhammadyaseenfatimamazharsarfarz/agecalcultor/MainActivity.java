package com.muhammadyaseenfatimamazharsarfarz.agecalcultor;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    TextView todayDate, dob;
    Button dobBtn, dobBtnCalculate;
    AdView adViewMainActivity;
    String mBirthdate, mToday;
    DatePickerDialog.OnDateSetListener dateSetListener;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todayDate = findViewById(R.id.todayDate);
        dob = findViewById(R.id.dob);
        dobBtn = findViewById(R.id.dobBtn);
        dobBtnCalculate = findViewById(R.id.dobBtnCalculate);
        result = findViewById(R.id.result);
        adViewMainActivity=findViewById(R.id.adViewMainActivity);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        adViewMainActivity.loadAd(adRequest);



        Calendar calendar = Calendar.getInstance();
        final int years = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int days = calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        mToday = simpleDateFormat.format(Calendar.getInstance().getTime());
        todayDate.setText("Today Date :" + mToday);
        dobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), dateSetListener, years, month, days);
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int years, int month, int days) {
                month = month + 1;
                mBirthdate = month + "/" + days + "/" + years;
                dob.setText("My Birth Date :" + mBirthdate);
                dob.setVisibility(View.VISIBLE);
                result.setText("");

            }
        };
        dobBtnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBirthdate == null) {
                    Toasty.info(MainActivity.this, "Please Select DOB", Toast.LENGTH_LONG).show();
                } else {
                    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date date1 = simpleDateFormat1.parse(mBirthdate);
                        Date date2 = simpleDateFormat1.parse(mToday);
                        long startDate = date1.getTime();
                        long endDate = date2.getTime();
                        Period period = new Period(startDate, endDate, PeriodType.yearMonthDay());
                        int years = period.getYears();
                        int months = period.getMonths();
                        int days = period.getDays();

                        result.setText("Day : " + String.valueOf(days) + " / " + "Months " + String.valueOf(months) + " / "
                                + "Years : " + String.valueOf(years));
                        result.setVisibility(View.VISIBLE);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

}