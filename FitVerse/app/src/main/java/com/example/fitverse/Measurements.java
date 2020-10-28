package com.example.fitverse;

import android.media.Image;
import android.widget.ImageView;

public class Measurements
{
    String Height;
    String Weight;
    String TargetWeight;
    String TargetCalorieIntake;
    String DailyWeightChange;
    ImageView image;

    public Measurements()
    {
        //empty constructor do not remove
    }

    public Measurements(String height, String weight, String targetWeight, String targetCalorieIntake, String dailyWeightChange)
    {
        Height = height;
        Weight = weight;
        TargetWeight = targetWeight;
        TargetCalorieIntake = targetCalorieIntake;
        DailyWeightChange = dailyWeightChange;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getTargetWeight() {
        return TargetWeight;
    }

    public void setTargetWeight(String targetWeight) {
        TargetWeight = targetWeight;
    }

    public String getTargetCalorieIntake() {
        return TargetCalorieIntake;
    }

    public void setTargetCalorieIntake(String targetCalorieIntake) {
        TargetCalorieIntake = targetCalorieIntake;
    }

    public String getDailyWeightChange() {
        return DailyWeightChange;
    }

    public void setDailyWeightChange(String dailyWeightChange) {
        DailyWeightChange = dailyWeightChange;
    }

    public String ToString()
    {
     return "Height:" + " " + " " + Height + "\r\n" + "Weight:" + " " + " " + Weight + "\r\n" + "Target Weight:" + " " + " " + TargetWeight +
             "\r\n" + "Target Calorie Intake:" + " " + " " + TargetCalorieIntake + "\r\n" + "Daily Weight Change:" + " " + " " + DailyWeightChange;
    }

}
