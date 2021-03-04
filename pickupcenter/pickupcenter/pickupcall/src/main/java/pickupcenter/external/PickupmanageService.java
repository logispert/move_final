
package pickupcenter.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(name="taximanage", url="http://localhost:8082")
//@FeignClient(name="pickmanage", url="http://pickmanage:8080", fallback = PickupmanageServiceFallback.class)
@FeignClient(name="pickmanage", url="http://localhost:8082", fallback = PickupmanageServiceFallback.class)
public interface PickupmanageService {

    @RequestMapping(method= RequestMethod.POST, path="/pickmanages")
    public void reqPickupAssign(@RequestBody Pickupmanage pickupmanage);

}