package np.com.nirbhay.musicaid.active_android;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by sushant on 1/23/2018 at 11:19 AM.
 */

public class FunSongModel extends Model {
    @Column(name = "music_path",unique = true)
    public String path;
    FunSongModel(String path){
        this.path = path;
    }

    public List<SadSongModel> getAllList(){
        return new Select().from(SadSongModel.class).execute();
    }
}
