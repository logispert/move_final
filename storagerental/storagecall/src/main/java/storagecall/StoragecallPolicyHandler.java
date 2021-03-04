package storagecall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import storagecall.config.kafka.KafkaProcessor;

@Service
public class StoragecallPolicyHandler {
	@Autowired
	StoragecallRepository storagecallRepository;

	@StreamListener(KafkaProcessor.INPUT)
	public void onStringEventListener(@Payload String eventString) {

	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever할당확인됨_(@Payload StorageassignCompleted storageassignCompleted) {
		System.out.println("##### EVT TYPE[StorageassignCompleted]  : " + storageassignCompleted.getEventType());
		if (storageassignCompleted.isMe() && storageassignCompleted.getTel() != null) {

//           try {
//               // 원래 데이터가 트랜잭션 커밋되기도 전에 이벤트가 너무 빨리 도달하는 경우를 막기 위함
//               Thread.currentThread().sleep(3000); //  no good. --> pay 가 TX 를 마친 후에만 실행되도록 수정함
//           } catch (InterruptedException e) {
//               e.printStackTrace();
//           }
			System.out.println("##### listener[StorageassignCompleted]  : " + storageassignCompleted.toJson());
			

			// Correlation id 는 '고객휴대폰번호' 임
			if(storageassignCompleted.getId() != null)
				storagecallRepository.findById(Long.valueOf(storageassignCompleted.getId())).ifPresent((택시호출) -> {
					택시호출.setStatus("호출확정");
					storagecallRepository.save(택시호출);
				});
//			StoragecallRepository.findBy휴대폰번호(StorageassignCompleted.get고객휴대폰번호()).ifPresent((Storagecall) -> {
//				System.out.println("StorageassignCompleted = " + StorageassignCompleted.get고객휴대폰번호());
//				Storagecall.setStatus("호출확정");
//				StoragecallRepository.save(Storagecall);
//			});
		}

//		if (StorageassignCompleted.isMe()) {
//			Storagecall 호출 = new Storagecall();
//			호출.setStatus(StorageassignCompleted.get할당상태());
//			StoragecallRepository.save(호출);
//
//			System.out.println("##### listener[StorageassignCompleted]  : " + StorageassignCompleted.toJson());
//		}
	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever할당취소됨_(@Payload StorageassignCancelled storageassignCancelled) {
		System.out.println("##### EVT TYPE[StorageassignCancelled]  : " + storageassignCancelled.getEventType());
		if (storageassignCancelled.isMe()) {
			System.out.println("##### listener[StorageassignCancelled]  : " + storageassignCancelled.toJson());
			storagecallRepository.findById(Long.valueOf(storageassignCancelled.getId())).ifPresent((택시호출) -> {
				택시호출.setStatus("호출취소");
				storagecallRepository.save(택시호출);
			});
		}
	}

}
