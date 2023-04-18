package roman.oscar.lecturaapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView

class GridAdapter(private val context: Context, private val libros: List<Libro>) : BaseAdapter() {
    private var count = 0

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
        val layoutParams = image.layoutParams as ViewGroup.MarginLayoutParams

        val marginTop = if (position% 2 == 0) 150 else 0
        layoutParams.setMargins(0, marginTop, 0, 0)

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