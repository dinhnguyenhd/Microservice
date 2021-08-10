package dinhnguyen.techs.mongodb.services;

import java.util.List;

public interface CommonActions<T> {

	public List<T> list();

	public T save(T object);

	public T update(T object);

	public T findByIds(String id);

	public void delete(T object);

	public void deleteById(String id);

}
