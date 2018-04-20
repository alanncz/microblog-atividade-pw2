/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.pw2.atividade.infraestrutura;

import ifpb.ads.pw2.atividade.interfaces.UsuarioDao;
import ifpb.ads.pw2.atividade.modelo.Usuario;
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
public class UsuarioDaoImpl implements UsuarioDao {

    @Autowired
    private Connection con;

    public UsuarioDaoImpl() throws ClassNotFoundException, SQLException {
        con = BancoCon.getConnection();
    }

    @Override
    public boolean cadastrar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome,senha, imagem, descricao) VALUES (?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, usuario.getNome());
        ps.setString(2, usuario.getSenha());
        ps.setString(3, usuario.getImagem());
        ps.setString(4, usuario.getDescricao());
        return ps.execute();
    }

    @Override
    public Usuario login(String login, String senha) throws SQLException {
        String sql = "SELECT * FROM usuario";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String nomeAtual = rs.getString("nome");
            String senhaAtual = rs.getString("senha");
            if (nomeAtual.equals(login) && senhaAtual.equals(senha)) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setImagem(rs.getString("imagem"));
                usuario.setDescricao(rs.getString("descricao"));
                return usuario;
            }
        }
        return null;
    }

    @Override
    public Usuario getUsuario(int id) throws SQLException {
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
            usuario.setDescricao(rs.getString("descricao"));
            return usuario;
        }
        return null;
    }

    @Override
    public Usuario getUsuario(String nome) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE nome = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nome);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setImagem(rs.getString("imagem"));
            usuario.setDescricao(rs.getString("descricao"));
            return usuario;
        }
        return null;

    }

    @Override
    public List<String> getUsuarioNames() throws SQLException {
        String sql = "SELECT nome FROM usuario";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        List<String> usernames = new ArrayList<>();
        while (rs.next()) {
            usernames.add(rs.getString(1));
        }
        stmt.close();
        return usernames;
    }

}
