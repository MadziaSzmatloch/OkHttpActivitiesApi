package com.example.okhttp3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.okhttp3.data.ActivityDto
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activitytext: TextView = findViewById(R.id.activity)
        val participiantstext: TextView = findViewById(R.id.participians)
        val typetext: TextView = findViewById(R.id.type)
        val loadBtn: Button = findViewById(R.id.LoadNewBtn)
        loadBtn.setOnClickListener{
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://www.boredapi.com/api/activity")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Error fetching data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body?.string()
                    if (!responseData.isNullOrBlank()) {
                        val gson = Gson()
                        val item = gson.fromJson(responseData, ActivityDto::class.java)

                        runOnUiThread {
                            activitytext.text = item.activity
                            participiantstext.text = item.participants.toString()
                            typetext.text = item.type
                        }


                    } else {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "Empty response", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
    }
}