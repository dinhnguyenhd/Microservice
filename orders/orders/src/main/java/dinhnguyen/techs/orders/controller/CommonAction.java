package dinhnguyen.techs.orders.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CommonAction<F, T> {

	@GetMapping("/list")
	public ResponseEntity<List<T>> list();

	@GetMapping("find/{id}")
	public ResponseEntity<T> findById(@PathVariable("id") Long id);

	@PostMapping("/save")
	public ResponseEntity<T> save(@Valid @RequestBody F object);

	@PostMapping("/update")
	public ResponseEntity<T> update(@Valid @RequestBody F object);

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id);
}
