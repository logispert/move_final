
package cleancall;

public class CleanassignCompleted extends AbstractEvent {

    private Long id;
    private String assignstatus; //호출,호출중,호출확정,호출취소
    private String cleanmanid;
    private String cleanman;
    private String cleanmantel;
    
    private String tel;
    private String location;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getAssignstatus() {
		return assignstatus;
	}

	public void setAssignstatus(String assignstatus) {
		this.assignstatus = assignstatus;
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

	public String getCleanmantel() {
		return cleanmantel;
	}

	public void setCleanmantel(String cleanmantel) {
		this.cleanmantel = cleanmantel;
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
