
package ascenter.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(name="taximanage", url="http://localhost:8082")
//@FeignClient(name="taximanage", url="http://taximanage:8080", fallback = AsmanageServiceFallback.class)
@FeignClient(name="taximanage", url="http://localhost:8082", fallback = AsmanageServiceFallback.class)
public interface AsmanageService {

    @RequestMapping(method= RequestMethod.POST, path="/택시관리s")
    public void reqAsmanAssign(@RequestBody Asmanage asmanage);

}