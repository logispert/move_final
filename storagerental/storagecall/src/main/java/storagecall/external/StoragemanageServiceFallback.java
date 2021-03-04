package storagecall.external;

import org.springframework.stereotype.Component;

@Component
public class StoragemanageServiceFallback implements StoragemanageService {
	 
	//@Override
	//public void 택시할당요청(Storagemanage Storagemanage)
	//{	
	//	System.out.println("Circuit breaker has been opened. Fallback returned instead.");
	//}
	
	
	@Override
	public void storageManageCall( Storagemanage cleanmanage) {
		// TODO Auto-generated method stub
		System.out.println("Circuit breaker has been opened. Fallback returned instead. " + cleanmanage.getId());
	}

}
