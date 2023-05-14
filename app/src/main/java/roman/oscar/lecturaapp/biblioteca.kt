package roman.oscar.lecturaapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class biblioteca : Fragment() {
    private var currentState: Bundle? = null
    private var adapter: LibroAdapter? = null
    private var libros = ArrayList<Libro>()

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
        val view = inflater.inflate(R.layout.fragment_biblioteca, container, false)
        val gridLibros: GridView = view.findViewById(R.id.libros_biblioteca)
        val layoutOpciones: LinearLayout = view.findViewById(R.id.opciones)
        adapter = LibroAdapter(requireContext(), libros, layoutOpciones)
        gridLibros.adapter = adapter
        val btnBorrar: Button = view.findViewById(R.id.opcion_borrar)
        val btnCancelar: Button = view.findViewById(R.id.opcion_cerrar)
        btnBorrar.setOnClickListener {
            borrarLibrosSeleccionados()
            adapter?.notifyDataSetChanged()
        }
        btnCancelar.setOnClickListener {
            adapter?.clearSelection()
            layoutOpciones.visibility = View.INVISIBLE
            adapter?.notifyDataSetChanged()

        }
        return view
    }

    private fun cargarLibros() {
        val database = FirebaseDatabase.getInstance().reference
        val usersRef = database.child("users")
        val librosRef = database.child("libro")

        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid

        userId?.let { uid ->
            val usuarioRef = usersRef.child(uid)

            usuarioRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.hasChild("favoritos")) {
                        val favoritos = dataSnapshot.child("favoritos").getValue(object : GenericTypeIndicator<List<String>>() {})
                        favoritos?.let {
                            librosRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(librosSnapshot: DataSnapshot) {
                                    libros.clear()
                                    for (libroSnapshot in librosSnapshot.children) {
                                        val titulo = libroSnapshot.child("titulo").getValue(String::class.java) ?: ""
                                        if (it.contains(titulo)) {
                                            val autor = libroSnapshot.child("autor").getValue(String::class.java) ?: ""
                                            val categoriasList = libroSnapshot.child("categorias")
                                                .getValue(object : GenericTypeIndicator<List<String>>() {})
                                                ?: emptyList()
                                            val image = libroSnapshot.child("image").getValue(String::class.java) ?: ""
                                            val paginas = libroSnapshot.child("paginas").getValue(Int::class.java) ?: 0
                                            val sinopsis = libroSnapshot.child("sinopsis").getValue(String::class.java) ?: ""
                                            val libro = Libro(
                                                titulo, image, autor, paginas, sinopsis,
                                                categoriasList as ArrayList<String>
                                            )
                                            libros.add(libro)
                                            Log.d("CargarLibros", "Libro agregado: $libro")

                                        }
                                    }
                                    adapter?.notifyDataSetChanged()
                                }
                                override fun onCancelled(databaseError: DatabaseError) {
                                }
                            })
                        }
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }
    }

    private fun borrarLibrosSeleccionados() {
        val database = FirebaseDatabase.getInstance().reference
        val selectedItems = adapter?.getSelectedItems() ?: emptyList()
        Log.d("CargarLibros", "Libro lista: $selectedItems")
        val usersRef = database.child("users")
        if (selectedItems.isNotEmpty()) {
            val currentUser = FirebaseAuth.getInstance().currentUser
            val userId = currentUser?.uid
            userId?.let { uid ->
                val usuarioRef = usersRef.child(uid)
                usuarioRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val favoritos = dataSnapshot.child("favoritos").getValue(object : GenericTypeIndicator<List<String>>() {})
                        favoritos?.let {
                            val nuevosFavoritos: MutableList<String> = it.toMutableList()
                            nuevosFavoritos.removeAll(selectedItems)
                            usuarioRef.child("favoritos").setValue(nuevosFavoritos)
                                .addOnSuccessListener {
                                    Toast.makeText(requireContext(), "Libros borrados de favoritos", Toast.LENGTH_SHORT).show()
                                    adapter?.clearSelection()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(requireContext(), "Error al borrar los libros de favoritos", Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                    }
                })
            }
        }
    }

    class LibroAdapter(context: Context, libros: ArrayList<Libro>, layoutOpciones: LinearLayout) : BaseAdapter() {
        private val context: Context = context
        private val libros: ArrayList<Libro> = libros
        private val layoutOpciones: LinearLayout = layoutOpciones
        private val selectedItems = mutableListOf<Libro>()
        override fun getCount(): Int {
            return libros.size
        }

        override fun getItem(position: Int): Any {
            return libros[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ResourceType")
        @RequiresApi(Build.VERSION_CODES.M)
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val libro = libros[position]
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.cell_libro_biblioteca, null)
            val image: ImageView = view.findViewById(R.id.image_libro_cell)
            val texto: TextView = view.findViewById(R.id.titulo_libro_cell)
            val checked_image: ImageView = view.findViewById(R.id.checked_image)
            texto.text = libro.titulo
            Picasso.get().load(libro.image).into(image)
            val originalColorFilter = image.drawable.colorFilter
            val colorMatrix = ColorMatrix(floatArrayOf(
                0.5f, 0f, 0f, 0f, 0f,
                0f, 0.5f, 0f, 0f, 0f,
                0f, 0f, 0.5f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            ))
            image.setOnClickListener {
                if (image.drawable.colorFilter == null) {
                    val colorFilter = ColorMatrixColorFilter(colorMatrix)
                    image.drawable.colorFilter = colorFilter
                    checked_image.visibility = View.VISIBLE
                    layoutOpciones.visibility = View.VISIBLE
                    selectedItems.add(libro)
                } else {
                    image.drawable.colorFilter = originalColorFilter
                    checked_image.visibility = View.GONE
                    selectedItems.remove(libro)
                }
                notifyDataSetChanged()
            }

            if (selectedItems.contains(libro)) {
                val colorFilter = ColorMatrixColorFilter(colorMatrix)
                image.drawable.colorFilter = colorFilter
                checked_image.visibility = View.VISIBLE
                layoutOpciones.visibility = View.VISIBLE
            } else {
                image.drawable.colorFilter = originalColorFilter
                checked_image.visibility = View.GONE
            }

            return view
        }
        fun getSelectedItems(): List<String> {
            val selectedTitles = mutableListOf<String>()
            for (libro in selectedItems) {
                selectedTitles.add(libro.titulo)
            }
            return selectedTitles
        }

        fun clearSelection() {
            selectedItems.clear()
            notifyDataSetChanged()
        }
    }
}