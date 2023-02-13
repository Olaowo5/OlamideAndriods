package algonquin.cst2335.owol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.textView);
        EditText PT = findViewById(R.id.editPass);
        Button Btn = findViewById(R.id.lognButton);

        Btn.setOnClickListener(clk -> {
            String ThePass = PT.getText().toString();
            checkPasswordComplexitiy(ThePass);
        });
    }

    /**
     * Used in checking password complexity
     * @param pw The String Object we are checking
     */
    void checkPasswordComplexitiy(String pw)
    {

    }
}