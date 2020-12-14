package com.studioos.batuguntingkertas

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import java.util.*

class SplashScreen : AppCompatActivity() {

    val image by lazy { findViewById<ImageView>(R.id.iv_title) }
    val batu by lazy { findViewById<ImageView>(R.id.iv_batu) }
    val kertas by lazy { findViewById<ImageView>(R.id.iv_kertas) }
    val gunting by lazy { findViewById<ImageView>(R.id.iv_gunting) }
    var animDuration = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onResume() {
        super.onResume()

        val url = "https://i.ibb.co/HC5ZPgD/splash-screen1.png"

        val ivTuTigngTas = mutableListOf(batu, kertas, gunting)

        Glide.with(this).load(url).into(image);
        image.animate().alpha(0.5f).scaleY(1.5f).scaleX(1.5f).setDuration(animDuration).start()
        Handler().postDelayed({
            image.animate().alpha(1f).scaleY(1f).scaleX(1f).setDuration(animDuration).start()
        }, animDuration)

        ivTuTigngTas.forEachIndexed { index, imageView ->
            imageView.animate().rotation(-45f).setDuration(500).start()
            Handler().postDelayed({
                imageView.animate().rotation(45f).setDuration(500).start()
            }, 500)
            Handler().postDelayed({
                imageView.animate().rotation(0f).setDuration(500).start()
            }, animDuration)
        }

        Handler().postDelayed({
            val intent = Intent(this, LandingPage::class.java)
            intent.putExtra("NAME", "Agung")
            startActivity(intent)
            finish()
        }, 3*animDuration)
    }

}