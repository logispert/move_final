package cleanmanage;

import javax.persistence.*;

import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Cleanmanage_table")
public class Cleanmanage {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private String orderId;
    private String tel;
    private String location;
    private String status; //호출,호출중,호출확정,호출취소
    private Integer cost;
    
    private String cleanmanid;
    private String cleanman;
    private String cleanmantel;
    
    @PostPersist
    public void onPostPersist(){
    	System.out.println("###############################=================================");

//    	CleanmanageAssigned CleanmanageAssigned = new CleanmanageAssigned();
//        BeanUtils.copyProperties(this, CleanmanageAssigned);
//        CleanmanageAssigned.publishAfterCommit();
        System.out.println("휴대폰번호 " + tel);
        System.out.println("location " + location);
        System.out.println("state " + status);
        System.out.println("cost " + cost);
    	
        System.out.println("orderId " + orderId);
        System.out.println("id " + getId());
        //System.out.println("location " + location);
        //System.out.println("state " + state);
        //System.out.println("cost " + cost);
    	
        
        if("호출취소".equals(status)){
			CleanmanageCancelled 택시할당취소됨 = new CleanmanageCancelled();
            BeanUtils.copyProperties(this, 택시할당취소됨);
            택시할당취소됨.publish();

        }else{

        	status = "called";
        	CleanmanageAssigned cleanmanageAssigned = new CleanmanageAssigned();
        	cleanmanageAssigned.setId(Long.valueOf(orderId));
        	
        	cleanmanageAssigned.setLocation(location);
        	cleanmanageAssigned.setTel(tel);
        	cleanmanageAssigned.setCost(cost);
        	cleanmanageAssigned.setStatus(status);
            BeanUtils.copyProperties(this, cleanmanageAssigned);
            cleanmanageAssigned.publishAfterCommit();
            
            
            // 테스트 코드~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//            try {
//                Thread.currentThread().sleep((long) (400 + Math.random() * 220));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }    
    }
    

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


	public String getLocation() {
		return location;
	}


	public void setLocation( String location ) {
		this.location = location;
	}
	
	public Integer getCost() {
		return cost;
	}


	public void setCost( Integer cost ) {
		this.cost = cost;
	}
	
	public String getStatus() {return status;}
	public void setStatus(String s) {status = s; }

	public String getCleanmanid() {
		return cleanmanid;
	}


	public void setCleanmanid( String cleanmanid ) {
		this.cleanmanid = cleanmanid;
	}


	public String getCleanman() {
		return cleanman;
	}


	public void setCleanman( String cleanman ) {
		this.cleanman = cleanman;
	}


	public String getCleanmantel() {
		return cleanmantel;
	}


	public void setCleanmantel( String cleanmantel ) {
		this.cleanmantel = cleanmantel;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}




}
