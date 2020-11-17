package com.codemonkeys.connectedclock.app.settings.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.codemonkeys.connectedclock.R


class AddStatusFragment : DialogFragment() {
    internal lateinit var listener: AddStatusDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater
            var view: View? = inflater.inflate(R.layout.add_status_dialog, null)
            builder.setView(view)
                // Add action buttons
                .setPositiveButton("Add/Save",
                        DialogInterface.OnClickListener { dialog, id ->
                            val statusName = view?.findViewById<EditText>(R.id.add_status_name_setting)?.text.toString()
                            val clockHandAngle = view?.findViewById<EditText>(R.id.add_status_clock_angle_setting)?.text.toString().toInt()
                            listener.onDialogPositiveClick(this, statusName, clockHandAngle)
                        })
                .setNegativeButton("Cancel",
                        DialogInterface.OnClickListener { dialog, id ->
                            getDialog()?.cancel()
                            // do not do anything
                            listener.onDialogNegativeClick(this)
                        })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as AddStatusDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    interface AddStatusDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment, statusName: String, clockHandAngle: Int)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }
}