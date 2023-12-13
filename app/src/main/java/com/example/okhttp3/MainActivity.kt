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

    private lateinit var activityText: TextView
    private lateinit var participiantsText: TextView
    private lateinit var typeText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityText = findViewById(R.id.activity)
        participiantsText = findViewById(R.id.participians)
        typeText = findViewById(R.id.type)
        val loadBtn: Button = findViewById(R.id.LoadNewBtn)

        loadBtn.setOnClickListener{
            fetchData()
        }
    }

    private fun fetchData(){
        // Tworzenie klienta OkHttp
        val client = OkHttpClient()

        // Tworzenie żądania
        val request = Request.Builder()
            .url("https://www.boredapi.com/api/activity")
            .build()

        //Wywołanie żądania asynchronicznie
        client.newCall(request).enqueue(object : Callback {
            //Wykona się jeśli zapytanie się nie powiedzie
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Error fetching data", Toast.LENGTH_SHORT).show()
                }
            }

            //Wykona po odebraniu odpowiedzi od serwera
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (!responseData.isNullOrBlank()) {
                    //Parsujemy odpowiedż za pomocą biblioteki Gson na obiekt klasy ActivityDto
                    val gson = Gson()
                    val item = gson.fromJson(responseData, ActivityDto::class.java)

                    runOnUiThread {
                        activityText.text = item.activity
                        participiantsText.text = item.participants.toString()
                        typeText.text = item.type
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