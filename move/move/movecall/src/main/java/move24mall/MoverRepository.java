package move24mall;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface MoverRepository extends PagingAndSortingRepository<Mover, Long>{

//	Optional<이사호출> findBy휴대폰번호(String 휴대폰번호);
}