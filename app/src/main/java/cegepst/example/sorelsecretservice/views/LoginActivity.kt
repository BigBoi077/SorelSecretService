package cegepst.example.sorelsecretservice.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cegepst.example.sorelsecretservice.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun onLogin(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}