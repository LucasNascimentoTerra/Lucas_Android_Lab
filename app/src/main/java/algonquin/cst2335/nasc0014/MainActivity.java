package algonquin.cst2335.nasc0014;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/** This class extends AppCompatActivity, it holds functions to check if a passwords meets the desired requirements
 * @author Lucas Nascimento Terra
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /** This holds the text at center of the screen
     */
    private TextView tv = null;
    /** This holds the text input area for the password to be typed in
     */
    private EditText et = null;
    /** This is a login button
     */
    private Button btn = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.textView);
        EditText et = findViewById(R.id.editText);
        Button btn = findViewById(R.id.button);




        btn.setOnClickListener( clk ->{

            String password = et.getText().toString();
            checkPasswordComplexity( password );
            boolean passwordValid = checkPasswordComplexity(password);

            if (passwordValid){
                tv.setText("Your password meets the requirements");
            } else {tv.setText("You shall not pass");}

        });





    }

    /** This Function checks if this string has an Upper Case letter, a lower case letter, a number, and a special symbol (#$%^&*!@?). If it is missing any of these 4 requirements, then show a Toast message
     *
     * @param pw String is being checked
     * @return Returns true if string meets requirements, returns false it it doesn't meet requirements
     */
    boolean checkPasswordComplexity(String pw) {



        boolean foundUpperCase = false;
        boolean foundLowerCase = false;
        boolean foundNumber = false;
        boolean foundSpecial = false;

        for (char c : pw.toCharArray()) {
            if (Character.isUpperCase(c))
                foundUpperCase = true;
            else if (Character.isLowerCase(c))
                foundLowerCase = true;
            else if (Character.isDigit(c))
                foundNumber = true;
            else if (isSpecialCharacter(c))
                foundSpecial = true;
        }


        if (!foundUpperCase) {

            Toast.makeText(this, "The password requires an Upper Case letter", Toast.LENGTH_SHORT).show();// Say that they are missing an upper case letter;

            return false;

        } else if (!foundLowerCase) {
            Toast.makeText(this, "The password requires a Lower Case letter", Toast.LENGTH_SHORT).show(); // Say that they are missing a lower case letter;

            return false;

        } else if (!foundNumber) {
            Toast.makeText(this, "The password requires a Number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundSpecial) {
            Toast.makeText(this, "The password requires a Special Symbol", Toast.LENGTH_SHORT).show();
            return false;
        } else

            return true; //only get here if they're all true
    }
/** This functions checks if there is a special character or not
 * @param c Checks string
 * @return returns true if there is a special character, returns false if there is no special character
 */
        boolean isSpecialCharacter (char c)
        {
            switch (c) {

                case '#':
                case '@':
                case '!':
                case '$':
                case '%':
                case '^':
                case '&':
                case '*':
                case '_':
                case '?':
            return true;

                default:
                    return false;
            }
        }
        //return true if c is one of: #$%^&*!@?

        //return false otherwise



}