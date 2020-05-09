package com.example.cbooks.registro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible

import com.example.cbooks.R
import com.example.cbooks.utils.Models
import com.facebook.LoginStatusCallback
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

/**
 * A simple [Fragment] subclass.
 */
class RegistroFragment : Fragment(), View.OnClickListener {
    private var LoginGoogle : Button? = null
    private lateinit var progress : ProgressBar

    private lateinit var rootView : View

    private val RC_SIGN_IN = 1

    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var model: Models


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?) : View?{
        rootView = inflater.inflate(R.layout.fragment_registro, container, false)
        GoogleInstances()
        FirebaseInstance()
        Instances()
        actions()
        return rootView
    }

    private fun GoogleInstances(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity!!,gso)
    }

    private fun FirebaseInstance(){
        auth = FirebaseAuth.getInstance()
    }

    private fun Instances(){
        LoginGoogle = rootView.findViewById(R.id.loginGooogle)
        progress = rootView.findViewById(R.id.progress_bar)
    }

    private fun actions(){
        LoginGoogle!!.setOnClickListener(this)
    }

    private fun signInGoogle(){
        progress.visibility = View.VISIBLE
        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthGoogle(account!!)
            }catch (e: ApiException){

            }
        }
    }

    private fun firebaseAuthGoogle(account: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(){task ->
        }
    }

    override fun onClick(view: View?){
        when(view?.id){
            R.id.loginGooogle -> {
                signInGoogle()
            }
        }
    }
}
