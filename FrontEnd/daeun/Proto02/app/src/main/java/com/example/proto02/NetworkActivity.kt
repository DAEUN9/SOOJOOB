package com.example.proto02

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class NetworkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)

        NetworkTask().execute()

    }
}

class NetworkTask(): AsyncTask<Any?, Any?, Any?>() {
    override fun doInBackground(vararg p0: Any?): Any? {
        val urlString: String = "http://mellowcode.org/json/students/"
        val url: URL = URL(urlString)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

        connection.requestMethod="GET"
        connection.setRequestProperty("Content-Type", "application/json")
        Log.d("connn", "111")
        var buffer = ""
        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            Log.d("connn","inputstream : " + connection.inputStream)
            val reader = BufferedReader(
                InputStreamReader(
                    connection.inputStream,
                    "UTF-8"
                )
            )
            buffer = reader.readLine()
            Log.d("connn", "inputstream : " + buffer)
        }

        val data = Gson().fromJson(buffer, Array<PersonFromServer>::class.java)
        val age = data[0].age

        Log.d("conn", "age : " + age)
        return null
    }
}