package move24mall.external;

import org.springframework.stereotype.Component;

@Component
public class MovermanageServiceFallback implements MovermanageService {
	
	@Override
	public void reqMoveAssign(Movermanage movermanage) {
		System.out.println("Circuit breaker has been opened. Fallback returned instead. " + movermanage.getId());
	}

}
