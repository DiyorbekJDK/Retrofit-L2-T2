package com.example.retrofit_l2_t2.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit_l2_t2.R
import com.example.retrofit_l2_t2.activity.MainActivity
import com.example.retrofit_l2_t2.adapter.CardAdapter
import com.example.retrofit_l2_t2.database.CardDatabase
import com.example.retrofit_l2_t2.databinding.FragmentFirstBinding
import com.example.retrofit_l2_t2.model.CardItem
import com.example.retrofit_l2_t2.network.RetroInstance
import com.example.retrofit_l2_t2.sharedPreferences.CardSharedPref
import com.example.retrofit_l2_t2.util.NetworkHelper
import com.example.retrofit_l2_t2.util.snackBar
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FirstFragment : Fragment() {


    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val cardAdapter by lazy { CardAdapter() }
    private val cardDatabase by lazy { CardDatabase(requireContext()) }
    //private val sharedPref by lazy { CardSharedPref(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allCode()


    }

    private fun allCode() {
        val network = NetworkHelper(requireContext())
        binding.btnAdd.setOnClickListener {
            if(network.isNetworkConnected()){
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }else{
                showInternetDialog()
            }

        }
        binding.rv.apply {
            adapter = cardAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        cardAdapter.onClick = {
            val bunde = bundleOf("card" to it)
            findNavController().navigate(R.id.action_FirstFragment_to_detailFragment, bunde)
        }

        checkInternet()
    }



    private fun checkInternet() {
        val network = NetworkHelper(requireContext())
        if (network.isNetworkConnected()) {
            getAllCardsOnline()
            snackBar("Online mode")
        } else {
            snackBar("Offline mode")
            getAllCardsOffline()
        }
    }
    private fun getAllCardsOffline() {
        if (cardDatabase.dao.isEmpty()) {
            showAlertDialog()
        } else {
            cardAdapter.submitList(cardDatabase.dao.getAllCardsFromDatabase())
            binding.progressBar.isVisible = false
        }


    }
    private fun getAllCardsOnline() {
        RetroInstance.apiService().getCards().enqueue(object : Callback<List<CardItem>> {
            override fun onResponse(
                call: Call<List<CardItem>>,
                response: Response<List<CardItem>>
            ) {
                if (response.isSuccessful) {
                    binding.progressBar.isVisible = false
                    cardAdapter.submitList(response.body())
                    if (cardDatabase.dao.isEmpty()) {
                        cardDatabase.dao.saveCardListToDatabase(response.body()!!)
                    } else {
                        cardDatabase.dao.deleteAllCardsFromDatabase()
                    }


                }
            }

            override fun onFailure(call: Call<List<CardItem>>, t: Throwable) {
                snackBar("There is some Error occurred")
                Log.d("***", "onFailure: ${t.message}")
            }
        })
    }
    private fun showAlertDialog() {
        val layoutInflater = LayoutInflater.from(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.offline_mode_dialog_layout, null)
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        alertDialog.setView(dialogView)
        val btn: MaterialButton = dialogView.findViewById(R.id.btnRefresh)
        val btn2: MaterialButton = dialogView.findViewById(R.id.btnExit)
        btn2.setOnClickListener {
            activity?.finish()
            alertDialog.dismiss()
        }
        btn.setOnClickListener {
            checkInternet()
            alertDialog.dismiss()
        }
        alertDialog.show()
    }
    private fun showInternetDialog() {
        val layoutInflater = LayoutInflater.from(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.no_internet_dialog, null)
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        alertDialog.setView(dialogView)
        val btn: MaterialButton = dialogView.findViewById(R.id.btnExit)
        btn.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}