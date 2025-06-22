package com.example.wheretonext.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wheretonext.R

class LoginFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tv_register).setOnClickListener {
            val email = view.findViewById<EditText>(R.id.edt_email).text.toString()
            goToRegister(email)
        }

        view.findViewById<TextView>(R.id.btn_login).setOnClickListener {
            goToHome()
        }
    }

    private fun goToRegister(email:String) {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment(email)
        findNavController().navigate(action)
    }


    private fun goToHome() {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController().navigate(action)
    }
}