
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
