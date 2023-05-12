package roman.oscar.lecturaapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import roman.oscar.lecturaapp.databinding.ActivityLibroDetailBinding

class LibroDetail : AppCompatActivity() {
    private lateinit var binding: ActivityLibroDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibroDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        validarFavorito()
        binding.btnFavorite.setOnClickListener {
            favorito()
            Log.d("LibroDetail", "Si entra al listener")
            val btnFavorite: AppCompatButton = findViewById(R.id.btnFavorite)
            val drawable = ContextCompat.getDrawable(this, R.drawable.favselect)
            btnFavorite.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable)
            btnFavorite.setBackgroundResource(R.drawable.round_button_selected)
        }
        cargarDescripcion()
        var gridCategorias: GridView = findViewById(R.id.gvCategories)
        val listaRecibida: List<String> = intent.getStringArrayListExtra("categorias")?.toList() ?: emptyList()
        val adapter = CategoriaAdapter(this, listaRecibida)
        gridCategorias.adapter = adapter

    }

    fun cargarDescripcion(){
        var titulo: TextView = findViewById(R.id.tvBookTitle) as TextView
        var descripcion: TextView = findViewById(R.id.tvBookDescription) as TextView
        var imagen: ImageView = findViewById(R.id.ivBookImage) as ImageView
        var autor: TextView = findViewById(R.id.tvBookAuthor) as TextView
        var paginas: TextView = findViewById(R.id.tvBookPages) as TextView

        intent.getStringExtra("titulo")
        var tituloLibro= intent.getStringExtra("titulo")
        var descripcionLibro= intent.getStringExtra("sinopsis")
        var imagenLibro= intent.getStringExtra("image")
        var paginasLibro= intent.getIntExtra("paginas", 0)
        var autorLibro = intent.getStringExtra("autor")

        titulo.setText(tituloLibro)
        descripcion.setText(descripcionLibro)

        Picasso.get().load(imagenLibro).into(imagen)

        autor.setText(autorLibro)
        paginas.setText("$paginasLibro páginas")
    }

    private fun favorito() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val database = FirebaseDatabase.getInstance().reference
        val currentUserRef = database.child("users").child(uid.toString())
        currentUserRef.child("favoritos").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val favoritos: MutableList<String> = if (dataSnapshot.exists()) {
                    dataSnapshot.value as MutableList<String>
                } else {
                    mutableListOf()
                }
                val nuevoFavorito = intent.getStringExtra("titulo")
                if (nuevoFavorito != null && !favoritos.contains(nuevoFavorito)) {
                    favoritos.add(nuevoFavorito)
                    currentUserRef.child("favoritos").setValue(favoritos)
                        .addOnCompleteListener { addFavoritoTask ->
                            if (addFavoritoTask.isSuccessful) {
                                Toast.makeText(this@LibroDetail, "Favorito añadido", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@LibroDetail, "Error al agregar el favorito", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this@LibroDetail, "Ya pertenece a tus favoritos", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@LibroDetail, "Error al obtener los favoritos", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun validarFavorito(){
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val database = FirebaseDatabase.getInstance().reference
        val currentUserRef = database.child("users").child(uid.toString())
        currentUserRef.child("favoritos").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val favoritos: MutableList<String> = if (dataSnapshot.exists()) {
                    dataSnapshot.value as MutableList<String>
                } else {
                    mutableListOf()
                }
                val nuevoFavorito = intent.getStringExtra("titulo")
                if (nuevoFavorito != null && !favoritos.contains(nuevoFavorito)) {

                } else {
                    val btnFavorite: AppCompatButton = findViewById(R.id.btnFavorite)
                    btnFavorite.setBackgroundResource(R.drawable.round_button_selected)
                    val drawable = ContextCompat.getDrawable(this@LibroDetail, R.drawable.favselect)
                    btnFavorite.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable)                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@LibroDetail, "Error al obtener los favoritos", Toast.LENGTH_SHORT).show()
            }
        })
    }

    class CategoriaAdapter(context: Context, categorias: List<String>) : BaseAdapter() {

        private val mContext = context
        private val mCategorias = categorias

        override fun getCount(): Int {
            return mCategorias.size
        }

        override fun getItem(position: Int): Any {
            return mCategorias[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val inflater = LayoutInflater.from(mContext)
            val view = inflater.inflate(R.layout.cell_categoria, null)
            val button: Button = view.findViewById(R.id.button_categoria_cell)
            button.text = mCategorias[position]
            button.setOnClickListener {
                // ...
            }
            return view
        }
    }
}