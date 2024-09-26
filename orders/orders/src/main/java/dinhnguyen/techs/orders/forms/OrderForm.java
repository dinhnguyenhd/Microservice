package dinhnguyen.techs.orders.forms;

import java.util.List;

import javax.validation.constraints.Email;
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
public class OrderForm {
	private Long id;

	@NotBlank
	private String title;

	@Email
	private String email;

	@NotNull
	private List<ProductForm> products;

}
