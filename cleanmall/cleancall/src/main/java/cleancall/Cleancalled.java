package cleancall;

public class Cleancalled extends AbstractEvent {

    private Long id;

    public Cleancalled(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}