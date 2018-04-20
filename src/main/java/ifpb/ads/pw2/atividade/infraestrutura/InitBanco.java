/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.pw2.atividade.infraestrutura;

import ifpb.ads.pw2.atividade.interfaces.InitBancoDao;
import ifpb.ads.pw2.atividade.interfaces.UsuarioDao;
import ifpb.ads.pw2.atividade.modelo.Usuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alann
 */

@Repository
public class InitBanco implements InitBancoDao {
    
    @Autowired
    private UsuarioDao daoUsuario;

    
    @Override
     public void initBanco() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException{
        
        File file = new File("initBanco/usuarios.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        
         while (br.ready()) {

            String linha = br.readLine();
            String array[] = linha.split(";");
            Usuario usuario = new Usuario();

            usuario.setNome(array[0]);
            usuario.setSenha(array[1]);
            
            File fotoPerfil = new File("initBanco/" + usuario.getNome() + ".jpg");
            
            String caminhoDiretorio = "src/main/imagens";
            String caminhoImagem = caminhoDiretorio + "/" + fotoPerfil.getName();
            File fotoPerfilCopiada = new File(caminhoImagem);
            
            usuario.setImagem(caminhoImagem);
            
            copyFile(fotoPerfil, fotoPerfilCopiada);
             System.out.println(usuario.getImagem());
             System.out.println(daoUsuario.cadastrar(usuario));

        }

        br.close();
        
    }
    
    @Override
    public void copyFile(File source, File destination) throws IOException {
        if (destination.exists())
            destination.delete();
        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destinationChannel = new FileOutputStream(destination).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(),
                    destinationChannel);
        } finally {
            if (sourceChannel != null && sourceChannel.isOpen())
                sourceChannel.close();
            if (destinationChannel != null && destinationChannel.isOpen())
                destinationChannel.close();
       }
   }
    
}
