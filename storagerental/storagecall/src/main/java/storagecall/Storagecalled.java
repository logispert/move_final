package storagecall;

public class Storagecalled extends AbstractEvent {

    private Long id;

    public Storagecalled(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}