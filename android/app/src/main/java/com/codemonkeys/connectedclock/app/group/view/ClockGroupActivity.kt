package com.codemonkeys.connectedclock.app.group.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.codemonkeys.connectedclock.R
import com.codemonkeys.connectedclock.app.group.viewmodel.ClockGroupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClockGroupActivity : AppCompatActivity() {

    private val viewModel by viewModels<ClockGroupViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock_group)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Register Group"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()

        val createGroupButton = findViewById<Button>(R.id.createGroupButton)
        val groupNameEdit = findViewById<EditText>(R.id.groupNameTextView)
        val groupPasswordEdit = findViewById<EditText>(R.id.passwordTextView)

        createGroupButton.setOnClickListener{
            // if groupName is null
            val groupName : String = groupNameEdit.text.toString()
            val password : String = groupPasswordEdit.text.toString()

            if (groupName.isNullOrEmpty() || password.isNullOrEmpty()) {
                val errorToast = Toast.makeText(this,
                                                   "Group Name or Password can't be empty",
                                                         Toast.LENGTH_LONG)
                errorToast.show()
            }
            else{
                val toast = Toast.makeText(applicationContext, "Creating Group", Toast.LENGTH_SHORT)
                toast.show()
                viewModel.createGroup(groupName, password)
            }
        }
    }
}