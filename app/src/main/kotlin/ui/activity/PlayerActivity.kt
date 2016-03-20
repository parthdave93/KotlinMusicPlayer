package ui.activity

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Explode
import android.view.Window
import com.demo.kotlindemo.R
import com.demo.kotlindemo.models.Song
import kotlinx.android.synthetic.main.activity_player.*
import java.util.*


//TODO: play song and show playlist , init and save playlist
open class PlayerActivity : AppCompatActivity() {


    var songs = ArrayList<Song>()
    var currentSongPosition:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            //            var changeTransform = TransitionInflater.from(this).inflateTransition(R.transition.songplayer)
            //            var explodeTransform = TransitionInflater.from(this).inflateTransition(android.R.transition.explode)

            // inside your activity (if you did not enable transitions in your theme)
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            // set an enter transition
            getWindow().setEnterTransition(Explode());
            // set an exit transition
            getWindow().setExitTransition(Explode());
            // set an enter transition
            //            getWindow().setEnterTransition(changeTransform);
            //            getWindow().setExitTransition(explodeTransform);
        }

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_player)

        var extras = intent.extras

        if(extras!=null){
            if(extras.containsKey("data"))
                songs.add(extras.getSerializable("data") as Song)

        }


        initControals()
    }

    fun initControals(){
        ivSongPic.setImageBitmap(BitmapFactory.decodeFile(songs.get(currentSongPosition).album_art))
        tvSongName.text = songs.get(currentSongPosition).album
    }
}
