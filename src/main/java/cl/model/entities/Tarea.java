package cl.model.entities;

import java.io.Serializable;
import java.util.Date;

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
@Table(name="TAREAS")
@Getter
@Setter
public class Tarea implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@Id
	@NotFound(action = NotFoundAction.IGNORE)
	@Column(name="IDENTIFICADOR_TAREA")
	@SequenceGenerator(name="T_GENERATOR_TASK", sequenceName="SEQ_TAREA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_GENERATOR_TASK")	
	private Integer identificadorTarea;

	@Column(name="DESCRIPCION_TAREA")
	private String descripcionTarea;

	@Column(name="FECHA_CREACION_TAREA")
	private Date fechaCreacionTarea;

	@Column(name="VIGENCIA_TAREA")
	private Boolean vigenciaTarea;


}