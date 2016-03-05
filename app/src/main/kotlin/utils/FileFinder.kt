package com.demo.kotlindemo.utils
import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore

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
    }
}