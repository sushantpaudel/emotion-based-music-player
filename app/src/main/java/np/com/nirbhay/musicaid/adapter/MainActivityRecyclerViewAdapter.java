package np.com.nirbhay.musicaid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;

import java.util.ArrayList;

import np.com.nirbhay.musicaid.active_android.HappySongModel;
import np.com.nirbhay.musicaid.data_set.MusicDescription;
import np.com.nirbhay.musicaid.R;
import np.com.nirbhay.musicaid.active_android.SadSongModel;

import static android.support.v4.content.res.ResourcesCompat.getDrawable;

/**
 * Created by sushant on 1/23/2018 at 9:17 AM.
 */

public class MainActivityRecyclerViewAdapter extends RecyclerView.Adapter<MainActivityRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MusicDescription> mData;

    public MainActivityRecyclerViewAdapter(Context context, ArrayList<MusicDescription> data) {
        this.context = context;
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_music_description, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView textView = holder.mTextView;
        ImageView imageView = holder.mImageView;
        final String musicDescription = mData.get(position).getMusicDescription();
        Bitmap albumArt = mData.get(position).getAlbumArt(context);
        if(albumArt !=null){
            imageView.setImageBitmap(albumArt);
        }else{
            imageView.setImageDrawable(getDrawable(context.getResources(), R.drawable.ic_audiofile,null));
        }
        textView.setText(musicDescription);
        final int finalPosition = position;
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO play music by clicking it...
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void add(int position, MusicDescription item) {
        mData.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextView;
        public ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.cardViewMusicMain);
            mImageView = (ImageView) itemView.findViewById(R.id.imageAlbumArt);
            mTextView = (TextView) itemView.findViewById(R.id.textMusicDescription);
        }
    }
}