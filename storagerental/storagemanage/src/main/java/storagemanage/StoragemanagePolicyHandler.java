package storagemanage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import storagemanage.config.kafka.KafkaProcessor;

@Service
public class StoragemanagePolicyHandler {
	@Autowired
	StoragemanageRepository storagemanageRepository;

	@StreamListener(KafkaProcessor.INPUT)
	public void onStringEventListener(@Payload String eventString) {

	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever호출취소됨_(@Payload StoragecallCancelled storagecallCancelled) {
		System.out.println("##### EVT TYPE[StoragecallCancelled]  : " + storagecallCancelled.getEventType());
		if (storagecallCancelled.isMe()) {
			System.out.println("##### listener  : " + storagecallCancelled.toJson());

			if (storagecallCancelled.getId() != null)
				// Correlation id 는 'custel' 임
				storagemanageRepository.findById(Long.valueOf(storagecallCancelled.getId())).ifPresent((storagemanage) -> {
					storagemanage.setStartdate("호출요청취소됨");
					storagemanageRepository.save(storagemanage);
				});
		}
	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever택시할당요청됨_(@Payload StoragemanageAssigned storagemanageAssigned) {
		System.out.println("##### EVT TYPE[StoragemanageAssigned]  : " + storagemanageAssigned.getEventType());
		if (storagemanageAssigned.isMe()) {
			System.out.println("##### listener[StorageassignCompleted]  : " + storagemanageAssigned.toJson());

			if (storagemanageAssigned.getId() != null)
				// Correlation id 는 'custel' 임
				storagemanageRepository.findById(Long.valueOf(storagemanageAssigned.getId())).ifPresent((storagemanage) -> {
					storagemanage.setStartdate(storagemanageAssigned.getStatus());
					storagemanageRepository.save(storagemanage);
				});

//        	StoragemanageRepository.findBycustel(StoragemanageAssigned.getCustel()).ifPresent((Storagemanage) -> {
//				System.out.println("StoragemanageAssigned = " + Storagemanage.getCustel());
//				Storagemanage.setState(StoragemanageAssigned.getState());
//				StoragemanageRepository.save(Storagemanage);
//			});
//            Storagemanage 관리 = new Storagemanage();
//            관리.setState(StorageassignCompleted.getState());
//            관리.setAddress(StorageassignCompleted.getAddress());
//            관리.setFloor(StorageassignCompleted.getFloor());
//            관리.setStorageid(StorageassignCompleted.getStorageid());
//            StoragemanageRepository.save(관리);
		}
	}

//    @StreamListener(KafkaProcessor.INPUT)
//    public void whenever택시할당확인됨_(@Payload StorageassignCompleted StorageassignCompleted){
//    	System.out.println("##### EVT TYPE[StorageassignCompleted]  : " + StorageassignCompleted.getEventType());
//        if(StorageassignCompleted.isMe()){
//            System.out.println("##### listener  : " + StorageassignCompleted.toJson());
//            Storagemanage 관리 = new Storagemanage();
//            관리.setState(StorageassignCompleted.get할당상태());
//            관리.setAddress(StorageassignCompleted.getAddress());
//            관리.setFloor(StorageassignCompleted.getFloor());
//            관리.setStorageid(StorageassignCompleted.getStorageid());
//            StoragemanageRepository.save(관리);
//        }
//    }

}
