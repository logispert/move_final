package move24mall;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Movermanage_table")
public class Movermanage {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private String orderId;
    private String tel;
    private String location;
    private String status; //호출,호출중,호출확정,호출취소
    private Integer cost;
    

	private String moverid;
    private String mover;
    private String movertel;
    
    @PrePersist
    public void onPrePersist(){
    	System.out.println("###############################=================================");

//    	이사할당요청됨 이사할당요청됨 = new 이사할당요청됨();
//        BeanUtils.copyProperties(this, 이사할당요청됨);
//        이사할당요청됨.publishAfterCommit();
        System.out.println("휴대폰번호 " + tel);
        System.out.println("호출위치 " + location);
        System.out.println("호출상태 " + status);
        System.out.println("예상요금 " + cost);
    	
        System.out.println("orderId " + orderId);
        System.out.println("id " + getId());
        //System.out.println("호출위치 " + 호출위치);
        //System.out.println("호출상태 " + 호출상태);
        //System.out.println("예상요금 " + 예상요금);
    	
        
        if("호출취소".equals(status)){
			MovermanageCancelled movermanageCancelled = new MovermanageCancelled();
            BeanUtils.copyProperties(this, movermanageCancelled);
            movermanageCancelled.publish();

        }else{
//            결제승인됨 결제승인됨 = new 결제승인됨();
//            BeanUtils.copyProperties(this, 결제승인됨);
//
//            //바로 이벤트를 보내버리면 주문정보가 커밋되기도 전에 배송발송됨 이벤트가 발송되어 주문테이블의 상태가 바뀌지 않을 수 있다.
//            // TX 리스너는 커밋이 완료된 후에 이벤트를 발생하도록 만들어준다.
//            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
//                @Override
//                public void beforeCommit(boolean readOnly) {
//                    결제승인됨.publish();
//                }
//            });
        	
//        	이사번호 = "";
//            이사기사이름 = "";
//            이사기사전화번호 = "";
//            orderId = "1";
//            고객휴대폰번호 = "";
//            호출위치 = "";
//            호출상태 = ""; //호출,호출중,호출확정,호출취소
//            예상요금 = 0;
            
        	status = "호출중";
        	MovermanageAssigned movermanageAssigned = new MovermanageAssigned();
        	movermanageAssigned.setId(Long.valueOf(orderId));
        	
        	movermanageAssigned.setLocation(location);
        	movermanageAssigned.setTel(tel);
        	movermanageAssigned.setCost(cost);
        	movermanageAssigned.setStatus(status);
            BeanUtils.copyProperties(this, movermanageAssigned);
            movermanageAssigned.publishAfterCommit();
            
            
            // 테스트 코드~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//            try {
//                Thread.currentThread().sleep((long) (400 + Math.random() * 220));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }    
    }
    
    
//    @PostPersist
//    public void onPostPersist(){
//    	System.out.println("###############################=================================");
//
////    	이사할당요청됨 이사할당요청됨 = new 이사할당요청됨();
////        BeanUtils.copyProperties(this, 이사할당요청됨);
////        이사할당요청됨.publishAfterCommit();
//        System.out.println("휴대폰번호 " + 고객휴대폰번호);
//        System.out.println("호출위치 " + 호출위치);
//        System.out.println("호출상태 " + 호출상태);
//        System.out.println("예상요금 " + 예상요금);
//    	
//        System.out.println("orderId " + orderId);
//        System.out.println("id " + getId());
//        //System.out.println("호출위치 " + 호출위치);
//        //System.out.println("호출상태 " + 호출상태);
//        //System.out.println("예상요금 " + 예상요금);
//    	
//        
//        if("호출취소".equals(호출상태)){
////            결제취소됨 결제취소됨 = new 결제취소됨();
////            BeanUtils.copyProperties(this, 결제취소됨);
////            결제취소됨.publish();
////        	이사할당취소됨 이사할당취소됨 = new 이사할당취소됨();
////            BeanUtils.copyProperties(this, 이사할당취소됨);
////            이사할당취소됨.publish();
//
//        }else{
////            결제승인됨 결제승인됨 = new 결제승인됨();
////            BeanUtils.copyProperties(this, 결제승인됨);
////
////            //바로 이벤트를 보내버리면 주문정보가 커밋되기도 전에 배송발송됨 이벤트가 발송되어 주문테이블의 상태가 바뀌지 않을 수 있다.
////            // TX 리스너는 커밋이 완료된 후에 이벤트를 발생하도록 만들어준다.
////            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
////                @Override
////                public void beforeCommit(boolean readOnly) {
////                    결제승인됨.publish();
////                }
////            });
//        	
////        	이사번호 = "";
////            이사기사이름 = "";
////            이사기사전화번호 = "";
////            orderId = "1";
////            고객휴대폰번호 = "";
////            호출위치 = "";
////            호출상태 = ""; //호출,호출중,호출확정,호출취소
////            예상요금 = 0;
//            
//        	호출상태 = "호출중";
//        	이사할당요청됨 이사할당요청됨 = new 이사할당요청됨();
//        	이사할당요청됨.setId(Long.valueOf(orderId));
//        	
//        	이사할당요청됨.set고객위치(호출위치);
//        	이사할당요청됨.set고객휴대폰번호(고객휴대폰번호);
//        	이사할당요청됨.set예상요금(예상요금);
//        	이사할당요청됨.set호출상태(호출상태);
//            BeanUtils.copyProperties(this, 이사할당요청됨);
//            이사할당요청됨.publishAfterCommit();
//            
//            
//            // 테스트 코드~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
////            try {
////                Thread.currentThread().sleep((long) (400 + Math.random() * 220));
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//        }     
//    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Integer getCost() {
		return cost;
	}


	public void setCost(Integer cost) {
		this.cost = cost;
	}


	public String getMoverid() {
		return moverid;
	}


	public void setMoverid(String moverid) {
		this.moverid = moverid;
	}


	public String getMover() {
		return mover;
	}


	public void setMover(String mover) {
		this.mover = mover;
	}


	public String getMovertel() {
		return movertel;
	}


	public void setMovertel(String movertel) {
		this.movertel = movertel;
	}

	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}




}
