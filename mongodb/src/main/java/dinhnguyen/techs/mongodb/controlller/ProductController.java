package dinhnguyen.techs.mongodb.controlller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dinhnguyen.techs.mongodb.entitys.Product;
import dinhnguyen.techs.mongodb.forms.ProductForm;
import dinhnguyen.techs.mongodb.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController implements CommonController<ProductForm, Product> {

	@Autowired
	private ProductService productService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	@GetMapping("/list")
	public ResponseEntity<List<Product>> list() {
		List<Product> list = this.productService.list();
		return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
	}

	@Override
	@PutMapping("/update")
	public ResponseEntity<Product> update(@RequestBody ProductForm form) {
		Product product = this.modelMapper.map(form, Product.class);
		product = this.productService.update(product);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@Override
	@GetMapping("/find/{id}")
	public ResponseEntity<Product> findById(String id) {
		Product product = this.productService.findByIds(id);
		return new ResponseEntity<Product>(product, HttpStatus.OK);

	}

	@Override
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(String id) {
		this.productService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	@PostMapping("/add")
	public ResponseEntity<Product> save(@Valid ProductForm forms) {
		Product product = this.modelMapper.map(forms, Product.class);
		product = this.productService.save(product);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

}
