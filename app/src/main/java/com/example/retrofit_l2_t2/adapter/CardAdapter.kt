package com.example.retrofit_l2_t2.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit_l2_t2.R
import com.example.retrofit_l2_t2.databinding.CardLayoutBinding
import com.example.retrofit_l2_t2.model.CardItem
import java.util.*


class CardAdapter : ListAdapter<CardItem, CardAdapter.CardViewHolder>(DiffCallBack()) {
    lateinit var onClick: (CardItem) -> Unit

    private class DiffCallBack : DiffUtil.ItemCallback<CardItem>() {
        override fun areItemsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
            return oldItem == newItem
        }
    }

    inner class CardViewHolder(private val binding: CardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cardItem: CardItem) {
            with(binding) {
                bankName.text = cardItem.bankName
                cardNumber.text = cardItem.number
                cardOwner.text= cardItem.cardHolderName
                cardExpiresData.text = "${cardItem.data1}/${cardItem.data2}"
                cardView.setCardBackgroundColor(randomColor())
            }
            itemView.setOnClickListener {
                onClick(cardItem)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            CardLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun randomColor(): Int {
        val random = Random()
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }
//    @ColorRes
//    private fun randomColor2(): Int {
//       val list = listOf(
//           R.color.red,
//           R.color.blue,
//           R.color.green,
//           R.color.app_color,
//           R.color.gray,
//           R.color.purple_500,
//           R.color.purple_700,
//           R.color.teal_200,
//           R.color.teal_700,
//           R.color.yellow,
//           R.color.orange
//        )
//        val random = (list.indices).random()
//        return list[random]
//   }
}