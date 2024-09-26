package dinhnguyen.techs.orders.forms;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductForm {

	private Long id;
	@NotBlank
	private String name;
	@NotNull
	private int quatity;
	@NotNull
	@Column(name = "price")
	private int price;

}
