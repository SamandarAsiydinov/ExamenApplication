package uz.context.examenapplication.activities.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import uz.context.examenapplication.R
import uz.context.examenapplication.activities.model.Cards
import java.util.*
import kotlin.collections.ArrayList

class MyAdapter(private val context: Context, private val cardList: ArrayList<Cards>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val card = cardList[position]

        if (holder is MyViewHolder) {
            holder.apply {
                cardDate.text = card.cardDate
                cardNumber.text = card.cardNumber
                cardHolder.text = card.cardHolder

                cardView.setCardBackgroundColor(ContextCompat.getColor(context,randomColor()))
            }
        }
    }

    override fun getItemCount(): Int = cardList.size

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardNumber: TextView = view.findViewById(R.id.textNumbers)
        val cardHolder: TextView = view.findViewById(R.id.cardHolderName)
        val cardDate: TextView = view.findViewById(R.id.cardDate)
        val cardView: CardView = view.findViewById(R.id.cardView)
    }

    private fun randomColor(): Int {
        val arrayColor = ArrayList<Int>()
        arrayColor.add(R.color.color1)
        arrayColor.add(R.color.color2)
        arrayColor.add(R.color.color3)
        arrayColor.add(R.color.color5)
        arrayColor.add(R.color.color7)
        arrayColor.add(R.color.color9)

        val random = Random()
        val randomColor = random.nextInt(arrayColor.size)
        return arrayColor[randomColor]
    }
}