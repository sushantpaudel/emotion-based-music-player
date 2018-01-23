package np.com.nirbhay.musicaid.active_android;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by sushant on 1/23/2018 at 11:18 AM.
 */

@Table(name = "SadSong")
public class SadSongModel extends Model {
    @Column(name = "music_path",unique = true)
    public String path;
    @Column(name = "title_name")
    public String title_name;
    @Column(name = "display_name")
    public String display_name;
    @Column(name = "duration")
    public int duration;
    @Column(name = "album_id")
    public int albumId;
    @Column(name = "artist_name")
    public String artistName;
    public List<SadSongModel> getAllList(){
        return new Select().from(SadSongModel.class).execute();
    }
}
