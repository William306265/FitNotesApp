package com.example.fitnotesapp;

public class ExerciseModel {

    private  int id;
    private String name;
    private int kg;
    private int sets;
    private int reps;
    private boolean checked;

    public ExerciseModel(int id, String name, int kg, int sets, int reps) {
        this.id = id;
        this.name = name;
        this.kg = kg;
        this.sets = sets;
        this.reps = reps;
        this.checked = false;
    }

    public  int getId() { return id; }

    public String getName() {
        return name;
    }

    public int getKg() {
        return kg;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}