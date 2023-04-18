package roman.oscar.lecturaapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class inicio : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)
        val gridLibros: GridView = view.findViewById(R.id.libros_catalog)
        cargarLibros()
        val adapter = GridAdapter(requireContext(), libros)
        // Establece el adaptador en el GridView
        gridLibros.adapter = adapter
        return view
    }

    private fun cargarLibros() {
        val categoriasLibroUno = arrayListOf("Ficción", "Popular", "Amistad","Novela","Arte","Escolar")
        val categoriasLibroDos = arrayListOf("Animales", "Belleza", "Socialismo","Fascismo","Comunismo","Google Chrome")
        val categoriasLibroTres = arrayListOf("Felicidad", "Escolar", "Amistad","Aprendizaje","Popular","Comedia")
        val categoriasLibroCuatro = arrayListOf("Comedia", "Popular", "Naturaleza","Aprendizaje","Actividad","Arte")

        libros.clear()
        libros.add(
            Libro(
                "Click",
                R.drawable.imagen_libro1,
                "Kayla Miller",
                "192 páginas",
                " La historia se centra en un pequeño príncipe que realiza una travesía por el universo. En este viaje descubre la extraña forma en que los adultos ven la vida y comprende el valor del amor y la amistad.",
                categoriasLibroUno
            )
        )
        libros.add(
            Libro(
                "Si le das un pastelito a un gato",
                R.drawable.imagen_libro2,
                "Laura Numeroff",
                "32 páginas",
                "Si le das un pastelito a un gato, querrá ponerle confites de colores. Cuando le des los confites, derramará algunos en el piso. Después de limpiar, sentirá calor. Tendrás que darle un traje de baño ... ¡y éste es sólo el comienzo!",
                categoriasLibroDos
            )
        )
        libros.add(
            Libro(
                "El chico de la última fila",
                R.drawable.imagen_libro3,
                "Onjali Q. Raúf",
                "300 páginas",
                "La amistad no conoce fronteras. Ahmet acaba de llegar nuevo al colegio y no puede comunicarse con nadie. Después de regalarle muchos caramelos, él y yo nos hemos hecho muy amigos. Es un buen chico.",
                categoriasLibroTres
            )
        )
        libros.add(
            Libro(
                "Horse Museum",
                R.drawable.imagen_libro4,
                "Dr. Seuss",
                "80 páginas",
                "Explore how different artists have seen horses, and maybe even find a new way of looking at them yourself.",
                categoriasLibroCuatro
            )
        )
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