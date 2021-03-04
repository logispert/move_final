package ascenter.external;

public class Asmanage {
	private String tel;
    private String location;
    private String status; //호출,호출중,호출확정,호출취소
    private Integer cost;
    private String orderId;
    
    private String asmantel;
    private String asmanid;
    private String asman;
    
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
	public String getAsmantel() {
		return asmantel;
	}
	public void setAsmantel(String asmantel) {
		this.asmantel = asmantel;
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

}
