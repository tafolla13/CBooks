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
import bolts.Task

import com.example.cbooks.R
import com.example.cbooks.home.HomeActivity
import com.example.cbooks.utils.Models
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class RegistroFragment : Fragment(), View.OnClickListener {
    private var LoginFacebook : Button? = null
    private var LoginGoogle : Button? = null
    private lateinit var progress : ProgressBar

    private lateinit var rootView : View

    private val RC_SIGN_IN = 1
    private val TAG = "sdsa"
    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var model: Models
    private var mCallbackManager: CallbackManager? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?) : View?{
        rootView = inflater.inflate(R.layout.fragment_registro, container, false)
        GoogleInstances()
        FirebaseInstance()
        Instances()
        actions()
        loginManager()
        return rootView
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null){
            startActivity(Intent(activity, HomeActivity::class.java))
            activity!!.finish()
        }
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
        LoginFacebook = rootView.findViewById(R.id.loginFacebook)
        LoginGoogle = rootView.findViewById(R.id.loginGooogle)
        progress = rootView.findViewById(R.id.progress_bar)
    }

    private fun actions(){
        LoginFacebook!!.setOnClickListener(this)
        LoginGoogle!!.setOnClickListener(this)

    }

    private fun signInGoogle(){
        progress.visibility = View.VISIBLE
        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mCallbackManager!!.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthGoogle(account!!)
            }catch (e: ApiException){
                Log.w(TAG, "Conexion con Google fallida")
            }
        }
    }

    private fun firebaseAuthGoogle(account: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(){task ->
        }
    }

    private fun firebaseAuthWithFacebook(token : AccessToken){
        Log.e(TAG, "handleFacebookAccessToken: "+ token)
        val crendential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(crendential)
            .addOnCompleteListener(activity as RegistroActivity, object : OnCompleteListener<AuthResult>{
                override fun onComplete(task: com.google.android.gms.tasks.Task<AuthResult>) {
                    Log.e(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                    if(!task.isSuccessful){
                        Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                    }
                }
            })
    }

    fun loginManager(){
        mCallbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(mCallbackManager,
            object: FacebookCallback<LoginResult>{
                override fun onSuccess(result: LoginResult?) {
                    Log.e(TAG, "Succesfull Facebook Sign In")
                    firebaseAuthWithFacebook(result?.accessToken!!)
                }

                override fun onCancel() {
                    Log.e(TAG, "Cancelled Facebook Sign In")
                }

                override fun onError(error: FacebookException?) {
                    Log.e(TAG, "Facebook sign in error : " + error.toString())
                }
            })
    }

    override fun onClick(view: View?){
        when(view?.id){
            R.id.loginGooogle -> {
                signInGoogle()
            }
            R.id.loginFacebook -> {
                LoginManager.getInstance().logInWithReadPermissions(
                    this,
                    Arrays.asList("user_photos", "email", "user_birthday", "public_profile")
                )
            }
        }
    }
}
