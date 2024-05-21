package org.example.demo.productos.complementos.repositories

import org.example.demo.database.SqlDelightManager
import org.example.demo.productos.complementos.mappers.toComplemento
import org.example.demo.productos.models.Bebida
import org.example.demo.productos.models.Comida
import org.example.demo.productos.models.Complemento
import org.lighthousegames.logging.logging



private val logger=logging()

class ComplementoRepositoryImpl:ComplementoRepository {
    private val db  = SqlDelightManager.databaseQueries

    /**
     * Consultamos la base de datos para obtener todas las entidades y convertirlas en objetos.
     * @return Devuelve una lista de objetos de tipo complemento.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */

    override fun findAll(): List<Complemento> {
        logger.debug { "Obteniendo todas los complementos" }
        return db.getAllComplemetoEntity().executeAsList().map { it.toComplemento() }
    }

    /**
     * Buscamos un complemento por su ID y una vez registrada la operación, ejecuta la base de datos.
     * @param id
     * @return Devuelve un objeto de tipo complemento o null.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */

    override fun findById(id: String): Complemento? {
        logger.debug { "Obteniendo complemento por id: $id" }
        return db.getByIdComplemetoEntity(id)
            .executeAsOneOrNull()
            ?.toComplemento()
    }

    /**
     * Devuelve una lista de objetos de complemento del tipo especificado.
     * @param tipo
     * @return Devuelve una lista de objetos de complemento del tipo especificado.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */

    override fun findByTipo(tipo: String): List<Complemento> {
        logger.debug { "Obteniendo complementos por tipo: $tipo" }
        return db.getComplementoByTipo(tipo).executeAsList().map { it.toComplemento() }
    }

    /**
     * Guardamos un complemento en la base de datos. Registra la operación y verifica si el complemento es tipo bebida o comida.
     * @param producto
     * @return Devuelve un producto.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */

    override fun save(producto: Complemento): Complemento {
        logger.debug { "Guardando complemento: $producto" }

        when(producto) {
            is Bebida -> {
                db.transaction {
                    db.insertComplemento(
                        tipo = "BEBIDA",
                        id = producto.id,
                        nombre = producto.nombre.toString(),
                        precio = producto.precio.toLong()
                    )
                    println("llega aqui")
                }
            }

            is Comida -> {
                db.transaction {
                    db.insertComplemento(
                        tipo = "COMIDA",
                        id = producto.id,
                        nombre = producto.nombre.toString(),
                        precio = producto.precio.toLong()
                    )
                }
            }
        }
        return producto
    }

    /**
     * Actualizamos complemento por el id en la base de datos y si se encuentra se devuelve el complemento original y si no se encuentra un null.
     * @param id
     * @param complemento
     * @return Devuelve el resultado.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */

    override fun update(id: String, complemento: Complemento): Complemento? {
        logger.debug { "Actualizando complemento con id: $id" }
        val result = this.findById(id) ?: return null

        when(complemento){
            is Bebida ->{
                db.updateComplementoEntity(id,complemento.nombre.toString(),complemento.precio.toLong(),"BEBIDA")
            }
            is Comida ->{
                db.updateComplementoEntity(id,complemento.nombre.toString(),complemento.precio.toLong(),"COMIDA")
            }
        }

        return result
    }

    /**
     * Borra un complemento con el id si existe, y devuelve el complemento original y si no se encuentra devuelve null.
     * @param id
     * @return Devuelve el resultado.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */

    override fun delete(id: String): Complemento? {
        logger.debug { "Borrando complemento con id: $id" }
        val result = this.findById(id) ?: return null
        db.deleteComplementoByID(id)
        return result
    }
}