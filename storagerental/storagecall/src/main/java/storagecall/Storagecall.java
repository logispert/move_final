package storagecall;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import storagecall.external.Storagemanage;
import storagecall.external.StoragemanageService;

@Entity
@Table(name="Storagecall_table")
public class Storagecall {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String tel;
    private String startdate;
    private String status; //호출,호출중,호출확정,호출취소
    private Integer cost;
    
	
    @PostPersist
    public void onPostPersist(){
//        Storagecalled Storagecalled = new Storagecalled();
//        BeanUtils.copyProperties(this, Storagecalled);
//        Storagecalled.publishAfterCommit();
    	
    	System.out.println("휴대폰번호 " + getTel());
        System.out.println("startdate " + getStartdate());
        System.out.println("status " + getStatus());
        System.out.println("cost " + getCost());
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.   	
    	if(getTel() != null)
		{
    		System.out.println("SEND###############################" + getId());
			Storagemanage storagemanage = new Storagemanage();
			storagemanage.setId(getId());
			storagemanage.setOrderId(String.valueOf(getId()));
			storagemanage.setTel(getTel());
	        if(getStartdate()!=null)
				storagemanage.setStartdate(getStartdate());
	        if(getStatus()!=null)
				storagemanage.setStatus(getStatus());
	        if(getCost()!=null)
				storagemanage.setCost(getCost());
	        
	        // mappings goes here
	        StoragecallApplication.applicationContext.getBean(StoragemanageService.class).storageManageCall(storagemanage);
		}

    }

	@PreRemove
	public void onPreRemove(){
		StoragecallCancelled 호출취소됨 = new StoragecallCancelled();
		BeanUtils.copyProperties(this, 호출취소됨);
		호출취소됨.publishAfterCommit();

		//Following code causes dependency to external APIs
		// it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

		//Storagemanage Storagemanage = new Storagemanage();
		// mappings goes here
		//Storagemanage.setId(getId());
		//Storagemanage.setOrderId(String.valueOf(getId()));
		//Storagemanage.setStatus("호출취소");
		//Storagemanage.set고객휴대폰번호(get휴대폰번호());
		
		// mappings goes here
		//StoragecallApplication.applicationContext.getBean(StoragemanageService.class).택시할당요청(Storagemanage);
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


	public void setTel( String tel ) {
		this.tel = tel;
	}


	public String getStartdate() {
		return startdate;
	}


	public void setStartdate( String startdate ) {
		this.startdate = startdate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus( String status ) {
		this.status = status;
	}


	public Integer getCost() {
		return cost;
	}


	public void setCost( Integer cost ) {
		this.cost = cost;
	}


}
