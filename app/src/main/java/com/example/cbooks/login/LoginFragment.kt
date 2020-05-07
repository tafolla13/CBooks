package com.example.cbooks.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import kotlinx.android.synthetic.main.fragment_login.view.*

import com.example.cbooks.R
import com.example.cbooks.registro.RegistroActivity

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    private lateinit var rootView: View

    private var Iniciar_Sesion : Button? = null
    private var Usuario : EditText? = null
    private var Password : EditText? = null
    private var Registro : TextView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?) : View? {
        rootView = inflater.inflate(R.layout.fragment_login, container, false)
        // Inflate the layout for this fragment
        Iniciar_Sesion = rootView.findViewById(R.id.login)
        Usuario = rootView.findViewById(R.id.Usuario)
        Password = rootView.findViewById(R.id.password)
        Iniciar_Sesion!!.isEnabled = false
        Iniciar_Sesio()
        Registrar(rootView)
        return rootView
    }

    //Valida campos para iniciar sesion
    private fun Iniciar_Sesio (){
        Usuario!!.addTextChangedListener(loginTextWatcher)
        Password!!.addTextChangedListener(loginTextWatcher)
        Iniciar_Sesion!!.setOnClickListener{
            Toast.makeText(activity,"Bienvendio a CBooks",Toast.LENGTH_LONG).show()
        }
    }

    //Este metodo habilita o deshabilita el boton de InciarSesion
    private val loginTextWatcher = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val Nombre_de_Usuario = Usuario!!.text.toString().trim()
            val pass = Password!!.text.toString().trim()
            Iniciar_Sesion!!.isEnabled = !Nombre_de_Usuario.isEmpty() && !pass.isEmpty()
        }
        override fun afterTextChanged(s: Editable?) {

        }
    }

    private fun Registrar(view: View){
        Registro = view.findViewById(R.id.registrarse)
        Registro!!.setOnClickListener {
            val i = Intent(activity,RegistroActivity::class.java)
            startActivity(i)
            activity!!.finish()
        }
    }
}
