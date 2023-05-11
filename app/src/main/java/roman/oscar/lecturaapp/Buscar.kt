package roman.oscar.lecturaapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Buscar.newInstance] factory method to
 * create an instance of this fragment.
 */
class Buscar : Fragment() {
    private var currentState: Bundle? = null
    private var adapter: CategoriaAdapter? = null
    private var categorias = ArrayList<Categoria>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentState = savedInstanceState
        cargarCategorias()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_buscar, container, false)
        val gridCategorias: GridView = view.findViewById(R.id.categorias_lista)
        cargarCategorias()
        adapter = CategoriaAdapter(requireContext(), categorias)
        gridCategorias.adapter = adapter
        val editTextBuscar: EditText = view.findViewById(R.id.editTextBuscar)
        editTextBuscar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Realiza alguna acción aquí cuando se presiona "Enter"
                Log.d("EditText", "El usuario busco algo")
                val intento = Intent(context, LibrosBuscados::class.java)
                intento.putExtra("buscado", editTextBuscar.text.toString())
                context?.startActivity(intento)
                true
            } else {
                false
            }
        }
        return view
    }

    private fun cargarCategorias() {
        val categorias1 = arrayListOf("Ficción", "Popular", "Amistad","Novela","Arte","Escolar")
        categorias.clear()
        var libros1 = ArrayList<Libro>()
        libros1.add(
            Libro(
                "Click",
                R.drawable.imagen_libro1,
                "Kayla Miller",
                192,
                " La historia se centra en un pequeño príncipe que realiza una travesía por el universo. En este viaje descubre la extraña forma en que los adultos ven la vida y comprende el valor del amor y la amistad.",
                categorias1
            )
        )
        libros1.add(
            Libro(
                "El principito",
                R.drawable.ic_android_black_24dp,
                "Antoine de Saint-Exupéry",
                300,
                " La historia se centra en un pequeño príncipe que realiza una travesía por el universo. En este viaje descubre la extraña forma en que los adultos ven la vida y comprende el valor del amor y la amistad.",
                categorias1
            )
        )
        libros1.add(
            Libro(
                "El pibe motosierra",
                R.drawable.ic_android_black_24dp,
                "Un chico japones",
                145,
                " La historia trata sobre un pibe que de repente se puede transformar en motosierra.",
                categorias1
            )
        )
        categorias.add(
            Categoria("Ficción", libros1, R.drawable.ficcion)
        )
    }

    class CategoriaAdapter(context: Context, categorias: ArrayList<Categoria>) : BaseAdapter() {
        private val context: Context = context
        private val categorias: ArrayList<Categoria> = categorias

        override fun getCount(): Int {
            return categorias.size
        }

        override fun getItem(position: Int): Any {
            return categorias[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val categoria = categorias[position]
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.cell_categoria_buscar, null)
            val image: ImageView = view.findViewById(R.id.image_libro_cell)
            image.setImageResource(categoria.image)
            image.setOnClickListener {
                val intento = Intent(context, CategoriaLibros::class.java)
                intento.putExtra("nombre", categoria.nombre)
                intento.putExtra("libros", categoria.libros)
                context.startActivity(intento)
            }
            return view
        }
    }
}