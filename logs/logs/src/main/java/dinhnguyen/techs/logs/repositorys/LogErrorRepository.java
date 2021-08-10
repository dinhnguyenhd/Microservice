package dinhnguyen.techs.logs.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dinhnguyen.techs.logs.entitys.LogError;

@Repository
public interface LogErrorRepository extends JpaRepository<LogError, Long> {

}
