package pickupcenter.external;

import org.springframework.stereotype.Component;

@Component
public class PickupmanageServiceFallback implements PickupmanageService {
	 
	//@Override
	//public void 택시할당요청(택시관리 택시관리) 
	//{	
	//	System.out.println("Circuit breaker has been opened. Fallback returned instead.");
	//}
	
	
	@Override
	public void reqPickupAssign(Pickupmanage pickupmanage) {
		System.out.println("Circuit breaker has been opened. Fallback returned instead. " + pickupmanage.getId());
	}

}
