package app.ditodev.ceritain.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkUtil {

   fun isOnline(context: Context): Boolean {
        val connManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connManager.activeNetwork ?: return false
        val actionNet = connManager.getNetworkCapabilities(network) ?: return false
        return when {
            actionNet.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actionNet.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actionNet.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            actionNet.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }

}