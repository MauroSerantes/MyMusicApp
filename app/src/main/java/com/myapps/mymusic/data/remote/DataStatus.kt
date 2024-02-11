package com.myapps.mymusic.data.remote

enum class Status{
    SUCCESS,
    ERROR
}

data class DataStatus<out T>(val status: Status, val data : T?, val messageError:String?){
    companion object{
        fun <T> success(data: T?): DataStatus<T> = DataStatus(Status.SUCCESS,data,null)

        fun <T> error(message:String?): DataStatus<T> = DataStatus(Status.ERROR,null, message)
    }
}