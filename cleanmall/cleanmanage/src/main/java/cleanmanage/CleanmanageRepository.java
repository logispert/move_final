package cleanmanage;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CleanmanageRepository extends PagingAndSortingRepository<Cleanmanage, Long>{

	//Optional<Cleanmanage> findBycustel( String getcustel);


}