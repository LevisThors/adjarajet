package com.example.adjarajet

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", 0)

        val textViewEmail = view.findViewById<TextView>(R.id.textViewEmail)
        val buttonLogout = view.findViewById<Button>(R.id.buttonLogout)

        val email = sharedPreferences.getString("USER_EMAIL", "Guest")
        textViewEmail.text = "Your email: $email"

        buttonLogout.setOnClickListener {
            sharedPreferences.edit().remove("USER_EMAIL").apply()
            val intent = Intent(requireContext(), SignInActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return view
    }
}
