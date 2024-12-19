package com.example.fitnotesapp;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExerciseDetail extends AppCompatActivity {

    private EditText kgInput, setsInput, repsInput;
    private Button saveButton;

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        dbHandler = new DBHandler(ExerciseDetail.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);


            // Center title programmatically
            toolbar.setTitleTextColor(Color.BLACK);
            toolbar.setTitleMarginStart(0); // Ensure the title isn't too close to the logo
            toolbar.setTitleMarginEnd(0);  // Equal spacing on the right side
            // Set your title text here
        }

        kgInput = findViewById(R.id.kg_input);
        setsInput = findViewById(R.id.sets_input);
        repsInput = findViewById(R.id.reps_input);
        saveButton = findViewById(R.id.save_button);

        String exerciseName = getIntent().getStringExtra("exercise_name");
        setTitle(exerciseName);
        TextWatcher fieldWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkAndHideKeyboard();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };

        kgInput.addTextChangedListener(fieldWatcher);
        setsInput.addTextChangedListener(fieldWatcher);
        repsInput.addTextChangedListener(fieldWatcher);

        saveButton.setOnClickListener(v -> {
            String kg = kgInput.getText().toString();
            String sets = setsInput.getText().toString();
            String reps = repsInput.getText().toString();

            if (kg.isEmpty() || sets.isEmpty() || reps.isEmpty()) {
                Toast.makeText(ExerciseDetail.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                dbHandler.addNewExercise(exerciseName, Double.parseDouble(kg), Integer.parseInt(sets), Integer.parseInt(reps));
                // Navigate back to MainActivity and clear the activity stack
                Intent intent = new Intent(ExerciseDetail.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // This clears the stack so MainActivity comes to the front
                startActivity(intent);
                finish();
            }
        });
    }
    private void checkAndHideKeyboard() {
        String kg = kgInput.getText().toString();
        String sets = setsInput.getText().toString();
        String reps = repsInput.getText().toString();

        if (!kg.isEmpty() && !sets.isEmpty() && !reps.isEmpty()) {

            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }

    }
}
