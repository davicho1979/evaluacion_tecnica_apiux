package cl.model.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TareaSaveRequest
{
	@Valid
	@NotNull
	private String descripcion;
	
	@Valid
	@NotNull
	private Boolean vigencia;

}