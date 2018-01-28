package np.com.nirbhay.musicaid;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;

import java.util.ArrayList;
import java.util.List;

import np.com.nirbhay.musicaid.active_android.HappySongModel;
import np.com.nirbhay.musicaid.active_android.SadSongModel;
import np.com.nirbhay.musicaid.adapter.DatabaseActivityRecycler;
import np.com.nirbhay.musicaid.adapter.MainActivityRecyclerViewAdapter;
import np.com.nirbhay.musicaid.data_set.MusicDescription;

public class DatabaseActivity extends AppCompatActivity {
    public int FLAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        addMusicToList();
        ActiveAndroid.initialize(this);
        FLAG = getIntent().getIntExtra("FLAG",0);
        System.err.println("Flag --> "+FLAG);
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void addMusicToList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<MusicDescription> data = listAllMusicFiles();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProgressBar progressBar = findViewById(R.id.progressBarDatabase);
                        progressBar.setVisibility(View.GONE);
                        addToRecyclerMain(data);
                    }
                });
            }
        }).start();
    }

    private ArrayList<MusicDescription> listAllMusicFiles() {
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION
        };

        Cursor cursor = this.managedQuery(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null);

        List<HappySongModel> listHappy = new HappySongModel().getAllList();
        List<SadSongModel> listSad = new SadSongModel().getAllList();
        ArrayList<MusicDescription> songs = new ArrayList<>();
        try{
            while (cursor.moveToNext()) {
                int count = 0;
                for (HappySongModel o : listHappy) {
                    if (o.title_name.equals(cursor.getString(2))) {
                        count++;
                        break;
                    }
                }
                if (count == 0) {
                    for (SadSongModel o : listSad) {
                        if (o.title_name.equals(cursor.getString(2))) {
                            count++;
                            break;
                        }
                    }
                }
                if (count == 0) {
                    MusicDescription music = new MusicDescription();
                    music.setAlbumId(cursor.getInt(0));
                    music.setArtistName(cursor.getString(1));
                    music.setTitleName(cursor.getString(2));
                    music.setMusicData(cursor.getString(3));
                    music.setDisplayName(cursor.getString(4));
                    music.setDuration(cursor.getInt(5));
                    music.setAlbumArt(albumArt(cursor.getInt(0)));
                    songs.add(music);
                }
            }
        }catch (Exception ignored){}
        return songs;
    }

    private Bitmap albumArt(int albumId) {
        Uri sArtworkUri = Uri
                .parse("content://media/external/audio/albumart");
        Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, albumId);
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), albumArtUri);
            bitmap = Bitmap.createScaledBitmap(bitmap, 60, 60, true);
        } catch (Exception exception) {
            bitmap = null;
        }
        return bitmap;
    }

    private void addToRecyclerMain(ArrayList<MusicDescription> data) {
        RecyclerView mRecyclerView = findViewById(R.id.recyclerViewDatabase);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        MainActivityRecyclerViewAdapter adapter = new DatabaseActivityRecycler(this, data,FLAG);
        mRecyclerView.setAdapter(adapter);
    }
}
