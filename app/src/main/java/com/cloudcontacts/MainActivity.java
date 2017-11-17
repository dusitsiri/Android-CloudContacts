package com.cloudcontacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private EditText name, salary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.nameEditText);
        salary = findViewById(R.id.salaryEditText);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("User");
//        databaseReference.setValue("John");
//        databaseReference.child("1").setValue("John1");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User addUser = dataSnapshot.getValue(User.class);
                Log.i("i", "Added: "+ addUser.name+","+addUser.salary);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                User changeUser = dataSnapshot.getValue(User.class);
                Log.i("i", "Changed: "+ changeUser.name+","+changeUser.salary);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                User removeUser = dataSnapshot.getValue(User.class);
                Log.i("i", "Removed: "+ removeUser.name+","+removeUser.salary);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                User moveUser = dataSnapshot.getValue(User.class);
                Log.i("i", "Moved: "+ moveUser.name+","+moveUser.salary);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("i", "Cancelled: "+databaseError.getMessage());
            }
        });
    }

    public void addContactOnClick(View view) {
        if (!name.getText().toString().equals("") && !salary.getText().toString().equals("")) {
            User user = new User(name.getText().toString(),Integer.parseInt(salary.getText().toString()));
            databaseReference.push().setValue(user);
            name.setText("");
            salary.setText("");
            Toast.makeText(MainActivity.this,"Added",Toast.LENGTH_LONG).show();
        }
    }
}
