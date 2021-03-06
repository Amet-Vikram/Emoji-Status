
package com.example.emojistatusapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "Register"

class Register : Fragment(R.layout.fragment_register) {

    private lateinit var etRegEmail : EditText
    private lateinit var etRegPassword : EditText
    private lateinit var btnRegister: Button
    private lateinit var auth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etRegEmail = requireView().findViewById(R.id.etRegEmail)
        etRegPassword = requireView().findViewById(R.id.etRegPassword)
        btnRegister = requireView().findViewById(R.id.btnRegister)
    }

    override fun onStart() {
        super.onStart()

        auth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener {
            createUser()
        }


    }

    private fun createUser() {
        val intentUserLogin = Intent(this.context, Authenticate::class.java)

        if(TextUtils.isEmpty(etRegEmail.toString())){
            etRegEmail.error = "Email can't be empty"
            etRegEmail.requestFocus()
        }else if(TextUtils.isEmpty(etRegPassword.toString())){
            etRegPassword.error = "Password can't be empty"
            etRegPassword.requestFocus()
        }else {
            auth.createUserWithEmailAndPassword(etRegEmail.text.toString().trim(), etRegPassword.text.toString()).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this.context, "Registration Successful. Login to proceed.", Toast.LENGTH_LONG).show()
                    startActivity(intentUserLogin)
                }else{
                    Log.e(TAG, "Registration Error: ${it.exception.toString()}")
                    Toast.makeText(this.context, "Registration error" + it.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}