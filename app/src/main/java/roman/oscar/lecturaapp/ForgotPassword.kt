package roman.oscar.lecturaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val btnChecar = findViewById<Button>(R.id.btnChecar)
        val etUser = findViewById<EditText>(R.id.etUser)

        val btnBack = findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnChecar.setOnClickListener {
            val email = etUser.text.toString()

            if (email.isEmpty()) {
                etUser.error = "El campo no puede estar vacío"
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Se ha enviado un correo electrónico para restablecer la contraseña", Toast.LENGTH_SHORT).show()
                            Handler().postDelayed({
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            }, 2000)
                        } else {
                            Toast.makeText(this, "Ocurrió un error al enviar el correo, revise la información proporcionada e intente de nuevo", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}