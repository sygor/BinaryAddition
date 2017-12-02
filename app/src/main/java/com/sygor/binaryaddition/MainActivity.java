package com.sygor.binaryaddition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText input1Text;
    EditText input2Text;
    TextView answerText;
    TextView checkText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input1Text = findViewById(R.id.input1);
        input2Text = findViewById(R.id.input2);
        answerText = findViewById(R.id.answer);
        checkText = findViewById(R.id.check);
    }


    public void calculateBinary(View view) {
        String resultString = performAddition(input1Text.getText().toString(), input2Text.getText().toString());
        answerText.setText(resultString);

        // math check
        Integer input1Binary = Integer.parseInt(input1Text.getText().toString(), 2);
        Integer input2Binary = Integer.parseInt(input2Text.getText().toString(), 2);

        Integer resultBinary = input1Binary + input2Binary;
        if (resultBinary.equals(Integer.parseInt(resultString,2))) {
            checkText.setText("Correct");
        } else {
            checkText.setText("Incorrect");
        }
    }

    private String performAddition(String input1, String input2) {
        String finalResult = "";
        char first;
        char second;
        char carryOver = '0'; // initial carryover

        for (int i=0; i<Math.max(input1.length(), input2.length()); i++) {
            // Get the char from the right to left. Return 0 if no more char to get
            first = input1.length()-i-1 >= 0 ? input1.charAt(input1.length()-i-1):'0';
            second = input2.length()-i-1 >= 0 ? input2.charAt(input2.length()-i-1):'0';

            // Perform 1 column's calculation
            AdditionResult result = singleDigitAddition(first, second, carryOver);
            carryOver = result.carryOver;
            finalResult = result.digit + finalResult;
        }

        // Take care of the final carry over
        if (carryOver == '1')
            finalResult = carryOver + finalResult;

        return finalResult;
    }

    private AdditionResult singleDigitAddition(char first, char second, char carryOver) {
        // Covert char into binary bit
        first -= 48;
        second -= 48;
        carryOver -= 48;

        // digit = first XOR second XOR carryover
        int resultDigit = first ^ second ^ carryOver;

        // carryover = (first AND second) OR ((first XOR second) AND carryover)
        int resultCarryover = (first & second) | ((first ^ second) & carryOver);
        
        return new AdditionResult((char)resultDigit, (char)resultCarryover);
    }

    class AdditionResult {
        private char digit;
        private char carryOver;

        AdditionResult(char digit, char carryOver) {
            // Convert binary bit back to char
            this.digit = (char) (digit+48);
            this.carryOver = (char) (carryOver+48);
        }
    }
}
