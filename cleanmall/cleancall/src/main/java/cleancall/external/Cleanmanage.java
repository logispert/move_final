package cleancall.external;

public class Cleanmanage {
	private String tel;
    private String location;
    private String status; //호출,호출중,호출확정,호출취소
    private Integer cost;
    private String orderId;
    
    private String cleanmantel;
    private String cleanmanid;
    private String cleanman;
    
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
	public String getCleanmantel() {
		return cleanmantel;
	}
	public void setCleanmantel(String cleanmantel) {
		this.cleanmantel = cleanmantel;
	}
	public String getCleanmanid() {
		return cleanmanid;
	}
	public void setCleanmanid(String cleanmanid) {
		this.cleanmanid = cleanmanid;
	}
	public String getCleanman() {
		return cleanman;
	}
	public void setCleanman(String cleanman) {
		this.cleanman = cleanman;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}

}
