package com.example.fitverse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewInformationActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Measurements");
    List<String> measurementsList;
    ArrayAdapter adapter;
    Measurements measurements;

    ListView measurementsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_information);
        measurementsListView = findViewById(R.id.lv_measurements);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                measurements = new Measurements();
                measurementsList = new ArrayList<String>();
                for (DataSnapshot measurementFromFirebase : snapshot.getChildren())
                {
                    measurements = measurementFromFirebase.getValue(Measurements.class);
                    measurementsList.add(measurements.ToString());

                }

                adapter = new ArrayAdapter(ViewInformationActivity.this, android.R.layout.simple_list_item_1, measurementsList);

                measurementsListView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(ViewInformationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}