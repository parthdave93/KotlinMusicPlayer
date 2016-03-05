package com.demo.kotlindemo.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.demo.kotlindemo.R
import com.demo.kotlindemo.models.Song
import kotlinx.android.synthetic.main.item_songtile.view.*
import java.util.*

/**
 * Created by sotsys014 on 3/3/16.
 */
class SongsListAdapter(val context: Context) : RecyclerView.Adapter<SongsListAdapter.ViewHolder>() {
    var showList: ArrayList<Song>? = null
    var SongList: ArrayList<Song>? = null


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(context, showList?.get(position) as Song)
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val view = LayoutInflater.from(context).inflate(R.layout.item_songtile, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = showList?.size ?: 0


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(context: Context, song: Song) {
            if (song.album_art!=null) {
                itemView.ivSongTilePic.setImageBitmap(BitmapFactory.decodeFile(song.album_art))
                itemView.ivSongTilePic.scaleType = ImageView.ScaleType.CENTER_CROP
            } else {
                itemView.ivSongTilePic.scaleType = ImageView.ScaleType.CENTER
            }
            itemView.tvSongName.text = song.album
            itemView.tvSongSubName.text = song.artist
            if (song.artist != null)
                itemView.tvSongSubName.visibility = if (song.artist!!.length.equals(0)) View.GONE else View.VISIBLE
        }
    }


    fun filter(searchString: String) {
        for (i in 0..showList?.size!!.minus(1)) {
            if (!showList?.get(i)?.artist?.contains(searchString)!! || !showList?.get(i)?.album?.contains(searchString)!!) {
                showList?.removeAt(i)
                notifyDataSetChanged()
            }
        }
    }
}
