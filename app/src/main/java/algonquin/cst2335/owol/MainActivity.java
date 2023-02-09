package algonquin.cst2335.owol;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.w( "MainActivity", "In onCreate() - Loading Widgets" );

        setContentView(R.layout.activity_main);
        Button LoginButton = findViewById(R.id.loginButton);
        EditText emailtext = findViewById(R.id.editEmail);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);


        String emailAddress = prefs.getString("emailName", "");

        emailtext.setText(emailAddress);

        LoginButton.setOnClickListener(clk->{
            Intent nextPage = new Intent(MainActivity.this,
                    SecondActivity.class);
            nextPage.putExtra("EmailAddress:",emailtext.getText().toString());

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("emailName", emailtext.getText().toString());

            editor.apply();

            startActivity(nextPage);

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w( "MainActivity", "In onCreate() - Loading Widgets" );
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.w( "MainActivity", "In onCreate() - Loading Widgets" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w( "MainActivity", "In onCreate() - Loading Widgets" );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w( "MainActivity", "In onCreate() - Loading Widgets" );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w( "MainActivity", "In onCreate() - Loading Widgets" );
    }


}