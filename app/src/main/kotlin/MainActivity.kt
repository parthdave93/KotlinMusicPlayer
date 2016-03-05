package com.demo.kotlindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.AdapterView
import com.demo.kotlindemo.adapters.SongsListAdapter
import com.demo.kotlindemo.models.Song
import com.demo.kotlindemo.utils.FileFinder
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_main.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action0
import rx.schedulers.Schedulers
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {


    var TAG = "MainActivity"
    var fileFinder = FileFinder()
    var songsList: ArrayList<Song>? = null
    var mAdapter: SongsListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        songsList = ArrayList()
        mAdapter = SongsListAdapter(this)
        rvSongsList?.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rvSongsList?.adapter = mAdapter

        mp3FromDb()
    }

    fun mp3FromDb() {
        songsList = SugarRecord.listAll(Song::class.java) as (ArrayList<Song>)
        mAdapter?.SongList = songsList
        mAdapter?.notifyDataSetChanged()
        mp3FileFind()
    }

    fun mp3FileFind() {
        fileFinder.dbSongsList = songsList
        fileFinder.fetchDirectories()
        fileFinder.fetchDirectories()
        fileFinder.matchFilePattern = ".mp3"

        var onComeplete = Action0 {
            mAdapter?.SongList = songsList
            mAdapter?.notifyDataSetChanged()
        }

        Observable.from(fileFinder.directories).map { file ->
            //            Log.d(TAG, "directory:" + file.name)
            fileFinder.scanDirectories(file.absolutePath)

        }.observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext { song ->
            if (song != null) {
                songsList?.add(song)
                mAdapter?.notifyDataSetChanged()
            }
        }.subscribe()
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

}
