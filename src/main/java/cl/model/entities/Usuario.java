package cl.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Entity
@Table(name="USUARIOS")
@Getter
@Setter
public class Usuario implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_USUARIO")
	private String idUsuario;

	private int activo;

	private String email;

	@Column(name="FECHA_CREACION")
	private Date fechaCreacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_MODIFICACION")
	private Date fechaModificacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_SESION")
	private Date fechaSesion;

	private String name;

	private String password;

	private String token;

	//bi-directional many-to-one association to Telefono
	@OneToMany(mappedBy="usuario")
	@NotFound(action = NotFoundAction.IGNORE)
	private List<Telefono> phones;

}