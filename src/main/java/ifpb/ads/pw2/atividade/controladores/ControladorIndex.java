/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.pw2.atividade.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author alann
 */

@Controller
@RequestMapping("/")
public class ControladorIndex {
    
    //@Autowired
    //private UsuarioDao dao;
    
    @RequestMapping(method = RequestMethod.GET)
    public String getIndex(){
        return "index";
    }
}
