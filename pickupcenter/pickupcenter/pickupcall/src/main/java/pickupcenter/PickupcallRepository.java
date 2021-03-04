package pickupcenter;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PickupcallRepository extends PagingAndSortingRepository<Pickupcall, Long>{

//	Optional<택시호출> findBy휴대폰번호(String 휴대폰번호);
}