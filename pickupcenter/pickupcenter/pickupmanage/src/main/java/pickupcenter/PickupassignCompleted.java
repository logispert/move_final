
package pickupcenter;

public class PickupassignCompleted extends AbstractEvent {

    private Long id;
    private String status; //호출,호출중,호출확정,호출취소
    private String workerid;
    private String worker;
    private String workertel;
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

	private String tel;
    private String location;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
