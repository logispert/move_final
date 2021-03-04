package move24mall.external;

public class Movermanage {
	private String tel;
    private String location;
    private String status; //호출,호출중,호출확정,호출취소
    private Integer cost;
    private String orderId;
    
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
	public String getMover() {
		return mover;
	}
	public void setMover(String mover) {
		this.mover = mover;
	}
	private String movertel;
    private String moverid;
    private String mover;
    
    private Long id;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMovertel() {
		return movertel;
	}
	public void setMovertel(String movertel) {
		this.movertel = movertel;
	}
	public String getMoverid() {
		return moverid;
	}
	public void setMoverid(String moverid) {
		this.moverid = moverid;
	}

}
