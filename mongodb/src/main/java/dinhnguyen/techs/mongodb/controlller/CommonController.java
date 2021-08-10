package dinhnguyen.techs.mongodb.controlller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface CommonController<F, T> {

	public ResponseEntity<List<T>> list();

	public ResponseEntity<T> save(@Valid @RequestBody F object);

	public ResponseEntity<T> update(@Valid @RequestBody F object);

	public ResponseEntity<T> findById(@PathVariable("id") String id);

	public ResponseEntity<?> delete(@PathVariable("id") String id);

}
