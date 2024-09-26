package dinhnguyen.techs.core.form.users;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchForm {

	private String fieldSelect;
	@NotEmpty(message = "Tell me the name user you want to search")
	private String name;
	@PositiveOrZero
	private int currentPage;
	@Positive
	private int pageSize;
}
