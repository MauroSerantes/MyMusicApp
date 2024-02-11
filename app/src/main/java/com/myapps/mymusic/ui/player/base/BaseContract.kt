package com.myapps.mymusic.ui.player.base

import android.content.Intent
import android.os.Bundle


interface BaseContract {
    interface View

    interface Presenter<V: View>{
        fun onAttach(view: V)
        fun onDetach()
    }
}