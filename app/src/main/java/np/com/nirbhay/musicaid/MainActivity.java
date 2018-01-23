package np.com.nirbhay.musicaid;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import np.com.nirbhay.musicaid.active_android.HappySongModel;
import np.com.nirbhay.musicaid.active_android.SadSongModel;
import np.com.nirbhay.musicaid.adapter.HappySongRecyclerViewAdapter;
import np.com.nirbhay.musicaid.adapter.MainActivityRecyclerViewAdapter;
import np.com.nirbhay.musicaid.adapter.SadSongRecyclerViewAdapter;
import np.com.nirbhay.musicaid.data_set.MusicDescription;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addMusicToList();
        ActiveAndroid.initialize(this);
        FloatingActionButton fabSad = findViewById(R.id.sad_menu_item);
        FloatingActionButton fabHappy = findViewById(R.id.happy_menu_item);
        FloatingActionButton fabLaughy = findViewById(R.id.laugh_menu_item);
        fabSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MusicDescription> data = changePathSadSong(new SadSongModel().getAllList());
                System.err.println(data.size());
                addToRecyclerSadSong(data);
            }
        });
        fabHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MusicDescription> data = changePathHappySong(new HappySongModel().getAllList());
                System.err.println(data.size());
                addToRecyclerHappySong(data);
            }
        });
        fabLaughy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MachineLearningActivity.class));
            }
        });
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private ArrayList<MusicDescription> changePathHappySong(List<HappySongModel> data) {
        ArrayList<MusicDescription> musicDescription = new ArrayList<>();
        for (HappySongModel song : data) {
            System.err.println();
            MusicDescription music = new MusicDescription();
            music.setArtistName(song.artistName);
            music.setTitleName(song.title_name);
            music.setMusicData(song.path);
            music.setDisplayName(song.display_name);
            music.setDuration(song.duration);
            music.setAlbumArt(albumArt(song.albumId));
            musicDescription.add(music);
        }
        return musicDescription;
    }

    private ArrayList<MusicDescription> changePathSadSong(List<SadSongModel> data) {
        ArrayList<MusicDescription> musicDescription = new ArrayList<>();
        for (SadSongModel song : data) {
            System.err.println();
            MusicDescription music = new MusicDescription();
            music.setArtistName(song.artistName);
            music.setTitleName(song.title_name);
            music.setMusicData(song.path);
            music.setDisplayName(song.display_name);
            music.setDuration(song.duration);
            music.setAlbumArt(albumArt(song.albumId));
            musicDescription.add(music);
        }
        return musicDescription;
    }

    private void addMusicToList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<MusicDescription> data = listAllMusicFiles();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProgressBar progressBar = findViewById(R.id.progressBarMain);
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

        ArrayList<MusicDescription> songs = new ArrayList<>();
        while (cursor.moveToNext()) {
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
        RecyclerView mRecyclerView = findViewById(R.id.recyclerViewMain);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        MainActivityRecyclerViewAdapter adapter = new MainActivityRecyclerViewAdapter(this, data);
        mRecyclerView.setAdapter(adapter);
    }

    private void addToRecyclerSadSong(ArrayList<MusicDescription> data) {
        RecyclerView mRecyclerView = findViewById(R.id.recyclerViewMain);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        MainActivityRecyclerViewAdapter adapter = new SadSongRecyclerViewAdapter(this, data);
        mRecyclerView.setAdapter(adapter);
    }

    private void addToRecyclerHappySong(ArrayList<MusicDescription> data) {
        RecyclerView mRecyclerView = findViewById(R.id.recyclerViewMain);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        MainActivityRecyclerViewAdapter adapter = new HappySongRecyclerViewAdapter(this, data);
        mRecyclerView.setAdapter(adapter);
    }
}
