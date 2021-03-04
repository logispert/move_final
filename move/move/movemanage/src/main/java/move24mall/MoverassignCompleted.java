
package move24mall;

public class MoverassignCompleted extends AbstractEvent {

    private Long id;
    private String status; //호출,호출중,호출확정,호출취소
    private String moverid;
    private String mover;
    private String movertel;
    
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

	private String 고객휴대폰번호;
    private String 호출위치;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String get고객휴대폰번호() {
		return 고객휴대폰번호;
	}

	public void set고객휴대폰번호(String 고객휴대폰번호) {
		this.고객휴대폰번호 = 고객휴대폰번호;
	}

	public String get호출위치() {
		return 호출위치;
	}

	public void set호출위치(String 호출위치) {
		this.호출위치 = 호출위치;
	}

}
