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

class VsPemain : AppCompatActivity(), CallbackHasil {

    val parent by lazy { findViewById<RelativeLayout>(R.id.vs_cpu_parent) }
    val image by lazy { findViewById<ImageView>(R.id.iv_title) }
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
    val tvPemain2 by lazy { findViewById<TextView>(R.id.tv_player02) }
    var pilihan1 = ""
    var pilihan2 = ""
    var name: String = "Pemain 1"
    var win1 = 0
    var win2 = 0
    var numAcak = 0
    var acakDuration = 100L
    var animDuration = 1000L
    var p2: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vs_cpu)

        name = intent.getStringExtra("NAME").toString()
        tvPemain1.text = name
        tvPemain2.text = "Pemain 2"

        reset()
        val url = "https://i.ibb.co/HC5ZPgD/splash-screen1.png"

        Glide.with(this).load(url).into(image);

        val ivpilihan = mutableListOf(ivBatu1, ivKertas1, ivGunting1, ivBatu2, ivKertas2, ivGunting2)

        ivpilihan.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                if (it == ivBatu1) {
                    if (flBatu1.isVisible == false) {
                        flBatu1.visibility = View.VISIBLE
                    }
                    pilihan1 = "batu"
                    setInfo1()
                } else if (it == ivKertas1) {
                    if (flKertas1.isVisible == false) {
                        flKertas1.visibility = View.VISIBLE
                    }
                    pilihan1 = "kertas"
                    setInfo1()
                } else if (it == ivGunting1) {
                    if (flGunting1.isVisible == false) {
                        flGunting1.visibility = View.VISIBLE
                    }
                    pilihan1 = "gunting"
                    setInfo1()
                } else if (it == ivBatu2) {
                    if (flBatu2.isVisible == false) {
                        flBatu2.visibility = View.VISIBLE
                    }
                    pilihan2 = "batu"
                } else if (it == ivKertas2) {
                    if (flKertas2.isVisible == false) {
                        flKertas2.visibility = View.VISIBLE
                    }
                    pilihan2 = "kertas"
                } else {
                    if (flGunting2.isVisible == false) {
                        flGunting2.visibility = View.VISIBLE
                    }
                    pilihan2 = "gunting"
                }
                if (!pilihan1.equals("")) {
                    lockButton1()
                }
                if (!pilihan2.equals("")) {
                    lockButton2()
                }
                it.animate().rotation(180f).scaleX(1.5f).scaleY(1.5f).setDuration(animDuration)
                    .start()
                Handler().postDelayed({
                    it.animate().rotation(0f).scaleX(1f).scaleY(1f).setDuration(animDuration)
                        .start()
                }, animDuration)
                if (!pilihan1.equals("") && !pilihan2.equals("")) {
                    startCompare()
                    llVS.animate().alpha(0f).scaleX(0.5f).scaleY(0.5f).setDuration(500).start()
                    ivClose.animate().alpha(0f).scaleX(0.5f).scaleY(0.5f).setDuration(500).start()
                }
            }
        }

        ivClose.setOnClickListener {
            onBackPressed()
        }

    }

    private fun setInfo1() {
        Toast.makeText(this, "$name Memilih $pilihan1", Toast.LENGTH_SHORT).show()
        Log.e("MainActivity", "$name memilih $pilihan1")
    }

    private fun startCompare() {
        var modelSuit = ModelSuit("p2", pilihan1, pilihan2)
        Log.e("MainActivity", "panggil ModelSuit")
        var controller = ContollerPermainan(this)
        controller.setModelSuit(modelSuit)
        controller.pilihanLawan()
        controller.cariPemenang()
    }

    private fun lockButton1() {
        ivBatu1.isEnabled = false
        ivKertas1.isEnabled = false
        ivGunting1.isEnabled = false
        Log.e("MainActivity", "lock Choices Button Pemain 1")
    }

    private fun lockButton2() {
        ivBatu2.isEnabled = false
        ivKertas2.isEnabled = false
        ivGunting2.isEnabled = false
        Log.e("MainActivity", "lock Choices Button Pemain 2")
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
        if (ivBatu2.isEnabled == false) {
            ivBatu2.isEnabled = true
            Log.e("MainActivity", "unlock Choices Button")
        }
        if (ivKertas2.isEnabled == false) {
            ivKertas2.isEnabled = true
            Log.e("MainActivity", "unlock Choices Button")
        }
        if (ivGunting2.isEnabled == false) {
            ivGunting2.isEnabled = true
            Log.e("MainActivity", "unlock Choices Button")
        }
    }

    private fun reset() {
        pilihan1 = ""
        pilihan2 = ""
        mutableListOf(flBatu1, flKertas1, flGunting1, flBatu2, flKertas2, flGunting2)
            .forEachIndexed { index, i ->
                if (i.isVisible == true) {
                    i.visibility = View.INVISIBLE
                    Log.e("MainActivity", "Chosen background invisible")
                }
            }
        llVS.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(animDuration).start()
        ivClose.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(animDuration).start()
        Log.e("MainActivity", "Reset permainan")
        unlookButton()
    }


    override fun animAcak(animAcak: String) {}

    override fun hasilLawan(hasilLawan: String) {
        when (hasilLawan) {
            "batu" -> {
                p2 = ivBatu2
            }
            "kertas" -> {
                p2 = ivKertas2
            }
            "gunting" -> {
                p2 = ivGunting2
            }
        }
        p2?.animate()?.rotation(-180f)?.scaleX(1.5f)?.scaleY(1.5f)?.setDuration(animDuration)
            ?.start()
        Handler().postDelayed({
            p2?.animate()?.rotation(0f)?.scaleX(1f)?.scaleY(1f)?.setDuration(animDuration)?.start()
        }, animDuration)
        Log.e("MainActivity", "pilihan Pemain 2 $hasilLawan")
        Toast.makeText(this, "Pemain 2 Memilih $hasilLawan", Toast.LENGTH_SHORT).show()
    }

    override fun hasilMenang(hasilMenang: String) {
        var pemenang = ""
        when (hasilMenang) {
            "0" -> {
                pemenang = "SERI!"
            }
            "1" -> {
                win1++
                pemenang = "$name\nMENANG!"
            }
            "2" -> {
                win2++
                pemenang = "Pemain 2\nMENANG!"
            }
        }
        Log.e("MainActivity", "pemenangnya $hasilMenang")

        Handler().postDelayed({
            tvWin1.text = "# $win1"
            tvWin2.text = "# $win2"
            val view =
                LayoutInflater.from(this).inflate(R.layout.dialog_hasil_permainan, null, false)
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setView(view)
            val dialog = dialogBuilder.create()
            dialog.setCancelable(false)
            val infoPemenang by lazy { view.findViewById<TextView>(R.id.pemenang) }
            val mainLagi by lazy { view.findViewById<Button>(R.id.main_lagi) }
            val keMenu by lazy { view.findViewById<Button>(R.id.ke_menu) }

            infoPemenang.text = pemenang
            mainLagi.setOnClickListener {
                reset()
                dialog.dismiss()
            }
            keMenu.setOnClickListener {
                dialog.dismiss()
                val intent1 = Intent(this, GameMenu::class.java)
                intent1.putExtra("NAME", name)
                startActivity(intent1)
                dialog.dismiss()
                finish()
            }
            dialog.show()

        }, 2 * animDuration)
    }

    override fun onBackPressed() {
        val snackbar =
            Snackbar.make(parent, "Yakin mau keluar dari Permainan ini?", Snackbar.LENGTH_SHORT)
        snackbar.setAction("Keluar") {
            snackbar.dismiss()
            finish()
        }.show()
    }

}