package move24mall;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface MovermanageRepository extends PagingAndSortingRepository<Movermanage, Long>{

	//Optional<Movermanage> findBy고객휴대폰번호(String get고객휴대폰번호);


}