package algonquin.cst2335.owol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Olamide Owolabi
 * @version 1.0.0.1
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Holds the text at the center of the screen
     */
    TextView tv = null;
    /**
     * This will hold the password the user will enter
     */
    EditText PT = null;
    /**
     * The button that will be used to run the password checker function
     */
    Button Btn = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv = findViewById(R.id.textView);
         PT = findViewById(R.id.editPass);
         Btn = findViewById(R.id.lognButton);

        Btn.setOnClickListener(clk -> {
            String ThePass = PT.getText().toString();

            if(!checkPasswordComplexitiy(ThePass))
            {
                tv.setText("You Shall Not Pass!");
            }
        });
    }

    /**
     * Used in checking password complexity
     * @param pw The String Object we are checking
     * @return Returns true if I deemed it Okay
     */
    boolean checkPasswordComplexitiy(String pw)
    {
        boolean IsOk = true;

        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;

        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;



        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);
          boolean bl=  isSpecialCharacter(c);

            //To Check if it has special character
          if(bl == true)
          {
              foundSpecial = true;

          }
          else if(Character.isDigit(c))
           {//To check if it has number
               foundNumber =true;
           }
          else if(Character.isUpperCase(c))
          {
              //To Check if it has Uppercase
              foundUpperCase =true;
          }
          else if(Character.isLowerCase(c))
          {
              foundLowerCase = true;
          }

        }

        //Check if it is lowcase

        if(!foundLowerCase)
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Password is missing Small Letter", Toast.LENGTH_SHORT);
            toast.show();
            IsOk = false;
        }
        else if(!foundUpperCase)
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Password is missing Capital Letter", Toast.LENGTH_SHORT);
            toast.show();
            IsOk =false;
        }
        else if (!foundNumber)
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Password is missing Number", Toast.LENGTH_SHORT);
            toast.show();
            IsOk =false;
        }
        else if(!foundSpecial)
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Password is missing Special Character", Toast.LENGTH_SHORT);
            toast.show();
            IsOk =false;
        }
        return IsOk;
    }//end password check

    /**
     * Will be used to check if a character of as string is special
     * @param cp the character were checking
     * @return true if it is a character else false
     */
    boolean isSpecialCharacter(char cp)
    {


        switch (cp)
        {
            case '#':
            case '?':
            case '*':
            case'@':
            case'!':
            case'%':
            case'^':
            case'$':
                return true;
            default:
                return false;
        }




    }
}