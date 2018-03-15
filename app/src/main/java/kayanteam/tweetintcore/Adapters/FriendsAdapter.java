package kayanteam.tweetintcore.Adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import kayanteam.tweetintcore.R;
import kayanteam.tweetintcore.Utiles.ResizeViewsUtiles;

/**
 * Created by mosta on 3/15/2018.
 */


public class FriendsAdapter  extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder>  {

    private List<User> users;
    private Context context;
    public FriendsAdapter(List<User> users,Context context){
        this.users=users;
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.friends_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        // set up any onClickListener you need on the view here
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name_txt.setText(users.get(position).name);
        holder.bio_txt.setText(users.get(position).description);
        Picasso.with(context).load(users.get(position).profileImageUrlHttps).
                placeholder(R.mipmap.twitter_icon).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        if (users!=null)
        return users.size();
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView name_txt,bio_txt;
        public CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.friends_item_profileImg_imageview);
            name_txt=(TextView)itemView.findViewById(R.id.friends_item_name_textview);
            bio_txt=(TextView)itemView.findViewById(R.id.friends_item_bio_textview);
            cardView=(CardView)itemView.findViewById(R.id.friends_item_cardview);

            // resize imageview
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            ResizeViewsUtiles.resize_views(context,imageView,6,6,true,false);
            else
                ResizeViewsUtiles.resize_views(context,imageView,6,6,true,true);
        }
    }
}
