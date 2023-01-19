package algonquin.cst2335.owol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import android.widget.CompoundButton;
import algonquin.cst2335.owol.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;
    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        //get viewmodel
        model = new ViewModelProvider(this).get(MainViewModel.class);
      // setContentView(R.layout.activity_main);


       //TextView myText= findViewById(R.id.textview);

        //TextView myText =  variableBinding.textview;

        //variableBinding.textview.setText(model.editString);

        //Two hours for this had to look for imprt
      model.editString.observe(this, s ->{
          variableBinding.textview.setText("Your edit test has "+s);
        });
       //TextView myedit =findViewById(R.id.myedittext);
        //TextView myedit = variableBinding.myedittext;

        //Button btn = findViewById(R.id.thebutton);
        //Button btn = variableBinding.thebutton;

        model.isSelected.observe(this, selected ->{
            variableBinding.checkbtn.setChecked(selected);
            variableBinding.radbtn.setChecked(selected);
            variableBinding.switchbtn.setChecked(selected);

            if (selected) {
                Toast.makeText(getApplicationContext(), "Compund Buttons Checked",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Compund Buttons UnChecked",
                        Toast.LENGTH_SHORT).show();
            }
        });

        variableBinding.checkbtn.setOnCheckedChangeListener((btn, isChecked) -> {

            /*
            if (isChecked) {
                Toast.makeText(getApplicationContext(), "Check Button Checked",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Check Button UnChecked",
                        Toast.LENGTH_SHORT).show();
            }
            */
            model.isSelected.postValue(isChecked);
        });

        variableBinding.radbtn.setOnCheckedChangeListener((btn, isChecked) -> {

            /*
            if (isChecked) {
                Toast.makeText(getApplicationContext(), "Radio Button Checked",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Radio Button UnChecked",
                        Toast.LENGTH_SHORT).show();
            }
    */
            model.isSelected.postValue(isChecked);
        });

        variableBinding.switchbtn.setOnCheckedChangeListener((btn, isChecked) -> {

            /*
            if (isChecked) {
                Toast.makeText(getApplicationContext(), "Switch Button Checked",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Switch Button UnChecked",
                        Toast.LENGTH_SHORT).show();
            }
    */
            model.isSelected.postValue(isChecked);
        });

        variableBinding.thebutton.setOnClickListener(
                click -> {

                    model.editString.postValue(variableBinding.myedittext.getText().toString());

                  // variableBinding.textview.setText("Your edit text has: " +model.editString);

                }
        );

        //Image on Click
        variableBinding.logoimg.setOnClickListener(
                click ->{

                }
        );

         ImageButton imgBut = variableBinding.myimagebutton;
        imgBut.setOnClickListener(
                click -> {
                    int width = imgBut.getWidth();
                    int height = imgBut.getHeight();

                    Toast.makeText(MainActivity.this,
                            "The width = " + width + " and height = " + height, Toast.LENGTH_SHORT).show();
                });

        /*
        if(btn == null) return; //no button found

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                String editString = myedit.getText().toString();

                myText.setText("Your edit text has "+ editString);

            }
        });
        */

    }
}