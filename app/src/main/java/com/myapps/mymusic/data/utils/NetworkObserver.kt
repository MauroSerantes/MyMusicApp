package com.myapps.mymusic.data.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

import javax.inject.Inject


class NetworkObserver @Inject constructor(application: Application){

    private val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    enum class Status{
        Connected,
        Disconnected
    }

    fun observeStatus(): Flow<Status> {
        return callbackFlow {

            val networkRequest = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()

            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(Status.Connected) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(Status.Disconnected) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(Status.Disconnected) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(Status.Disconnected) }
                }
            }

            connectivityManager.registerNetworkCallback(networkRequest,networkCallback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(networkCallback)
            }
        }.distinctUntilChanged()
    }
}
