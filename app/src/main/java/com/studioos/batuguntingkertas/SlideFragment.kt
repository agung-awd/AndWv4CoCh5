package com. studioos.batuguntingkertas


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.studioos.batuguntingkertas.GameMenu
import com.studioos.batuguntingkertas.R

class SlideFragment(private val position: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (this.position) {
            0 -> {
                view.findViewById<ImageView>(R.id.image_view)
                    .setImageResource(R.drawable.img_lawan_pemain2)
                view.findViewById<TextView>(R.id.tv_info).text =
                    "Bermain suit melawan sesama \npemain."
                view.findViewById<EditText>(R.id.edit_text)
                    .visibility = View.INVISIBLE
                view.findViewById<ImageView>(R.id.next)
                    .visibility = View.INVISIBLE
            }
            1 -> {
                view.findViewById<ImageView>(R.id.image_view)
                    .setImageResource(R.drawable.img_lawan_com)
                view.findViewById<TextView>(R.id.tv_info).text =
                    "Bermain suit melawan komputer."
                view.findViewById<EditText>(R.id.edit_text)
                    .visibility = View.INVISIBLE
                view.findViewById<ImageView>(R.id.next)
                    .visibility = View.INVISIBLE
            }
            2 -> {
                view.findViewById<ImageView>(R.id.image_view)
                    .setImageResource(R.drawable.img_avatar_kosong)
                view.findViewById<TextView>(R.id.tv_info).text =
                    "Tuliskan namamu di bawah"
                val eText by lazy { view.findViewById<EditText>(R.id.edit_text) }
                eText.visibility = View.VISIBLE
                val btNext by lazy { view.findViewById<ImageView>(R.id.next) }
                btNext.visibility = View.INVISIBLE
                eText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?, start: Int, count: Int, after: Int
                    ) { }

                    override fun onTextChanged(
                        s: CharSequence?, start: Int, before: Int, count: Int
                    ) {
                        if (s?.length!! >= 3) btNext.visibility = View.VISIBLE
                        else if (s?.length!! <3) btNext.visibility = View.INVISIBLE
                    }

                    override fun afterTextChanged(s: Editable?) {}

                })
                btNext.setOnClickListener {
                    startActivity(
                        Intent(context, GameMenu::class.java)
                            .putExtra("NAME", eText.text.toString())
                    )
                    activity?.finish()
                }
            }
        }
    }

}
