package ascenter.external;

import org.springframework.stereotype.Component;

@Component
public class AsmanageServiceFallback implements AsmanageService {
	 
	//@Override
	//public void 택시할당요청(택시관리 택시관리) 
	//{	
	//	System.out.println("Circuit breaker has been opened. Fallback returned instead.");
	//}
	
	
	@Override
	public void reqAsmanAssign(Asmanage asmanage) {
		// TODO Auto-generated method stub
		System.out.println("Circuit breaker has been opened. Fallback returned instead. " + asmanage.getId());
	}

}
