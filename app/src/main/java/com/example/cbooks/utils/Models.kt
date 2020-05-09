package com.example.cbooks.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.ProgressBar
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth


public class Models{
    private lateinit var alertDialog: android.app.AlertDialog
    private lateinit var progressBar: ProgressBar

    //Comprobar conexion a internet

    fun signOut(auth: FirebaseAuth, client: GoogleSignInClient) {
        auth.signOut()
        client.signOut()
    }
}