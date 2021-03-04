package storageassign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import storageassign.config.kafka.KafkaProcessor;

@Service
public class StorageassignPolicyHandler {
	@Autowired
	StorageassignRepository storageassignRepository;

	@StreamListener(KafkaProcessor.INPUT)
	public void onStringEventListener(@Payload String eventString) {

	}

	private static String[][] storgeBank = { { "마포구창고A", "9층", "1-1" }, { "서대문구창고A", "4층", "1-2" },
			{ "중구창고A", "4층", "1-3" }, { "파주창고A", "3층", "2-1" }, { "경기창고A", "2층", "2-2" }, { "서울창고A", "3층", "2-3" } };

	public static StorageassignCompleted getStorageassigned() {
		StorageassignCompleted storageassignCompleted = new StorageassignCompleted();

		int randDriver = (int) (Math.random() * 6);
		storageassignCompleted.setAddress(storgeBank[randDriver][0]);
		storageassignCompleted.setFloor(storgeBank[randDriver][1]);
		storageassignCompleted.setStorageid(storgeBank[randDriver][2]);
		return storageassignCompleted;
	}

	// private String 호출상태; //호출,호출중,호출확정,호출취소
	@StreamListener(KafkaProcessor.INPUT)
	public void whenever택시할당요청됨_(@Payload StoragemanageAssigned storagemanageAssigned) {
		System.out.println("##### EVT TYPE[StoragemanageAssigned]  : " + storagemanageAssigned.getEventType());
		if (storagemanageAssigned.isMe()) {
			System.out.println("##### listener  : " + storagemanageAssigned.toJson());

			if (storagemanageAssigned.getStatus() != null && storagemanageAssigned.getStatus().equals("호출중")) {

				storagemanageAssigned.setStatus("호출확정");
				// StorageassignCompleted StorageassignCompleted = Assigner.get택시할당됨();
				// BeanUtils.copyProperties(StoragemanageAssigned, StorageassignCompleted);
				// StorageassignCompleted.setEventType("StorageassignCompleted");
				storagemanageAssigned.publish();

				StorageassignCompleted storageassignCompleted = getStorageassigned();
				storageassignCompleted.setId(storagemanageAssigned.getId());
				storageassignCompleted.setAssignstatus("할당확정");
				storageassignCompleted.setTel(storagemanageAssigned.getTel());
				storageassignCompleted.setLocation(storagemanageAssigned.getLocation());
				storageassignCompleted.setEventType("StorageassignCompleted");
				// StoragemanageAssigned.publishAfterCommit();
				storageassignCompleted.publish();
			}
		}
	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever할당확인됨_(@Payload StorageassignCompleted storageassignCompleted) {
		System.out.println("##### EVT TYPE[StorageassignCompleted]  : " + storageassignCompleted.getEventType());
		if (storageassignCompleted.isMe()) {
			System.out.println("##### listener  : " + storageassignCompleted.toJson());

			if (storageassignCompleted.getAssignstatus() != null
					&& storageassignCompleted.getAssignstatus().equals("할당확정")) {

//            	StorageassignCompleted StorageassignCompleted = Assigner.get택시할당됨();
//            	BeanUtils.copyProperties(StoragemanageAssigned, StorageassignCompleted);
//            	
//                //StorageassignCompleted.setEventType("StorageassignCompleted");
//            	StorageassignCompleted.setEventType("StorageassignCompleted");
//            	//StoragemanageAssigned.publishAfterCommit();
//            	StorageassignCompleted.publish();
			}
		}
	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever택시할당취소됨_(@Payload StoragemanageCancelled storagemanageCancelled) {

		if (storagemanageCancelled.isMe()) {
			System.out.println("##### listener  : " + storagemanageCancelled.toJson());

			storageassignRepository.findById(Long.valueOf(storagemanageCancelled.getId()))
					.ifPresent((storageassign) -> {
						storageassign.setStatus("할당취소");
						storageassignRepository.save(storageassign);
					});

			storagemanageCancelled.publish();
		}
	}

}
