package move24mall;

public class MovermanageAssigned extends AbstractEvent {

    private Long id;

    public MovermanageAssigned(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}