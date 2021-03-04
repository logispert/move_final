package taxiguider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import taxiguider.config.kafka.KafkaProcessor;

@Service
public class DriverassignPolicyHandler{
	@Autowired DriverassignRepository driverassignRepository;
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
	
	public static DriverassignCompleted getDriverassignCompleted() {
		DriverassignCompleted driverassigned = new DriverassignCompleted();
		
		int randDriver = (int)(Math.random() * 6);
		driverassigned.setDriver(driverBank[randDriver][0]);
		driverassigned.setDrivertel(driverBank[randDriver][1]);
		driverassigned.setDriverid(driverBank[randDriver][2]);
        return driverassigned;
	}
    
    //private String 호출상태; //호출,호출중,호출확정,호출취소
    @StreamListener(KafkaProcessor.INPUT)
    public void whenever택시할당요청됨_(@Payload DrivermanageAssigned drivermanageAssigned){
    	System.out.println("##### EVT TYPE[택시할당요청됨]  : " + drivermanageAssigned.getEventType());
        if(drivermanageAssigned.isMe()){
            System.out.println("##### listener  : " + drivermanageAssigned.toJson());
            
            if(drivermanageAssigned.getStatus() != null  && drivermanageAssigned.getStatus().equals("호출중"))
            {     	
            	drivermanageAssigned.setStatus("호출확정");
            	//할당확인됨 할당확인됨 = Assigner.get택시할당됨();
            	//BeanUtils.copyProperties(택시할당요청됨, 할당확인됨);
            	//할당확인됨.setEventType("할당확인됨");
            	drivermanageAssigned.publish();
            	
            	DriverassignCompleted diverassignCompleted = getDriverassignCompleted();
            	diverassignCompleted.setId(drivermanageAssigned.getId());
            	diverassignCompleted.setAssignstatus("할당확정");
            	diverassignCompleted.setTel(drivermanageAssigned.getTel());
            	diverassignCompleted.setLocation(drivermanageAssigned.getLocation());
            	diverassignCompleted.setEventType("DrivermanageAssigned");
            	//diverassignCompleted.publishAfterCommit();
            	diverassignCompleted.publish(); 
            }  
        }
    }
    
    @StreamListener(KafkaProcessor.INPUT)
    public void whenever할당확인됨_(@Payload DriverassignCompleted driverassignCompleted){
    	System.out.println("##### EVT TYPE[할당확인됨]  : " + driverassignCompleted.getEventType());
        if(driverassignCompleted.isMe()){
            System.out.println("##### listener  : " + driverassignCompleted.toJson());
            
            if(driverassignCompleted.getAssignstatus() != null  && driverassignCompleted.getAssignstatus().equals("할당확정"))
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
    public void whenever택시할당취소됨_(@Payload DrivermanageCancelled drivermanageCancelled){
    	
        if(drivermanageCancelled.isMe()){
            System.out.println("##### listener  : " + drivermanageCancelled.toJson());
            
            
            driverassignRepository.findById(Long.valueOf(drivermanageCancelled.getId())).ifPresent((driverassign) -> {
            	driverassign.setStatus("할당취소");
            	driverassignRepository.save(driverassign);
			});
            
            drivermanageCancelled.publish();
        }
    }

}
