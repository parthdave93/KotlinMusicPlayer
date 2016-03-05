package com.demo.kotlindemo.models

import com.orm.SugarRecord
import java.util.*

/**
 * Created by sotsys014 on 3/3/16.
 */


open class Song : SugarRecord() {
    var filePath: String = ""
    var fileThumb: ByteArray? = null
    var songName: String? = ""
    var songArtistName: String? = ""
    var songGenere: String? = ""
    var songAlbum : String? = ""
    var playedCount: Int = 0
    var liked: Boolean = false
    var songSub: String? = ""
    var year : String? = ""
}

fun ArrayList<Song>.compare(song:Song):Boolean{
    for(i in 0..this.size.minus(1))
    {
        if(song.Compare(this.get(i)))
            return true
    }
    return false
}

fun Song.Compare(song:Song) = (songName == song.songName && songAlbum == song.songAlbum && filePath == song.filePath)
