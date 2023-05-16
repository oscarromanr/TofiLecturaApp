package roman.oscar.lecturaapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoriaLibros.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoriaLibros : AppCompatActivity() {
    val libros1 = ArrayList<Libro>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_catalogo_categoria)
        var gridLibros: GridView = findViewById(R.id.libros_categoria)
        val textoCategoria: TextView = findViewById(R.id.nombre_categoria)

        val nombre = intent.getStringExtra("nombre")
        if (nombre != null) {
            cargarCategorias(nombre)
        }
        textoCategoria.text = nombre
        val adapter = LibroAdapter(this, libros1)
        gridLibros.adapter = adapter
    }

    private fun cargarCategorias(nombreCategoria: String) {
        val database = FirebaseDatabase.getInstance().reference.child("libro")

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                snapshot.children.forEach { libroSnapshot ->
                    val libro = libroSnapshot.getValue(Libro::class.java)
                    libro?.let {
                        if (it.categorias.contains(nombreCategoria)) {
                            libros1.add(it)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    class LibroAdapter(context: Context, libros: ArrayList<Libro>) : BaseAdapter() {

        private val mContext = context
        private val mLibros = libros

        override fun getCount(): Int {
            return mLibros.size
        }

        override fun getItem(position: Int): Any {
            return mLibros[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val libro = mLibros[position]
            val inflater = LayoutInflater.from(mContext)
            val view = inflater.inflate(R.layout.cell_libro, null)
            val image: ImageView = view.findViewById(R.id.image_libro_cell)

            Picasso.get().load(libro.image).into(image)
            image.setOnClickListener {
                val intento = Intent(mContext, LibroDetail::class.java)
                intento.putExtra("titulo", libro.titulo)
                intento.putExtra("image", libro.image)
                intento.putExtra("autor", libro.autor)
                intento.putExtra("paginas", libro.paginas)
                intento.putExtra("sinopsis", libro.sinopsis)
                intento.putStringArrayListExtra("categorias", libro.categorias)
                mContext.startActivity(intento)
            }
            return view
        }
    }

}