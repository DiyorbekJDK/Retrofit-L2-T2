package com.example.retrofit_l2_t2.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.retrofit_l2_t2.R
import com.example.retrofit_l2_t2.databinding.FragmentDetailBinding
import com.example.retrofit_l2_t2.databinding.FragmentSecondBinding
import com.example.retrofit_l2_t2.model.CardItem
import com.example.retrofit_l2_t2.model.CardResponse
import com.example.retrofit_l2_t2.network.RetroInstance
import com.example.retrofit_l2_t2.util.NetworkHelper
import com.example.retrofit_l2_t2.util.snackBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allCode()

    }

    private fun allCode() {
        val card: CardItem? = arguments?.getParcelable("card")
        val network = NetworkHelper(requireContext())
        if (network.isNetworkConnected()) {
            RetroInstance.apiService().getCardById(card?.id!!)
                .enqueue(object : Callback<CardItem> {
                    override fun onResponse(
                        call: Call<CardItem>,
                        response: Response<CardItem>
                    ) {
                        if (response.isSuccessful) {
                            binding.progressBar3.isVisible = false
                            binding.cardView.isVisible = true
                            binding.name.text = card.bankName
                            binding.number.text = card.number
                            binding.owner.text = card.cardHolderName
                            binding.data.text = "${card.data1}/${card.data2}"
                            binding.cvv.text = card.cvv
                            binding.btnDelete.isVisible = true
                            binding.btnEdit.isVisible = true

                            binding.btnEdit.setOnClickListener {
                                val bundle = bundleOf("id" to card.id)
                                findNavController().navigate(
                                    R.id.action_detailFragment_to_SecondFragment,
                                    bundle
                                )
                            }

                            binding.btnDelete.setOnClickListener {
                                if (network.isNetworkConnected()) {
                                    RetroInstance.apiService().deleteCard(card.id)
                                        .enqueue(object : Callback<CardResponse> {
                                            override fun onResponse(
                                                call: Call<CardResponse>,
                                                response: Response<CardResponse>
                                            ) {
                                                snackBar("Deleted Successfully")
                                                findNavController().popBackStack()
                                            }

                                            override fun onFailure(
                                                call: Call<CardResponse>,
                                                t: Throwable
                                            ) {
                                                snackBar("Error!")
                                            }
                                        })
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<CardItem>, t: Throwable) {
                        snackBar("There is some Error occurred")
                        Log.d("&&&", "onFailure: ${t.message}")
                    }
                })
        } else {
            binding.progressBar3.isVisible = false
            binding.cardView.isVisible = true
            binding.name.text = card?.bankName
            binding.number.text = card?.number
            binding.owner.text = card?.cardHolderName
            binding.data.text = "${card?.data1}/${card?.data2}"
            binding.cvv.text = card?.cvv
            binding.btnDelete.isVisible = true
            binding.btnEdit.isVisible = true
            if (network.isNetworkConnected()) {
                binding.btnDelete.setOnClickListener {
                    if (network.isNetworkConnected()) {
                        RetroInstance.apiService().deleteCard(card?.id!!)
                            .enqueue(object : Callback<CardResponse> {
                                override fun onResponse(
                                    call: Call<CardResponse>,
                                    response: Response<CardResponse>
                                ) {
                                    snackBar("Deleted Successfully")
                                    findNavController().popBackStack()
                                }

                                override fun onFailure(
                                    call: Call<CardResponse>,
                                    t: Throwable
                                ) {
                                    snackBar("Error!")
                                }
                            })
                    }
                }
            } else {
                snackBar("You must be online to delete card!")
            }
        }
    }
}