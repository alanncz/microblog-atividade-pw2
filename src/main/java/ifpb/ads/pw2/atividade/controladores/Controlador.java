/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.pw2.atividade.controladores;

import ifpb.ads.pw2.atividade.interfaces.AmigoDao;
import ifpb.ads.pw2.atividade.interfaces.PostagemDao;
import ifpb.ads.pw2.atividade.interfaces.UsuarioDao;
import ifpb.ads.pw2.atividade.modelo.Postagem;
import ifpb.ads.pw2.atividade.modelo.Usuario;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author alann
 */
@Controller
@RequestMapping("/")
public class Controlador {

    @Autowired
    private UsuarioDao daoUsuario;
    @Autowired
    private PostagemDao daoPostagem;
    @Autowired
    private AmigoDao daoAmigo;

    @PostMapping(value = "/cadastrar")
    public String cadastrar(Usuario usuario, @RequestParam("foto") MultipartFile file)
            throws FileNotFoundException, IOException, SQLException {
        
        if (daoUsuario.getUsuario(usuario.getNome()) != null) return "redirect:/";
        String caminhoImagem = salvarImagem(usuario, file);
        usuario.setImagem(caminhoImagem);
        daoUsuario.cadastrar(usuario);
        return "redirect:/";
    }

    @PostMapping(value = "/login")
    public ModelAndView login(Usuario usuario, HttpSession session) throws SQLException {

        Usuario usuarioLogado = daoUsuario.login(usuario.getNome(), usuario.getSenha());
        if (usuarioLogado == null) {
            return new ModelAndView("redirect:/");
        }

        List<Postagem> postagens = atualizarAmigos(usuarioLogado);

        session.setAttribute("usuarioLogado", usuarioLogado);
        session.setAttribute("postagens", postagens);
        ModelAndView mv = new ModelAndView("home");
        return mv;
    }

    @PostMapping(value = "/postar")
    public ModelAndView postar(Postagem post, HttpSession session) throws SQLException {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        post.setUsuario(usuarioLogado);
        daoPostagem.cadastrarPostagem(post);

        List<Postagem> postagens = atualizarAmigos(usuarioLogado);

        session.setAttribute("postagens", postagens);
        ModelAndView mv = new ModelAndView("home");
        return mv;

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @GetMapping("/home")
    public String home(HttpSession session) {
        return "home";
    }

    @PostMapping(value = "/buscar")
    public ModelAndView execute(@RequestParam String nome, HttpSession session) throws SQLException {

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        Usuario usuarioBuscado = daoUsuario.getUsuario(nome);
        if (usuarioBuscado == null) {
            return new ModelAndView("home");
        }
        List<Postagem> postagensUsuarioBuscado = daoPostagem.getPostagens(usuarioBuscado);
        List<Integer> idAmigos = daoAmigo.getIdAmigos(usuarioLogado.getId());

        int isAmigo = 0;

        if (usuarioBuscado.getId() == usuarioLogado.getId()) {
            isAmigo = -1;
        } else {
            for (int id : idAmigos) {
                if (id == usuarioBuscado.getId()) {
                    isAmigo = 1;
                    break;
                }
            }
        }

        session.setAttribute("isAmigo", isAmigo);
        session.setAttribute("usuarioBuscado", usuarioBuscado);
        session.setAttribute("postagensUsuarioBuscado", postagensUsuarioBuscado);
        return new ModelAndView("postagens");
    }
    
    @GetMapping(value = "/{nome}")
    public ModelAndView buscarNome(@PathVariable String nome, HttpSession session) throws SQLException {

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        Usuario usuarioBuscado = daoUsuario.getUsuario(nome);
        if (usuarioBuscado == null) {
            return new ModelAndView("home");
        }
        List<Postagem> postagensUsuarioBuscado = daoPostagem.getPostagens(usuarioBuscado);
        List<Integer> idAmigos = daoAmigo.getIdAmigos(usuarioLogado.getId());

        int isAmigo = 0;

        if (usuarioBuscado.getId() == usuarioLogado.getId()) {
            isAmigo = -1;
        } else {
            for (int id : idAmigos) {
                if (id == usuarioBuscado.getId()) {
                    isAmigo = 1;
                    break;
                }
            }
        }

        session.setAttribute("isAmigo", isAmigo);
        session.setAttribute("usuarioBuscado", usuarioBuscado);
        session.setAttribute("postagensUsuarioBuscado", postagensUsuarioBuscado);
        return new ModelAndView("postagens");
    }

    @GetMapping(value = "/post/{id}")
    public ModelAndView visualizarPost(@PathVariable Integer id, HttpSession session) throws SQLException {
        Postagem postagem = daoPostagem.getPostagemId(id);
        session.setAttribute("postagemUnica", postagem);
        ModelAndView mv = new ModelAndView("postagem");
        return mv;
    }

    @GetMapping(value = "/excluirPost/{id}")
    public ModelAndView excluirPost(@PathVariable Integer id, HttpSession session) throws SQLException {

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        daoPostagem.excluirPost(id);
        List<Postagem> postagens = atualizarAmigos(usuarioLogado);
        session.setAttribute("postagens", postagens);
        ModelAndView mv = new ModelAndView("home");
        return mv;
    }

    @GetMapping(value = "/favoritar/{id}")
    public ModelAndView favoritarPost(@PathVariable Integer id, HttpSession session) throws SQLException {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        List<Postagem> postagensFavoritas = daoPostagem.getPostagemFovoritas(usuarioLogado.getId());

        if (postagensFavoritas == null) {
            daoPostagem.cadastrarFavorito(usuarioLogado.getId(), id);
        } else {
            for (Postagem post : postagensFavoritas) {
                if (post.getId() == id) {
                    return new ModelAndView("home");
                }
            }
        }

        daoPostagem.cadastrarFavorito(usuarioLogado.getId(), id);
        ModelAndView mv = new ModelAndView("home");
        return mv;
    }

    @GetMapping(value = "/favoritos")
    public ModelAndView getListaFavoritos(HttpSession session) throws SQLException {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        List<Postagem> postagensFavoritas = daoPostagem.getPostagemFovoritas(usuarioLogado.getId());
        session.setAttribute("postagensFavoritas", postagensFavoritas);
        ModelAndView mv = new ModelAndView("favoritos");
        return mv;
    }

    @GetMapping(value = "/addAmigo/{id}")
    public ModelAndView addAmigo(@PathVariable Integer id, HttpSession session) throws SQLException {

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        daoAmigo.cadastrarAmigo(usuarioLogado.getId(), id);
        List<Postagem> postagens = atualizarAmigos(usuarioLogado);

        session.setAttribute("postagens", postagens);
        ModelAndView mv = new ModelAndView("home");
        return mv;
    }

    @GetMapping(value = "/excluirAmigo/{id}")
    public ModelAndView exluirAmigo(@PathVariable Integer id, HttpSession session) throws SQLException {

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        daoAmigo.removerAmigo(usuarioLogado.getId(), id);

        List<Postagem> postagens = atualizarAmigos(usuarioLogado);

        session.setAttribute("postagens", postagens);

        ModelAndView mv = new ModelAndView("home");
        return mv;
    }

    private List<Postagem> atualizarAmigos(Usuario usuarioLogado) throws SQLException {

        List<Integer> idAmigos = daoAmigo.getIdAmigos(usuarioLogado.getId());

        List<Postagem> postagens = daoPostagem.getPostagens(usuarioLogado);

        for (int id : idAmigos) {
            List<Postagem> listaPostagemsAmigo = daoPostagem.getAllPostagensAmigo(id);
            for (Postagem post : listaPostagemsAmigo) {
                postagens.add(post);
            }
            listaPostagemsAmigo.clear();
        }

        Collections.sort(postagens);
        Collections.reverse(postagens);
        return postagens;
    }

    public String salvarImagem(Usuario usuario, MultipartFile arquivo) {

        try {
            String caminhoDiretorio = "src/main/webapp/imagens";
            File pastaUsuario = new File(caminhoDiretorio);
            if (!pastaUsuario.exists()) {
                pastaUsuario.mkdir();
            }

            String caminhoImagem = caminhoDiretorio + "/" + usuario.getId() + arquivo.getOriginalFilename();
            File arq = new File(caminhoImagem);

            FileOutputStream out = new FileOutputStream(arq);
            out.write(arquivo.getBytes());
            out.close();
            return "/imagens/" + usuario.getId() + arquivo.getOriginalFilename();

        } catch (IOException ex) {
        }
        return null;
    }

    @GetMapping("/usernames")
    @ResponseBody
    public String getUsernames(HttpServletResponse res) throws SQLException {
        StringBuilder sb = new StringBuilder();
        List<String> usernames = daoUsuario.getUsuarioNames();
        if (usernames.isEmpty()) {
            return "";
        }
        usernames.stream().forEach((n) -> sb.append(n).append(";"));
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}
