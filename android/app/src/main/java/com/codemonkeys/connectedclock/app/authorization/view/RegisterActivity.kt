package com.codemonkeys.connectedclock.app.authorization.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.codemonkeys.connectedclock.R
import com.codemonkeys.connectedclock.app.authorization.viewmodel.LoginViewModel
import com.codemonkeys.connectedclock.app.authorization.viewmodel.RegisterViewModel
import com.codemonkeys.connectedclock.app.zone.viewmodel.ZoneViewModel
import com.codemonkeys.shared.user.requests.LoginUserRequest
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setCallbacks()
    }

    fun setCallbacks() {
        val registerButton = findViewById<Button>(R.id.btn_register_r)

        registerButton.setOnClickListener {
            val password = findViewById<EditText>(R.id.et_password_r).text.toString()
            val username = findViewById<EditText>(R.id.et_user_name_r).text.toString()
            val groupID = findViewById<EditText>(R.id.et_group_id_r).text.toString()
            val clockHand = findViewById<EditText>(R.id.et_clock_hand_r).text.toString().toInt()

            this.viewModel.registerUser(username, password, groupID, clockHand)

        }

    }
}