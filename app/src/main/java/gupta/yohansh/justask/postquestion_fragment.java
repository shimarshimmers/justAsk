package gupta.yohansh.justask;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class postquestion_fragment extends Fragment {

// String searched;
   private DatabaseReference reference,databaseReference;
    private FirebaseUser currentuser;
    private FirebaseAuth mauth;


    EditText topic,question;
    Button post;
    TextView click;
    String topicstring,questionstring;




    @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.post, container, false);
        mauth=FirebaseAuth.getInstance();
        currentuser=mauth.getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("users").child(currentuser.getUid());
        databaseReference=FirebaseDatabase.getInstance().getReference("questionsasked");

        topic=view.findViewById(R.id.topics);
        question=view.findViewById(R.id.question);
        post=view.findViewById(R.id.post);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topicstring=topic.getText().toString();
                questionstring=question.getText().toString();
                if(TextUtils.isEmpty(topic.getText().toString())){
                    Toast.makeText(getContext(), "Please give topic ", Toast.LENGTH_SHORT).show();

                }
                if(TextUtils.isEmpty(question.getText().toString())){
                    Toast.makeText(getContext(), "Please write your question ", Toast.LENGTH_SHORT).show();

                }

                else{
                    HashMap<Object,String> hashMap1=new HashMap<>();
                    HashMap<Object,String> hashMap2=new HashMap<>();
                    hashMap1.put("questiontopic",topicstring);
                    hashMap1.put("question",questionstring);
                    hashMap2.put("questiontopic",topicstring);
                    hashMap2.put("question",questionstring);
                    hashMap2.put("from",currentuser.getUid());
                    hashMap2.put("fromemail",currentuser.getEmail());
                    databaseReference.push().setValue(hashMap2);
                    reference.child("questions").push().setValue(hashMap1);
                    Toast.makeText(getContext(), "Your Question is posted successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });






            return view;

        }


}



