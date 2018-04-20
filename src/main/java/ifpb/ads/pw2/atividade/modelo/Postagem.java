/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.pw2.atividade.modelo;

/**
 *
 * @author alann
 */
public class Postagem implements Comparable {

    private int id;
    private String menssagem;
    private Usuario usuario;

    public Postagem() {

    }

    public String getMenssagem() {
        return menssagem;
    }

    public void setMenssagem(String menssagem) {
        this.menssagem = menssagem;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Postagem{" + "id=" + id + ", menssagem=" + menssagem + ", usuario=" + usuario + '}';
    }

    @Override
    public int compareTo(Object o) {
        
        Postagem post = (Postagem) o;
         
        if (this.id < post.id) {
            return -1;
        }
        if (this.id > post.id) {
            return 1;
        }
        return 0;
        
    }

   

}
