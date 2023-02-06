package com.example.retrofit_l2_t2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit_l2_t2.databinding.CardLayoutBinding
import com.example.retrofit_l2_t2.model.CardItem


class CardAdapter : ListAdapter<CardItem, CardAdapter.CardViewHolder>(DiffCallBack()) {
    lateinit var onClick: (id: Int) -> Unit

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
                cardNumber.text = cardItem.number.toString()
                cardOwner.text= cardItem.cardHolderName
                cardExpiresData.text = "${cardItem.data1}/${cardItem.data2}"
            }
            itemView.setOnClickListener {
                onClick(cardItem.id)
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
}