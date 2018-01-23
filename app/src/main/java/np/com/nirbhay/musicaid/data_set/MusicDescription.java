package np.com.nirbhay.musicaid.data_set;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by sushant on 1/23/2018 at 9:22 AM.
 */

public class MusicDescription {
    private Bitmap albumArt;
    private String musicDescription;


    public Integer getAlbumId() {
        return albumId;
    }

    private Integer albumId;

    public String getArtistName() {
        return artistName;
    }

    private String artistName;

    public String getTitleName() {
        return titleName;
    }

    private String titleName;

    private String musicData;

    public String getDisplayName() {
        return displayName;
    }

    private String displayName;

    public Integer getDuration() {
        return duration;
    }

    private Integer duration;
    public String getMusicData() {
        return musicData;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public void setMusicData(String data) {
        this.musicData = data;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Bitmap getAlbumArt(Context context) {
        return albumArt;
    }

    public String getMusicDescription() {
        musicDescription = artistName + "\n" + titleName + "\n(" + (duration).toString() + ")";
        return musicDescription;
    }

    public void setAlbumArt(Bitmap albumArt) {
        this.albumArt = albumArt;
    }

    public void setMusicDescription(String musicDescription) {
        this.musicDescription = musicDescription;
    }
}
