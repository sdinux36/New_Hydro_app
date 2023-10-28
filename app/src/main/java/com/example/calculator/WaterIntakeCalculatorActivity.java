package com.example.calculator;

// IM_2020_092_Nipuni Dhananjana
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;


// IM_2020_092_Nipuni Dhananjana
public class WaterIntakeCalculatorActivity extends AppCompatActivity {

    private EditText ageEditText;
    private RadioButton maleRadioButton, femaleRadioButton;
    private EditText feetEditText, inchesEditText;
    private EditText weightEditText;
    private Spinner weightUnitSpinner, activityLevelSpinner;
    private Spinner climateSpinner;
    private Button calculateButton;
    private TextView recommendedIntakeValue;
    private Button homeButton, nextButton;

// IM_2020_092_Nipuni Dhananjana
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ageEditText = findViewById(R.id.ageEditText);
        maleRadioButton = findViewById(R.id.maleRadioButton);
        femaleRadioButton = findViewById(R.id.femaleRadioButton);
        feetEditText = findViewById(R.id.feetEditText);
        inchesEditText = findViewById(R.id.inchesEditText);
        weightEditText = findViewById(R.id.weightEditText);
        weightUnitSpinner = findViewById(R.id.weightUnitSpinner);
        activityLevelSpinner = findViewById(R.id.activityLevelSpinner);
        climateSpinner = findViewById(R.id.climateSpinner);
        calculateButton = findViewById(R.id.calculateButton);
        recommendedIntakeValue = findViewById(R.id.recommendedIntakeValue);
        homeButton = findViewById(R.id.homeButton);
        nextButton = findViewById(R.id.nextButton);

        // IM_2020_092_Nipuni Dhananjana
        // Populate weight unit spinner
        ArrayAdapter<CharSequence> weightUnitAdapter = ArrayAdapter.createFromResource(
                this, R.array.weight_units, android.R.layout.simple_spinner_item
        );
        weightUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightUnitSpinner.setAdapter(weightUnitAdapter);

        // IM_2020_092_Nipuni Dhananjana
        // Populate activity level spinner
        ArrayAdapter<CharSequence> activityLevelAdapter = ArrayAdapter.createFromResource(
                this, R.array.activity_levels, android.R.layout.simple_spinner_item
        );
        activityLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityLevelSpinner.setAdapter(activityLevelAdapter);

        // IM_2020_092_Nipuni Dhananjana
        // Populate climate spinner
        ArrayAdapter<CharSequence> climateAdapter = ArrayAdapter.createFromResource(
                this, R.array.climate_factors, android.R.layout.simple_spinner_item
        );
        climateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        climateSpinner.setAdapter(climateAdapter);


        // IM_2020_092_Nipuni Dhananjana
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calculate recommended water intake
                calculateRecommendedWaterIntake();
            }
        });

        // IM_2020_092_Nipuni Dhananjana
        homeButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(WaterIntakeCalculatorActivity.this, sMainActivity.class);
            startActivity(intent);
        }
    });

        // IM_2020_092_Nipuni Dhananjana
        nextButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(WaterIntakeCalculatorActivity.this, sMainActivity.class);
            startActivity(intent);
        }
    });
}

        // IM_2020_092_Nipuni Dhananjana
        private void calculateRecommendedWaterIntake() {
            // Retrieve user inputs
            String ageStr = ageEditText.getText().toString().trim();
            String feetStr = feetEditText.getText().toString().trim();
            String inchesStr = inchesEditText.getText().toString().trim();
            String weightStr = weightEditText.getText().toString().trim();

            // Check if all user inputs are entered
            if (ageStr.isEmpty() || feetStr.isEmpty() || inchesStr.isEmpty() || weightStr.isEmpty()) {
                // Display an error message if any of the input fields are empty
                Toast.makeText(this, "Please enter all the inputs", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if either the male or female radio button is checked
            if (!maleRadioButton.isChecked() && !femaleRadioButton.isChecked()) {
                // Display an error message if gender is not selected
                Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
                return;
            }


            int age = Integer.parseInt(ageStr);
            int heightFeet = Integer.parseInt(feetStr);
            int heightInches = Integer.parseInt(inchesStr);
            double weight = Double.parseDouble(weightStr);
            String weightUnit = weightUnitSpinner.getSelectedItem().toString();
            double activityFactor = getActivityFactor();
            String climateFactor = climateSpinner.getSelectedItem().toString();

            // IM_2020_092
        // Calculate TDEE
        double tdee = calculateTDEE(age, heightFeet, heightInches, weight, weightUnit, activityFactor);

        // Adjust TDEE based on climate factor
        double climateFactorMultiplier = getClimateFactorMultiplier(climateFactor);
        tdee *= climateFactorMultiplier;

        // Convert TDEE to recommended water intake (ml)
        double recommendedWaterIntake = tdee;

        // Display the recommended water intake
        recommendedIntakeValue.setText(String.format("%.2f ml", recommendedWaterIntake));
    }

    // IM_2020_092
    private double getActivityFactor() {
        String selectedActivity = activityLevelSpinner.getSelectedItem().toString();
        switch (selectedActivity) {
            case "Sedentary (little to no exercise)":
                return 1.2;
            case "Lightly Active (light exercise 1-3 days / week)":
                return 1.375;
            case "Moderately Active (exercise 3-5 days / week)":
                return 1.55;
            case "Very Active (exercise 6-7 days / week)":
                return 1.725;
            case "Extremely Active (very heavy exercise / day)":
                return 1.9;
            default:
                return 1.0; // Default to sedentary
        }
    }

    // IM_2020_092
    private double calculateTDEE(int age, int heightFeet, int heightInches, double weight, String weightUnit, double activityFactor) {
        double heightInCm = ((heightFeet * 12) + heightInches) * 2.54;
        double weightInKg = (weightUnit.equals("Kg")) ? weight : weight * 0.453592;

        if (maleRadioButton.isChecked()) {
            return ((10 * weightInKg) + (6.25 * heightInCm) - (5 * age) + 5) * activityFactor;
        } else if (femaleRadioButton.isChecked()) {
            return ((10 * weightInKg) + (6.25 * heightInCm) - (5 * age) - 161) * activityFactor;
        } else {
            return 0;
        }
    }

    // IM_2020_092
    private double getClimateFactorMultiplier(String climateFactor) {
        //logic to determine the multiplier based on climate
        switch (climateFactor) {
            case "Hot and Dry":
                return 1.2;
            case "Moderate":
                return 1.0; // No adjustment for moderate climate
            default:
                return 1.0; // Default to no adjustment
        }
    }}
// IM_2020_092
