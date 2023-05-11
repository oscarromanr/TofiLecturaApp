package roman.oscar.lecturaapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import com.google.firebase.database.*

class LibrosBuscados : AppCompatActivity() {
    private var libros = ArrayList<Libro>()
    private var adapter2: GridAdapter? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libros_buscados)
        var textoBuscado = intent.getStringExtra("buscado")
        var gridCategorias: GridView = findViewById(R.id.libros_buscados)
        adapter2 = GridAdapter(this, libros)
        gridCategorias.adapter = adapter2
        buscar(textoBuscado)

        val btnBack = findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun buscar(textoBuscado: String?)
    {
        val database = FirebaseDatabase.getInstance().reference
        val textoBuscadoFinal = textoBuscado+"\uf8ff"
        val query = database.child("libro").orderByChild("titulo").startAt(textoBuscado).endAt(textoBuscadoFinal)
        val query2 = database.child("libro").orderByChild("autor").startAt(textoBuscado).endAt(textoBuscadoFinal)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                libros.clear()
                for (libroSnapshot in dataSnapshot.children) {
                    val autor = libroSnapshot.child("autor").getValue(String::class.java) ?: ""
                    val categoriasList = libroSnapshot.child("categorias")
                        .getValue(object : GenericTypeIndicator<List<String>>() {})
                        ?: emptyList()
                    val image = libroSnapshot.child("image").getValue(String::class.java) ?: ""
                    val paginas = libroSnapshot.child("paginas").getValue(Int::class.java) ?: 0
                    val sinopsis = libroSnapshot.child("sinopsis").getValue(String::class.java) ?: ""
                    val titulo = libroSnapshot.child("titulo").getValue(String::class.java) ?: ""
                    val resId = resources.getIdentifier(image, "drawable", packageName)
                    val libro = Libro(
                        titulo, resId, autor, paginas, sinopsis,
                        categoriasList as ArrayList<String>
                    )
                    libros.add(libro)
                }
                //librosCargados = true
                adapter2?.notifyDataSetChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        query2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (libroSnapshot in dataSnapshot.children) {
                    val autor = libroSnapshot.child("autor").getValue(String::class.java) ?: ""
                    val categoriasList = libroSnapshot.child("categorias")
                        .getValue(object : GenericTypeIndicator<List<String>>() {})
                        ?: emptyList()
                    val image = libroSnapshot.child("image").getValue(String::class.java) ?: ""
                    val paginas = libroSnapshot.child("paginas").getValue(Int::class.java) ?: 0
                    val sinopsis = libroSnapshot.child("sinopsis").getValue(String::class.java) ?: ""
                    val titulo = libroSnapshot.child("titulo").getValue(String::class.java) ?: ""
                    val resId = resources.getIdentifier(image, "drawable", packageName)
                    val libro = Libro(
                        titulo, resId, autor, paginas, sinopsis,
                        categoriasList as ArrayList<String>
                    )
                    libros.add(libro)
                }
                //librosCargados = true
                adapter2?.notifyDataSetChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

    }

}