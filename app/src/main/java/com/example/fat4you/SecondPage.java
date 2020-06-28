package com.example.fat4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SecondPage extends AppCompatActivity {
    private static final String SHARED_PREFS = "sharedPrefs";
    ImageView img1;
    ImageView img2, img3, img4, img5, img6;
    FirebaseAuth mAuth;
    Dialog myDialog;
private int count;
    ImageView Profile;
    DatabaseReference mDatabase;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("count");
        mDatabase.addValueEventListener(new ValueEventListener() {
            private String TAG=ListProducts.class.getSimpleName();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//               count=dataSnapshot.child("count").getValue(Integer.TYPE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

        myDialog = new Dialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mAuth = FirebaseAuth.getInstance();

        AllAngleExpandableButton button = (AllAngleExpandableButton) findViewById(R.id.button_expandable);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.menu, R.drawable.random, R.drawable.logoutt, R.drawable.like,R.drawable.plus};
        for (int i = 0; i < drawable.length; i++) {
            ButtonData buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
            buttonDatas.add(buttonData);
        }
        button.setButtonDatas(buttonDatas);
        button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
                //do whatever you want,the param index is counted from startAngle to endAngle,
                //the value is from 1 to buttonCount - 1(buttonCount if aebIsSelectionMode=true)
                switch (index) {
                    case 2:
                     mAuth.signOut();
                       LoginManager.getInstance().logOut();
                        startActivity(new Intent(SecondPage.this, MainActivity.class));
                        break;
                    case 3:
                        mDatabase= FirebaseDatabase.getInstance().getReference().child("count");
                        mDatabase.child("count").setValue(count+1);
                       Toast.makeText(getApplicationContext(), "Tanks For The Like Total Like " + count + "", Toast.LENGTH_LONG).show();

                        break;
                    case 4:
                        Intent inten = new Intent(SecondPage.this, AddRecipit.class);
                        startActivity(inten);
                        break;
                    case 1:

                        // Here, we are generating a random number
                        Random generator = new Random();
                        int number = generator.nextInt(5) + 1;
                        // The '5' is the number of activities

                        Class activity = null;

                        // Here, we are checking to see what the output of the random was
                        switch (number) {
                            case 1:
                                // E.g., if the output is 1, the activity we will open is ActivityOne.class
                                activity = listofproducts.class;
                                break;
                            case 2:
                                activity = listofproducts.class;
                                break;
                            case 3:
                                activity = listofproducts.class;
                                break;
                            case 4:
                                activity = listofproducts.class;
                                break;
                            default:
                                activity = SecondPage.class;
                                break;
                        }
                        // We use intents to start activities
                        Intent intent = new Intent(SecondPage.this, listofproducts.class);
                        intent.putExtra("product","rice");

                        startActivity(intent);
                }
            }


            @Override
            public void onExpand() {

            }

            @Override
            public void onCollapse() {

            }
        });
        img1 = findViewById(R.id.riceimg);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondPage.this, listofproducts.class);
                intent.putExtra("product","rice");

                startActivity(intent);
            }
        });
        img2 = findViewById(R.id.meat);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondPage.this, listofproducts.class);
                intent.putExtra("product","meat");

                startActivity(intent);
            }
        });
        img3 = findViewById(R.id.milk);
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondPage.this, listofproducts.class);
                intent.putExtra("product","milk");

                startActivity(intent);
            }
        });
        img4 = findViewById(R.id.pasta);
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondPage.this, listofproducts.class);
                intent.putExtra("product","pasta");
                startActivity(intent);
            }
        });

        img5 = findViewById(R.id.fish);
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondPage.this, listofproducts.class);
                intent.putExtra("product","fish");

                startActivity(intent);
            }
        });
        img6 = findViewById(R.id.potatoo);
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondPage.this, listofproducts.class);
                intent.putExtra("product","potato");

                startActivity(intent);
            }
        });


    }

    public void onBackPressed() {
        if (AccessToken.getCurrentAccessToken() != null) {
            startActivity(new Intent(this,SecondPage.class));

        }

    }



    }
