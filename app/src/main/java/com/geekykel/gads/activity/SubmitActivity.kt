package com.geekykel.gads.activity

import android.app.Activity
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.geekykel.gads.R
import com.geekykel.gads.model.ResultWrapper
import com.geekykel.gads.ui.main.SubmitViewModel
import com.google.android.material.snackbar.Snackbar

class SubmitActivity : AppCompatActivity() {

    private lateinit var viewModel: SubmitViewModel

    private var confirmationAlertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit)
        viewModel = ViewModelProvider(this).get(SubmitViewModel::class.java)

        val backImageButton: ImageButton = findViewById(R.id.backImageButton)
        val button: Button = findViewById(R.id.button)

        viewModel.showConfirmationDialog.observe(this, Observer {
            if (it) showConfirmationAlertDialog()
            else confirmationAlertDialog?.dismiss()
        })

        viewModel.showSuccessDialog.observe(this, Observer {
            if (it) showSuccessAlertDialog()
        })

        viewModel.showFailDialog.observe(this, Observer {
            if (it) showErrorAlertDialog()
        })

        viewModel.errorMsg.observe(this, Observer {
            //showErrorSnackBar(, it)
        })

        viewModel.submitStatus.observe(this, Observer {
            viewModel.showConfirmationDialog(false)
            if (it is ResultWrapper.Success<*>) {
                viewModel.showSuccessDialog(true)
            } else {
                viewModel.showFailDialog(true)
            }
        })

        backImageButton.setOnClickListener {
            finish()
        }

        button.setOnClickListener {
            hideKeyboard(it)
            viewModel.submit()
        }
    }

    fun hideKeyboard(view: View) {
        var context = view.context
        while (context !is Activity && context is ContextWrapper) {
            context = context.baseContext
        }

        val inputMethodManager =
            (context as Activity).getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

    }

    private fun showConfirmationAlertDialog() {

        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val dialogView: View = layoutInflater.inflate(R.layout.confirmation_dialog, null)
        dialogBuilder.setView(dialogView)

        val closeButton: ImageButton = dialogView.findViewById(R.id.closeImageButton) as ImageButton

        val yesButton: Button = dialogView.findViewById(R.id.yesButton) as Button

        confirmationAlertDialog = dialogBuilder.create()
        confirmationAlertDialog?.setCanceledOnTouchOutside(false)
        confirmationAlertDialog?.show()

        closeButton.setOnClickListener {
            confirmationAlertDialog?.dismiss()
            viewModel.showConfirmationDialog(false)
        }

        yesButton.setOnClickListener {
            viewModel.submit()
        }
    }

    private fun showErrorAlertDialog() {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val dialogView: View = layoutInflater.inflate(R.layout.error_dialog, null)
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.showFailDialog(false)
            alertDialog.dismiss()
        }, 2000)
    }

    private fun showSuccessAlertDialog() {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val dialogView: View = layoutInflater.inflate(R.layout.success_dialog, null)
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()

        Handler(Looper.getMainLooper()).postDelayed({
            alertDialog.dismiss()
            viewModel.showSuccessDialog(false)
            finish()
        }, 2000)
    }

    private fun showErrorSnackBar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setBackgroundTint(Color.RED).show()
    }
}
