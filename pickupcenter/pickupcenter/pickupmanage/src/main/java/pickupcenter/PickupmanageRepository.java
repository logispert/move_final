package pickupcenter;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PickupmanageRepository extends PagingAndSortingRepository<Pickupmanage, Long>{

	//Optional<Pickupmanage> findBy고객휴대폰번호(String get고객휴대폰번호);


}