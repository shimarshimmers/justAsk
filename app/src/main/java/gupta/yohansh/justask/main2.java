package gupta.yohansh.justask;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class main2 extends AppCompatActivity {

    Fragment selectedFragment;
    postquestion_fragment postquestion_fragment;
    fav_fragment favFragment;
    feeds_fragment feeds_fragment;
    FirebaseAuth mauth;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        mauth=FirebaseAuth.getInstance();
        FirebaseUser user=mauth.getCurrentUser();
        if(user==null){
            Intent i=new Intent(main2.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        else{
            FrameLayout frameLayout=findViewById(R.id.fragmentcontainer);

            frameLayout.refreshDrawableState();
            frameLayout.removeAllViews();
            postquestion_fragment=new postquestion_fragment();
            favFragment=new fav_fragment();
            feeds_fragment=new feeds_fragment();


            BottomNavigationView  bottomNav = findViewById(R.id.bottomnavigation);
            bottomNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem menuItem) {

                }
            });
            bottomNav.setOnNavigationItemSelectedListener(navListener);


            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,feeds_fragment).addToBackStack(null)
                    .commit();
        }



    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    selectedFragment=null;

                    switch (item.getItemId()) {
                        case R.id.explore:
                            selectedFragment = new feeds_fragment();
                            break;
                        case R.id.courses:
                            selectedFragment = new postquestion_fragment();
                            break;
                        case R.id.favorites:
                            selectedFragment = new fav_fragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,
                            selectedFragment).commit();

                    return true;
                }


            };

    @Override
    public void onBackPressed() {

    }
}
