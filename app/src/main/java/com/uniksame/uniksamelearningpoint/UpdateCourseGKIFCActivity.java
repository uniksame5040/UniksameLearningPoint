package com.uniksame.uniksamelearningpoint;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ComputerShortsModel;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ModelMotivation;

public class UpdateCourseGKIFCActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int REQUEST_UPLOAD_TYPE;
    private EditText title_edittext, description_edittext, url_edittext, page_type_edittext;
    private TextView choose_topic_text;
    private String titleTextString, descriptionTextString, imageUrlString, pageTypeTextString;
    private String isSectionSelected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course_gkifcactivity);

        title_edittext = findViewById(R.id.title_edittext);
        description_edittext = findViewById(R.id.description_edittext);
        url_edittext = findViewById(R.id.url_edittext);
        page_type_edittext = findViewById(R.id.page_type_edittext);

        Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.courses_array_list, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        choose_topic_text = findViewById(R.id.choose_topic_text);
        Button upload_all_topic_button = findViewById(R.id.upload_all_topic_button);
        upload_all_topic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validationOfTopicCourse() && REQUEST_UPLOAD_TYPE == 103) {
                    uploadAllTopics();
                } else if (validationOfTopicCourse() && REQUEST_UPLOAD_TYPE == 102) {
                    uploadCourseTopics();
                } else if (validationOfTopicCourse() && REQUEST_UPLOAD_TYPE == 101) {
                    uploadAllCourse();
                } else if (REQUEST_UPLOAD_TYPE == 404){
                    Toast.makeText(UpdateCourseGKIFCActivity
                            .this, "Please select category", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(UpdateCourseGKIFCActivity
                            .this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadAllTopics() {

        FirebaseDatabase.getInstance().getReference().child("UniksameHost").child("ComputerDailyShorts").push()
                .setValue(new ComputerShortsModel(descriptionTextString,
                        imageUrlString, titleTextString, pageTypeTextString))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UpdateCourseGKIFCActivity.this, isSectionSelected + " uploaded", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadCourseTopics() {

        FirebaseDatabase.getInstance().getReference().child("UniksameHost").child("FreeCourse").child(isSectionSelected).push()
                .setValue(new ComputerShortsModel(descriptionTextString,
                        imageUrlString, titleTextString, pageTypeTextString))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UpdateCourseGKIFCActivity.this, isSectionSelected + " uploaded", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadAllCourse() {

        FirebaseDatabase.getInstance().getReference().child("UniksameHost").child("FreeCourse").child("AllCourses").push()
                .setValue(new ComputerShortsModel(descriptionTextString,
                        imageUrlString, titleTextString, pageTypeTextString))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UpdateCourseGKIFCActivity.this, isSectionSelected + " uploaded", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadMotivationVideo() {

        FirebaseDatabase.getInstance().getReference().child("UniksameHost").child("YouTubeVideo").push()
                .setValue(new ModelMotivation(titleTextString,
                        pageTypeTextString, imageUrlString))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UpdateCourseGKIFCActivity.this, isSectionSelected + " uploaded", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 1:
                Toast.makeText(this, "" + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                isSectionSelected = adapterView.getItemAtPosition(i).toString();//"All new courses";
                REQUEST_UPLOAD_TYPE = 101;
                choose_topic_text.setText(isSectionSelected);
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                isSectionSelected = adapterView.getItemAtPosition(i).toString(); //"Daily topics";
                REQUEST_UPLOAD_TYPE = 103;
                choose_topic_text.setText(isSectionSelected);
                // Whatever you want to happen when the thrid item gets selected
                break;
            default:
                isSectionSelected = adapterView.getItemAtPosition(i).toString();
                spinnerAdapterValidation(i,isSectionSelected);
                break;

        }
    }

    private void spinnerAdapterValidation(int spinnerAdapterPosition, String isSectionSelected) {
        if (spinnerAdapterPosition != 0){
            choose_topic_text.setText(isSectionSelected);
            REQUEST_UPLOAD_TYPE = 102;
        }else {
            REQUEST_UPLOAD_TYPE = 404;
            Toast.makeText(this,  isSectionSelected, Toast.LENGTH_SHORT).show();
            choose_topic_text.setText(isSectionSelected);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean validationOfTopicCourse() {
        titleTextString = title_edittext.getText().toString().trim();
        descriptionTextString = description_edittext.getText().toString().trim();
        imageUrlString = url_edittext.getText().toString().trim();
        pageTypeTextString = page_type_edittext.getText().toString().trim();

        if (titleTextString.isEmpty()) {
            title_edittext.setError("Field cannot be empty");
            return false;
        } else if (descriptionTextString.isEmpty()) {
            description_edittext.setError("Field cannot be empty");
            return false;
        } else if (imageUrlString.isEmpty()) {
            url_edittext.setError("Field cannot be empty");
            return false;
        } else if (pageTypeTextString.isEmpty()) {
            page_type_edittext.setError("Field cannot be empty");
            return false;
        } else if (isSectionSelected.isEmpty()) {
            Toast.makeText(this, "Please select section", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            title_edittext.setError(null);
            description_edittext.setError(null);
            url_edittext.setError(null);
            page_type_edittext.setError(null);
            return true;
        }
    }

}