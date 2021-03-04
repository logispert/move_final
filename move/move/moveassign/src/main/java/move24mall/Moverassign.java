package move24mall;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="이사할당_table")
public class Moverassign {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String status; //호출,호출중,호출확정,호출취소
    private String moverid;
    private String mover;
    private String movertel;
    
    @PrePersist
    public void onPrePersist(){
    	System.out.println("==============이사할당================");


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



}
