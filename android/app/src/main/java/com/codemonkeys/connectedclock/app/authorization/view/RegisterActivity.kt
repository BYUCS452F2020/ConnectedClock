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
import com.codemonkeys.connectedclock.app.authorization.viewmodel.RegisterViewModel
import com.codemonkeys.connectedclock.app.group.view.ClockGroupActivity
import com.codemonkeys.connectedclock.app.zone.viewmodel.ZoneViewModel
import com.codemonkeys.shared.user.requests.LoginUserRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
            val clockHand = findViewById<EditText>(R.id.et_clock_hand_r).text.toString()
            val group = viewModel.getGroup()

            if(password.length == 0 || username.length == 0 || clockHand.length == 0) {
                Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show()
            } else if (group == null){
                Toast.makeText(this, "You need to choose a group", Toast.LENGTH_SHORT).show()
            } else {
                val coroutineScope = CoroutineScope(Dispatchers.Main)
                val c = this
                coroutineScope.launch {
                    val success = viewModel.registerUser(username, password, group.groupID, clockHand.toInt())
                    if(!success) {
                        Toast.makeText(c, "Error creating user", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        val groupButton = findViewById<Button>(R.id.btn_group_r)

        groupButton.setOnClickListener {
            val intent = Intent(this, ClockGroupActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}