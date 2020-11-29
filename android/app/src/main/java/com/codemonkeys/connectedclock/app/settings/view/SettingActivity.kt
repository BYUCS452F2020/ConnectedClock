package com.codemonkeys.connectedclock.app.settings.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codemonkeys.connectedclock.R
import com.codemonkeys.connectedclock.app.settings.viewmodel.SettingViewModel
import com.codemonkeys.shared.status.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//@AndroidEntryPoint
class SettingActivity : AppCompatActivity(), AddStatusFragment.AddStatusDialogListener,
                                                SettingRecyclerViewClickListener,
                                                AdapterView.OnItemSelectedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val viewModel by viewModels<SettingViewModel>()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_setting_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        //actionbar
        val actionbar = supportActionBar
        actionbar!!.title = "Setting"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
        // set addStatueButton
        val addStatusButton: Button = findViewById(R.id.setting_add_status_button)
        addStatusButton.setOnClickListener{
            addStatue()
        }
        // logout button
        val logOutButton: Button = findViewById(R.id.setting_logout_button)
        logOutButton.setOnClickListener{
            logOutUser()
        }
        // user hand index options
        val spinner: Spinner = findViewById(R.id.setting_user_clock_hand_index_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.user_hand_index,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.setSelection(this.viewModel.getCurrentUserHandIndex())
        spinner.onItemSelectedListener = this
        // recycle view stuff
        recyclerView = findViewById<RecyclerView>(R.id.setting_status_recyclerView)
        // Whenever the status changed notified the adopter
        this.viewModel.getCurrentStatus().observe(this){
            this.onChanged(it, this)
        }
    }

    fun onChanged(currentStatus: MutableList<Status>, context: SettingActivity) {
        // set the recycle view
        viewManager = LinearLayoutManager(context)
        viewAdapter = SettingStatusAdapter(currentStatus, context)
        recyclerView.apply {
            // use a linear layout manager
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val selectedItem = parent.getItemAtPosition(pos).toString()
        val toast = Toast.makeText(applicationContext, "Selected " + selectedItem, Toast.LENGTH_SHORT)
        toast.show()
        this.viewModel.setUserHandIndex(selectedItem)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // when save get clicked save the status to the AWS
        when (item.itemId) {
            R.id.activity_setting_menu_saveMenuItem -> {
                val c = this
                val coroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    viewModel.saveStatus()
                    Toast.makeText(c, "Saved Statuses", Toast.LENGTH_LONG).show()
                }
            }
            android.R.id.home -> {
                // this activity finished and back to the upper activity
                this.finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        val toast = Toast.makeText(applicationContext, "Nothing Selected", Toast.LENGTH_SHORT)
        toast.show()
    }


    // the go back arrow
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun addStatue() {
        // here start a dialog
        val dialog = AddStatusFragment()
        dialog.show(supportFragmentManager, "AddStatusFragment")
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, statusName: String, clockHandAngle: Int) {
        this.viewModel.addStatus(statusName, clockHandAngle)
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {}

    override fun onDeletionClick(position: Int) {
        this.viewModel.removeStatus(position)
    }

    private fun logOutUser(){
        val toast = Toast.makeText(applicationContext, "Logging Out", Toast.LENGTH_SHORT)
        toast.show()
        this.viewModel.logOutUser()
    }

}