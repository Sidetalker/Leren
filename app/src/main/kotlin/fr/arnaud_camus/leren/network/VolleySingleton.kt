package fr.arnaud_camus.leren.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack

/**
 * Created by arnaud on 3/11/17.
 */
class VolleySingleton private constructor(private val context: Context) {

    private var requestQueue: RequestQueue?

    init {
        requestQueue = getRequestQueue()
    }

    fun getRequestQueue(): RequestQueue {
        if (requestQueue == null) {
            val cache = DiskBasedCache(context.cacheDir, CACHE_SIZE)
            val network = BasicNetwork(HurlStack())
            requestQueue = RequestQueue(cache, network)
            requestQueue!!.start()
        }
        return requestQueue!!
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        getRequestQueue().add(req)
    }

    fun stop() {
        getRequestQueue().stop()
    }

    companion object {
        private val CACHE_SIZE = 1024 * 1024 * 10
        private var instance: VolleySingleton? = null

        @Synchronized fun getInstance(context: Context): VolleySingleton {
            if (instance == null) {
                instance = VolleySingleton(context)
            }
            return instance!!
        }
    }
}