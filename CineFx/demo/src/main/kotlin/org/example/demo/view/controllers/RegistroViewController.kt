package org.example.demo.view.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import org.example.demo.routes.RoutesManager
import org.example.demo.view.viewModel.RegistroViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger= logging()
class RegistroViewController:KoinComponent {
    val view:RegistroViewModel by inject()
    @FXML
    lateinit var nombreRegister1: TextField
    @FXML
    lateinit var apellidos: TextField
    @FXML
    lateinit var botonRegister: Button
    @FXML
    lateinit var contraseñaRegister: PasswordField
    @FXML
    lateinit var emailRegister: TextField
    @FXML
    lateinit var contraseñaConfirmarRegister: PasswordField

    @FXML
    fun initialize(){
        logger.debug { "inicializando controller register" }
        initDefaultEvents()
    }

    private fun initDefaultEvents() {
        botonRegister.setOnAction { botonRegisterOnAction() }
    }

    private fun botonRegisterOnAction(){
        if (!view.comprobarUsuario(emailRegister.text)) RoutesManager.alerta("Email", "El Email introducido ya esta registrado")
        if (!view.comprobarNombre(nombreRegister1.text)) RoutesManager.alerta("Nombre", "El nombre no puede estar vacio")
        if (!view.comprobarApellido(apellidos.text)) RoutesManager.alerta("Apellido", "El apellido no puede estar vacio")
        if (view.comprobarContraseña(contraseñaRegister.text)) RoutesManager.alerta("Contraseña", "La Contraseña no puede estar vacia")
        if (contraseñaConfirmarRegister.text.isBlank()) RoutesManager.alerta("Contraseña Confrimacion", "La Contraseña de confirmacion no puede estar vacia")
        if (!view.comprobarEmail(emailRegister.text)) RoutesManager.alerta("Email", "Introduce un email Valido")
        if (contraseñaConfirmarRegister.text != contraseñaRegister.text)RoutesManager.alerta("Contraseña", "Las contraseñas deben coincidir")
        if (
            view.comprobarUsuario(emailRegister.text)&&
            view.comprobarNombre(nombreRegister1.text) &&
            view.comprobarApellido(apellidos.text)&&
            view.comprobarEmail(emailRegister.text)&&
            view.comprobarContraseña(contraseñaRegister.text)&&
            contraseñaConfirmarRegister.text == contraseñaRegister.text
        ){
            RoutesManager.changeScene(view = RoutesManager.View.INICIO_SESION, title = "Inicio Sesion")
        }

    }
}