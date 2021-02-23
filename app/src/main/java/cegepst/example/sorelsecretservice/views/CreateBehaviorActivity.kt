package cegepst.example.sorelsecretservice.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cegepst.example.sorelsecretservice.R

class CreateBehaviorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_behavior)
    }

    fun saveBehavior(view: View) {
        val name = findViewById<EditText>(R.id.behaviorNameEntry).text.toString()
        val description = findViewById<EditText>(R.id.behaviorDescriptionEntry).text.toString()

        if (name.equals("") || description.equals("")) {
            Toast.makeText(this, "Please make sure both fields are not empty", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val data = Intent()
        data.putExtra("name", name)
        data.putExtra("description", description)

        setResult(RESULT_OK, data)
        finish()
    }

    fun cancelAction(view: View) {
        finish()
    }
}