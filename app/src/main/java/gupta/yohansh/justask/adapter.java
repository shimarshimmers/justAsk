package gupta.yohansh.justask;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class adapter extends RecyclerView.Adapter<adapter.myviewholder> {
    private List<model> modelList;
    favhelper fv;
    private Context context;
    model art;

    public adapter(List<model> models, Context context) {
        this.modelList = models;
        this.context = context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemscard,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, final int position) {

        final model models=modelList.get(position);
        holder.title.setText("Title :  "+models.getQuestiontopic());
        holder.description.setText(models.getQuestion());
        holder.answers.setText("from email  "+models.getFromemail());


        holder.answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,answer.class);
                context.startActivity(i);
            }
        });


        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fv=new favhelper(context);
                fv.addfav(modelList.get(position));
                Toast.makeText(context, "added", Toast.LENGTH_SHORT).show();

            }
        });



    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView title,description,answers;
        Button answer;
        ImageView save;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.topic);
            description=itemView.findViewById(R.id.description);
            answers=itemView.findViewById(R.id.source);
            save=itemView.findViewById(R.id.save);
            answer=itemView.findViewById(R.id.answer);

        }
}


}
