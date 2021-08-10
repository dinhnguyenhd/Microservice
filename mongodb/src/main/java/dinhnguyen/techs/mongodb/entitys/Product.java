package dinhnguyen.techs.mongodb.entitys;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document()
public class Product {

	@Id
	private String id;

	@Field(value = "name")
	@NotBlank(message = "name is not blank")
	private String name;

	@Field(value = "quatity")
	@NotBlank(message = "quatity is not blank")
	private int quatity;

	@NotBlank
	@Field(value = "price")

	private int price;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
