package storagecall;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface StoragecallRepository extends PagingAndSortingRepository<Storagecall, Long>{

//	Optional<Storagecall> findBy휴대폰번호(String 휴대폰번호);
}