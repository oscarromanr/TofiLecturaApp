package roman.oscar.lecturaapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
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
import com.google.firebase.database.FirebaseDatabase

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
        editTextBuscar.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
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
        categorias.add(
            Categoria("Ficción", null, R.drawable.ficcion)
        )
        categorias.add(
            Categoria("Comedia", null, R.drawable.imagen_libro3)
        )
        categorias.add(
            Categoria("Amistad", null, R.drawable.imagen_libro1)
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
            val text: TextView = view.findViewById(R.id.nombre_categoria)
            Log.d("Variable categoria", "Variable categoria: $categoria")
            Log.d("Categorías obtenidas", "Categorias obtenidas: $categorias")
            text.setText(categoria.nombre)
            image.setImageResource(categoria.image)
            image.setOnClickListener {
                val intento = Intent(context, CategoriaLibros::class.java)
                intento.putExtra("nombre", categoria.nombre)
                Log.d("Nombre que envia", "Nombre que envia: ${categoria.nombre}")
                context.startActivity(intento)
            }
            return view
        }
    }
}