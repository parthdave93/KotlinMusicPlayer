package com.demo.kotlindemo.models

import android.util.Log
import com.demo.kotlindemo.adapters.SongsListAdapter
import com.orm.SugarRecord
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by sotsys014 on 3/3/16.
 */


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

inline fun ArrayList<Song>.compare(song: Song): Boolean {
    for (i in 0..this.size.minus(1)) {
        if (song.Compare(this.get(i)))
            return true
    }
    return false
}

inline fun Song.Compare(song: Song) = (id == song.id && album == song.album && artist == song.artist)

fun filterSongs(adapter: SongsListAdapter?, searchString: String) {
    adapter?.showList?.clear()
    adapter?.notifyDataSetChanged()
    Log.d("searchString", "" + searchString)
    if (searchString.length > 0) {
        //    adapter?.showList = adapter?.SongList
        Observable.from(adapter?.SongList).filter {
            Log.d("Song", "" + it.album)
            Log.d("searchString", "" + searchString)
            (it?.album?.contains(searchString)!! || it?.artist?.contains(searchString)!!)
        }.observeOn(Schedulers.newThread()).doOnNext {
            adapter?.showList?.add(it)
            adapter?.notifyDataSetChanged()
        }.observeOn(AndroidSchedulers.mainThread()).debounce(6, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers.mainThread()).subscribe()
    } else {
        adapter?.showList = adapter?.SongList
        adapter?.notifyDataSetChanged()
    }
}