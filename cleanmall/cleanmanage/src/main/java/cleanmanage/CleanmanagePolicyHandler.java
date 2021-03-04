package cleanmanage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import cleanmanage.config.kafka.KafkaProcessor;

@Service
public class CleanmanagePolicyHandler {
	@Autowired
	CleanmanageRepository cleanmanageRepository;

	@StreamListener(KafkaProcessor.INPUT)
	public void onStringEventListener(@Payload String eventString) {

	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever호출취소됨_(@Payload CleancallCancelled cleancallCancelled) {
		System.out.println("##### EVT TYPE[CleancallCancelled]  : " + cleancallCancelled.getEventType());
		if (cleancallCancelled.isMe()) {
			System.out.println("##### listener  : " + cleancallCancelled.toJson());

			if (cleancallCancelled.getId() != null)
				// Correlation id 는 'custel' 임
				cleanmanageRepository.findById(Long.valueOf(cleancallCancelled.getId())).ifPresent((cleanmanage) -> {
					cleanmanage.setStatus("호출요청취소됨");
					cleanmanageRepository.save(cleanmanage);
				});
		}
	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever택시할당요청됨_(@Payload CleanmanageAssigned cleanmanageAssigned) {
		System.out.println("##### EVT TYPE[CleanmanageAssigned]  : " + cleanmanageAssigned.getEventType());
		if (cleanmanageAssigned.isMe()) {
			System.out.println("##### listener[CleanassignCompleted]  : " + cleanmanageAssigned.toJson());

			if (cleanmanageAssigned.getId() != null)
				// Correlation id 는 'custel' 임
				cleanmanageRepository.findById(Long.valueOf(cleanmanageAssigned.getId())).ifPresent((cleanmanage) -> {
					cleanmanage.setStatus(cleanmanageAssigned.getStatus());
					cleanmanageRepository.save(cleanmanage);
				});

//        	CleanmanageRepository.findBycustel(CleanmanageAssigned.getCustel()).ifPresent((Cleanmanage) -> {
//				System.out.println("CleanmanageAssigned = " + Cleanmanage.getCustel());
//				Cleanmanage.setState(CleanmanageAssigned.getState());
//				CleanmanageRepository.save(Cleanmanage);
//			});
//            Cleanmanage 관리 = new Cleanmanage();
//            관리.setState(CleanassignCompleted.getState());
//            관리.setCleanman(CleanassignCompleted.getCleanman());
//            관리.setCleanmantel(CleanassignCompleted.getCleanmantel());
//            관리.setCleanmanid(CleanassignCompleted.getCleanmanid());
//            CleanmanageRepository.save(관리);
		}
	}

//    @StreamListener(KafkaProcessor.INPUT)
//    public void whenever택시할당확인됨_(@Payload CleanassignCompleted CleanassignCompleted){
//    	System.out.println("##### EVT TYPE[CleanassignCompleted]  : " + CleanassignCompleted.getEventType());
//        if(CleanassignCompleted.isMe()){
//            System.out.println("##### listener  : " + CleanassignCompleted.toJson());
//            Cleanmanage 관리 = new Cleanmanage();
//            관리.setState(CleanassignCompleted.get할당상태());
//            관리.setCleanman(CleanassignCompleted.getCleanman());
//            관리.setCleanmantel(CleanassignCompleted.getCleanmantel());
//            관리.setCleanmanid(CleanassignCompleted.getCleanmanid());
//            CleanmanageRepository.save(관리);
//        }
//    }

}
