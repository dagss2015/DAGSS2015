/*
 Proyecto Java EE, DAGSS-2015/16
 */
package es.uvigo.esei.dagss.controladores.converters;

import es.uvigo.esei.dagss.dominio.daos.MedicoDAO;
import es.uvigo.esei.dagss.dominio.entidades.Medico;
import javax.ejb.EJB;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author ribadas
 */
@FacesConverter(value = "medicoConverter", forClass = Medico.class)
public class MedicoConverter extends GenericoConverter<Medico> {
    @EJB
    MedicoDAO dao;

    @Override
    protected Long recuperarId(Medico entidad) {
        return entidad.getId();
    }

    @Override
    protected Medico recuperarEntidad(Long id) {
        return dao.buscarPorId(id);
    }
}
