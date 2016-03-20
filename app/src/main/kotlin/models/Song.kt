package com.demo.kotlindemo.models

import com.orm.SugarRecord
import java.io.Serializable
import java.util.*

//open for public class
open class Song : SugarRecord(), Serializable {

    var id: String? = null
    var title: String? = null
    var album: String? = null
    var artist: String? = null
    var album_art: String? = null
    var album_key: String? = null
    var maxyear: String? = null
    var minyear: String? = null
    var numsongs_by_artist: String? = null
    var numsongs: String? = null
    var album_id: String? = null
    var albumThumb: Boolean = true
    var tags: String = ""

}

inline fun Song.filter(filterString: String): Boolean =
        this.album?.contains(filterString, true)!! || this.artist?.contains(filterString, true)!! || this.tags?.contains(filterString, true)!!


//to compare song object from list of song object and if it finds d object then return true
inline fun ArrayList<Song>.compare(song: Song): Boolean {
    for (i in 0..this.size.minus(1)) {
        if (song.Compare(this.get(i)))
            return true
    }
    return false
}

//to compare song object with another song object
inline fun Song.Compare(song: Song) = (id == song.id && album == song.album && artist == song.artist)