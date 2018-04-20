/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.pw2.atividade.infraestrutura;

import ifpb.ads.pw2.atividade.interfaces.AmigoDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alann
 */

@Repository
public class AmigoDaoImpl implements AmigoDao {
    
    @Autowired
    private final Connection con;
    
    public AmigoDaoImpl() throws ClassNotFoundException, SQLException {
        con = BancoCon.getConnection();
    }

    @Override
    public boolean cadastrarAmigo(int idUsuario, int idAmigo) throws SQLException {
        
        String sql = "INSERT INTO amigo (id_usuario,id_amigo) VALUES (?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idUsuario);
        ps.setInt(2, idAmigo);
        return ps.execute();
    }

    @Override
    public boolean removerAmigo(int idUsuario, int idAmigo) throws SQLException {
        
        String sql = "DELETE FROM amigo WHERE id_usuario=? AND id_amigo=? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idUsuario);
        ps.setInt(2, idAmigo);
        return ps.execute();  
    }

    @Override
    public List<Integer> getIdAmigos(int idUsuario) throws SQLException {
        
        String sql = "SELECT * FROM amigo WHERE id_usuario=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idUsuario);
        ResultSet rs = ps.executeQuery();
        List<Integer> idAmigos = new ArrayList();
        
        while(rs.next()){
            int id = 0;
            id = rs.getInt("id_amigo");
            idAmigos.add(id);
            
        }
        return idAmigos;
    }
    
}
