package com.example.retrofit_l2_t2.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.retrofit_l2_t2.R
import com.example.retrofit_l2_t2.databinding.FragmentSecondBinding
import com.example.retrofit_l2_t2.model.CardItem
import com.example.retrofit_l2_t2.model.CardResponse
import com.example.retrofit_l2_t2.network.RetroInstance
import com.example.retrofit_l2_t2.util.snackBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allCode()

    }

    private fun allCode() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        fillTheCard()
        val id: Int? = arguments?.getInt("id")
        if (id == null) {
            binding.btnSaveEdit.setOnClickListener {
                binding.progressBar2.isVisible = true
                RetroInstance.apiService().addCard(
                    CardItem(
                        binding.bankName.text?.toString()?.trim()!!,
                        binding.cardHolder.text?.toString()?.trim()!!,
                        binding.cardCvv.text?.toString()?.trim()!!,
                        binding.cardDay.text?.toString()!!,
                        binding.cardYear.text?.toString()!!,
                        0,
                        binding.cardNumber.text?.toString()!!
                    )
                ).enqueue(object : Callback<CardResponse> {
                    override fun onResponse(
                        call: Call<CardResponse>,
                        response: Response<CardResponse>
                    ) {
                        if (response.isSuccessful) {
                            binding.progressBar2.isVisible = false
                            snackBar("Saved Successfully")
                            binding.apply {
                                bankName.text?.clear()
                                cardCvv.text?.clear()
                                cardDay.text?.clear()
                                cardHolder.text?.clear()
                                cardYear.text?.clear()
                                cardNumber.text?.clear()
                            }
                        }
                    }

                    override fun onFailure(call: Call<CardResponse>, t: Throwable) {
                        snackBar("There is some Error occurred")
                        Log.d("&&&", "onFailure: ${t.message}")
                    }
                })
            }
        }else{
            binding.btnSaveEdit.text = "Update Card"
            RetroInstance.apiService().getCardById(id).enqueue(object : Callback<CardItem>{
                override fun onResponse(
                    call: Call<CardItem>,
                    response: Response<CardItem>
                ) {
                    if (response.isSuccessful){
                        binding.apply {
                            bankName.setText(response.body()?.bankName)
                            cardCvv.setText(response.body()?.cvv)
                            cardDay.setText(response.body()?.data1)
                            cardHolder.setText(response.body()?.cardHolderName)
                            cardYear.setText(response.body()?.data2)
                            cardNumber.setText(response.body()?.number)
                        }
                        binding.btnSaveEdit.setOnClickListener {
                            RetroInstance.apiService().updateCard(id,CardItem(
                                binding.bankName.text?.toString()?.trim()!!,
                                binding.cardHolder.text?.toString()?.trim()!!,
                                binding.cardCvv.text?.toString()?.trim()!!,
                                binding.cardDay.text?.toString()!!,
                                binding.cardYear.text?.toString()!!,
                                id,
                                binding.cardNumber.text?.toString()!!
                            )).enqueue(object: Callback<CardResponse>{
                                override fun onResponse(
                                    call: Call<CardResponse>,
                                    response: Response<CardResponse>
                                ) {
                                    if(response.isSuccessful){
                                        snackBar("Updated!")
                                    }
                                }

                                override fun onFailure(call: Call<CardResponse>, t: Throwable) {
                                    snackBar("There is some Error occurred")
                                    Log.d("%%%", "onFailure: ${t.message}")
                                }
                            })

                        }
                        binding.btnBack.setOnClickListener {
                            findNavController().popBackStack()
                            findNavController().popBackStack()
                        }

                    }
                }

                override fun onFailure(call: Call<CardItem>, t: Throwable) {
                    snackBar("Error")
                }
            })
        }
    }

    private fun fillTheCard() {
        binding.bankName.addTextChangedListener {
            binding.cardLayout.bankName.text = it.toString()
        }
        binding.cardNumber.addTextChangedListener {
            binding.cardLayout.cardNumber.text = it.toString()
        }
        binding.cardDay.addTextChangedListener { d ->
            binding.cardLayout.cardExpiresData.text =
                d.toString()
        }

        binding.cardHolder.addTextChangedListener {
            binding.cardLayout.cardOwner.text = it.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}