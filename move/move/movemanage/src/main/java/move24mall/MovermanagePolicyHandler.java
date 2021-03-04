package move24mall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import move24mall.config.kafka.KafkaProcessor;

@Service
public class MovermanagePolicyHandler {
	@Autowired
	MovermanageRepository movermanageRepository;

	@StreamListener(KafkaProcessor.INPUT)
	public void onStringEventListener(@Payload String eventString) {

	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever호출취소됨_(@Payload MovercallCancelled movercallCancelled) {
		System.out.println("##### EVT TYPE[호출취소됨]  : " + movercallCancelled.getEventType());
		if (movercallCancelled.isMe()) {
			System.out.println("##### listener  : " + movercallCancelled.toJson());

			if (movercallCancelled.getId() != null)
				// Correlation id 는 '고객휴대폰번호' 임
				movermanageRepository.findById(Long.valueOf(movercallCancelled.getId())).ifPresent((movermanage) -> {
					movermanage.setStatus("호출요청취소됨");
					movermanageRepository.save(movermanage);
				});
		}
	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever이사할당요청됨_(@Payload MovermanageAssigned movermanageAssigned) {
		System.out.println("##### EVT TYPE[이사할당요청됨]  : " + movermanageAssigned.getEventType());
		if (movermanageAssigned.isMe()) {
			System.out.println("##### listener[할당확인됨]  : " + movermanageAssigned.toJson());

			if (movermanageAssigned.getId() != null)
				// Correlation id 는 '고객휴대폰번호' 임
				movermanageRepository.findById(Long.valueOf(movermanageAssigned.getId())).ifPresent((movermanage) -> {
					movermanage.setStatus(movermanageAssigned.getStatus());
					movermanageRepository.save(movermanage);
				});

//        	이사관리Repository.findBy고객휴대폰번호(이사할당요청됨.get고객휴대폰번호()).ifPresent((이사관리) -> {
//				System.out.println("이사할당요청됨 = " + 이사관리.get고객휴대폰번호());
//				이사관리.set호출상태(이사할당요청됨.get호출상태());
//				이사관리Repository.save(이사관리);
//			});
//            이사관리 관리 = new 이사관리();
//            관리.set호출상태(할당확인됨.get호출상태());
//            관리.set이사기사이름(할당확인됨.get이사기사이름());
//            관리.set이사기사전화번호(할당확인됨.get이사기사전화번호());
//            관리.set이사번호(할당확인됨.get이사번호());
//            이사관리Repository.save(관리);
		}
	}

//    @StreamListener(KafkaProcessor.INPUT)
//    public void whenever이사할당확인됨_(@Payload 할당확인됨 할당확인됨){
//    	System.out.println("##### EVT TYPE[할당확인됨]  : " + 할당확인됨.getEventType());
//        if(할당확인됨.isMe()){
//            System.out.println("##### listener  : " + 할당확인됨.toJson());
//            이사관리 관리 = new 이사관리();
//            관리.set호출상태(할당확인됨.get할당상태());
//            관리.set이사기사이름(할당확인됨.get이사기사이름());
//            관리.set이사기사전화번호(할당확인됨.get이사기사전화번호());
//            관리.set이사번호(할당확인됨.get이사번호());
//            이사관리Repository.save(관리);
//        }
//    }

}
