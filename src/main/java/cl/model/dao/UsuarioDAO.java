package cl.model.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cl.model.entities.Usuario;
import cl.model.repositories.UsuarioRepository;

@Component
public class UsuarioDAO 
{
    @Autowired
    private UsuarioRepository usuarioRepository;
 
    public Iterable<Usuario> listarUsuarios() 
    {
        return usuarioRepository.findAll();
    }
    
    public Optional<Usuario> buscarUsuarioById(String idUsuario) 
    {
        return usuarioRepository.findById(idUsuario);
    }    
    
    public Optional<Usuario> buscarUsuarioPorEmail(String email) 
    {
        return usuarioRepository.findByEmail(email);
    } 
    
    public <S extends Usuario> Usuario guardarUsuario(Usuario usuarioObj) 
    {
        return usuarioRepository.save(usuarioObj);
    }   
}
