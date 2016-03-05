package com.demo.kotlindemo.utils
import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore

/**
 * Created by sotsys014 on 2/3/16.
 */
class FileFinder {
    fun scanForMp3s(contentResolver: ContentResolver): Cursor {
        val projection: Array<String> = arrayOf(
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.ALBUM_ART,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS,
                MediaStore.Audio.Albums.ALBUM_KEY,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS,
                MediaStore.Audio.Albums.FIRST_YEAR,
                MediaStore.Audio.Albums.LAST_YEAR

        )

        var selection: String = ""
        var selectionArgs: Array<String>? = null
        var sortOrder = MediaStore.Audio.Media.ALBUM + " ASC"
        var cursor = contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, sortOrder)
        cursor.moveToFirst()
        return cursor
        /*for (i in 0..cursor.count) {
            var song = Song()

            song.id = cursor.getColumnName(0)
            song.album = cursor.getColumnName(1)
            song.artist = cursor.getColumnName(2)
            song.album_art = cursor.getColumnName(3)
            song.numsongs = cursor.getColumnName(4)
            song.album_id = cursor.getColumnName(5)
            song.album_key = cursor.getColumnName(6)
            song.numsongs = cursor.getColumnName(7)
            song.numsongs_by_artist = cursor.getColumnName(8)
            song.maxyear = cursor.getColumnName(9)
            song.minyear = cursor.getColumnName(10)

        }*/

    }
}