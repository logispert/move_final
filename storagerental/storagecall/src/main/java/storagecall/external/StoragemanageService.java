
package storagecall.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(name="taximanage", url="http://localhost:8082")
@FeignClient(name="taximanage", url="http://localhost:8082", fallback = StoragemanageServiceFallback.class)
public interface StoragemanageService {

    @RequestMapping(method= RequestMethod.POST, path="/storagemanages")
    public void storageManageCall( @RequestBody Storagemanage cleanmanage);

}