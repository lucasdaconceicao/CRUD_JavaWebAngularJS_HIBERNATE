
package com.backend.model;

import com.backend.infra.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UsuariosDAO {
    public void excluir(int id) throws Exception{
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();
        try{
           Usuarios u = selecionar(id);
           sessao.delete(u);
           transacao.commit();
        }
        catch(Exception e){
            transacao.rollback();
        }
    }
    public Usuarios selecionar(int id) throws Exception{
        return (Usuarios) HibernateUtil
                .getSessionFactory()
                .openSession()
                .get(Usuarios.class,id);
    }
}
