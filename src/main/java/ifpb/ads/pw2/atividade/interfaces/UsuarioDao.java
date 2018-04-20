/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.pw2.atividade.interfaces;

import ifpb.ads.pw2.atividade.modelo.Usuario;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author alann
 */
public interface UsuarioDao {
    
    public boolean cadastrar(Usuario usuario) throws SQLException;
    public Usuario login(String login, String senha) throws SQLException;
    public Usuario getUsuario(int id) throws SQLException;
    public Usuario getUsuario(String nome) throws SQLException;
    public List<String> getUsuarioNames() throws SQLException;
    
    
}
