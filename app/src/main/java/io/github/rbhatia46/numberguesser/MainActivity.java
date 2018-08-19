package io.github.rbhatia46.numberguesser;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int answer;

    public enum RandomNumberRange {
        MAX(100), MIN(1);

        private RandomNumberRange(int value){
            this.value = value;
        }

        private int value;

        public int getValue() {
            return this.value;
        }

        public void setValue(int value){
            this.value = value;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        answer = createRandomNumber();
    }

    public int createRandomNumber() {
        Random randy = new Random();
        return randy.nextInt(MainActivity.RandomNumberRange.MAX.getValue()) + MainActivity.RandomNumberRange.MIN.getValue();
    }

    private Boolean isGuessCorrect(int userGuess) {
        return userGuess == answer;
    }

    private void validateAndCheckGuess(String userInput) {
        try {
            int guessInput = Integer.parseInt(userInput);
            if (guessInput <= RandomNumberRange.MIN.getValue()) {
                displayInvalidUserInputToast();
            } else {
                checkGuess(guessInput);
            }
        } catch (NumberFormatException e){
            displayInvalidUserInputToast();
        }
    }

    private void checkGuess(int guessInput) {
        if (isGuessCorrect(guessInput)) {
            displayCorrectAnswerToast();
            clearUserInputEditText();
            answer = createRandomNumber();
        } else {
            displayHintToast(guessInput > answer);
        }
    }

    private void clearUserInputEditText() {
        EditText userEditText = (EditText) findViewById(R.id.guessEditText);
        userEditText.setText("");
    }

    private void displayHintToast(Boolean higher) {
        String message = "The correct answer is higher";

        if (higher) {
            message = "The correct answer is lower";
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void displayCorrectAnswerToast() {
        Toast.makeText(this, "You guessed correctly!", Toast.LENGTH_LONG).show();
    }

    private void displayInvalidUserInputToast() {
        Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
    }

    public void onGuessSubmit(View view) {
        EditText userEditText = (EditText) findViewById(R.id.guessEditText);
        String userInput = userEditText.getText().toString();
        validateAndCheckGuess(userInput);
    }
}