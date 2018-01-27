package np.com.nirbhay.musicaid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;

import java.util.ArrayList;

import np.com.nirbhay.musicaid.R;
import np.com.nirbhay.musicaid.active_android.HappySongModel;
import np.com.nirbhay.musicaid.active_android.SadSongModel;
import np.com.nirbhay.musicaid.data_set.MusicDescription;

import static android.support.v4.content.res.ResourcesCompat.getDrawable;

/**
 * Created by sushant on 1/24/2018 at 8:41 AM.
 */

public class DatabaseActivityRecycler extends MainActivityRecyclerViewAdapter {
    private ArrayList<MusicDescription> mData;
    private Context context;
    private int FLAG;
    public DatabaseActivityRecycler(Context context, ArrayList<MusicDescription> data, int FLAG) {
        super(context, data, FLAG);
        this.context = context;
        this.mData = data;
        this.FLAG = FLAG;
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
        System.err.println("FLAG --> " + FLAG);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActiveAndroid.initialize(context);
                switch(FLAG) {
                    case 1:
                        SadSongModel sadSong = new SadSongModel();
                        MusicDescription sadMusic = mData.get(finalPosition);
                        sadSong.path = sadMusic.getMusicData();
                        sadSong.title_name = sadMusic.getTitleName();
                        sadSong.display_name = sadMusic.getDisplayName();
                        sadSong.duration = sadMusic.getDuration();
                        sadSong.albumId = sadMusic.getAlbumId();
                        sadSong.artistName = sadMusic.getArtistName();
                        sadSong.save();
                        break;
                    case 2:
                        HappySongModel happySong = new HappySongModel();
                        MusicDescription happyMusic = mData.get(finalPosition);
                        happySong.path = happyMusic.getMusicData();
                        happySong.title_name = happyMusic.getTitleName();
                        happySong.display_name = happyMusic.getDisplayName();
                        happySong.duration = happyMusic.getDuration();
                        happySong.albumId = happyMusic.getAlbumId();
                        happySong.artistName = happyMusic.getArtistName();
                        happySong.save();
                        break;
                }
            }
        });

    }
}
