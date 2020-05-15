package sg.edu.rp.c346.id19037610.reservation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText name, number, grpSize;
    DatePicker date;
    TimePicker time;
    Button submit, reset;
    RadioButton smoking, noSmoking;
    RadioGroup smokeGrp;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.txtName);
        number = findViewById(R.id.txtMobileNo);
        grpSize = findViewById(R.id.txtGroupSize);
        date = findViewById(R.id.datePicker);
        time = findViewById(R.id.timePicker);
        time.setIs24HourView(true);
        submit = findViewById(R.id.btnConfirm);
        reset = findViewById(R.id.btnReset);
        smoking = findViewById(R.id.smokeZone);
        noSmoking = findViewById(R.id.noSmoke);
        smokeGrp = findViewById(R.id.grpSmoke);

        time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if(time.getCurrentHour()<8){
                    time.setCurrentHour(8);
                    time.setCurrentMinute(0);
                    Toast.makeText(MainActivity.this, "Restaurant opens at 8am", Toast.LENGTH_SHORT).show();
                }else if(time.getCurrentHour()>21){
                    time.setCurrentHour(21);
                    time.setCurrentMinute(0);
                    Toast.makeText(MainActivity.this,"Restaurant closes at 9pm", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Challenge 2: Restrict the reservation to a date and time that is after today
        Calendar now = Calendar.getInstance();
        //Get the current day
        //86400000 = 24 hours
        date.setMinDate(now.getTimeInMillis()+86400000);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check for empty fields
                if(name.getText().length()==0){
                    Toast.makeText(MainActivity.this,"Name field cannot be blank!", Toast.LENGTH_SHORT).show();
                }else if(number.getText().length()==0){
                    Toast.makeText(MainActivity.this,"Mobile Number field cannot be blank!", Toast.LENGTH_SHORT).show();
                }else if(grpSize.getText().length()==0){
                    Toast.makeText(MainActivity.this,"Group Size field cannot be blank!", Toast.LENGTH_SHORT).show();
                }else if(!smoking.isChecked() && !noSmoking.isChecked()){
                    Toast.makeText(MainActivity.this,"Please choose smoking zone preference", Toast.LENGTH_SHORT).show();
                } else{
                    String smoke = "";
                    if(smoking.isChecked()){
                        smoke = "Smoking Zone";
                    }else if(noSmoking.isChecked()){
                        smoke="Non-Smoking Zone";
                    }
                    Toast.makeText(MainActivity.this,"Hi "+name.getText()+", A table for "+grpSize.getText()+" has been booked at a " +
                            smoke+". The reservation is on "+date.getDayOfMonth()+"/"+date.getMonth()+"/"+date.getYear()+
                            " at "+time.getCurrentHour()+":"+String.format("%02d",time.getCurrentMinute()),Toast.LENGTH_LONG).show();
                }

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                number.setText("");
                grpSize.setText("");
                date.updateDate(2020,5, 1);
                time.setCurrentHour(19);
                time.setCurrentMinute(30);
                smokeGrp.clearCheck();
            }
        });
    }
}
