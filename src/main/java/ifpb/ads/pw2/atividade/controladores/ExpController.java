/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.pw2.atividade.controladores;;

import java.sql.SQLException;
import de.siegmar.fastcsv.writer.CsvWriter;
import ifpb.ads.pw2.atividade.interfaces.PostagemDao;
import ifpb.ads.pw2.atividade.interfaces.UsuarioDao;
import ifpb.ads.pw2.atividade.modelo.Postagem;
import ifpb.ads.pw2.atividade.modelo.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author alann
 */
@Controller
@RequestMapping("/export")
public class ExpController {

    @Autowired
    private PostagemDao daoPostagem;
    
    @Autowired
    private UsuarioDao daoUsuario;

    @GetMapping
    public void getCSVFile(int id, HttpServletResponse response) throws SQLException {
        
        Usuario usuario = daoUsuario.getUsuario(id);
        List<Postagem> postagens = daoPostagem.getPostagens(usuario);

        CsvWriter csvWriter = new CsvWriter();
        csvWriter.setFieldSeparator(';');
        csvWriter.setTextDelimiter('\'');
        csvWriter.setLineDelimiter("\r\n".toCharArray());
        csvWriter.setAlwaysDelimitText(true);

        List<String[]> collected = postagens.stream()
                .map((p) -> new String[]{Integer.toString(p.getId()), p.getMenssagem()})
                .collect(Collectors.toList());

        Collection<String[]> data = new ArrayList();
        data.add(new String[]{"id", "menssagem"});
        collected.forEach(data::add);
        
        try {
            File file = new File("export.csv");

            csvWriter.write(file, StandardCharsets.UTF_8, data);

            InputStream inputStream = new FileInputStream(file);
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment; filename=export.csv");
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
        } catch (IOException ex) {
            System.out.println("ocurred an error trying to create and upload csv file");
        }
    }

}
