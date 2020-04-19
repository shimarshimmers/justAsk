package gupta.yohansh.justask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private Button btnSignOut;
    private int RC_SIGN_IN = 100;
    FirebaseUser user;

DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInButton = findViewById(R.id.sign_in_button);
        mAuth = FirebaseAuth.getInstance();
            user=mAuth.getCurrentUser();

            btnSignOut = findViewById(R.id.sign_out_button);

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            mAuth = FirebaseAuth.getInstance();
            reference=FirebaseDatabase.getInstance().getReference("users");

            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signIn();
                }
            });

            btnSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mGoogleSignInClient.signOut();
                    Toast.makeText(MainActivity.this,"You are Logged Out",Toast.LENGTH_SHORT).show();
                    btnSignOut.setVisibility(View.INVISIBLE);
                }
            });
        }



    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{

            final GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            final FirebaseUser user=mAuth.getCurrentUser();
            FirebaseGoogleAuth(acc);

        }
        catch (ApiException e){
            Toast.makeText(MainActivity.this,"Sign In Failed",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(final GoogleSignInAccount acct) {
        //check if the account is null
        if (acct != null) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        String personName = acct.getDisplayName();
                        String email=user.getEmail();
                        String uid=user.getUid();

                        HashMap<Object,String> hashMap=new HashMap<>();
                        hashMap.put("name",personName);
                        hashMap.put("emailid",email);
                        hashMap.put("uid",uid);
                        FirebaseDatabase database=FirebaseDatabase.getInstance();
                        DatabaseReference reference=database.getReference("users");
                        reference.child(uid).setValue(hashMap);

                        Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,aftersignup.class));
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(MainActivity.this, "acc failed", Toast.LENGTH_SHORT).show();
        }
    }

}
