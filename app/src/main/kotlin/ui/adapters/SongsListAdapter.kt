package com.demo.kotlindemo.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import com.demo.kotlindemo.R
import com.demo.kotlindemo.models.Song
import kotlinx.android.synthetic.main.item_songtile.view.*
import java.util.*

open class SongsListAdapter(val context: Context) : RecyclerView.Adapter<SongsListAdapter.ViewHolder>() {
    var showList: ArrayList<Song>? = null
    var SongList: ArrayList<Song>? = null
    var itemFilter :ItemFilter = ItemFilter()





    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(SongList?.get(position) as Song)
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val view = LayoutInflater.from(context).inflate(R.layout.item_songtile, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = SongList?.size ?: 0


    open class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(song: Song) {
            if (song.album_art != null) {
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



    public inner class ItemFilter : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults? {
            var filterString = constraint.toString().toLowerCase()
            var results = FilterResults()
            var filteredList = ArrayList<Song>()
            if (showList != null)
                for (i in 0..(showList as ArrayList<Song>).size.minus(1)) {
                    if(showList?.get(i)?.album?.contains(filterString,true)!! || showList?.get(i)?.artist?.contains(filterString,true)!!)
                        filteredList.add((showList as ArrayList<Song>).get(i))
                }

            results.values = filteredList
            results.count = filteredList?.size
            return results
        }

        override fun publishResults(CharSequence: CharSequence?, filterResults: FilterResults?) {
            SongList = filterResults?.values as ArrayList<Song>?
            notifyDataSetChanged()
        }
    }
}
