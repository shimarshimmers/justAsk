package gupta.yohansh.justask;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class aftersignup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button next;
    EditText topic;
    String lang,topics;
    private DatabaseReference reference;
    private FirebaseUser currentuser;
    private FirebaseAuth mauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftersignup);
        mauth=FirebaseAuth.getInstance();
        currentuser=mauth.getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("users").child(currentuser.getUid());
        next=findViewById(R.id.button);
        topic=findViewById(R.id.topic);
        Spinner spinner=findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                topics=topic.getText().toString();
                if(TextUtils.isEmpty(topics)){
                    Toast.makeText(aftersignup.this, "Please give topic ", Toast.LENGTH_SHORT).show();

                }
                if(TextUtils.isEmpty(lang)){
                    Toast.makeText(aftersignup.this, "Please choose language ", Toast.LENGTH_SHORT).show();

                }

                else{
                    HashMap<Object,String> hashMap=new HashMap<>();
                    hashMap.put("language",lang);
                    hashMap.put("topics",topics);
                    reference.setValue(hashMap);
                    startActivity(new Intent(aftersignup.this,main2.class));
                    finish();

                }
            }
        });
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, android.view.View view, int i, long l) {
        lang=adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
