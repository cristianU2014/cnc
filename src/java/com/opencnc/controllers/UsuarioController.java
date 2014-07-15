/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.opencnc.controllers;

import com.opencnc.beans.Usuario;
import com.opencnc.util.HibernateUtil;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author root
 */
//******************************************************************************
//Creacion, visualizacion, edicion, borrado, inicio y fin de Sesion de usuarios,
//cambio y recuperacion de contraseña, para los usuarios
//******************************************************************************

@Controller
public class UsuarioController {
    // Implemento Log4j para eventos tipo log
    private static final Logger logger = Logger.getLogger(UsuarioController.class.getName());
 
//******************************************************************************
//En lista los usuarios de la base de datos, esto es solo para cuentas 
//administrativas.
//******************************************************************************
    @RequestMapping  ("/usuario/lista")
    public ModelAndView   lista  (HttpServletRequest request, 
                                    HttpServletResponse response) throws IOException{
        HttpSession sess =  request.getSession();
        if (sess != null){
            Session  s = HibernateUtil.getSessionFactory().openSession();
        
            Criteria  c =s.createCriteria(Usuario.class);
            List<Usuario> l = c.list();
            ModelAndView m = new ModelAndView("/usuario/lista");
            //HttpSession session = request.getSession(true);
            //Usuario us = (Usuario)request.getAttribute("usuario");
            Usuario us = (Usuario)sess.getAttribute("usuario");
            //Usuario us = (Usuario)session.getAttribute("usuario");

            if(us == null){
                 return new ModelAndView("redirect:/usuario/login.htm");   
            }else {
                m.addObject("nombreUsuario",us.getNombre());
                m.addObject("usuarios",l);
                logger.info("Empieza a mostrar lista");
                return m;
            }
        }else{
            request.removeAttribute("usuario");
            return new ModelAndView("redirect:/usuario/login.htm");
        }   
    }
/**
 * *****************************************************************************
 * Crea la vista para ingresar al formulario los datos para la creacion de 
 * usuarios.
 * *****************************************************************************
 * @return
 * @throws IOException 
 */
    @RequestMapping ("/usuario/crear")
    public ModelAndView crear ()throws IOException{
        Usuario u = new Usuario();
        
        ModelAndView m = new ModelAndView("/usuario/crear");
        m.addObject("usuario",u);
        
        logger.info("Empieza a crear un nuevo usuario");
        return m;
    }
/**
 * *****************************************************************************
 * Recoge la informacion del formulario de creacion de usuario y valida que ç
 * tenga contenido y los guarda en la base de datos.
 * *****************************************************************************
 * @param usuario
 * @param request
 * @param response
 * @return
 * @throws Exception 
 */
    @RequestMapping ("/usuario/guardar")
    public ModelAndView guardar (@ModelAttribute Usuario usuario, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response)
                                            throws Exception{
        if (!"".equals(usuario.getApellido()) 
            && !"".equals(usuario.getNombre()) 
            && !"".equals(usuario.getEmail())
             ){
            
            usuario.setEstado("A");
            Calendar c = new GregorianCalendar();
            Date d1 = c.getTime();
            
            usuario.setCreadoFecha(d1);
            usuario.setCreadoPor(1);
            Session s = HibernateUtil.getSessionFactory().openSession();
            
            Transaction t = s.getTransaction();
            s.beginTransaction();
            s.saveOrUpdate(usuario);
            t.commit();
        }
        logger.info("Guarda un nuevo usuario");
        return lista(request, response);
    }
    
/**
 * *****************************************************************************
 * Recibe el parametro "id" para editar el usuario ya creado.
 * *****************************************************************************
 * @param id
 * @param request
 * @param response
 * @return
 * @throws IOException 
 */
    @RequestMapping  ("/usuario/editar/{id}")
    public ModelAndView   editar  ( @PathVariable  Integer id, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response)
                                            throws IOException{
        HttpSession sess =  request.getSession();
        if (sess != null){
            Session s = HibernateUtil.getSessionFactory().openSession();
        
            Usuario u = (Usuario)s.get(Usuario.class, id);
            ModelAndView m = new ModelAndView ("/usuario/editar");
            m.addObject("usuario",u);

            logger.info("Empieza a mostrar lista");
            return m;
        }else{
            request.removeAttribute("usuario");
            return new ModelAndView("redirect:/usuario/login.htm");
        } 
    }
    
/**
 * *****************************************************************************
 * Recibe el "id" del usuario y lo borra de la base de datos.
 * *****************************************************************************
 * @param id
 * @param request
 * @param response
 * @return
 * @throws IOException 
 */
    
    @RequestMapping ("/usuario/borrar/{id}")
    
    public ModelAndView borrar(@PathVariable Integer id, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response)
                                                throws IOException{
        HttpSession sess =  request.getSession();
        if (sess != null){
           Usuario us = (Usuario)sess.getAttribute("usuario");
        
            Session s = HibernateUtil.getSessionFactory().openSession();

            Usuario u = (Usuario) s.get(Usuario.class, id);
            if (us.getUsuarioId() == u.getUsuarioId()){
                return lista(request, response);
            }else{
                Transaction t = s.beginTransaction();
                s.delete(u);
                t.commit();
                logger.info("Borrar usuario");
                return lista(request, response);
            } 
        }else{
            request.removeAttribute("usuario");
            return new ModelAndView("redirect:/usuario/login.htm");
        }    
    }
    
/**
 * *****************************************************************************
 * Crea la vista para el login o inicio de la sesion.
 * *****************************************************************************
 * @return
 * @throws IOException 
 */
    
    @RequestMapping("/usuario/login")
    public ModelAndView login ()throws IOException{
        
        Usuario u = new Usuario();
        
        ModelAndView m = new ModelAndView("/usuario/login");
        m.addObject("usuario",u);
        return m;
    }
    
/**
 * *****************************************************************************
 * Valida los datos de e-mail y contraseña, crea la variable de sesion.
 * *****************************************************************************
 * @param usuario
 * @param request
 * @param response
 * @return
 * @throws IOException 
 */
        
    @RequestMapping("/usuario/iniciarSesion")
    public ModelAndView iniciarSesion (@ModelAttribute Usuario usuario, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response)
                                            throws IOException{
      ModelAndView m = new ModelAndView();
    
      Session s = HibernateUtil.getSessionFactory().openSession();
      
      Criteria c = s.createCriteria(Usuario.class);
      c.add(Restrictions.eq("email", usuario.getEmail()));
      c.add(Restrictions.eq("clave", usuario.getClave()));
      
      List<Usuario> l = c.list();
     
      if (l.isEmpty()){
          m.addObject("errorId", null);
          request.removeAttribute("usuario");
          try {
              return login();
              //return m;
          } catch (Exception ex) {
              java.util.logging.Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
          }
               
      }
      else {
          Usuario ul = l.get(0);
           
          HttpSession ses =  request.getSession();
          ses.setAttribute("usuario", ul);
          request.setAttribute("usuario", ul);
          try {
              //return lista(request);
              //return new ModelAndView("redirect:/modelo/crearModelo.htm");
              //return  crearModelo(request);
              return ModeloController.crearModelo(request, response);
          } catch (Exception ex) {
              java.util.logging.Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
          }
          
      }   
        return null;
      
    }
    
/**
 * *****************************************************************************
 * Cambia la contraseña.
 * *****************************************************************************
 * *****************************************************************************
 * Metodo aun si desarrollo.
 * *****************************************************************************
 * @param request
 * @param response
 * @return
 * @throws IOException 
 */
    
    @RequestMapping("usuario/cambiarContrasena")
    public ModelAndView cambiarContrasena (HttpServletRequest request, 
                                            HttpServletResponse response)
                                            throws IOException{
        HttpSession sess =  request.getSession();
        if (sess != null){
            ModelAndView m = new ModelAndView();
        
        return m;
        }else{
            request.removeAttribute("usuario");
            return new ModelAndView("redirect:/usuario/login.htm");
        }  
    }
    
/**
 * *****************************************************************************
 * Recupera la contraseña.
 * *****************************************************************************
 * Metodo aun si desarrollo.
 * *****************************************************************************
 * @return
 * @throws IOException 
 */
    
    @RequestMapping  ("/usuario/recuperarContra")
    public ModelAndView   recuperar  ()throws IOException{
        
        ModelAndView m = new ModelAndView("/usuario/recuperarContra");
        
        return m;
    } 
    
/**
 * *****************************************************************************
 * Hace el Logout.
 * *****************************************************************************
 * Metodo aun le fata desarrollo para algunas vistas, solo implementado para el
 * index.jsp
 * *****************************************************************************
 * @param request
 * @param response
 * @return
 * @throws IOException 
 */
    @RequestMapping  ("/usuario/logout")
    public ModelAndView   logout  (HttpServletRequest request, 
                                    HttpServletResponse response)
                                    throws IOException{
        HttpSession sess =  request.getSession();
        if (sess != null){
            sess.removeAttribute("usuario");
            return new ModelAndView("redirect:/usuario/login.htm");
        }else{
            return new ModelAndView("redirect:/usuario/login.htm");
        }  
    } 
    
}
