/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.pw2.atividade.infraestrutura;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author alann
 */

@Configuration
public class BancoCon {

    /**
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    
    @Bean
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection connection
                = DriverManager.getConnection("jdbc:h2:mem:;"
                        + "INIT=RUNSCRIPT FROM './src/main/resources/create_schema.sql"
                        + "'\\;", "sa", "");
        return connection;

    }
}
