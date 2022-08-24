package com.example.androidmvvm.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast

object Utils {
    fun Activity.toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun customDialog(
        context: Context?,
        title: String,
        message: String,
        dialogListener: DialogListener,
    ) {
        context?.let { context ->
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setCancelable(true)
            builder.setPositiveButton("OK") { dialogInterface: DialogInterface?, i: Int ->
                dialogListener.onPositiveClick()
                dialogInterface?.dismiss()
            }
            builder.setNegativeButton("NO")
            { dialogInterface: DialogInterface?, i: Int ->
                dialogListener.onNegativeClick()
                dialogInterface?.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    interface DialogListener {
        fun onPositiveClick()
        fun onNegativeClick()
    }
}