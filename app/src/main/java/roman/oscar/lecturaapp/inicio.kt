package roman.oscar.lecturaapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class inicio : Fragment() {
    private var currentState: Bundle? = null
    private var adapter: LibroAdapter? = null
    private var libros = ArrayList<Libro>()
    private var librosCargados = false
    private var adapter2: GridAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentState = savedInstanceState
        cargarLibros()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)
        val gridLibros: GridView = view.findViewById(R.id.libros_catalog)
        adapter2 = GridAdapter(requireContext(), libros)
        // Establece el adaptador en el GridView
        gridLibros.adapter = adapter2
        return view
    }

    private fun cargarLibros() {
        if (librosCargados) {
            // Los libros ya han sido cargados previamente, no es necesario cargarlos nuevamente
            return
        }
        val database = FirebaseDatabase.getInstance().reference
        val librosRef = database.child("libro")
        librosRef.addValueEventListener(object : ValueEventListener {
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
                    val resId = resources.getIdentifier(image, "drawable", context?.packageName)
                    val libro = Libro(
                        titulo, resId, autor, paginas, sinopsis,
                        categoriasList as ArrayList<String>
                    )
                    libros.add(libro)
                }
                librosCargados = true
                adapter2?.notifyDataSetChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    class LibroAdapter(context: Context, libros: ArrayList<Libro>) : BaseAdapter() {
        private val context: Context = context
        private val libros: ArrayList<Libro> = libros

        override fun getCount(): Int {
            return libros.size
        }

        override fun getItem(position: Int): Any {
            return libros[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val libro = libros[position]
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.cell_libro, null)
            val image: ImageView = view.findViewById(R.id.image_libro_cell)


            image.setImageResource(libro.image)
            image.setOnClickListener {
                val intento = Intent(context, LibroDetail::class.java)
                intento.putExtra("titulo", libro.titulo)
                intento.putExtra("image", libro.image)
                intento.putExtra("autor", libro.autor)
                intento.putExtra("paginas", libro.paginas)
                intento.putExtra("sinopsis", libro.sinopsis)
                intento.putStringArrayListExtra("categorias", libro.categorias)
                context.startActivity(intento)
            }
            return view
        }
    }
}