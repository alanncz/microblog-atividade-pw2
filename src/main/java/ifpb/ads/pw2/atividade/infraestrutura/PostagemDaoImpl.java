/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.pw2.atividade.infraestrutura;

import ifpb.ads.pw2.atividade.interfaces.PostagemDao;
import ifpb.ads.pw2.atividade.modelo.Postagem;
import ifpb.ads.pw2.atividade.modelo.Usuario;
import java.io.IOException;
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
public class PostagemDaoImpl implements PostagemDao {

    @Autowired
    private final Connection con;

    public PostagemDaoImpl() throws ClassNotFoundException, SQLException, IOException {
        con = BancoCon.getConnection();
    }

    @Override
    public boolean cadastrarPostagem(Postagem postagem) throws SQLException {
        String sql = "INSERT INTO postagem (menssagem,id_usuario) VALUES (?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        int id = postagem.getUsuario().getId();
        ps.setString(1, postagem.getMenssagem());
        ps.setInt(2, id);
        return ps.execute();
    }

    @Override
    public List<Postagem> getPostagens(Usuario usuuario) throws SQLException {

        String sql = "SELECT * FROM postagem WHERE id_usuario = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, usuuario.getId());
        ResultSet rs = ps.executeQuery();
        List<Postagem> postagens = new ArrayList();

        while (rs.next()) {
            Postagem post = new Postagem();
            post.setMenssagem(rs.getString("menssagem"));
            post.setUsuario(usuuario);
            post.setId(rs.getInt("id"));
            postagens.add(post);
        }
        return postagens;
    }

    @Override
    public List<Postagem> getAllPostagensAmigo(int id) throws SQLException {

        String sql = "SELECT * FROM postagem WHERE id_usuario = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        List<Postagem> postagens = new ArrayList();

        while (rs.next()) {

            Postagem post = new Postagem();
            post.setUsuario(getUsuario(rs.getInt("id_usuario")));
            post.setMenssagem(rs.getString("menssagem"));
            post.setId(rs.getInt("id"));
            postagens.add(post);
        }
        return postagens;
    }

    @Override
    public Postagem getPostagemId(int id) throws SQLException {
        String sql = "SELECT * FROM postagem WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Postagem post = new Postagem();
            post.setId(rs.getInt("id"));
            post.setMenssagem(rs.getString("menssagem"));
            post.setUsuario(getUsuario(rs.getInt("id_usuario")));
            return post;
        }

        return null;
    }

    private Usuario getUsuario(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setImagem(rs.getString("imagem"));
            return usuario;
        }

        return null;
    }

    @Override
    public boolean cadastrarFavorito(int id_usuario, int id_postagem) throws SQLException {

        String sql = "INSERT INTO favoritos (id_usuario, id_postagem) VALUES (?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id_usuario);
        ps.setInt(2, id_postagem);
        return ps.execute();
    }

    @Override
    public List<Postagem> getPostagemFovoritas(int id_usuario) throws SQLException {

        String sql = "SELECT * FROM favoritos WHERE id_usuario = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id_usuario);
        ResultSet rs = ps.executeQuery();
        
        List<Integer> ids = new ArrayList();
        List<Postagem> postagensFavoritas = new ArrayList();
        
        while (rs.next()) ids.add(rs.getInt("id_postagem"));
        
        for (int id : ids){
            Postagem post = getPostagemId(id);
            postagensFavoritas.add(post);
        }
        return postagensFavoritas;

    }
    
    @Override
    public boolean excluirPost(int id) throws SQLException{
        
        String sql = "DELETE FROM postagem WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.execute(); 
    }
}


