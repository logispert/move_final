
package ascenter;

public class AscallCancelled extends AbstractEvent {

    private Long id;
    private String assinstatus; //호출취소
    private String tel;

    public String getAssinstatus() {
		return assinstatus;
	}

	public void setAssinstatus(String assinstatus) {
		this.assinstatus = assinstatus;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
