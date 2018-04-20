/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.pw2.atividade.interfaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author alann
 */
public interface InitBancoDao {
    
    public void initBanco() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException;
    public void copyFile(File source, File destination) throws IOException;
    
}
