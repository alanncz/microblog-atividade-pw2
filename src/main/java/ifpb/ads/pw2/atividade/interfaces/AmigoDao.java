/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.pw2.atividade.interfaces;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author alann
 */
public interface AmigoDao {
    
    public boolean cadastrarAmigo(int idUsuario, int idAmigo) throws SQLException;
    
    public boolean removerAmigo(int idUsuario, int idAmigo) throws SQLException;
    
    public List<Integer> getIdAmigos(int idUsuario) throws SQLException;
    
}
