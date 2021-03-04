package pickupcenter;

public class Pickupcalled extends AbstractEvent {

    private Long id;

    public Pickupcalled(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}