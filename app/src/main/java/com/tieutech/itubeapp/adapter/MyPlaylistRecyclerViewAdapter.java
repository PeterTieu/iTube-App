package com.tieutech.itubeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tieutech.itubeapp.R;
import com.tieutech.itubeapp.model.YouTubeURL;
import java.util.List;

//ABOUT: RecyclerView Adapter for the list of YouTube URLs
public class MyPlaylistRecyclerViewAdapter extends RecyclerView.Adapter<MyPlaylistRecyclerViewAdapter.ViewHolder>{

    //======= DEFINE VARIABLES =======
    private List<YouTubeURL> youTubeURLs; //Arraylist of YouTubeURLs for the RecyclerView
    private Context context; //Application Context
    private OnMyPlaylistListener onRemoveClick; //Interface defining method to override in MyPlayListActivity

    //Constructor for the RecyclerView Adapter
    public MyPlaylistRecyclerViewAdapter(List<YouTubeURL> youTubeURLs, Context context, OnMyPlaylistListener onRemoveClick) {
        this.youTubeURLs = youTubeURLs;
        this.context = context;
        this.onRemoveClick = onRemoveClick;
    }


    //======= DEFINE METHODS =======
    //Upon the creation of the ViewHolder of each item in the RecyclerView
    @NonNull
    @Override
    public MyPlaylistRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.youtube_url_row, parent, false); //Create the view of the ViewHolder
        return new ViewHolder(itemView, onRemoveClick); //Link the ViewHolder to the RecyclerView Adapter
    }

    //Modify the display of the view elements in the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MyPlaylistRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.ytURLTextView.setText(youTubeURLs.get(position).getYtURL());
    }

    //Return the size of the dataset
    @Override
    public int getItemCount() {
        return youTubeURLs.size();
    }

    //ViewHolder for each item in the RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //View variables
        TextView ytURLTextView;
        ImageView removeImageView;

        //Interface variables
        OnMyPlaylistListener onRemoveClick;

        public ViewHolder(@NonNull View itemView, OnMyPlaylistListener onRemoveClick){
            super(itemView);

            //Obtain views
            ytURLTextView = (TextView) itemView.findViewById(R.id.ytURLTextView);
            removeImageView = (ImageView) itemView.findViewById(R.id.removeImageView);

            //Define the interface variable
            this.onRemoveClick = onRemoveClick;

            itemView.findViewById(R.id.removeImageView).setOnClickListener(this);
        }

        //OnClickListener for the ViewHolder
        @Override
        public void onClick(View view) {

            //Listener for the entire Order item
            if (view == removeImageView) {
                onRemoveClick.onRemoveClick(getAdapterPosition()); //Defined in MyPlaylistActivity
            }
        }
    }

    //Interface to be implemented in the MyPlaylistActivity
    public interface OnMyPlaylistListener {
        void onRemoveClick(int position); //Callback method to override in the MyPlaylistActivity
    }
}