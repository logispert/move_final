package ascenter;

public class AsmanageAssigned extends AbstractEvent {

    private Long id;

    public AsmanageAssigned(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}