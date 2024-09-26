package dinhnguyen.techs.orders.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface CommonAction<F, T> {

	public ResponseEntity<List<T>> list();

	public ResponseEntity<T> findById(@PathVariable("id") Long id);

	public ResponseEntity<T> save(@Valid @RequestBody F object);

	public ResponseEntity<T> update(@Valid @RequestBody F object);

	public ResponseEntity<?> delete(@PathVariable("id") Long id);
}
