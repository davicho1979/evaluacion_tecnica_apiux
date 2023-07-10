package cl.model.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


//@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@Builder
public class Result
{
	private boolean success;
	private String message;
	private String code;
	private String errorCode;
	private String timestamp;
}
