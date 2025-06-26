package com.example.wheretonext.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.wheretonext.R
import com.example.wheretonext.daos.UserDAO
import com.example.wheretonext.data.AppDatabase
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var userDao: UserDAO

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get DAO from Room database
        userDao = AppDatabase.getDatabase(requireContext()).userDao

        val edtEmail = view.findViewById<EditText>(R.id.edt_email)
        val edtPassword = view.findViewById<EditText>(R.id.edt_password)
        val btnLogin = view.findViewById<TextView>(R.id.btn_login)
        val tvRegister = view.findViewById<TextView>(R.id.tv_register)

        // Navigate to register screen
        tvRegister.setOnClickListener {
            val email = edtEmail.text.toString()
            goToRegister(email)
        }

        // Handle login
        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    val user = userDao.login(email, password)
                    if (user != null) {
                        goToHome()
                    } else {
                        Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToRegister(email: String) {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment(email)
        findNavController().navigate(action)
    }

    private suspend fun goToHome() {
        val email = view?.findViewById<EditText>(R.id.edt_email)?.text.toString()
        val password = view?.findViewById<EditText>(R.id.edt_password)?.text.toString()

        // Get instance of your AppDatabase
        val db = AppDatabase.getDatabase(requireContext())

        // Assuming you have a userDao in your AppDatabase
        val user = db.userDao.login(email, password)

        if (user != null) {
            val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(user.role)
            findNavController().navigate(action)
        } else {
            Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show()
        }
    }

}
