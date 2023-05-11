package roman.oscar.lecturaapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi

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
        cargarLibros()
        adapter = LibroAdapter(requireContext(), libros, layoutOpciones)
        gridLibros.adapter = adapter
        return view
    }

    private fun cargarLibros() {
        val categorias = arrayListOf("Ficción", "Popular", "Amistad","Novela","Arte","Escolar")
        libros.clear()
        libros.add(
            Libro(
                "Click",
                R.drawable.imagen_libro1,
                "Kayla Miller",
                192,
                " La historia se centra en un pequeño príncipe que realiza una travesía por el universo. En este viaje descubre la extraña forma en que los adultos ven la vida y comprende el valor del amor y la amistad.",
                categorias
            )
        )
        libros.add(
            Libro(
                "El principito",
                R.drawable.ic_android_black_24dp,
                "Antoine de Saint-Exupéry",
                300,
                " La historia se centra en un pequeño príncipe que realiza una travesía por el universo. En este viaje descubre la extraña forma en que los adultos ven la vida y comprende el valor del amor y la amistad.",
                categorias
            )
        )
    }

    class LibroAdapter(context: Context, libros: ArrayList<Libro>, layoutOpciones: LinearLayout) : BaseAdapter() {
        private val context: Context = context
        private val libros: ArrayList<Libro> = libros
        private val layoutOpciones: LinearLayout = layoutOpciones

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
            texto.setText(libro.titulo)
            image.setImageResource(libro.image)
            image.setOnClickListener {
                val colorMatrix = ColorMatrix(floatArrayOf(
                    0.5f, 0f, 0f, 0f, 0f,
                    0f, 0.5f, 0f, 0f, 0f,
                    0f, 0f, 0.5f, 0f, 0f,
                    0f, 0f, 0f, 1f, 0f
                ))
                val colorFilter = ColorMatrixColorFilter(colorMatrix)
                image.colorFilter = colorFilter
                val checked_image: ImageView = view.findViewById(R.id.checked_image)
                checked_image.visibility = View.VISIBLE
                layoutOpciones.visibility = View.VISIBLE
            }
            return view
        }
    }
}