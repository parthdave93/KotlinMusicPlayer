package com.demo.kotlindemo.models

import android.util.Log
import com.demo.kotlindemo.adapters.SongsListAdapter
import com.orm.SugarRecord
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.*
import java.util.concurrent.TimeUnit

//open for public class
open class Song : SugarRecord() {
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
}


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


//to filter songs from adapter
inline fun filterSongs(adapter: SongsListAdapter?, searchString: String) {
    Log.d("searchString", "" + searchString)
    if (searchString.length > 0) {
        //    adapter?.showList = adapter?.SongList
        Observable.just(searchString).doOnNext {
            adapter?.itemFilter?.filter(searchString)
        }.observeOn(AndroidSchedulers.mainThread()).debounce(6, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers.mainThread()).subscribe()
    } else {
        adapter?.SongList = adapter?.showList
        adapter?.notifyDataSetChanged()
    }
}