package com.example.fitnotesapp;

import static com.example.fitnotesapp.R.*;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ExerciseModel> exerciseList;
    private Button addButton;

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DBHandler(MainActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Drawable logo = ContextCompat.getDrawable(this, R.drawable.logo);

        if (getSupportActionBar() != null) {

            int width = getResources().getDimensionPixelSize(R.dimen.logo_width);
            int height = getResources().getDimensionPixelSize(R.dimen.logo_height);


            Bitmap bitmap = ((BitmapDrawable) logo).getBitmap();
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
            logo = new BitmapDrawable(getResources(), scaledBitmap);


            getSupportActionBar().setLogo(logo);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(true);


            getSupportActionBar().setTitle("FitNotes");


            toolbar.setTitleTextColor(Color.parseColor("#EDF6F9"));
            toolbar.setTitleMarginStart(2); // Space on the left side for logo
            toolbar.setTitleMarginEnd(2);   // Space on the right side for title if needed

        }



        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHandler = new DBHandler(MainActivity.this);

        exerciseList = dbHandler.getAllExercises();


        ExerciseAdapter adapter = new ExerciseAdapter(exerciseList);
        recyclerView.setAdapter(adapter);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Exercises.class));
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String name = data.getStringExtra("exercise_name");
            int kg = data.getIntExtra("kg", 0);
            int sets = data.getIntExtra("sets", 0);
            int reps = data.getIntExtra("reps", 0);

        }
    }

    private class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

        private List<ExerciseModel> exercises;


        public ExerciseAdapter(List<ExerciseModel> exercises) {
            this.exercises = exercises;


        }

        private boolean areAllChecked() {
            int checkedCount = 0;
            for (ExerciseModel exercise : exercises) {
                if (exercise.isChecked()) {
                    checkedCount++;
                }
            }
            return checkedCount == exercises.size(); // All checkboxes are checked
        }

        @Override
        public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.exercise_item, parent, false);
            return new ExerciseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ExerciseViewHolder holder, int position) {
            ExerciseModel exercise = exercises.get(position);
            holder.exerciseName.setText(exercise.getName());
            holder.exerciseDetails.setText(
                    String.format("%d kg, %d sets, %d reps", exercise.getKg(), exercise.getSets(), exercise.getReps())
            );
            holder.checkBox.setChecked(exercise.isChecked());







            holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {exercise.setChecked(isChecked);

            if (areAllChecked()) {
                showCompletionPopup(holder.itemView);
            }
            });

            if (areAllChecked()) {
                showCompletionPopup(holder.itemView);
            }


            holder.deleteButton.setOnClickListener(v -> {
                dbHandler.deleteExercise(exercise.getId());
                exercises.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(holder.itemView.getContext(), "Exercise deleted", Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public int getItemCount() {
            return exercises.size();
        }

        public class ExerciseViewHolder extends RecyclerView.ViewHolder {
            TextView exerciseName, exerciseDetails;
            CheckBox checkBox;
            Button deleteButton;


            public ExerciseViewHolder(View itemView) {
                super(itemView);
                exerciseName = itemView.findViewById(R.id.exercise_name);
                exerciseDetails = itemView.findViewById(R.id.exercise_details);
                checkBox = itemView.findViewById(R.id.checkbox);
                deleteButton = itemView.findViewById(R.id.delete_button);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        exerciseList.clear();
        exerciseList.addAll(dbHandler.getAllExercises());
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void showCompletionPopup(View parentView) {

        // Inflate the popup view
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_completed, null);

        // Create and set up the dialog
        final android.app.Dialog popupDialog = new android.app.Dialog(this);
        popupDialog.setContentView(popupView);

        popupDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Animate the popup
        popupView.startAnimation(android.view.animation.AnimationUtils.loadAnimation(this, R.anim.scale_up));

        // Find the close button and set its click listener
        Button closeButton = popupView.findViewById(R.id.popup_close_button);
        closeButton.setOnClickListener(v -> popupDialog.dismiss());

        // Find the delete button and set its click listener
        Button deleteButton = popupView.findViewById(R.id.popup_delete_button);
        deleteButton.setOnClickListener(v -> {
            // Delete all exercises from the database
            dbHandler.deleteAllExercises(); // Implement this method in your DBHandler class

            // Clear the list and notify adapter
            exerciseList.clear();
            recyclerView.getAdapter().notifyDataSetChanged();

            // Show a toast and dismiss the popup
            Toast.makeText(this, "All exercises deleted", Toast.LENGTH_SHORT).show();
            popupDialog.dismiss();
        });

        popupDialog.show();
    }






}
