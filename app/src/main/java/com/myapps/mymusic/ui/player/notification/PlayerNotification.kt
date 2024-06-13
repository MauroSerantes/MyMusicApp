package com.myapps.mymusic.ui.player.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.myapps.mymusic.R
import com.myapps.mymusic.domain.TrackModel
import com.myapps.mymusic.ui.player.main.PlayerActivity
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

class PlayerNotification {
    companion object PlayerNotificationConstants{
        const val CHANNEL_ID_1 = "CHANNEL_1"
        const val CHANNEL_ID_2 = "CHANNEL_2"
        const val ACTION_NEXT = "NEXT"
        const val ACTION_PREVIOUS = "PREVIOUS"
        const val ACTION_PLAY = "PLAY"
    }



    fun createNotification(context: Context, trackModel: TrackModel, notificationManager: NotificationManagerCompat,isPlaying:Boolean):Notification{

        val intent = Intent(context,PlayerActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) PendingIntent.FLAG_IMMUTABLE else 0
        )

        createNotificationChannels(notificationManager)

        val prevIntent = Intent(context,NotificationReceiver::class.java)
            .setAction(ACTION_PREVIOUS)
        val prevPendingIntent = PendingIntent.getBroadcast(context,0,prevIntent,PendingIntent.FLAG_IMMUTABLE)

        val nextIntent = Intent(context,NotificationReceiver::class.java)
            .setAction(ACTION_NEXT)
        val nextPendingIntent = PendingIntent.getBroadcast(context,0,nextIntent,PendingIntent.FLAG_IMMUTABLE)

        val playIntent = Intent(context,NotificationReceiver::class.java)
            .setAction(ACTION_PLAY)
        val playPendingIntent = PendingIntent.getBroadcast(context,0,playIntent,PendingIntent.FLAG_IMMUTABLE)


        val drawable = if(isPlaying){
            R.drawable.pause
        }
        else{
            R.drawable.play
        }


        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID_2)
            .setSmallIcon(R.drawable.baseline_music_note_24)
            .setContentTitle(trackModel.title)
            .setContentText(trackModel.artistName)
            .addAction(R.drawable.skip_previous,"Previous",prevPendingIntent)
            .addAction(drawable,"Play",playPendingIntent)
            .addAction(R.drawable.skip_next,"Next",nextPendingIntent)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(
                    MediaSessionCompat(context,"PlayerAudio")
                        .sessionToken
                )
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOnlyAlertOnce(true)

        return notificationBuilder.build()
    }
    private fun createNotificationChannels(notificationManager: NotificationManagerCompat){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelOne = NotificationChannel(
                CHANNEL_ID_1,
                "Channel(1)",
                NotificationManager.IMPORTANCE_HIGH
            )
            val channelTwo = NotificationChannel(
                CHANNEL_ID_2,
                "Channel(2)",
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationManager.createNotificationChannel(channelOne)
            notificationManager.createNotificationChannel(channelTwo)
        }
    }
}
