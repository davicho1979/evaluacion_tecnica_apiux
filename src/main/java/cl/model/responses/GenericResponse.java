package cl.model.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericResponse<T> 
{
	private T data;
	private Result result;

}
