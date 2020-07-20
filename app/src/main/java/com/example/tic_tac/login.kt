package com.example.tic_tac

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.*
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*


class login : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var database= FirebaseDatabase.getInstance()
    private var myref= database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
    }
    fun buloginevent(view: View){
        logintofirebase(email.text.toString() , password.text.toString())
    }
    fun logintofirebase(email:String , password:String){
        mAuth!!.createUserWithEmailAndPassword(email , password)
            .addOnCompleteListener(this){ task->
                if(task.isSuccessful){
                    Toast.makeText(applicationContext , "LoggedIn Successfully" , Toast.LENGTH_LONG).show()
                    var currentuser= mAuth!!.currentUser
                    if(currentuser!=null) {
                        myref.child("Users").child(splitstring(currentuser.email.toString())).child("Request").setValue(currentuser.uid!!)
                    }
                    loadmain()
                }else{
                    Toast.makeText(applicationContext , "Log In FAILED!!" , Toast.LENGTH_LONG).show()

                }

            }
    }

    override fun onStart() {
        super.onStart()
        loadmain()
    }
    fun loadmain(){
        var currentuser= mAuth!!.currentUser
        if(currentuser!=null) {
            // save in database
           // myref.child("Users").child(currentuser.uid).setValue(currentuser.email)

               // myref.child("Users").child(currentuser.uid!!).setValue(currentuser.email!!)

            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("email", currentuser.email)
            intent.putExtra("uid", currentuser.uid)
            startActivity(intent)

        }
    }
    fun splitstring(str:String):String{
        var split = str.split("@")
        return split[0]
    }

}