package gupta.yohansh.justask;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class feeds_fragment extends Fragment {


    private RecyclerView mrecyclerview;
    private DatabaseReference ref,reference;
    private FirebaseUser currentuser;
    private FirebaseAuth mauth;


    ArrayList<model> mylist = new ArrayList<>();






    String uid;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



            View view= inflater.inflate(R.layout.feeds, container, false);
            mauth=FirebaseAuth.getInstance();
            FirebaseUser user=mauth.getCurrentUser();
            if(user==null){
                Intent i=new Intent(getContext(),MainActivity.class);
                startActivity(i);

            }
            else{
                currentuser=mauth.getCurrentUser();
                uid=currentuser.getUid();


                mrecyclerview = (RecyclerView) view.findViewById(R.id.myrecyclerview);
                mrecyclerview.setHasFixedSize(true);
                mrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                final adapter adp = new adapter(mylist, getActivity());
                mrecyclerview.setAdapter(adp);

                ref = FirebaseDatabase.getInstance().getReference("questionsasked");
                ref.keepSynced(true);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mylist.clear();

                        for(DataSnapshot ds:dataSnapshot.getChildren()){

                            mylist.add(ds.getValue(model.class));

                        }

                        adp.notifyDataSetChanged();



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }









            return  view;


        }



}



