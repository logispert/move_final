
package move24mall.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(name="taximanage", url="http://localhost:8082")
//@FeignClient(name="movermanage", url="http://movermanage:8080", fallback = MovermanageServiceFallback.class)
@FeignClient(name="movermanage", url="http://localhost:8082", fallback = MovermanageServiceFallback.class)
public interface MovermanageService {

    @RequestMapping(method= RequestMethod.POST, path="/movermanages")
    public void reqMoveAssign(@RequestBody Movermanage mg);

}