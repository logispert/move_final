package cleanassign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import cleanassign.config.kafka.KafkaProcessor;

@Service
public class CleanassignPolicyHandler {
	@Autowired
	CleanassignRepository 할당Repository;

	@StreamListener(KafkaProcessor.INPUT)
	public void onStringEventListener(@Payload String eventString) {

	}

	private static String[][] driverBank = { { "백영곤", "010-2345-6789", "34가4567" },
			{ "안채우", "010-3345-7789", "44나4567" }, { "임광현", "010-4345-8789", "54다4567" },
			{ "장윤정", "010-5345-9789", "64라4567" }, { "옥준삼", "010-6345-0789", "74마4567" },
			{ "유승오", "010-7345-1789", "84사4567" } };

	public static CleanassignCompleted get택시할당됨() {
		CleanassignCompleted cleanassignCompleted = new CleanassignCompleted();

		int randDriver = (int) (Math.random() * 6);
		cleanassignCompleted.setCleanman(driverBank[randDriver][0]);
		cleanassignCompleted.setCleanmantel(driverBank[randDriver][1]);
		cleanassignCompleted.setCleanmanid(driverBank[randDriver][2]);
		return cleanassignCompleted;
	}

	// private String 호출상태; //호출,호출중,호출확정,호출취소
	@StreamListener(KafkaProcessor.INPUT)
	public void whenever택시할당요청됨_(@Payload CleanmanageAssigned cleanmanageAssigned) {
		System.out.println("##### EVT TYPE[CleanmanageAssigned]  : " + cleanmanageAssigned.getEventType());
		if (cleanmanageAssigned.isMe()) {
			System.out.println("##### listener  : " + cleanmanageAssigned.toJson());

			if (cleanmanageAssigned.getStatus() != null && cleanmanageAssigned.getStatus().equals("호출중")) {

				cleanmanageAssigned.setStatus("호출확정");
				// CleanassignCompleted CleanassignCompleted = Assigner.get택시할당됨();
				// BeanUtils.copyProperties(CleanmanageAssigned, CleanassignCompleted);
				// CleanassignCompleted.setEventType("CleanassignCompleted");
				cleanmanageAssigned.publish();

				CleanassignCompleted 할당확인됨 = get택시할당됨();
				할당확인됨.setId(cleanmanageAssigned.getId());
				할당확인됨.setAssignstatus("할당확정");
				할당확인됨.setTel(cleanmanageAssigned.getTel());
				할당확인됨.setLocation(cleanmanageAssigned.getLocation());
				할당확인됨.setEventType("CleanassignCompleted");
				// CleanmanageAssigned.publishAfterCommit();
				할당확인됨.publish();
			}
		}
	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever할당확인됨_(@Payload CleanassignCompleted 할당확인됨) {
		System.out.println("##### EVT TYPE[CleanassignCompleted]  : " + 할당확인됨.getEventType());
		if (할당확인됨.isMe()) {
			System.out.println("##### listener  : " + 할당확인됨.toJson());

			if (할당확인됨.getAssignstatus() != null && 할당확인됨.getAssignstatus().equals("할당확정")) {

//            	CleanassignCompleted CleanassignCompleted = Assigner.get택시할당됨();
//            	BeanUtils.copyProperties(CleanmanageAssigned, CleanassignCompleted);
//            	
//                //CleanassignCompleted.setEventType("CleanassignCompleted");
//            	CleanassignCompleted.setEventType("CleanassignCompleted");
//            	//CleanmanageAssigned.publishAfterCommit();
//            	CleanassignCompleted.publish();
			}
		}
	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever택시할당취소됨_(@Payload CleanmanageCancelled 택시할당취소됨) {

		if (택시할당취소됨.isMe()) {
			System.out.println("##### listener  : " + 택시할당취소됨.toJson());

			할당Repository.findById(Long.valueOf(택시할당취소됨.getId())).ifPresent((택시호출) -> {
				택시호출.setStatus("할당취소");
				할당Repository.save(택시호출);
			});

			택시할당취소됨.publish();
		}
	}

}
