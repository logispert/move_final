
package move24mall;

public class MoverassignCompleted extends AbstractEvent {

    private Long id;
    private String assinstatus; //호출,호출중,호출확정,호출취소
    private String moverid;
    private String mover;
    private String movertel;
    
    private String tel;
    private String location;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getMoverid() {
		return moverid;
	}

	public void setMoverid(String moverid) {
		this.moverid = moverid;
	}

	public String getAssinstatus() {
		return assinstatus;
	}

	public void setAssinstatus(String assinstatus) {
		this.assinstatus = assinstatus;
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

}
