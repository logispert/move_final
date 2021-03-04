package pickupcenter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="Pickupassign_table")
public class Pickupassign {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String status; //호출,호출중,호출확정,호출취소
    private String workerid;
    public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getWorkerid() {
		return workerid;
	}


	public void setWorkerid(String workerid) {
		this.workerid = workerid;
	}


	public String getWorker() {
		return worker;
	}


	public void setWorker(String worker) {
		this.worker = worker;
	}


	public String getWorkertel() {
		return workertel;
	}


	public void setWorkertel(String workertel) {
		this.workertel = workertel;
	}

	private String worker;
    private String workertel;
    
    @PrePersist
    public void onPrePersist(){
    	System.out.println("==============택시할당================");


        //할당확인됨 할당확인됨 = new 할당확인됨();
        //BeanUtils.copyProperties(this, 할당확인됨);
        //할당확인됨.publishAfterCommit();


        //할당취소됨 할당취소됨 = new 할당취소됨();
        //BeanUtils.copyProperties(this, 할당취소됨);
        //할당취소됨.publishAfterCommit();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
