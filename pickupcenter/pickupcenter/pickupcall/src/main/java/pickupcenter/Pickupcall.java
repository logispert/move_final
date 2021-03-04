package pickupcenter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import pickupcenter.external.Pickupmanage;
import pickupcenter.external.PickupmanageService;

@Entity
@Table(name="Pickupcall_table")
public class Pickupcall {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String tel;
    private String location;
    private String status; //호출,호출중,호출확정,호출취소
    private Integer cost;
    
	
    @PostPersist
    public void onPostPersist(){
//        택시호출요청됨 택시호출요청됨 = new 택시호출요청됨();
//        BeanUtils.copyProperties(this, 택시호출요청됨);
//        택시호출요청됨.publishAfterCommit();
    	
    	System.out.println("휴대폰번호 " + getTel());
        System.out.println("호출위치 " + getLocation());
        System.out.println("호출상태 " + getStatus());
        System.out.println("예상요금 " + getCost());
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.   	
    	if(getTel() != null)
		{
    		System.out.println("SEND###############################" + getId());
			Pickupmanage pickupmanage = new Pickupmanage();
			pickupmanage.setId(getId());
			pickupmanage.setOrderId(String.valueOf(getId()));
			pickupmanage.setTel(getTel());
	        if(getLocation()!=null) 
	        	pickupmanage.setLocation(getLocation());
	        if(getStatus()!=null) 
	        	pickupmanage.setStatus(getStatus());
	        if(getCost()!=null) 
	        	pickupmanage.setCost(getCost());
	        
	        // mappings goes here
	        PickupcallApplication.applicationContext.getBean(PickupmanageService.class).reqPickupAssign(pickupmanage);
		}

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

	@PreRemove
	public void onPreRemove(){
		PickupcallCancelled 호출취소됨 = new PickupcallCancelled();
		BeanUtils.copyProperties(this, 호출취소됨);
		호출취소됨.publishAfterCommit();

		//Following code causes dependency to external APIs
		// it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

		//택시관리 택시관리 = new 택시관리();
		// mappings goes here
		//택시관리.setId(getId());
		//택시관리.setOrderId(String.valueOf(getId()));
		//택시관리.set호출상태("호출취소");
		//택시관리.set고객휴대폰번호(get휴대폰번호());
		
		// mappings goes here
		//TaxicallApplication.applicationContext.getBean(택시관리Service.class).택시할당요청(택시관리);
	}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
