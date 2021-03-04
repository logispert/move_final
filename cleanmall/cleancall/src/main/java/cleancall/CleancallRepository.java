package cleancall;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CleancallRepository extends PagingAndSortingRepository<Cleancall, Long>{

//	Optional<Cleancall> findBy휴대폰번호(String 휴대폰번호);
}