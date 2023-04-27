package roman.oscar.lecturaapp

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class FavoriteThemesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_themes)

        val btnBack = findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {
            val intent = Intent(this, ConfigureProfileActivity::class.java)
            startActivity(intent)
        }

        configurarBotones();

    }


    private fun configurarBotones() {
        val btnSports = findViewById<Button>(R.id.btnSports)
        val btnAnimals = findViewById<Button>(R.id.btnAnimals)
        val btnMagic = findViewById<Button>(R.id.btnMagic)
        val btnVideogames = findViewById<Button>(R.id.btnVideogames)
        val btnMusic = findViewById<Button>(R.id.btnMusic)
        val btnSpace = findViewById<Button>(R.id.btnSpace)

        var btnSportsSeleccionado = false
        var btnAnimalsSeleccionado = false
        var btnMagicSeleccionado = false
        var btnVideogamesSeleccionado = false
        var btnMusicSeleccionado = false
        var btnSpaceSeleccionado = false

        btnSports.setOnClickListener {
            if (btnSportsSeleccionado) {
                btnSports.setBackgroundResource(R.drawable.ic_age)
                btnSports.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_icon_sports, 0, 0)
                btnSports.setTextColor(Color.parseColor("#66607E")) //Color texto
                btnSportsSeleccionado = false
            } else {
                btnSports.setBackgroundResource(R.drawable.gradient_gold_button)
                btnSports.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_icon_sports_selected, 0, 0)
                btnSports.setTextColor(Color.parseColor("#715F20")) //Color texto
                btnSportsSeleccionado = true
            }
        }

        btnAnimals.setOnClickListener {
            if (btnAnimalsSeleccionado) {
                btnAnimals.setBackgroundResource(R.drawable.ic_age)
                btnAnimals.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_icon_animals, 0, 0)
                btnAnimals.setTextColor(Color.parseColor("#66607E")) //Color texto
                btnAnimalsSeleccionado = false
            } else {
                btnAnimals.setBackgroundResource(R.drawable.gradient_gold_button)
                btnAnimals.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_icon_animals_selected, 0, 0)
                btnAnimals.setTextColor(Color.parseColor("#715F20")) //Color texto
                btnAnimalsSeleccionado = true
            }
        }

        btnMagic.setOnClickListener {
            if (btnMagicSeleccionado) {
                btnMagic.setBackgroundResource(R.drawable.ic_age)
                btnMagic.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_icon_magic, 0, 0)
                btnMagic.setTextColor(Color.parseColor("#66607E")) //Color texto
                btnMagicSeleccionado = false
            } else {
                btnMagic.setBackgroundResource(R.drawable.gradient_gold_button)
                btnMagic.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_icon_magic_selected, 0, 0)
                btnMagic.setTextColor(Color.parseColor("#715F20")) //Color texto
                btnMagicSeleccionado = true
            }
        }

        btnVideogames.setOnClickListener {
            if (btnVideogamesSeleccionado) {
                btnVideogames.setBackgroundResource(R.drawable.ic_age)
                btnVideogames.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_icon_videogames, 0, 0)
                btnVideogames.setTextColor(Color.parseColor("#66607E")) //Color texto
                btnVideogamesSeleccionado = false
            } else {
                btnVideogames.setBackgroundResource(R.drawable.gradient_gold_button)
                btnVideogames.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_icon_videogames_selected, 0, 0)
                btnVideogames.setTextColor(Color.parseColor("#715F20")) //Color texto
                btnVideogamesSeleccionado = true
            }
        }

        btnMusic.setOnClickListener {
            if (btnMusicSeleccionado) {
                btnMusic.setBackgroundResource(R.drawable.ic_age)
                btnMusic.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_icon_music, 0, 0)
                btnMusic.setTextColor(Color.parseColor("#66607E")) //Color texto
                btnMusicSeleccionado = false
            } else {
                btnMusic.setBackgroundResource(R.drawable.gradient_gold_button)
                btnMusic.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_icon_music_selected, 0, 0)
                btnMusic.setTextColor(Color.parseColor("#715F20")) //Color texto
                btnMusicSeleccionado = true
            }
        }

        btnSpace.setOnClickListener {
            if (btnSpaceSeleccionado) {
                btnSpace.setBackgroundResource(R.drawable.ic_age)
                btnSpace.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_icon_space, 0, 0)
                btnSpace.setTextColor(Color.parseColor("#66607E")) //Color texto
                btnSpaceSeleccionado = false
            } else {
                btnSpace.setBackgroundResource(R.drawable.gradient_gold_button)
                btnSpace.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_icon_space_selected, 0, 0)
                btnSpace.setTextColor(Color.parseColor("#715F20")) //Color texto
                btnSpaceSeleccionado = true
            }
        }
    }
}