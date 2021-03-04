package move24mall;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import move24mall.external.Movermanage;
import move24mall.external.MovermanageService;

@Entity
@Table(name="Movercall_table")
public class Mover {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String tel;
    private String location;
    private String status; //호출,호출중,호출확정,호출취소
    private Integer cost;
    
	
    @PostPersist
    public void onPostPersist(){
//        이사호출요청됨 이사호출요청됨 = new 이사호출요청됨();
//        BeanUtils.copyProperties(this, 이사호출요청됨);
//        이사호출요청됨.publishAfterCommit();
    	
    	System.out.println("휴대폰번호 " + getTel());
        System.out.println("호출위치 " + getLocation());
        System.out.println("호출상태 " + getStatus());
        System.out.println("예상요금 " + getCost());
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.   	
    	if(getTel() != null)
		{
    		System.out.println("SEND###############################" + getId());
			Movermanage movermanage = new Movermanage();
			movermanage.setId(getId());
			movermanage.setOrderId(String.valueOf(getId()));
			movermanage.setTel(getTel());
	        if(getLocation()!=null) 
	        	movermanage.setLocation(getLocation());
	        if(getStatus()!=null) 
	        	movermanage.setStatus(getStatus());
	        if(getCost()!=null) 
	        	movermanage.setCost(getCost());
	        
	        // mappings goes here
	        MovercallApplication.applicationContext.getBean(MovermanageService.class).reqMoveAssign(movermanage);
		}

    }

	@PreRemove
	public void onPreRemove(){
		MovercallCancelled 호출취소됨 = new MovercallCancelled();
		BeanUtils.copyProperties(this, 호출취소됨);
		호출취소됨.publishAfterCommit();

		//Following code causes dependency to external APIs
		// it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

		//이사관리 이사관리 = new 이사관리();
		// mappings goes here
		//이사관리.setId(getId());
		//이사관리.setOrderId(String.valueOf(getId()));
		//이사관리.set호출상태("호출취소");
		//이사관리.set고객휴대폰번호(get휴대폰번호());
		
		// mappings goes here
		//TaxicallApplication.applicationContext.getBean(이사관리Service.class).이사할당요청(이사관리);
	}


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


}
