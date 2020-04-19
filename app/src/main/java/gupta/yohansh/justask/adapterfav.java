package gupta.yohansh.justask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class adapterfav extends RecyclerView.Adapter<adapterfav.myviewholder> {
    private List<model> models;
    private fav_fragment context;
    favhelper favoritehelper;
    model art;

    public adapterfav(List<model> models, fav_fragment context) {
        this.models = models;
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

        final model model=models.get(position);
        holder.title.setText(model.getQuestiontopic());
        holder.description.setText(model.getQuestion());


//        holder.favsave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                favoritehelper=new Favoritehelper(context);
//                Toast.makeText(context, ""+models.get(position).getTitle(), Toast.LENGTH_SHORT).show();
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView title,description,source,author,published;
        CardView cardView;
        ImageView imageView,favsave;
        ProgressBar progressBar;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.topic);
            description=itemView.findViewById(R.id.description);
            cardView= itemView.findViewById(R.id.cardview);

        }
}


}
