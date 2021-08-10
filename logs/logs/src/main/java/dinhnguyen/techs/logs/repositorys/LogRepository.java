package dinhnguyen.techs.logs.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dinhnguyen.techs.logs.entitys.Logs;

@Repository
public interface LogRepository extends JpaRepository<Logs, Long> {
	
	@Query("from Logs logs where logs.name = ?1")
	public Logs getServiceByName(String name);

}
