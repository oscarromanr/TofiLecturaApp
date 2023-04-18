package roman.oscar.lecturaapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LibroDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libro_detail)
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
        var imagenLibro= intent.getIntExtra("image", 0)
        var paginasLibro= intent.getIntExtra("paginas", 0)
        var autorLibro = intent.getStringExtra("autor")

        titulo.setText(tituloLibro)
        descripcion.setText(descripcionLibro)
        imagen.setImageResource(imagenLibro)
        autor.setText(autorLibro)
        paginas.setText("$paginasLibro páginas")
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