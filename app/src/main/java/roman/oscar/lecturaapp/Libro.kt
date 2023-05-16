package roman.oscar.lecturaapp

data class Libro(
    var titulo: String,
    var image: String,
    var autor: String,
    var paginas: Int,
    var sinopsis: String,
    var categorias: ArrayList<String>
) {
    constructor() : this("", "", "", 0, "", ArrayList())
}