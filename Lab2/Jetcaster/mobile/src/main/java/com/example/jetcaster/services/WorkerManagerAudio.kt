package com.example.jetcaster.services

import androidx.work.CoroutineWorker
import android.content.Context
import androidx.work.WorkerParameters
import android.media.AudioManager
import android.media.MediaPlayer

class WorkerManagerAudio (
    appContext: Context,
    workerParameters: WorkerParameters
):CoroutineWorker(appContext, workerParameters) {
    var mediaPlayer: MediaPlayer = MediaPlayer()

    override suspend fun doWork(): Result {
        fun play(){
            var audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

            try {
                mediaPlayer.setDataSource(audioUrl)
                mediaPlayer.prepare()
                mediaPlayer.start()

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
        fun stop(){
            try {
                mediaPlayer.stop()

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
        if (mediaPlayer.isPlaying){
            stop()
        }
        else{
            play()
        }
        return Result.success()
    }
}