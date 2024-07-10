package ir.developre.chistangame.global

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import ir.developre.chistangame.R


class PlayMusicService : Service() {

    private var myPlayer = MediaPlayer()

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        myPlayer = MediaPlayer.create(this, R.raw.music_game)
        myPlayer.isLooping = true

    }

    @Deprecated("Deprecated in Java")
    override fun onStart(intent: Intent?, startid: Int) {
        myPlayer.start()

    }

    override fun onDestroy() {
        myPlayer.stop()
    }

}