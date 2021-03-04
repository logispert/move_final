package storagemanage;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface StoragemanageRepository extends PagingAndSortingRepository<Storagemanage, Long>{

	Optional<Storagemanage> findBycustel( String getcustel);


}