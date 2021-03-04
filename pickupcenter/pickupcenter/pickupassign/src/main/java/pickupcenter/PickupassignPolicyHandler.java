package pickupcenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import pickupcenter.config.kafka.KafkaProcessor;

@Service
public class PickupassignPolicyHandler{
	@Autowired PickupassignRepository pickupassignRepository;
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
	
	public static PickupassignCompleted getPickupassign() {
		PickupassignCompleted pickupassignCompleted = new PickupassignCompleted();
		
		int randDriver = (int)(Math.random() * 6);
		pickupassignCompleted.setWorker(driverBank[randDriver][0]);
		pickupassignCompleted.setWorkertel(driverBank[randDriver][1]);
		pickupassignCompleted.setWorkerid(driverBank[randDriver][2]);
        return pickupassignCompleted;
	}
    
    //private String 호출상태; //호출,호출중,호출확정,호출취소
    @StreamListener(KafkaProcessor.INPUT)
    public void whenever택시할당요청됨_(@Payload PickupmanageAssigned pickupmanageAssigned){
    	System.out.println("##### EVT TYPE[택시할당요청됨]  : " + pickupmanageAssigned.getEventType());
        if(pickupmanageAssigned.isMe()){
            System.out.println("##### listener  : " + pickupmanageAssigned.toJson());
            
            if(pickupmanageAssigned.getStatus() != null  && pickupmanageAssigned.getStatus().equals("호출중"))
            {
            	
            	pickupmanageAssigned.setStatus("호출확정");
            	//할당확인됨 할당확인됨 = Assigner.get택시할당됨();
            	//BeanUtils.copyProperties(택시할당요청됨, 할당확인됨);
            	//할당확인됨.setEventType("할당확인됨");
            	pickupmanageAssigned.publish();
            	
            	PickupassignCompleted pickupassignCompleted = getPickupassign();
            	pickupassignCompleted.setId(pickupmanageAssigned.getId());
            	pickupassignCompleted.setStatus("할당확정");
            	pickupassignCompleted.setTel(pickupmanageAssigned.getTel());
            	pickupassignCompleted.setLocation(pickupmanageAssigned.getLocation());
            	pickupassignCompleted.setEventType("PickupassignCompleted");
            	//택시할당요청됨.publishAfterCommit();
            	pickupassignCompleted.publish(); 
            }  
        }
    }
    
    @StreamListener(KafkaProcessor.INPUT)
    public void whenever할당확인됨_(@Payload PickupassignCompleted pickupassignCompleted){
    	System.out.println("##### EVT TYPE[할당확인됨]  : " + pickupassignCompleted.getEventType());
        if(pickupassignCompleted.isMe()){
            System.out.println("##### listener  : " + pickupassignCompleted.toJson());
            
            if(pickupassignCompleted.getStatus() != null  && pickupassignCompleted.getStatus().equals("할당확정"))
            {
            	
//            	할당확인됨 할당확인됨 = Assigner.get택시할당됨();
//            	BeanUtils.copyProperties(택시할당요청됨, 할당확인됨);
//            	
//                //할당확인됨.setEventType("할당확인됨");
//            	할당확인됨.setEventType("할당확인됨");
//            	//택시할당요청됨.publishAfterCommit();
//            	할당확인됨.publish(); 
            }  
        }
    }
    
    
    @StreamListener(KafkaProcessor.INPUT)
    public void whenever택시할당취소됨_(@Payload PickupmanageCancelled pickupmanageCancelled){
    	
        if(pickupmanageCancelled.isMe()){
            System.out.println("##### listener  : " + pickupmanageCancelled.toJson());
            
            
            pickupassignRepository.findById(Long.valueOf(pickupmanageCancelled.getId())).ifPresent((pickupassign) -> {
            	pickupassign.setStatus("할당취소");
				pickupassignRepository.save(pickupassign);
			});
            
            pickupmanageCancelled.publish();
        }
    }

}
