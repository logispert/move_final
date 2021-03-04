package cleancall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import cleancall.config.kafka.KafkaProcessor;

@Service
public class CleancallPolicyHandler {
	@Autowired
	CleancallRepository cleancallRepository;

	@StreamListener(KafkaProcessor.INPUT)
	public void onStringEventListener(@Payload String eventString) {

	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever할당확인됨_(@Payload CleanassignCompleted cleanassignCompleted) {
		System.out.println("##### EVT TYPE[CleanassignCompleted]  : " + cleanassignCompleted.getEventType());
		if (cleanassignCompleted.isMe() && cleanassignCompleted.getTel() != null) {

//           try {
//               // 원래 데이터가 트랜잭션 커밋되기도 전에 이벤트가 너무 빨리 도달하는 경우를 막기 위함
//               Thread.currentThread().sleep(3000); //  no good. --> pay 가 TX 를 마친 후에만 실행되도록 수정함
//           } catch (InterruptedException e) {
//               e.printStackTrace();
//           }
			System.out.println("##### listener[CleanassignCompleted]  : " + cleanassignCompleted.toJson());
			

			// Correlation id 는 '고객휴대폰번호' 임
			if(cleanassignCompleted.getId() != null)
				cleancallRepository.findById(Long.valueOf(cleanassignCompleted.getId())).ifPresent((cleancall) -> {
					cleancall.setStatus("호출확정");
					cleancallRepository.save(cleancall);
				});
//			CleancallRepository.findBy휴대폰번호(CleanassignCompleted.get고객휴대폰번호()).ifPresent((Cleancall) -> {
//				System.out.println("CleanassignCompleted = " + CleanassignCompleted.get고객휴대폰번호());
//				Cleancall.set호출상태("호출확정");
//				CleancallRepository.save(Cleancall);
//			});
		}

//		if (CleanassignCompleted.isMe()) {
//			Cleancall 호출 = new Cleancall();
//			호출.set호출상태(CleanassignCompleted.get할당상태());
//			CleancallRepository.save(호출);
//
//			System.out.println("##### listener[CleanassignCompleted]  : " + CleanassignCompleted.toJson());
//		}
	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever할당취소됨_(@Payload CleanassignCancelled 할당취소됨) {
		System.out.println("##### EVT TYPE[CleanassignCancelled]  : " + 할당취소됨.getEventType());
		if (할당취소됨.isMe()) {
			System.out.println("##### listener[CleanassignCancelled]  : " + 할당취소됨.toJson());
			cleancallRepository.findById(Long.valueOf(할당취소됨.getId())).ifPresent((cleancall) -> {
				cleancall.setStatus("호출취소");
				cleancallRepository.save(cleancall);
			});
		}
	}

}
