package cl.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="TELEFONOS")
@Getter
@Setter
public class Telefono implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotFound(action = NotFoundAction.IGNORE)
	@SequenceGenerator(name="T_GENERATOR", sequenceName="SEQ_TELEFONO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_GENERATOR")	
	@Column(name="ID_TELEFONO")
	private Integer idTelefono;

	@Column(name="COD_CIUDAD")
	private String citycode;

	@Column(name="COD_PAIS")
	private String contrycode;

	@Column(name="NUMERO")
	private String number;
	
	@Column(name="ID_USUARIO")
	private String usuario;

	
}