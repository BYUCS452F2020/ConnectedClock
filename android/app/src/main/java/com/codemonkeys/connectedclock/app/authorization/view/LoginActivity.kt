package com.codemonkeys.connectedclock.app.authorization.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.codemonkeys.connectedclock.R
import com.codemonkeys.connectedclock.app.authorization.viewmodel.LoginViewModel
import com.codemonkeys.connectedclock.app.zone.viewmodel.ZoneViewModel
import com.codemonkeys.shared.user.requests.LoginUserRequest
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setCallbacks()
    }

    fun setCallbacks() {
        val loginButton = findViewById<Button>(R.id.btn_submit)
        val registerButton = findViewById<Button>(R.id.btn_register)

        loginButton.setOnClickListener {
            val password = findViewById<EditText>(R.id.et_password).text.toString()
            val username = findViewById<EditText>(R.id.et_user_name).text.toString()
            viewModel.loginUser(username, password)

        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}