package np.com.nirbhay.musicaid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import np.com.nirbhay.musicaid.R;
import np.com.nirbhay.musicaid.active_android.HappySongModel;
import np.com.nirbhay.musicaid.data_set.MusicDescription;

import static android.support.v4.content.res.ResourcesCompat.getDrawable;

/**
 * Created by sushant on 1/23/2018 at 7:43 PM.
 */

public class HappySongRecyclerViewAdapter extends MainActivityRecyclerViewAdapter {
    private ArrayList<MusicDescription> mData;
    private Context context;
    public HappySongRecyclerViewAdapter(Context context, ArrayList<MusicDescription> data) {
        super(context, data, 2);
        this.context = context;
        this.mData = data;
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
                Toast.makeText(context,mData.get(finalPosition).getDisplayName(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new HappySongModel().deleteData(mData.get(finalPosition).getMusicData());
                mData.remove(finalPosition);
                notifyDataSetChanged();
                return false;
            }
        });
    }
}
