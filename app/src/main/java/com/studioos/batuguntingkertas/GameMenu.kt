package com.studioos.batuguntingkertas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class GameMenu : AppCompatActivity() {

    val parent by lazy { findViewById<LinearLayout>(R.id.parent_menu) }
    val vsPemain by lazy { findViewById<ImageView>(R.id.iv_vs_pemain) }
    val vsCPU by lazy { findViewById<ImageView>(R.id.iv_vs_cpu) }
    val tvVsPemain by lazy { findViewById<TextView>(R.id.tv_vs_pemain) }
    val tvVsCpu by lazy { findViewById<TextView>(R.id.tv_vs_cpu) }
    var name: String =""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_menu)

//        name = intent.extras?.getString("NAME").toString()
        name = intent.getStringExtra("NAME").toString()
        tvVsPemain.text = "$name vs Pemain"
        tvVsCpu.text = "$name vs CPU"

        vsPemain.setOnClickListener {
            val intent1 = Intent(this, VsPemain::class.java)
            intent1.putExtra("NAME", name)
            startActivity(intent1)
            finish()
        }

        vsCPU.setOnClickListener {
            val intent1 = Intent(this, VsCPU::class.java)
            intent1.putExtra("NAME", name)
            startActivity(intent1)
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        val snackbar = Snackbar.make(parent, "Selamat Datang $name", Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Tutup") {
            snackbar.dismiss()
        }.show()
    }
}