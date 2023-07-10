package cl.model.responses;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponse 
{

	private String idUsuario;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private Date fechaSesion;
	private int activo;
	private String token;
	

}
