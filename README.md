# Conexión base de datos relacional
### ¿Qué es JDBC?
JDBC (Java Database Connectivity) es una API de Java que permite conectar aplicaciones con bases de datos para ejecutar consultas y gestionar datos. Facilita la interacción entre Java y sistemas de gestión de bases de datos relacionales.

---

### ¿Cuáles son sus componentes?
**DriverManager:** Gestiona los controladores y conexiones a bases de datos.

**Driver:** Traduce las llamadas JDBC en comandos específicos del DBMS.

**Connection:** Representa una conexión activa con la base de datos.

**Statement:** Ejecuta consultas SQL (simple, parametrizada o procedimientos almacenados).

**ResultSet:** Maneja los datos devueltos por las consultas.

**SQLException:** Gestiona errores y excepciones de las operaciones JDBC.

---

### Librerias de Scala que permiten conectarse a una base de datos relacional:

#### Comparativa: Slick vs Doobie

| Característica             | **Slick**                                                   | **Doobie**                                              |
|----------------------------|------------------------------------------------------------|-------------------------------------------------------|
| **Enfoque**                | Declarativo (ORM + DSL funcional para consultas).          | Funcional puro (escribe SQL directamente).            |
| **Facilidad de uso**       | Más sencillo para principiantes gracias al DSL.            | Requiere mayor conocimiento de programación funcional.|
| **Soporte SQL**            | Genera SQL a partir de consultas Scala.                    | Permite escribir SQL nativo directamente.            |
| **Integración funcional**  | Compatible con programación funcional, pero más acoplado al estilo híbrido. | Diseñado específicamente para programación funcional (Cats, ZIO). |
| **Gestión de transacciones**| Integrada y transparente.                                  | Control explícito con transacciones funcionales.      |
| **Rendimiento**            | Optimizado para consultas típicas ORM.                     | Ligero y eficiente en consultas complejas.           |
| **Popularidad**            | Muy popular y ampliamente adoptado en la comunidad.        | Popular entre proyectos funcionales modernos.         |
| **Documentación**          | Extensa y bien estructurada.                               | Buena, pero requiere conocimientos de FP.            |
| **Casos de uso ideales**   | Proyectos con consultas simples y ORM.                     | Proyectos funcionales y consultas SQL complejas.      |
| **Ejemplo de Bases de Datos soportadas** | PostgreSQL, MySQL, SQLite, H2, Oracle.            | PostgreSQL, MySQL, SQLite, H2, Oracle.               |

---

### Documentación de como establecer una conexión a una base de datos relacional
#### Generar una base de datos en mysql

<img width="200" alt="image" src="https://github.com/user-attachments/assets/8d860d80-e23f-4f08-9349-73a5290be34c" />

<img width="200" alt="image" src="https://github.com/user-attachments/assets/2df1a6b2-ba84-415b-b911-35716853df45" />

#### Generar una tabla con datos de prueba
```sql
CREATE TABLE productos (
    idProducto INT PRIMARY KEY,
    nombreProducto VARCHAR(25),
    precio DOUBLE
);
INSERT INTO productos (idProducto, nombreProducto, precio) VALUES
(1, 'Arroz', 1.35),    
(2, 'Frijoles', 1.20),
(3, 'Aceite', 3.50),   
(4, 'Azúcar', 1.10);   
```

<img width="280" alt="image" src="https://github.com/user-attachments/assets/da252458-d265-4b9f-acdb-0960137e5b04" />

#### Desde Scala establecemos la conexión a la base datos
Importamos las Dependencias:
```Scala
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.29"
```
Establecemos la conexión a la base datos
```Scala
 val url = "jdbc:mysql://localhost:3306/prueba"
    val user = "root"
    val password = "kevin"

    try {

      Class.forName("com.mysql.cj.jdbc.Driver")

      // Conectar a la base de datos
      val connection: Connection = DriverManager.getConnection(url, user, password)

      println("Conexión exitosa a la base de datos.")
    }
```

<img width="395" alt="image" src="https://github.com/user-attachments/assets/78f23140-76b3-47b0-9c03-9af1867ef3d7" />

#### Desde Scala realizamos la consulta de todos los datos de la tabla de prueba. 
```Scala
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

```

<img width="380" alt="image" src="https://github.com/user-attachments/assets/4afb45b4-5a95-496f-8528-7f66d03cd5cf" />


## Referencias
Ejemplo de conexión JDBC de Scala y SELECT de SQL | alvinalexander.com. (n.d.). https://alvinalexander.com/scala/scala-jdbc-connection-mysql-sql-select-example/

Danilo Mendoza. (2021, January 11). Slick | Librería de Scala para trabajar bases de datos relacionales [Video]. YouTube. https://www.youtube.com/watch?v=yA5ormu6knc

The two major SQL libraries in the Scala ecosystem these days are Doobie (https:. . . | Hacker News. (n.d.). https://news.ycombinator.com/item?id=26102974
