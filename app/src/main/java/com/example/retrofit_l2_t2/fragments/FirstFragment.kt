package com.example.retrofit_l2_t2.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit_l2_t2.R
import com.example.retrofit_l2_t2.adapter.CardAdapter
import com.example.retrofit_l2_t2.database.CardDatabase
import com.example.retrofit_l2_t2.databinding.FragmentFirstBinding
import com.example.retrofit_l2_t2.model.CardResponse
import com.example.retrofit_l2_t2.network.RetroInstance
import com.example.retrofit_l2_t2.util.NetworkHelper
import com.example.retrofit_l2_t2.util.snackBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val cardAdapter by lazy { CardAdapter() }
    private val cardDatabase by lazy {CardDatabase(requireContext())}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rv.apply {
            adapter = cardAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val network = NetworkHelper(requireContext())
        if (network.isNetworkConnected()){
            getAllCardsOnline()
            snackBar("Online mode")
        }



    }

    private fun getAllCardsOnline() {
        RetroInstance.apiService().getCards().enqueue(object : Callback<CardResponse>{
            override fun onResponse(call: Call<CardResponse>, response: Response<CardResponse>) {
                if (response.isSuccessful){
                    binding.progressBar.isVisible = false
                    cardAdapter.submitList(response.body()?.card)
                    cardDatabase.dao.saveCardListToDatabase(response.body()?.card!!)
                }
            }

            override fun onFailure(call: Call<CardResponse>, t: Throwable) {
                Log.d("@@@", "onFailure: ${t.message}")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}