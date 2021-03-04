package move24mall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import move24mall.config.kafka.KafkaProcessor;

@Service
public class MoverassignPolicyHandler{
	@Autowired MoverassignRepository moverassignRepository;
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }
    
    private static String[][] driverBank = 
	{
		{"백영곤", "010-2345-6789", "34가4567"},
		{"안채우", "010-3345-7789", "44나4567"},
		{"임광현", "010-4345-8789", "54다4567"},
		{"장윤정", "010-5345-9789", "64라4567"},
		{"옥준삼", "010-6345-0789", "74마4567"},
		{"유승오", "010-7345-1789", "84사4567"}
	};
	
	public static MoverassignCompleted getMoverAssigned() {
		MoverassignCompleted moverassignCompleted = new MoverassignCompleted();
		
		int randDriver = (int)(Math.random() * 6);
		moverassignCompleted.setMover(driverBank[randDriver][0]);
		moverassignCompleted.setMovertel(driverBank[randDriver][1]);
		moverassignCompleted.setMoverid(driverBank[randDriver][2]);
        return moverassignCompleted;
	}
    
    //private String 호출상태; //호출,호출중,호출확정,호출취소
    @StreamListener(KafkaProcessor.INPUT)
    public void whenever이사할당요청됨_(@Payload MovermanageAssigned movermanageAssigned){
    	System.out.println("##### EVT TYPE[이사할당요청됨]  : " + movermanageAssigned.getEventType());
        if(movermanageAssigned.isMe()){
            System.out.println("##### listener  : " + movermanageAssigned.toJson());
            
            if(movermanageAssigned.getStatus() != null  && movermanageAssigned.getStatus().equals("호출중"))
            {
            	
            	movermanageAssigned.setStatus("호출확정");
            	//할당확인됨 할당확인됨 = Assigner.get이사할당됨();
            	//BeanUtils.copyProperties(이사할당요청됨, 할당확인됨);
            	//할당확인됨.setEventType("할당확인됨");
            	movermanageAssigned.publish();
            	
            	MoverassignCompleted moverassignCompleted = getMoverAssigned();
            	moverassignCompleted.setId(movermanageAssigned.getId());
            	moverassignCompleted.setAssinstatus("할당확정");
            	moverassignCompleted.setTel(movermanageAssigned.getTel());
            	moverassignCompleted.setLocation(movermanageAssigned.getLocation());
            	moverassignCompleted.setEventType("MoverassignCompleted");
            	//이사할당요청됨.publishAfterCommit();
            	moverassignCompleted.publish(); 
            }  
        }
    }
    
    @StreamListener(KafkaProcessor.INPUT)
    public void whenever할당확인됨_(@Payload MoverassignCompleted moverassignCompleted){
    	System.out.println("##### EVT TYPE[할당확인됨]  : " + moverassignCompleted.getEventType());
        if(moverassignCompleted.isMe()){
            System.out.println("##### listener  : " + moverassignCompleted.toJson());
            
            if(moverassignCompleted.getAssinstatus() != null  && moverassignCompleted.getAssinstatus().equals("할당확정"))
            {
            	
//            	할당확인됨 할당확인됨 = Assigner.get이사할당됨();
//            	BeanUtils.copyProperties(이사할당요청됨, 할당확인됨);
//            	
//                //할당확인됨.setEventType("할당확인됨");
//            	할당확인됨.setEventType("할당확인됨");
//            	//이사할당요청됨.publishAfterCommit();
//            	할당확인됨.publish(); 
            }  
        }
    }
    
    
    @StreamListener(KafkaProcessor.INPUT)
    public void whenever이사할당취소됨_(@Payload MovermanageCancelled movermanageCancelled){
    	
        if(movermanageCancelled.isMe()){
            System.out.println("##### listener  : " + movermanageCancelled.toJson());
            
            
            moverassignRepository.findById(Long.valueOf(movermanageCancelled.getId())).ifPresent((moverassign) -> {
            	moverassign.setStatus("할당취소");
            	moverassignRepository.save(moverassign);
			});
            
            movermanageCancelled.publish();
        }
    }

}
