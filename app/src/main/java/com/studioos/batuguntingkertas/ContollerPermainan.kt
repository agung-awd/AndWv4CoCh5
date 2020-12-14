package com.studioos.batuguntingkertas

import android.util.Log

public class ContollerPermainan (private val listener:CallbackHasil){
    private var modelSuit : ModelSuit? = null
    private var pilihan2 = arrayListOf("batu", "kertas", "gunting")
    var suitC2 = pilihan2.random()


    fun animasiAcak(){
        var animAcak = pilihan2.random()
        listener.animAcak(animAcak)
    }

    fun pilihanLawan(){
        if (modelSuit!!.gameMode.equals("cpu")) {
            listener.hasilLawan(suitC2)
            Log.e("ContollerPermainan", "Com memilih = $suitC2")
        } else {
            listener.hasilLawan(modelSuit!!.suitP2)
            Log.e("ContollerPermainan", "Pemain 2 memilih = ${modelSuit!!.suitP2}")
        }
    }

    fun cariPemenang() {
        var hasilSuit: String
        var suit2 : String
        if (modelSuit!!.gameMode.equals("cpu")){
            suit2 = suitC2
        } else {
            suit2 = modelSuit!!.suitP2
        }
        if (modelSuit!!.suit1.equals("batu") && suit2.equals("batu") ||
            modelSuit!!.suit1.equals("gunting") && suit2.equals("gunting") ||
            modelSuit!!.suit1.equals("kertas") && suit2.equals("kertas"))
            hasilSuit = "0"
        else if (modelSuit!!.suit1.equals("batu") && suit2.equals("kertas") ||
            modelSuit!!.suit1.equals("kertas") && suit2.equals("gunting") ||
            modelSuit!!.suit1.equals("gunting") && suit2.equals("batu"))
            hasilSuit = "2"
        else {
            hasilSuit = "1"
        }
        listener.hasilMenang(hasilSuit)
        Log.e("ContollerPermainan", "Pemenang Pemain $hasilSuit")
    }

    fun setModelSuit(modelSuit: ModelSuit){
        this.modelSuit = modelSuit
        Log.e("ContollerPermainan", "game mode ${modelSuit!!.gameMode} dari Model")
        Log.e("ContollerPermainan", "Ambil data Pilihan pemain 1 ${modelSuit.suit1} dari Model")
        Log.e("ContollerPermainan", "Ambil data Pilihan pemain 2 ${modelSuit.suitP2} dari Model")
    }
}