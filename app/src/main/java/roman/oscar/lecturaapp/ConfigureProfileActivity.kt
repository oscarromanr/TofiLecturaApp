package roman.oscar.lecturaapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ConfigureProfileActivity : AppCompatActivity() {
    private lateinit var imgAvatar: ImageView
    private lateinit var rightButton: Button
    private lateinit var leftButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configure_profile)
        val btnNext = findViewById<Button>(R.id.btnNext)
        var tvAge = findViewById<TextView>(R.id.tvAge)
        var etName = findViewById<TextView>(R.id.etName)
        var btnPlus = findViewById<Button>(R.id.btnPlus)
        var btnMinus = findViewById<Button>(R.id.btnMinus)
        var mAge = 12
        var indiceImg = 0
        val imageResources = intArrayOf(roman.oscar.lecturaapp.R.drawable.img_avatar_uno, roman.oscar.lecturaapp.R.drawable.avatarprofile)

        imgAvatar = findViewById(R.id.imgAvatar);
        rightButton = findViewById(R.id.btnRight);
        leftButton = findViewById(R.id.btnLeft);

        tvAge.text = mAge.toString()
        btnNext.setOnClickListener {
            val intent = Intent(this, FavoriteThemesActivity::class.java)
            startActivity(intent)
        }

        val btnBack = findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        rightButton.setOnClickListener {
            val currentConstantState = imgAvatar.drawable.constantState?.newDrawable()?.constantState
            val currentIndex = imageResources.indexOfFirst { resources.getDrawable(it).constantState?.equals(currentConstantState) == true } ?: 0
            val nextIndex = (currentIndex + 1) % imageResources.size
            indiceImg = nextIndex
            imgAvatar.setImageResource(imageResources[nextIndex])
        }
        leftButton.setOnClickListener {
            val currentConstantState = imgAvatar.drawable.constantState?.newDrawable()?.constantState
            val currentIndex = imageResources.indexOfFirst { resources.getDrawable(it).constantState?.equals(currentConstantState) == true } ?: 0
            val previousIndex = if (currentIndex == 0) imageResources.size - 1 else currentIndex - 1
            indiceImg = previousIndex
            imgAvatar.setImageResource(imageResources[previousIndex])
        }
        btnPlus.setOnClickListener {
            mAge++
            tvAge.text = mAge.toString()
        }
        btnMinus.setOnClickListener {
            if (mAge == 0) {
            } else {
                mAge--
                tvAge.text = mAge.toString()
            }
        }

        btnNext.setOnClickListener {
            var mName = etName.text.toString()

            if (mName.isEmpty()){
                etName.error = "Ingrese su nombre"
                etName.requestFocus()
            } else if (!mName.matches("^[\\p{L}\\s]+\$".toRegex())) {
                etName.error = "Su nombre solo debe contener letras y espacios"
                etName.requestFocus()
            } else {
                val mAge = tvAge.text.toString()
                val imageName = resources.getResourceEntryName(imageResources[indiceImg])

                val sharedPreferences = getSharedPreferences("infoUsuario", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("age", mAge)
                editor.putString("imageName", imageName)
                editor.putString("name", mName)
                editor.apply()

                println(sharedPreferences.getString("user", ""))
                println(sharedPreferences.getString("email", ""))
                println(sharedPreferences.getString("password", ""))
                println(sharedPreferences.getString("age", ""))
                println(sharedPreferences.getString("imageName", ""))
                println(sharedPreferences.getString("name", ""))

                val intent = Intent(this, FavoriteThemesActivity::class.java)
                startActivity(intent)
            }
        }
    }
}