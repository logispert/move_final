package ascenter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="Asassign_table")
public class Asassign {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String status; //호출,호출중,호출확정,호출취소
    private String asmanid;
    private String asman;
    private String asmantel;
    

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


    public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getAsmanid() {
		return asmanid;
	}


	public void setAsmanid(String asmanid) {
		this.asmanid = asmanid;
	}


	public String getAsman() {
		return asman;
	}


	public void setAsman(String asman) {
		this.asman = asman;
	}


	public String getAsmantel() {
		return asmantel;
	}


	public void setAsmantel(String asmantel) {
		this.asmantel = asmantel;
	}


}
