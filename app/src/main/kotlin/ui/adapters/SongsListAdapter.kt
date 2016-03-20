package com.demo.kotlindemo.adapters

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import com.demo.kotlindemo.R
import com.demo.kotlindemo.models.Song
import com.demo.kotlindemo.models.filter
import kotlinx.android.synthetic.main.item_songtile.view.*
import startActivityWithBundle
import ui.activity.PlayerActivity
import java.util.*

open class SongsListAdapter(public val context: Context) : RecyclerView.Adapter<SongsListAdapter.ViewHolder>() {
    var showList: ArrayList<Song>? = null
    var SongList: ArrayList<Song>? = null
    var itemFilter: ItemFilter = ItemFilter()
    var filteredText: String = ""


    //binding the views data
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        //showing the data
        holder?.bindView(SongList?.get(position) as Song)
    }

    //create view holder
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val view = LayoutInflater.from(context).inflate(R.layout.item_songtile, parent, false)
        return ViewHolder(view)
    }

    //items count
    override fun getItemCount(): Int = SongList?.size ?: 0

    //view holder
    open inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(song: Song) {
            if (song.album_art != null) {
                itemView.ivSongTilePic.setImageBitmap(BitmapFactory.decodeFile(song.album_art))
                itemView.ivSongTilePic.scaleType = ImageView.ScaleType.CENTER_CROP
            } else {
                itemView.ivSongTilePic.scaleType = ImageView.ScaleType.CENTER
            }
            itemView.tvSongName.text = song.album
            itemView.tvSongSubName.text = song.artist
            if (song.tags.isNotEmpty()) {
                itemView.tvTags.text = "#" + song.tags
                itemView.tvTags.visibility = View.VISIBLE
            } else {
                itemView.tvTags.visibility = View.GONE
            }
            if (song.artist != null)
                itemView.tvSongSubName.visibility = if (song.artist!!.length.equals(0)) View.GONE else View.VISIBLE

            itemView.setOnClickListener {
                var bundle = Bundle()
                bundle.putSerializable("data", song)

                var pair1 = android.support.v4.util.Pair(itemView.ivSongTilePic, context.getString(R.string.transition_songPic)) as android.support.v4.util.Pair<View, String>
                var pair2 = android.support.v4.util.Pair(itemView.tvSongName, context.getString(R.string.transition_songName))as android.support.v4.util.Pair<View, String>

                var options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(context as Activity, pair1, pair2);

                context.startActivityWithBundle(context, PlayerActivity::class.java, bundle, options)
            }
        }
    }

    open inner class ItemFilter : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults? {
            var filterString = constraint.toString().toLowerCase()
            var results = FilterResults()
            var filteredList = ArrayList<Song>()
            //if it's not filtered then we have to find string from all list
            if (!filterString.isEmpty() && !filterString.contains(filteredText)) {
                if (showList != null)
                    for (i in 0..(showList as ArrayList<Song>).size.minus(1)) {
                        if (showList?.get(i)!!.filter(filterString))
                            filteredList.add((showList as ArrayList<Song>).get(i))
                    }
            } else if (filterString.isEmpty()) {
                filteredList.addAll(SongList!!)
            }
            //if it's already filtered then y bother to find from all list character sequence will be continued so just find from what we are shawing
            else {

                if (SongList != null)
                    for (i in 0..(SongList as ArrayList<Song>).size.minus(1)) {
                        if (SongList?.get(i)!!.filter(filterString))
                            filteredList.add((SongList as ArrayList<Song>).get(i))
                    }
            }

            results.values = filteredList
            results.count = filteredList?.size


            return results
        }

        override fun publishResults(CharSequence: CharSequence?, filterResults: FilterResults?) {
            SongList = filterResults?.values as ArrayList<Song>?
            notifyDataSetChanged()
            filteredText = "" + CharSequence
        }
    }
}
