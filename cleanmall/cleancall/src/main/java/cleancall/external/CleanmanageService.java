
package cleancall.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(name="taximanage", url="http://localhost:8082")
@FeignClient(name="cleanmanage", url="http://localhost:8082", fallback = CleanmanageServiceFallback.class)
public interface CleanmanageService {

    @RequestMapping(method= RequestMethod.POST, path="/cleanmanages")
    public void cleanManageCall(@RequestBody Cleanmanage cleanmanage);

}