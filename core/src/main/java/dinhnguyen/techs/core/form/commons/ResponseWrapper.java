package dinhnguyen.techs.core.form.commons;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResponseWrapper<V> {

	private String result;
	private V data;
	private int statusCode;
	private Map<String, String> errors;

}
