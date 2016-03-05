package com.demo.kotlindemo.ui.activity

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import com.demo.kotlindemo.R
import com.demo.kotlindemo.adapters.SongsListAdapter
import com.demo.kotlindemo.models.Song
import com.demo.kotlindemo.models.filterSongs
import com.demo.kotlindemo.rx.IterableCursor
import com.demo.kotlindemo.utils.FileFinder
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_main.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {


    var TAG = "MainActivity"
    var songsList: ArrayList<Song>? = null
    var mAdapter: SongsListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setSupportActionBar(toolbar)

        songsList = ArrayList()
        mAdapter = SongsListAdapter(this)
        rvSongsList?.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rvSongsList?.adapter = mAdapter

        mp3FromDb()
    }

    fun mp3FromDb() {
        songsList = SugarRecord.listAll(Song::class.java) as (ArrayList<Song>)
        mAdapter?.SongList = songsList
        mAdapter?.showList = songsList
        mAdapter?.notifyDataSetChanged()

        //this will fetch all mp3 files from sd card
        mp3FileFind()
    }

    fun mp3FileFind() {

        Observable.from(IterableCursor(FileFinder().scanForMp3s(this.contentResolver))).map { cursor: Cursor? ->

            var song = Song()

            song.id = cursor?.getString(0)
            song.album = cursor?.getString(1)
            song.artist = cursor?.getString(2)
            song.album_art = cursor?.getString(3)
            song.numsongs = cursor?.getString(4)
            song.album_key = cursor?.getString(5)
            song.numsongs = cursor?.getString(6)
            song.maxyear = cursor?.getString(7)
            song.minyear = cursor?.getString(8)
            Log.d(TAG, "directory:" + song.artist)
            song
        }.observeOn(Schedulers.newThread()).doOnNext { song ->
            //what this will do is add songs one by one in arraylist

            if (song != null) {
                songsList?.add(song)
                mAdapter?.notifyDataSetChanged()
            }
        }.observeOn(AndroidSchedulers.mainThread()).subscribe()
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflator = menuInflater
        menuInflator.inflate(R.menu.search, menu)
        var searchManager = getSystemService(Context.SEARCH_SERVICE)

        var myActionMenuItem = menu?.findItem(R.id.menu_search)
        var searchView = myActionMenuItem?.getActionView() as SearchView?



        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if ( !searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem?.collapseActionView();
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                filterSongs(mAdapter, query)
                return false
            }
        })

        return true
    }


}
