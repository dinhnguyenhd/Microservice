package dinhnguyen.techs.mongodb.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductForm {

	private Long id;

	@NotBlank
	private String name;

	@NotNull
	private int quatity;

	@NotNull
	private int price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuatity() {
		return quatity;
	}

	public void setQuatity(int quatity) {
		this.quatity = quatity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
