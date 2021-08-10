package dinhnguyen.techs.orders.forms;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OrderForm {
	private Long id;

	@NotBlank
	private String title;

	@Email
	private String email;

	@NotNull
	private List<ProductForm> products;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<ProductForm> getProducts() {
		return products;
	}

	public void setProducts(List<ProductForm> products) {
		this.products = products;
	}

}
