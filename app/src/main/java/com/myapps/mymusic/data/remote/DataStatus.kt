package com.myapps.mymusic.data.remote

import android.provider.ContactsContract.RawContacts.Data

enum class Status{
    LOADING,
    SUCCESS,
    ERROR
}

data class DataStatus<out T>(val status: Status, val data : T?, val messageError:String?){
    companion object{
        fun <T> loading(data: T? = null):DataStatus<T>  = DataStatus(Status.LOADING,data,null)
        fun <T> success(data: T?): DataStatus<T> = DataStatus(Status.SUCCESS,data,null)
        fun <T> error(message:String?): DataStatus<T> = DataStatus(Status.ERROR,null, message)
    }

}