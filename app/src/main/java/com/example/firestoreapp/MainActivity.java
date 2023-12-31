package com.example.firestoreapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    private Button saveBtn;
    private Button updateBtn;
    private Button deleteBtn;
    private Button readBtn;
    private TextView textView;
    private EditText nameET;
    private EditText emailET;

    //Firebase Firestore:
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference friendRef =
            db.collection("Users").document("bVPXKS3UysIaGFlBBbl6");
    //replace "Friends" the document Id of the Field for Update and Delete


    private CollectionReference collectionReference = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        updateBtn = findViewById(R.id.updateBTN);
        saveBtn = findViewById(R.id.SaveBTN);
        deleteBtn = findViewById(R.id.deleteBTN);
        readBtn = findViewById(R.id.readBTN);
        textView = findViewById(R.id.text);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToNewDocument();
            }
        });
        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAllDocumentsInCollection();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSpecificDocument();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAll();
            }
        });
}


    private  void saveDataToNewDocument(){
        String name = nameET.getText().toString();
        String email = emailET.getText().toString();

        Friend friend = new Friend(name, email);

        collectionReference.add(friend).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String docId = documentReference.getId();
            }
        });
}

private void GetAllDocumentsInCollection(){
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                String data = "";
                //This code is executed when the data retrieval is successful
                //the queryDocumentSnapshot contains the documents in the collection
                //Each QuerySnapshot ---> represents a document in a collection
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){

                    //transforming snapshots into Object
                    Friend friend = snapshot.toObject(Friend.class);

                    data += "Name: "+friend.getName() + " Email: "+friend.getEmail()+"\n";
                }

                textView.setText(data);
            }
        });
}

private  void UpdateSpecificDocument(){
        String name = nameET.getText().toString();
        String email = emailET.getText().toString();

        friendRef.update("name",name);
        friendRef.update("email",email);
}

private void DeleteAll() {
        friendRef.delete();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.menu,menu);
        return true;
    }
}

