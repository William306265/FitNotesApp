package com.example.fitnotesapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Exercises extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);


        recyclerView = findViewById(R.id.recyclerView);


        exercises = new ArrayList<>();
        populateExerciseList();


        ExerciseAdapter adapter = new ExerciseAdapter(exercises);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_item_spacing);
        recyclerView.addItemDecoration(new SpacingItemDecoration(spacingInPixels));

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterExercises(newText, adapter);
                return true;
            }
        });

    }
    private void filterExercises(String query, ExerciseAdapter adapter) {
        List<String> filteredList = new ArrayList<>();
        for (String exercise : exercises) {
            if (exercise.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(exercise);
            }
        }


        adapter.updateList(filteredList);


        if (filteredList.isEmpty()) {
            filteredList.add("No exercises found");
        }
    }



    private void populateExerciseList() {
        String[] exercisesArray = {
                "Assisted Dip", "Band-Assisted Bench Press", "Bar Dip", "Bench Press", "Bench Press Against Band",
                "Board Press", "Cable Chest Press", "Close-Grip Bench Press", "Close-Grip Feet-Up Bench Press",
                "Decline Bench Press", "Decline Push-Up", "Dumbbell Chest Fly", "Dumbbell Chest Press",
                "Dumbbell Decline Chest Press", "Dumbbell Floor Press", "Dumbbell Pullover", "Feet-Up Bench Press",
                "Floor Press", "Incline Bench Press", "Incline Dumbbell Press", "Incline Push-Up", "Kettlebell Floor Press",
                "Kneeling Incline Push-Up", "Kneeling Push-Up", "Machine Chest Fly", "Machine Chest Press", "Pec Deck",
                "Pin Bench Press", "Push-Up", "Push-Up Against Wall", "Push-Ups With Feet in Rings", "Resistance Band Chest Fly",
                "Smith Machine Bench Press", "Smith Machine Incline Bench Press", "Smith Machine Reverse Grip Bench Press",
                "Standing Cable Chest Fly", "Standing Resistance Band Chest Fly", "Arnold Press", "Band External Shoulder Rotation",
                "Band Internal Shoulder Rotation", "Band Pull-Apart", "Barbell Front Raise", "Barbell Rear Delt Row",
                "Barbell Upright Row", "Behind the Neck Press", "Cable Lateral Raise", "Cable Rear Delt Row", "Cuban Press",
                "Dumbbell Front Raise", "Dumbbell Horizontal Internal Shoulder Rotation", "Dumbbell Horizontal External Shoulder Rotation",
                "Dumbbell Lateral Raise", "Dumbbell Rear Delt Row", "Dumbbell Shoulder Press", "Face Pull", "Front Hold",
                "Landmine Press", "Lying Dumbbell External Shoulder Rotation", "Lying Dumbbell Internal Shoulder Rotation",
                "Machine Lateral Raise", "Machine Shoulder Press", "Monkey Row", "Overhead Press", "Plate Front Raise",
                "Power Jerk", "Push Press", "Reverse Cable Flyes", "Reverse Dumbbell Flyes", "Reverse Machine Fly",
                "Seated Dumbbell Shoulder Press", "Seated Barbell Overhead Press", "Seated Smith Machine Shoulder Press",
                "Snatch Grip Behind the Neck Press", "Squat Jerk", "Split Jerk", "Barbell Curl", "Barbell Preacher Curl",
                "Bayesian Curl", "Bodyweight Curl", "Cable Crossover Bicep Curl", "Cable Curl With Bar", "Cable Curl With Rope",
                "Concentration Curl", "Drag Curl", "Dumbbell Curl", "Dumbbell Preacher Curl", "Hammer Curl", "Incline Dumbbell Curl",
                "Machine Bicep Curl", "Spider Curl", "Barbell Standing Triceps Extension", "Barbell Lying Triceps Extension",
                "Bench Dip", "Crossbody Cable Triceps Extension", "Close-Grip Push-Up", "Dumbbell Lying Triceps Extension",
                "Dumbbell Standing Triceps Extension", "Overhead Cable Triceps Extension", "Tricep Bodyweight Extension",
                "Tricep Pushdown With Bar", "Tricep Pushdown With Rope", "Air Squat", "Barbell Hack Squat", "Barbell Lunge",
                "Barbell Walking Lunge", "Belt Squat", "Body Weight Lunge", "Bodyweight Leg Curl", "Box Jump", "Box Squat",
                "Bulgarian Split Squat", "Chair Squat", "Dumbbell Lunge", "Dumbbell Squat", "Front Squat", "Goblet Squat",
                "Hack Squat Machine", "Half Air Squat", "Hip Adduction Machine", "Jumping Lunge", "Landmine Hack Squat",
                "Landmine Squat", "Leg Curl On Ball", "Leg Extension", "Leg Press", "Lying Leg Curl", "Nordic Hamstring Eccentric",
                "Pause Squat", "Reverse Barbell Lunge", "Romanian Deadlift", "Safety Bar Squat", "Seated Leg Curl",
                "Shallow Body Weight Lunge", "Side Lunges (Bodyweight)", "Smith Machine Bulgarian Split Squat", "Smith Machine Squat",
                "Squat", "Step Up", "Zercher Squat", "Zombie Squat", "Assisted Chin-Up", "Assisted Pull-Up", "Back Extension",
                "Banded Muscle-Up", "Barbell Row", "Barbell Shrug", "Block Clean", "Block Snatch", "Cable Close Grip Seated Row",
                "Cable Wide Grip Seated Row", "Chest to Bar", "Chin-Up", "Clean", "Clean and Jerk", "Deadlift", "Deficit Deadlift",
                "Dumbbell Deadlift", "Dumbbell Row", "Dumbbell Shrug", "Floor Back Extension", "Good Morning", "Gorilla Row",
                "Hang Clean", "Hang Power Clean", "Hang Power Snatch", "Hang Snatch", "Inverted Row", "Inverted Row with Underhand Grip",
                "Jefferson Curl", "Jumping Muscle-Up", "Kettlebell Swing", "Lat Pulldown With Pronated Grip", "Lat Pulldown With Supinated Grip",
                "Muscle-Up (Bar)", "Muscle-Up (Rings)", "One-Handed Cable Row", "One-Handed Lat Pulldown", "Pause Deadlift",
                "Pendlay Row", "Power Clean", "Power Snatch", "Pull-Up", "Pull-Up With a Neutral Grip", "Rack Pull", "Ring Pull-Up",
                "Ring Row", "Scap Pull-Up", "Seal Row", "Seated Machine Row", "Single Leg Deadlift with Kettlebell", "Snatch",
                "Snatch Grip Deadlift", "Stiff-Legged Deadlift", "Banded Side Kicks", "Cable Pull Through", "Clamshells",
                "Cossack Squat", "Death March with Dumbbells", "Dumbbell Romanian Deadlift", "Dumbbell Frog Pumps", "Fire Hydrants",
                "Frog Pumps", "Glute Bridge", "Hip Abduction Against Band", "Hip Abduction Machine", "Hip Thrust", "Hip Thrust Machine",
                "Hip Thrust With Band Around Knees", "Lateral Walk With Band", "Machine Glute Kickbacks", "One-Legged Glute Bridge",
                "One-Legged Hip Thrust", "Reverse Hyperextension", "Romanian Deadlift", "Single Leg Romanian Deadlift",
                "Standing Glute Kickback in Machine", "Step Up", "Ball Slams", "Cable Crunch", "Crunch", "Dead Bug", "Hanging Knee Raise",
                "Hanging Leg Raise", "Hanging Sit-Up", "Hanging Windshield Wiper", "High to Low Wood Chop with Band",
                "Horizontal Wood Chop with Band", "Kneeling Ab Wheel Roll-Out", "Kneeling Plank", "Kneeling Side Plank",
                "Lying Leg Raise", "Lying Windshield Wiper", "Lying Windshield Wiper with Bent Knees", "Machine Crunch",
                "Mountain Climbers", "Oblique Crunch", "Oblique Sit-Up", "Plank", "Plank with Leg Lifts", "Russian Twist",
                "Side Plank", "Sit-Up", "Barbell Standing Calf Raise", "Eccentric Heel Drop", "Heel Raise", "Seated Calf Raise",
                "Standing Calf Raise", "Barbell Wrist Curl", "Barbell Wrist Curl Behind the Back", "Bar Hang", "Dumbbell Wrist Curl",
                "Farmers Walk", "Fat Bar Deadlift", "Gripper", "One-Handed Bar Hang", "Plate Pinch", "Plate Wrist Curl", "Towel Pull-Up",
                "Wrist Roller"
        };

        // Convert the array into a list and add it to the exercises list
        for (String exercise : exercisesArray) {
            exercises.add(exercise);
        }
    }


    private class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

        private List<String> exerciseList;


        ExerciseAdapter(List<String> exercises) {
            this.exerciseList = exercises;
        }
        public void updateList(List<String> newList) {
            exerciseList = newList;
            notifyDataSetChanged();
        }


        @Override
        public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ExerciseViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(ExerciseViewHolder holder, int position) {
            String exercise = exerciseList.get(position);
            holder.exerciseText.setText(exercise);
            holder.itemView.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.rounded_item));

            holder.exerciseText.setTextColor(Color.parseColor("#000000"));


        }


        @Override
        public int getItemCount() {
            return exerciseList.size();
        }


        public class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView exerciseText;

            ExerciseViewHolder(View itemView) {
                super(itemView);
                exerciseText = itemView.findViewById(android.R.id.text1);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                String exercise = exerciseList.get(getAdapterPosition());
                Intent intent = new Intent(Exercises.this, ExerciseDetail.class);
                intent.putExtra("exercise_name", exercise);
                startActivity(intent);
            }
        }
    }
}
