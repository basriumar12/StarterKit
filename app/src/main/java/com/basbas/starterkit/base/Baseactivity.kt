package com.basbas.starterkit.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.basbas.starterkit.R
import com.basbas.starterkit.utils.hasNetwork
import com.valdesekamdem.library.mdtoast.MDToast
import org.jetbrains.anko.ctx

open class Baseactivity : AppCompatActivity(), Baseview {

    lateinit var loading: MaterialDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val materialDialog = MaterialDialog.Builder(this)
        materialDialog.progress(true,0)
        materialDialog.cancelable(false)
        loading = materialDialog.build()

    }

    override fun intentTo(target:Class<*>){
        startActivity(Intent(this,target))
    }

    override fun showErrorMessage(message: String?) {
        MDToast.makeText(this,message, MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show()
    }

    override fun showSuccessMessage(message: String?) {
        MDToast.makeText(this,message,MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show()
    }

    override fun showInfoMessage(message: String?) {
        MDToast.makeText(this,message,MDToast.LENGTH_SHORT,MDToast.TYPE_INFO).show()
    }

    override fun showLongErrorMessage(message: String?) {
        MDToast.makeText(this,message,MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show()
    }

    override fun showLongSuccessMessage(message: String?) {
        MDToast.makeText(this,message,MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show()
    }

    override fun showLongInfoMessage(message: String?) {
        MDToast.makeText(this,message,MDToast.LENGTH_LONG,MDToast.TYPE_INFO).show()
    }

    override fun showProgressDialog() {
        loading.setContent(R.string.progress_dialog)
        loading.show()
    }

    override fun showProgressDialog(message: String?) {
        loading.setContent(message)
        loading.show()
    }

    override fun updateMessageDialog(message: String?) {
        loading.setContent(message)
    }

    override fun dismissProgressDialog() {
        if(loading.isShowing)
            loading.dismiss()
    }

    override fun closeActivity() {
        this.finish()
    }

    override fun refresh() {
        onResume()
    }


    override fun onStart() {
        super.onStart()
        val builder = AlertDialog.Builder(ctx)
        if (hasNetwork(this) ==false){

            builder.setTitle("Info ")
            builder.setMessage("anda tidak aktif internet, please aktifkan intenet !!")
            builder.setPositiveButton("Ok"){ _,_ ->
                finish()
            }

            builder.show()
        }


    }
}