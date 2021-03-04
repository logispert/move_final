
package pickupcenter;

public class PickupcallCancelled extends AbstractEvent {

    private Long id;
    private String 할당상태; //호출취소
    private String 고객휴대폰번호;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String get할당상태() {
        return 할당상태;
    }

    public void set할당상태(String 할당상태) {
        this.할당상태 = 할당상태;
    }

    public String get고객휴대폰번호() {
        return 고객휴대폰번호;
    }

    public void set고객휴대폰번호(String 고객휴대폰번호) {
        this.고객휴대폰번호 = 고객휴대폰번호;
    }
}
