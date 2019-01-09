package com.example.phosphor.idecided;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phosphor.idecided.Model.Constants;
import com.google.firebase.auth.FirebaseAuth;

public class CountyListActivity extends BaseActivity {

    ListView listViewCounties;

    FirebaseAuth mAuth;

    String [] Counties = {"Baringo","Bomet","Bungoma","Busia","Elgeyo Marakwet","Embu","Garissa","Homa Bay",
            "Isiolo","Kajiado","Kakamega","Kericho","Kiambu","Kilifi","Kirinyaga","Kisii","Kisumu",
            "Kitui","Kwale","Laikipia","Lamu","Machakos","Makueni","Mandera","Meru","Migori","Marsabit","Mombasa",
            "Muranga","Nairobi","Nakuru","Nandi","Narok","Nyamira","Nyandarua","Nyeri","Samburu","Siaya","Taita Taveta",
            "Tana River","Tharaka-Nithi","Turkana","Uasin Gishu","Vihiga","Wajir","West Pokot"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_county_list);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null){
            startActivity(new Intent(this, UserLoginActivity.class));
            finish();
        }

        listViewCounties = findViewById(R.id.listViewCounties);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, Counties);
        listViewCounties.setAdapter(adapter);

        listViewCounties.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 4){
                    String item = "elgeyomarakwet";
                    setUserConstants(item);
                    Intent myintent = new Intent(getApplication(),LeadersExpListViewActivity.class);
                    startActivity(myintent);
                }

                if (position == 7){
                    String item = "homabay";
                    setUserConstants(item);
                    Intent myintent = new Intent(getApplication(),LeadersExpListViewActivity.class);
                    startActivity(myintent);
                }

                if (position == 38){
                    String item = "taitataveta";
                    setUserConstants(item);
                    Intent myintent = new Intent(getApplication(),LeaderDetailsActivity.class);
                    startActivity(myintent);
                }

                if (position == 39){
                    String item = "tanariver";
                    setUserConstants(item);
                    Intent myintent = new Intent(getApplication(),LeaderDetailsActivity.class);
                    startActivity(myintent);
                }

                if (position == 42){
                    String item = "uasingishu";
                    setUserConstants(item);
                    Intent myintent = new Intent(getApplication(),LeaderDetailsActivity.class);
                    startActivity(myintent);
                }

                if (position == 45){
                    String item = "westpokot";
                    setUserConstants(item);
                    Intent myintent = new Intent(getApplication(),LeaderDetailsActivity.class);
                    startActivity(myintent);
                }

                if (position != 4 && position != 7 && position != 38 && position != 39 && position != 42 && position !=45) {
                    String item = (String) listViewCounties.getItemAtPosition(position);
                    setUserConstants(item);
                    Toast.makeText(getApplication(), "You selected : " + item, Toast.LENGTH_SHORT).show();
                    Intent myintent = new Intent(getApplication(), LeadersExpListViewActivity.class);
                    startActivity(myintent);
                }
            }
        });
    }

    public void setUserConstants(String item){
        Constants.selectedCounty=item;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                signOut();
                finish();
                startActivity(new Intent(this, UserLoginActivity.class));
                break;

            case R.id.menuSettings:
                Toast.makeText(this, "You clicked settings", Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
