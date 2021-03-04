
package cleanassign;

public class CleanmanageAssigned extends AbstractEvent {

    private Long id;
    private String tel;
    private String location;
    private String status; //호출,호출중,호출확정,호출취소
    private Integer cost;
    
    public Long getId() {
        return id;
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

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

}