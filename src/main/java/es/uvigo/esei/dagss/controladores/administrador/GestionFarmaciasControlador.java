/*
 Proyecto Java EE, DAGSS-2015
 */
package es.uvigo.esei.dagss.controladores.administrador;

import es.uvigo.esei.dagss.dominio.daos.FarmaciaDAO;
import es.uvigo.esei.dagss.dominio.daos.UsuarioDAO;
import es.uvigo.esei.dagss.dominio.entidades.Direccion;
import es.uvigo.esei.dagss.dominio.entidades.Farmacia;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author dagss
 */
@Named(value = "gestionFarmaciasControlador")
@SessionScoped
public class GestionFarmaciasControlador implements Serializable {

    @EJB
    FarmaciaDAO farmaciaDAO;

    @EJB
    UsuarioDAO usuarioDAO;

    List<Farmacia> farmacias;
    Farmacia farmaciaActual;

    String password1;
    String password2;

    public GestionFarmaciasControlador() {
    }

    @PostConstruct
    public void inicializar() {
        farmacias = farmaciaDAO.buscarTodos();
    }

    public List<Farmacia> getFarmacias() {
        return farmacias;
    }

    public void setFarmacias(List<Farmacia> farmacias) {
        this.farmacias = farmacias;
    }

    public Farmacia getFarmaciaActual() {
        return farmaciaActual;
    }

    public void setFarmaciaActual(Farmacia farmaciaActual) {
        this.farmaciaActual = farmaciaActual;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public void doEliminar() {
        farmaciaDAO.eliminar(farmaciaActual);
        farmacias = farmaciaDAO.buscarTodos(); // Actualizar lista 
    }

    public void doNuevo() {
        farmaciaActual = new Farmacia(); // Farmacia vacia
        farmaciaActual.setDireccion(new Direccion()); // Con dirección vacía
        farmaciaActual.setFechaAlta(Calendar.getInstance().getTime());
        farmaciaActual.setUltimoAcceso(farmaciaActual.getFechaAlta());
    }

    public void doEditar(Farmacia farmacia) {
        farmaciaActual = farmacia;   // Otra alternativa: volver a refrescarlos desde el DAO
    }

    public void doGuardarNuevo() {
        if (passwordsValidos()) {
            // Crea un nueva Farmacia
            farmaciaActual = farmaciaDAO.crear(farmaciaActual);

            // Ajustar su password
            usuarioDAO.actualizarPassword(farmaciaActual.getId(), password1);

            // Actualiza lista
            farmacias = farmaciaDAO.buscarTodos();

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Password incorrecto (usente o no coincidencia)", ""));
        }
    }

    public void doGuardarEditado() {
        if (passwordsValidos()) {
            // Actualiza una Farmacia
            farmaciaActual = farmaciaDAO.actualizar(farmaciaActual);

            // Ajustar su password
            usuarioDAO.actualizarPassword(farmaciaActual.getId(), password1);

            // Actualiza lista
            farmacias = farmaciaDAO.buscarTodos();
        }
    }

    private boolean passwordsValidos() {
        if ((password1 != null) && (password2 != null)) {
            return password1.equals(password2);
        }
        return false;
    }

    public String doVolver() {
        return "../index?faces-redirect=true";
    }

}
