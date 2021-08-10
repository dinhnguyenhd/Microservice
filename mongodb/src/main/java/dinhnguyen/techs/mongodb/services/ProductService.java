package dinhnguyen.techs.mongodb.services;

import java.util.List;

import dinhnguyen.techs.mongodb.entitys.Product;
import dinhnguyen.techs.mongodb.forms.SearchProductForm;

public interface ProductService extends CommonActions<Product> {

	public Product findbyName(String email);

	public List<Product> complexSearch(SearchProductForm form);

	public Product saveComplex(Product product);

}
