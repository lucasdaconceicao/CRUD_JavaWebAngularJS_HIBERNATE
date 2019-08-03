
package com.backend.controllers;

import com.backend.infra.HibernateUtil;
import com.backend.model.UsuariosDAO;
import com.backend.model.Usuarios;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author lucas
 */
@Path("usuarios")
public class UsuariosController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<Usuarios> listUsuarios(){
        try {
            return (List<Usuarios>) HibernateUtil
                .getSessionFactory()
                .openSession()
               .createQuery("from usuarios")
               .list();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE,null,ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/")
    public Usuarios getUsuario(@PathParam("id") int id){
        try {
            UsuariosDAO usuariosDAO=new UsuariosDAO();
            return usuariosDAO.selecionar(id);
        }
        catch (Exception ex) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE,null,ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response create(Usuarios usuario){
         Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();
        try {
            sessao.save(usuario);
            transacao.commit();
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            transacao.rollback();
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE,null,ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response update(Usuarios usuario){
       Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();
        try {
            sessao.merge(usuario);
            transacao.commit();
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            transacao.rollback();
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE,null,ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    @DELETE
    @Path("{id}/")
    public Response delete(@PathParam("id") int id){
        try {
            UsuariosDAO usuariosDAO=new UsuariosDAO();
            usuariosDAO.excluir(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE,null,ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
 