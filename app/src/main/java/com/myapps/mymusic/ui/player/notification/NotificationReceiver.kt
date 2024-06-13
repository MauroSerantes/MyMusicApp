package com.myapps.mymusic.ui.player.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.myapps.mymusic.ui.player.notification.PlayerNotification.PlayerNotificationConstants.ACTION_NEXT
import com.myapps.mymusic.ui.player.notification.PlayerNotification.PlayerNotificationConstants.ACTION_PLAY
import com.myapps.mymusic.ui.player.notification.PlayerNotification.PlayerNotificationConstants.ACTION_PREVIOUS
import com.myapps.mymusic.ui.player.service.MediaPlayerService
import com.myapps.mymusic.ui.player.utils.ActionConstants.NEXT
import com.myapps.mymusic.ui.player.utils.ActionConstants.PLAY_PAUSE
import com.myapps.mymusic.ui.player.utils.ActionConstants.PREV

class NotificationReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == null) return
        val intent1 = Intent(context,MediaPlayerService::class.java)
        when(intent.action){
            ACTION_NEXT ->{
                intent1.action = NEXT
                context?.startService(intent1)
            }
            ACTION_PREVIOUS->{
                intent1.action = PREV
                context?.startService(intent1)
            }
            ACTION_PLAY->{
                intent1.action = PLAY_PAUSE
                context?.startService(intent1)
            }
            else->{}
        }
    }
}