package move24mall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import move24mall.config.kafka.KafkaProcessor;


@Service
public class MoverPolicyHandler {
	@Autowired
	MoverRepository moverRepository;

	@StreamListener(KafkaProcessor.INPUT)
	public void onStringEventListener(@Payload String eventString) {

	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever할당확인됨_(@Payload MoverassignCompleted moverassignCompleted) {
		System.out.println("##### EVT TYPE[할당확인됨]  : " + moverassignCompleted.getEventType());
		if (moverassignCompleted.isMe() && moverassignCompleted.getTel() != null) {

//           try {
//               // 원래 데이터가 트랜잭션 커밋되기도 전에 이벤트가 너무 빨리 도달하는 경우를 막기 위함
//               Thread.currentThread().sleep(3000); //  no good. --> pay 가 TX 를 마친 후에만 실행되도록 수정함
//           } catch (InterruptedException e) {
//               e.printStackTrace();
//           }
			System.out.println("##### listener[할당확인됨]  : " + moverassignCompleted.toJson());
			

			// Correlation id 는 '고객휴대폰번호' 임
			if(moverassignCompleted.getId() != null)
				moverRepository.findById(Long.valueOf(moverassignCompleted.getId())).ifPresent((mover) -> {
					mover.setStatus("호출확정");
					moverRepository.save(mover);
				});
//			이사호출Repository.findBy휴대폰번호(할당확인됨.get고객휴대폰번호()).ifPresent((이사호출) -> {
//				System.out.println("할당확인됨 = " + 할당확인됨.get고객휴대폰번호());
//				이사호출.set호출상태("호출확정");
//				이사호출Repository.save(이사호출);
//			});
		}

//		if (할당확인됨.isMe()) {
//			이사호출 호출 = new 이사호출();
//			호출.set호출상태(할당확인됨.get할당상태());
//			이사호출Repository.save(호출);
//
//			System.out.println("##### listener[할당확인됨]  : " + 할당확인됨.toJson());
//		}
	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever할당취소됨_(@Payload MoverassignCancelled moverassignCancelled) {
		System.out.println("##### EVT TYPE[할당취소됨]  : " + moverassignCancelled.getEventType());
		if (moverassignCancelled.isMe()) {
			System.out.println("##### listener[할당취소됨]  : " + moverassignCancelled.toJson());
			moverRepository.findById(Long.valueOf(moverassignCancelled.getId())).ifPresent((이사호출) -> {
				이사호출.setStatus("호출취소");
				moverRepository.save(이사호출);
			});
		}
	}

}
