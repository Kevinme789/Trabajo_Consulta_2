import java.sql.{Connection, DriverManager, SQLException}

object MySQLConexion {
  def main(args: Array[String]): Unit = {
    // Datos de la conexión
    val url = "jdbc:mysql://localhost:3306/prueba"
    val user = "root"
    val password = "kevin"

    try {

      Class.forName("com.mysql.cj.jdbc.Driver")

      // Conectar a la base de datos
      val connection: Connection = DriverManager.getConnection(url, user, password)

      println("Conexión exitosa a la base de datos.")

      // Realizar una consulta de prueba
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery("SELECT * FROM productos")

      // Imprimir los resultados
      while (resultSet.next()) {
        val idProducto = resultSet.getInt("idProducto")
        val nombreProducto = resultSet.getString("nombreProducto")
        val precio = resultSet.getDouble("precio")
        println(s"ID: $idProducto, Producto: $nombreProducto, Precio: $precio")
      }

      // Cerrar la conexión
      resultSet.close()
      statement.close()
      connection.close()

    } catch {
      case e: SQLException => e.printStackTrace()
      case e: Exception => e.printStackTrace()
    }
  }
}
