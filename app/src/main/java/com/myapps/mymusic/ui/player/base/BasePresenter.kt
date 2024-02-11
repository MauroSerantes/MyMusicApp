package com.myapps.mymusic.ui.player.base

import android.content.Intent
import android.os.Bundle


abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V>{
    protected var view:V ?= null

    override fun onAttach(view: V) {
        this.view = view
    }

    override fun onDetach() {
        this.view = null
    }
}