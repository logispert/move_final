package ascenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import ascenter.config.kafka.KafkaProcessor;

@Service
public class AsassignPolicyHandler{
	@Autowired AsassignRepository asassignRepository;
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
	
	public static AsassignCompleted getAsassign() {
		AsassignCompleted asassignCompleted = new AsassignCompleted();
		
		int randDriver = (int)(Math.random() * 6);
		asassignCompleted.setAsman(driverBank[randDriver][0]);
		asassignCompleted.setAsmantel(driverBank[randDriver][1]);
		asassignCompleted.setAsmanid(driverBank[randDriver][2]);
        return asassignCompleted;
	}
    
    //private String 호출상태; //호출,호출중,호출확정,호출취소
    @StreamListener(KafkaProcessor.INPUT)
    public void whenever택시할당요청됨_(@Payload AsmanageAssigned asmanageAssigned){
    	System.out.println("##### EVT TYPE[택시할당요청됨]  : " + asmanageAssigned.getEventType());
        if(asmanageAssigned.isMe()){
            System.out.println("##### listener  : " + asmanageAssigned.toJson());
            
            if(asmanageAssigned.getStatus() != null  && asmanageAssigned.getStatus().equals("호출중"))
            {
            	
            	asmanageAssigned.setStatus("호출확정");
            	//할당확인됨 할당확인됨 = Assigner.get택시할당됨();
            	//BeanUtils.copyProperties(택시할당요청됨, 할당확인됨);
            	//할당확인됨.setEventType("할당확인됨");
            	asmanageAssigned.publish();
            	
            	AsassignCompleted asassignCompleted = getAsassign();
            	asassignCompleted.setId(asmanageAssigned.getId());
            	asassignCompleted.setStatus("할당확정");
            	asassignCompleted.setTel(asmanageAssigned.getTel());
            	asassignCompleted.setLocation(asmanageAssigned.getLocation());
            	asassignCompleted.setEventType("할당확인됨");
            	//택시할당요청됨.publishAfterCommit();
            	asassignCompleted.publish(); 
            }  
        }
    }
    
    @StreamListener(KafkaProcessor.INPUT)
    public void whenever할당확인됨_(@Payload AsassignCompleted asassignCompleted){
    	System.out.println("##### EVT TYPE[할당확인됨]  : " + asassignCompleted.getEventType());
        if(asassignCompleted.isMe()){
            System.out.println("##### listener  : " + asassignCompleted.toJson());
            
            if(asassignCompleted.getStatus() != null  && asassignCompleted.getStatus().equals("할당확정"))
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
    public void whenever택시할당취소됨_(@Payload AsmanageCancelled asmanageCancelled){
    	
        if(asmanageCancelled.isMe()){
            System.out.println("##### listener  : " + asmanageCancelled.toJson());
            
            
            asassignRepository.findById(Long.valueOf(asmanageCancelled.getId())).ifPresent((asassign) -> {
            	asassign.setStatus("할당취소");
            	asassignRepository.save(asassign);
			});
            
            asmanageCancelled.publish();
        }
    }

}
