package com.studioos.batuguntingkertas

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class VsCPU : AppCompatActivity(), CallbackHasil {

    val parent by lazy {findViewById<RelativeLayout>(R.id.vs_cpu_parent)}
    val image by lazy {findViewById<ImageView>(R.id.iv_title)}
    val ivClose by lazy { findViewById<ImageView>(R.id.iv_close) }
    val ivBatu1 by lazy { findViewById<ImageView>(R.id.iv_batu1) }
    val ivKertas1 by lazy { findViewById<ImageView>(R.id.iv_kertas1) }
    val ivGunting1 by lazy { findViewById<ImageView>(R.id.iv_gunting1) }
    val ivBatu2 by lazy { findViewById<ImageView>(R.id.iv_batu2) }
    val ivKertas2 by lazy { findViewById<ImageView>(R.id.iv_kertas2) }
    val ivGunting2 by lazy { findViewById<ImageView>(R.id.iv_gunting2) }
    val flBatu1 by lazy { findViewById<FrameLayout>(R.id.fl_batu1) }
    val flKertas1 by lazy { findViewById<FrameLayout>(R.id.fl_kertas1) }
    val flGunting1 by lazy { findViewById<FrameLayout>(R.id.fl_gunting1) }
    val flBatu2 by lazy { findViewById<FrameLayout>(R.id.fl_batu2) }
    val flKertas2 by lazy { findViewById<FrameLayout>(R.id.fl_kertas2) }
    val flGunting2 by lazy { findViewById<FrameLayout>(R.id.fl_gunting2) }
    val llVS by lazy { findViewById<LinearLayout>(R.id.ll_vs) }
    val tvWin1 by lazy { findViewById<TextView>(R.id.tv_win1) }
    val tvWin2 by lazy { findViewById<TextView>(R.id.tv_win2) }
    val tvPemain1 by lazy { findViewById<TextView>(R.id.tv_player01) }
    var pilihan1 = ""
    var name : String = "Pemain 1"
    var win1 = 0
    var win2 = 0
    var numAcak = 0
    var acakDuration = 100L
    var animDuration = 1000L
    var cpu : ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vs_cpu)

        name = intent.getStringExtra("NAME").toString()
        tvPemain1.text = name

        reset()
        val url = "https://i.ibb.co/HC5ZPgD/splash-screen1.png"

        Glide.with(this).load(url).into(image);

        val ivpilihan = mutableListOf(ivBatu1, ivKertas1, ivGunting1)

        ivpilihan.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                if (it == ivBatu1) {
                    if (flBatu1.isVisible == false) {
                        flBatu1.visibility = View.VISIBLE
                    }
                    pilihan1 = "batu"
                } else if (it == ivKertas1) {
                    if (flKertas1.isVisible == false) {
                        flKertas1.visibility = View.VISIBLE
                    }
                    pilihan1 = "kertas"
                } else {
                    if (flGunting1.isVisible == false) {
                        flGunting1.visibility = View.VISIBLE
                    }
                    pilihan1 = "gunting"
                }
                Toast.makeText(this, "$name Memilih $pilihan1", Toast.LENGTH_SHORT).show()
                Log.e("MainActivity", "$name memilih $pilihan1")
                lockButton()
                loopAnimmasiAcak()
                it.animate().rotation(180f).scaleX(1.5f).scaleY(1.5f).setDuration(animDuration).start()
                Handler().postDelayed({
                    it.animate().rotation(0f).scaleX(1f).scaleY(1f).setDuration(animDuration).start()
                }, animDuration)
                llVS.animate().alpha(0f).scaleX(0.5f).scaleY(0.5f).setDuration(500).start()
                ivClose.animate().alpha(0f).scaleX(0.5f).scaleY(0.5f).setDuration(500).start()
            }
        }

        ivClose.setOnClickListener {
            onBackPressed()
        }

    }

    private fun loopAnimmasiAcak() {
        mutableListOf(flBatu2, flKertas2, flGunting2)
            .forEachIndexed { index, i ->
                Handler().postDelayed({
                    if (i.isVisible == true) {
                        i.visibility = View.INVISIBLE
                        Log.e("MainActivity", "Com Chosen background invisible")
                    }
                }, acakDuration)
            }
        var controllerAnim = ContollerPermainan(this)
        Handler().postDelayed({
            if (numAcak <= 30) {
                controllerAnim.animasiAcak()
                Log.e("MainActivity", "repeat #${numAcak}")
            } else {
                startCompare()
                numAcak  = 0
            }
        }, acakDuration)
    }

    private fun startCompare() {
        var modelSuit = ModelSuit("cpu", pilihan1,"")
        Log.e("MainActivity", "panggil ModelSuit")
        var controller = ContollerPermainan(this)
        controller.setModelSuit(modelSuit)
        controller.pilihanLawan()
        controller.cariPemenang()
    }

    private fun lockButton() {
        ivBatu1.isEnabled = false
        ivKertas1.isEnabled = false
        ivGunting1.isEnabled = false
        Log.e("MainActivity", "lock Choices Button")
    }

    private fun unlookButton() {
        if (ivBatu1.isEnabled == false) {
            ivBatu1.isEnabled = true
            Log.e("MainActivity", "unlock Choices Button")
        }
        if (ivKertas1.isEnabled == false) {
            ivKertas1.isEnabled = true
            Log.e("MainActivity", "unlock Choices Button")
        }
        if (ivGunting1.isEnabled == false) {
            ivGunting1.isEnabled = true
            Log.e("MainActivity", "unlock Choices Button")
        }
    }

    private fun reset() {
        mutableListOf(flBatu1, flKertas1, flGunting1, flBatu2, flKertas2, flGunting2)
            .forEachIndexed { index, i ->
                if (i.isVisible == true) {
                    i.visibility = View.INVISIBLE
                    Log.e("MainActivity", "Chosen background invisible")
                }
            }
        llVS.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(animDuration).start()
        ivClose.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(animDuration).start()
        Log.e("MainActivity", "Make Win Label Alpha and VS label no Alpha")
        pilihan1 = ""
        unlookButton()
    }

    override fun animAcak(animAcak: String) {
        when (animAcak) {
            "batu" -> {
                flBatu2.visibility = View.VISIBLE
                Log.e("MainActivity", "anim batu")
            }
            "kertas" -> {
                flKertas2.visibility = View.VISIBLE
                Log.e("MainActivity", "anim kertas")
            }
            "gunting" -> {
                flGunting2.visibility = View.VISIBLE
                Log.e("MainActivity", "anim gunting")
            }
        }
        numAcak++
        loopAnimmasiAcak()
    }

    override fun hasilLawan(hasilLawan: String) {
        when (hasilLawan) {
            "batu" -> {
                cpu = ivBatu2
                flBatu2.visibility = View.VISIBLE
            }
            "kertas" -> {
                cpu = ivKertas2
                flKertas2.visibility = View.VISIBLE
            }
            "gunting" -> {
                cpu = ivGunting2
                flGunting2.visibility = View.VISIBLE
            }
        }
        cpu?.animate()?.rotation(-180f)?.scaleX(1.5f)?.scaleY(1.5f)?.setDuration(animDuration)?.start()
        Handler().postDelayed({
            cpu?.animate()?.rotation(0f)?.scaleX(1f)?.scaleY(1f)?.setDuration(animDuration)?.start()
        }, animDuration)
        Log.e("MainActivity", "pilihan Computer $hasilLawan")
        Toast.makeText(this, "CPU Memilih $hasilLawan", Toast.LENGTH_SHORT).show()
    }


    override fun hasilMenang(hasilMenang: String) {
        var pemenang = ""
        when (hasilMenang) {
            "0" -> {
                pemenang ="SERI!"
            }
            "1" -> {
                win1++
                pemenang = "$name\nMENANG!"
            }
            "2" -> {
                win2++
                pemenang = "CPU\nMENANG!"
            }
        }
        Log.e("MainActivity", "pemenangnya $hasilMenang")

        Handler().postDelayed({
            tvWin1.text = "# $win1"
            tvWin2.text = "# $win2"
            val view = LayoutInflater.from(this).inflate(R.layout.dialog_hasil_permainan, null, false)
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setView(view)
            val dialog = dialogBuilder.create()
            dialog.setCancelable(false)
            val infoPemenang by lazy {view.findViewById<TextView>(R.id.pemenang)}
            val mainLagi by lazy {view.findViewById<Button>(R.id.main_lagi)}
            val keMenu by lazy {view.findViewById<Button>(R.id.ke_menu)}

            infoPemenang.text = pemenang
            mainLagi.setOnClickListener {
                reset()
                dialog.dismiss()
            }
            keMenu.setOnClickListener {
                val intent1 = Intent(this, GameMenu::class.java)
                intent1.putExtra("NAME", name)
                startActivity(intent1)
                dialog.dismiss()
                finish()
            }
            dialog.show()

        }, 2*animDuration)
    }

    override fun onBackPressed() {
        val snackbar = Snackbar.make(parent, "Yakin mau keluar dari Permainan ini?", Snackbar.LENGTH_SHORT)
        snackbar.setAction("Keluar"){
            snackbar.dismiss()
            finish()
        }.show()
    }

}