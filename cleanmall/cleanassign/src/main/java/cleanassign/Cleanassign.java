package cleanassign;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="Cleanassign_table")
public class Cleanassign {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String status; //호출,호출중,호출확정,호출취소
    private String cleanmanid;
    private String cleanman;
    private String cleanmantel;
    
    @PrePersist
    public void onPrePersist(){
    	System.out.println("==============Cleanassign================");
    	//CleanassignCompleted CleanassignCompleted = new CleanassignCompleted();
        //BeanUtils.copyProperties(this, CleanassignCompleted);
        //CleanassignCompleted.publishAfterCommit();


        //CleanassignCancelled CleanassignCancelled = new CleanassignCancelled();
        //BeanUtils.copyProperties(this, CleanassignCancelled);
        //CleanassignCancelled.publishAfterCommit();
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



}
