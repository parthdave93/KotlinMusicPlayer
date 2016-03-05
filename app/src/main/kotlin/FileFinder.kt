package com.demo.kotlindemo.utils

import android.media.MediaMetadataRetriever
import android.os.Environment
import com.demo.kotlindemo.models.Song
import com.demo.kotlindemo.models.compare
import java.io.File
import java.util.*

/**
 * Created by sotsys014 on 2/3/16.
 */
class FileFinder {

    var MEDIA_PATH = Environment.getExternalStorageDirectory().path + "/"
    var directories: Array<File>? = null
    var thumbgs: ArrayList<String>? = null
    var songsList: HashMap<String, String>? = null
    var matchFilePattern: String = ".mp3"
    var TAG = "fileFinder"
    var dbSongsList: ArrayList<Song>? = null

    fun fetchDirectories() {
        songsList = HashMap()
        thumbgs = ArrayList()
        MEDIA_PATH = Environment.getExternalStorageDirectory().path + "/"
        //        Log.d(TAG, "MEDIA_PATH:" + MEDIA_PATH)
        var homeDirectory = File(MEDIA_PATH)
        //list down d list of directories
        directories = homeDirectory.listFiles()
    }

    fun scanDirectories(directoryName: String): Song? {
        var directory = File(directoryName)
        //        Log.d(TAG, "scanDirectories:" + directory.name)
        if (directory.exists()) {
            var directoriesInsideFile = directory.listFiles()
            if (directoriesInsideFile != null && directoriesInsideFile.size > 0) {
                for (i in 0..directoriesInsideFile.size.minus(1)) {
                    if (directoriesInsideFile.get(i).isDirectory)
                        scanDirectories(directoriesInsideFile.get(i).absolutePath)
                    else
                        scanForFiles(directoriesInsideFile.get(i))
                }
            } else {
                return scanForFiles(directory)
            }
        }
        return null
    }

    fun scanForFiles(searchedFile: File): Song? {
        //        Log.d(TAG, "scanForFiles:" + searchedFile.name)
        if (searchedFile.name.endsWith(matchFilePattern, true)) {
            songsList?.put(searchedFile.name, searchedFile.absolutePath)


            var song = Song()
            var mediaMetadataRetriver = MediaMetadataRetriever()
            mediaMetadataRetriver.setDataSource(searchedFile.absolutePath)
            song.filePath = searchedFile.absolutePath
            //                    song.fileThumb = mediaMetadataRetriver.embeddedPicture
            song.songName = mediaMetadataRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)

            if (song.songName?.length == 0)
                song.songName = searchedFile.name
            else if (song.songName == null)
                song.songName = searchedFile.name

            song.songSub = mediaMetadataRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST)
            song.songGenere = mediaMetadataRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE)
            song.songArtistName = mediaMetadataRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
            song.songAlbum = mediaMetadataRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)
            song.year = mediaMetadataRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE)

            song.fileThumb = mediaMetadataRetriver.getEmbeddedPicture();
            if (dbSongsList != null)
                if (!dbSongsList?.compare(song)!!) {
                    song.save()
                    dbSongsList?.add(song)
                }
            return song
        }
        return null
    }

}