package com.example.wheretonext.ui.register

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.wheretonext.R
import com.example.wheretonext.data.AppDatabase
import com.example.wheretonext.data.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class RegisterFragment: Fragment() {
    private val args: RegisterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstNameEditText = view.findViewById<EditText>(R.id.edt_first_name)
        val lastNameEditText = view.findViewById<EditText>(R.id.edt_last_name)
        val emailEditText = view.findViewById<EditText>(R.id.edt_email)
        val passwordEditText = view.findViewById<EditText>(R.id.edt_password)
        val registerButton = view.findViewById<Button>(R.id.btn_register)

        registerButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString().trim()
            val lastName = lastNameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString()

            // Basic validation
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.error = "Invalid email"
                return@setOnClickListener
            }

            if (password.length < 6) {
                passwordEditText.error = "Password must be at least 6 characters"
                return@setOnClickListener
            }

            val newUser = User(
                id = UUID.randomUUID().toString(),
                firstname = firstName,
                lastname = lastName,
                email = email,
                password = password,
                role = "user"
            )

            // Insert into database using DAO
            CoroutineScope(Dispatchers.IO).launch {
                val db = AppDatabase.getDatabase(requireContext())
                db.userDao.insertUser(newUser)
            }

            Toast.makeText(requireContext(), "Registered successfully!", Toast.LENGTH_SHORT).show()
            // Optional: navigate to login or home screen

        }

        /*view.findViewById<TextView>(R.id.tv_login).setOnClickListener {
            goToLogin()
        }*/
    }

    /*private fun goToLogin() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(action)
    }*/



}