package storageassign;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="Storageassign_table")
public class Storageassign {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String status; //호출,호출중,호출확정,호출취소
    private String storageid;
    private String address;
    private String floor;
    
    @PrePersist
    public void onPrePersist(){
    	System.out.println("==============Storageassign================");


        //StorageassignCompleted StorageassignCompleted = new StorageassignCompleted();
        //BeanUtils.copyProperties(this, StorageassignCompleted);
        //StorageassignCompleted.publishAfterCommit();


        //StorageassignCancelled StorageassignCancelled = new StorageassignCancelled();
        //BeanUtils.copyProperties(this, StorageassignCancelled);
        //StorageassignCancelled.publishAfterCommit();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


	public String getStatus() {
		return status;
	}

	public void setStatus( String status ) {
		this.status = status;
	}


	public String getStorageid() {
		return storageid;
	}

	public void setStorageid( String storageid ) {
		this.storageid = storageid;
	}


	public String getAddress() {
		return address;
	}

	public void setAddress( String address ) {
		this.address = address;
	}


	public String getFloor() {
		return floor;
	}

	public void setFloor( String floor ) {
		this.floor = floor;
	}



}
