package fr.arnaud_camus.leren.network

import android.content.Context
import android.util.Log
import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import fr.arnaud_camus.leren.models.Word
import io.realm.Realm
import java.io.ByteArrayInputStream
import java.io.InputStreamReader

/**
 * Created by arnaud on 3/11/17.
 */
class GetInitialDictionary: Request<List<Word>> {
    val mListener: Response.Listener<List<Word>>
    val mContext: Context

    public constructor(context: Context,
                       listener: Response.Listener<List<Word>>,
                       errorListener: Response.ErrorListener)
            : super(Method.GET,
                    "https://docs.google.com/spreadsheets/d/1vJ5IRwkwB-639Fs5tmbM-DpE7poUSdZLVRPppe4RjLc/pub?output=tsv",
                    errorListener) {
        this.mListener = listener
        this.mContext = context
        setShouldCache(false)
    }

    override fun parseNetworkResponse(response: NetworkResponse) : Response<List<Word>>  {
        val inputStream = ByteArrayInputStream(response.data)
        val ditctionary: List<Word>?
        try {
            ditctionary = Word.parseInputStream(InputStreamReader(inputStream))
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            ditctionary.forEach {
                realm.copyToRealmOrUpdate(it)
            }
            realm.commitTransaction()
            realm.close()
        } catch (e: Exception) {
            return Response.error(ParseError(e))
        }
        return Response.success(ditctionary, HttpHeaderParser.parseCacheHeaders(response))
    }


    override  fun deliverResponse(response: List<Word>) = mListener.onResponse(response)
}