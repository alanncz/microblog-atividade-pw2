/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.pw2.atividade.interfaces;

import ifpb.ads.pw2.atividade.modelo.Postagem;
import ifpb.ads.pw2.atividade.modelo.Usuario;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author alann
 */

public interface PostagemDao {
    
    public boolean cadastrarPostagem(Postagem postagem) throws SQLException;
    public List<Postagem> getPostagens(Usuario usuuario)throws SQLException;
    public List<Postagem> getAllPostagensAmigo(int id) throws SQLException;
    public Postagem getPostagemId(int id)throws SQLException;
    public boolean cadastrarFavorito(int id_usuario, int id_postagem)throws SQLException;
    public List<Postagem> getPostagemFovoritas(int id_usuario)throws SQLException;
    public boolean excluirPost(int id) throws SQLException;
    
}
