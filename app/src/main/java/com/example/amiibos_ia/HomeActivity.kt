package com.example.amiibos_ia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)


        val logout = findViewById<Button>(R.id.btn_SignOut)

        logout.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                val intent= Intent(this, LoginScreen::class.java)
                Toast.makeText(this,"Logging Out",Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            }
        }

        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            var name = user.displayName
            var email = user.email
            var photoUrl = user.photoUrl

            // Let's assign the values to the user's UI

            val tv_username: TextView = findViewById(R.id.tv_username)
            tv_username.text = name

            val tv_email: TextView = findViewById(R.id.tv_email)
            tv_email.text = email

            // Now, we will assign the user's profile pic

            val mImageView: ImageView = findViewById(R.id.mImageView)

            if (photoUrl !== null){

                mImageView?.let { it1 ->
                    Glide.with(this)
                        .load(photoUrl)
                        .into(it1)
                }

            }

        }

        val gotoCamera: Button = findViewById(R.id.btn_goto_camera)

        gotoCamera.setOnClickListener{

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

    }

}

