# move_final
![이사업체](https://user-images.githubusercontent.com/78134019/109764467-ed861e00-7c36-11eb-8a2d-420fad3b560e.jpg)


# 서비스 시나리오

기능적 요구사항
1. 고객이 이사유형(포장/반포장)을 선택하고 이사업체 호출 요청한다.
2. 고객의 집 위치에서 가용 가능한 이사업체를 할당 요청한다.
3. 할당요청된 이사업체 중 하나를 자동할당 한다.
4. 할당 즉시, 고객에게 이사업체 정보를 전달 한다.
5. 고객은 이사업체 호출을 취소 할 수 있다.
6. 호출이 취소 되면 해당 배정을 취소한다.
7. 고객은 업체배정상태를 중간중간 조회하고 카톡으로 받는다.

비기능적 요구사항
1. 트랜잭션
- 이사업체가 할당확인 되지 않으면 고객은 이사업체 요청을 할 수 없다. Sync 호출
2. 장애격리
- 이사업체 할당요청은 할당확인 기능이 동작하지 않더라도, 365일 24시간 받을 수 있어야 한다 Async (event-driven), Eventual Consistency
- 이사업체 요청이 과중되면 이사팀 할당확인 요청을 잠시동안 받지 않고 잠시후에 하도록 유도한다 Circuit breaker, fallback
3. 성능
- 고객은 이사 상태를 조회하고 할당/할당취소 여부를 카톡으로 확인 할 수 있어야 한다. CQRS, Event driven



# 체크포인트

1. Saga
1. CQRS
1. Correlation
1. Req/Resp
1. Gateway
1. Deploy/ Pipeline
1. Circuit Breaker
1. Autoscale (HPA)
1. Zero-downtime deploy (Readiness Probe)
1. Config Map/ Persistence Volume
1. Polyglot
1. Self-healing (Liveness Probe)


# 분석/설계

http://www.msaez.io/#/storming/3KbjlEhxgPTVUlDsH8iPIgFSDDu2/mine/63f3e7dff2126e413c27f4dc2d0a672d
## Event Storming 결과
* MSAEz 로 모델링한 이벤트스토밍 결과:  


### 이벤트 도출
![이벤트도출](https://user-images.githubusercontent.com/78134019/109766384-71410a00-7c39-11eb-8b9f-1c0bf33b8c89.jpg)


### 부적격 이벤트 탈락
![부적격이벤트](https://user-images.githubusercontent.com/78134019/109766405-769e5480-7c39-11eb-849c-7308652b0e64.jpg)



- 과정중 도출된 잘못된 도메인 이벤트들을 걸러내는 작업을 수행함
- 이사유형 (포장/반포장) 선택됨:  UI 의 이벤트이지, 업무적인 의미의 이벤트가 아니라서 제외
- 가용 이사업체 조회됨 :  계획된 사업 범위 및 프로젝트에서 벗어서난다고 판단하여 제외




	

### 액터, 커맨드 부착하여 읽기 좋게
![액터커멘드](https://user-images.githubusercontent.com/78134019/109766450-874eca80-7c39-11eb-817a-ec618a6fb2d4.jpg)



### 어그리게잇으로 묶기
![어그리게잇](https://user-images.githubusercontent.com/78134019/109766462-8cac1500-7c39-11eb-907b-e69d1f506a82.jpg)


- 호출, 이사업체관리, 이사업체 할당 어그리게잇을 생성하고 그와 연결된 command 와 event 들에 의하여 트랜잭션이 유지되어야 하는 단위로 그들 끼리 묶어줌 
 



### 바운디드 컨텍스트로 묶기

![바운디드컨텍스트](https://user-images.githubusercontent.com/78134019/109766503-99306d80-7c39-11eb-91c4-0d91567b8666.jpg)


    - 도메인 서열 분리 
        - Core Domain:  app(front), store : 없어서는 안될 핵심 서비스이며, 연견 Up-time SLA 수준을 99.999% 목표, 배포주기는 app 의 경우 1주일 1회 미만, store 의 경우 1개월 1회 미만
        - Supporting Domain:  customer(view) : 경쟁력을 내기위한 서비스이며, SLA 수준은 연간 60% 이상 uptime 목표, 배포주기는 각 팀의 자율이나 표준 스프린트 주기가 1주일 이므로 1주일 1회 이상을 기준으로 함.
        - General Domain:  pay : 결제서비스로 3rd Party 외부 서비스를 사용하는 것이 경쟁력이 높음 

### 폴리시 부착 (괄호는 수행주체, 폴리시 부착을 둘째단계에서 해놔도 상관 없음. 전체 연계가 초기에 드러남)

![폴리시부착](https://user-images.githubusercontent.com/78134019/109766523-9f264e80-7c39-11eb-8087-2ee77dd7de4c.jpg)


### 폴리시의 이동

![폴리시이동](https://user-images.githubusercontent.com/78134019/109766543-a51c2f80-7c39-11eb-9b6f-b7ab19415fd3.jpg)


### 컨텍스트 매핑 (점선은 Pub/Sub, 실선은 Req/Resp)

![컨텍스트매핑](https://user-images.githubusercontent.com/78134019/109766554-ab121080-7c39-11eb-85f7-f3e4bb95276c.jpg)




### 완성된 모형
![최종모델](https://user-images.githubusercontent.com/78134019/109766569-af3e2e00-7c39-11eb-92a3-f43869c03c05.jpg)




### 기능적 요구사항 검증

![image](https://user-images.githubusercontent.com/78134019/109766592-b7966900-7c39-11eb-8bd2-ebefd53a417e.png)



- 이 이사업체를 호출요청한다.(ok)
- 업체 관리 시스템이 이사업체 할당을 요청한다.(ok)
- 업체 자동 할당이 완료된다.(ok)
- 상태 및 할당상태를 갱신한다.(ok)
- 에게 카톡 알림을 한다.(ok)




![image](https://user-images.githubusercontent.com/78134019/109766641-caa93900-7c39-11eb-9cb7-d56bd1256a84.png)



- 이 이사업체를 호출취소요청한다.(ok)
- 업체 관리 시스템이 이사업체 할당 취소를 요청한다.(ok)
- 업체 할당이 취소된다.(ok)
- 상태로 갱신한다.(ok)
- 에게 카톡 알림을 한다.(ok)





![image](https://user-images.githubusercontent.com/78134019/109766685-d98feb80-7c39-11eb-84df-2b8681650511.png)

  
	- 고객이 호출진행내역을 볼 수 있어야 한다. (ok)


### 비기능 요구사항 검증

![image](https://user-images.githubusercontent.com/78134019/109766726-e6acda80-7c39-11eb-9766-90cf066b500f.png)



- 마이크로 서비스를 넘나드는 시나리오에 대한 트랜잭션 처리 
- 이사팀 할당요청이 완료되지 않은 호출요청 완료처리는 최종 할당이 되지 않는 경우 
  무한정 대기 등 대고객 서비스 및 신뢰도에 치명적 문제점이 있어 ACID 트랜잭션 적용. 
  호출요청 시 이사팀 할당요청에 대해서는 Request-Response 방식 처리 
- 호출요청 완료시 할당확인 및 결과 전송: asman manage service 에서
  asman Assign 마이크로서비스로 이사팀할당 요청이 전달되는 과정에 있어서 
  asman Assign 마이크로 서비스가 별도의 배포주기를 가지기 때문에 Eventual Consistency 방식으로 트랜잭션 처리함. 
- 나머지 모든 inter-microservice 트랜잭션: 호출상태, 할당/할당취소 여부 등 이벤트에 대해 카톡을 처리하는 등, 
  데이터 일관성의 시점이 크리티컬하지 않은 모든 경우가 대부분이라 판단, Eventual Consistency 를 기본으로 채택함




## 헥사고날 아키텍처 다이어그램 도출 (Polyglot)

![image](https://user-images.githubusercontent.com/78134019/109766767-f9271400-7c39-11eb-8dc5-6be3964415bc.png)






# 구현:

서비스를 로컬에서 실행하는 방법은 아래와 같다 
각 서비스별로 bat 파일로 실행한다. 

```
- run_zookeeper.bat
call setenv.bat
zookeeper-server-start.bat %KAFKA_HOME%\config\zookeeper.properties 

- run_kafka.bat
call setenv.bat
kafka-server-start.bat %KAFKA_HOME%\config\server.properties
pause ..

- run_movecall.bat
call setenv.bat
REM java  -Xmx400M -Djava.security.egd=file:/dev/./urandom -jar food-delivery\app\target\app-0.0.1-SNAPSHOT.jar --spring.profiles.active=docker
REM java  -Xmx400M -Djava.security.egd=file:/dev/./urandom -jar food-delivery\app\target\app-0.0.1-SNAPSHOT.jar --spring.profiles.active=default
cd ..\move\movecall
mvn clean spring-boot:run
pause ..

- run_movemanage.bat
call setenv.bat
REM java  -Xmx400M -Djava.security.egd=file:/dev/./urandom -jar food-delivery\pay\target\pay-0.0.1-SNAPSHOT.jar --spring.profiles.active=docker
REM java  -Xmx400M -Djava.security.egd=file:/dev/./urandom -jar food-delivery\pay\target\pay-0.0.1-SNAPSHOT.jar --spring.profiles.active=default
cd ..\move\movemanage
mvn clean spring-boot:run
pause ..

- run_moveassign.bat
call setenv.bat
REM java  -Xmx400M -Djava.security.egd=file:/dev/./urandom -jar food-delivery\store\target\store-0.0.1-SNAPSHOT.jar --spring.profiles.active=docker
REM java  -Xmx400M -Djava.security.egd=file:/dev/./urandom -jar food-delivery\store\target\store-0.0.1-SNAPSHOT.jar --spring.profiles.active=default
cd ..\move\moveassign
mvn clean spring-boot:run
pause ..

- run_customer.bat
call setenv.bat
SET CONDA_PATH=%ANACONDA_HOME%;%ANACONDA_HOME%\BIN;%ANACONDA_HOME%\condabin;%ANACONDA_HOME%\Library\bin;%ANACONDA_HOME%\Scripts;
SET PATH=%CONDA_PATH%;%PATH%;
cd ..\move\customer\
python policy-handler.py 
pause ..

```

## DDD 의 적용
총 3개의 Domain 으로 관리되고 있으며, 이사업체요청(Movecall) , 이사관리(Movemanage), 이사할당(Moveassign) 으로 구성된다. 

![img.png](img.png)

![img_1.png](img_1.png)

## 폴리글랏 퍼시스턴스

```
위치 : /move>movemanage>pom.xml
```
![폴리그랏DB_최종](https://user-images.githubusercontent.com/78134019/109745194-d800fc00-7c16-11eb-87bd-2f65884a5f71.jpg)



## 폴리글랏 프로그래밍 - 파이썬
```
위치 : /move>cutomer>policy-handler.py
```
![img_2.png](img_2.png)

## 마이크로 서비스 호출 흐름

- movecall 서비스 호출처리
이사업체호출(movecall)->이사업체관리(movemanage) 간의 호출처리 됨.
이사업체 할당에서 이사업체를 할당하여 호출 확정 상태가 됨.
두 개의 호출 상태
를 만듬.
```
http localhost:8081/movers tel="01012345678" status="호출" location="mapo" cost=2500000
http localhost:8081/movers tel="01012345678" status="호출" location="guro" cost=1500000
```
![img_9.png](img_9.png)
![img_10.png](img_10.png)

호출 결과는 모두 이사업체 할당(taxiassign)에서 할당처리되어 이사업체 호출(movecall)에서
호출 확정, 이사업체 관리(movemanage)에서 호출확정 상태가 되어 있음.
movecall: 호출확정
![img_5.png](img_5.png)
![img_6.png](img_6.png)

movemanage: 호출확정
![img_7.png](img_7.png)
![img_8.png](img_8.png)

- movecall 서비스 호출 취소 처리

호출 취소는 이사업체 호출에서 다음과 같이 호출 하나를 취소 함으로써 진행 함.

```
http delete localhost:8081/movers/1
HTTP/1.1 204
Date: Thu, 04 Mar 2021 13:44:26 GMT
```
호출이 취소 되면 이사업체 호출이 하나가 삭제되어 movers/2 만조회되는 것을 확인.
```
http localhost:8081/movers
```
![img_11.png](img_11.png)

이사업체 관리에서는 해당 호출에 대해서 호출요청취소로 상태가 변경 됨.

```
http localhost:8082/movermanages/
```
![img_12.png](img_12.png)

- 고객 메시지 서비스 처리
고객(customer)는 호출 확정과 할당 확정에 대한 메시지를 다음과 같이 받을 수 있으며,
할당 된 택시기사의 정보를 또한 확인 할 수 있다.
파이썬으로 구현 하였음.
![img_13.png](img_13.png)

## Gateway 적용

서비스에 대한 하나의 접점을 만들기 위한 게이트웨이의 설정은 8080로 설정 하였으며, 다음 마이크로서비스에 대한 설정 입니다.
```
이사업체 호출 서비스 : 8081
이사업체 관리 서비스 : 8082
이사업체 할당 서비스 : 8084
```

gateway > applitcation.yml 설정

![img_14.png](img_14.png)
![img_42.png](img_42.png)


gateway 테스트

```
http localhost:8080/movers
-> gateway 를 호출하나 8081 로 호출됨
```
![img_16.png](img_16.png)


## 동기식 호출 과 Fallback 처리

이사업체 호출(movecall)-> 이사업체 관리(movemanage) 간의 호출은 동기식 일관성을 유지하는 트랜잭션으로 처리함.
호출 프로토콜은 이미 앞서 Rest Repository 에 의해 노출되어있는 REST 서비스를 FeignClient 를 이용하여 호출하도록 한다. 

구현 소스는 아래와 같으며,
```
# movecall > external > MovemanageService.java


package move24mall.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(name="movermanage", url="http://movermanage:8080", fallback = MovermanageServiceFallback.class)
@FeignClient(name="movermanage", url="http://localhost:8082", fallback = MovermanageServiceFallback.class)
public interface MovermanageService {

    @RequestMapping(method= RequestMethod.POST, path="/movermanages")
    public void reqMoveAssign(@RequestBody Movermanage mg);

}

```

MovemanageService 인터페이스를 구현한 MovemanageServiceFallback 클래스
```
# movecall > external > MovemanageServiceFallback.java


package move24mall.external;

import org.springframework.stereotype.Component;

@Component
public class MovermanageServiceFallback implements MovermanageService {
	
	@Override
	public void reqMoveAssign(Movermanage movermanage) {
		System.out.println("Circuit breaker has been opened. Fallback returned instead. " + movermanage.getId());
	}

}

```


- 이사업체 호출을 하면 이사업체 관리가 호출되도록 동기적 진행
```
# Mover.java

    @PostPersist
    public void onPostPersist(){

    	System.out.println("휴대폰번호 " + getTel());
        System.out.println("호출위치 " + getLocation());
        System.out.println("호출상태 " + getStatus());
        System.out.println("예상요금 " + getCost());
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.   	
    	if(getTel() != null)
		{
    		System.out.println("SEND###############################" + getId());
			Movermanage movermanage = new Movermanage();
			movermanage.setId(getId());
			movermanage.setOrderId(String.valueOf(getId()));
			movermanage.setTel(getTel());
	        if(getLocation()!=null) 
	        	movermanage.setLocation(getLocation());
	        if(getStatus()!=null) 
	        	movermanage.setStatus(getStatus());
	        if(getCost()!=null) 
	        	movermanage.setCost(getCost());
	        
	        // mappings goes here
	        MovercallApplication.applicationContext.getBean(MovermanageService.class).reqMoveAssign(movermanage);
		}
```

- 동기식 호출 적용으로 이사업체 관리 시스템이 정상적이지 않으면 , 이사업체 호출도 접수될 수 없음을 확인 
```
# 이사업체 관리 시스템 down 후 movecall 호출 
#movecall

C:\Users\Administrator>http localhost:8081/movers tel="01012345678" status="호출" location="mapo" cost=2500000
```

![택시관리죽으면택시콜놉](https://user-images.githubusercontent.com/78134019/109464780-905d6180-7aaa-11eb-9c90-e7d1326deea1.jpg)

```
# 이사업체 관리 (movemanage) 재기동 후 호출하기

#주문하기(order)
http localhost:8081/택시호출s 휴대폰번호="01012345678" 호출상태="호출"
```
![img_18.png](img_18.png)

-fallback 
![img_17.png](img_17.png)

## 비동기식 호출 / 장애격리  / 성능

택시 관리 (Taxi manage) 이후 택시 할당(Taxi Assign) 은 비동기식 처리이므로 , 택시 호출(Taxi call) 의 서비스 호출에는 영향이 없다
 
고객이 이사업체 호출(Move call) 후 상태가 [호출]로 변경되고 할당이 완료되면 [호출확정] 로 변경이 되지만 , 택시 할당(Taxi Assign)이 정상적이지 않으므로 [호출]로 남아있음. 
--> (시간적 디커플링)
<이사업체 호출 movecall>
![img_20.png](img_20.png)
<이사업체 할당(moveassign)이 정상적이지 않아 호출확정이 아닌 호출중으로 남아있음>
![img_21.png](img_21.png)


## 성능 조회 / View 조회
고객이 호출한 모든 정보는 조회가 가능하다. 

![img_13.png](img_13.png)



# 운영

## Deploy / Pipeline

- az login
```
  {
    "cloudName": "AzureCloud",
    "homeTenantId": "6011e3f8-2818-42ea-9a63-66e6acc13e33",
    "id": "a231bfd6-369c-4bbb-8d38-3887804ff067",
    "isDefault": true,
    "managedByTenants": [],
    "name": "4",
    "state": "Enabled",
    "tenantId": "6011e3f8-2818-42ea-9a63-66e6acc13e33",
    "user": {
      "name": "skuser16@gkn2021hotmail.onmicrosoft.com",
      "type": "user"
    }
  }
```


- account set 
```
az account set --subscription "종량제4"
```


- 리소스그룹생성
```
그룹명 : skuser16-rsrcgrp
```


- 클러스터 생성
```
클러스터 명 : skuser16-aks
```

- 토큰 가져오기
```
az aks get-credentials --resource-group skuser16-rsrcgrp --name skuser16-aks
```

- aks에 acr 붙이기
```
az aks update -n skuser16-aks -g skuser16-rsrcgrp --attach-acr skuser16
```

![img_26.png](img_26.png)

- 네임스페이스 만들기
```
kubectl create ns skuser16ns
kubectl get ns
```
![img_27.png](img_27.png)

- 소스 패키징

클라우드 배포를 위해서 다음과 같이 패키징 작업을 하였습니다.
```
cd gateway
mvn clean && mvn package
cd ..
cd movecall
mvn clean && mvn package
cd ..
cd movemanage
mvn clean && mvn package
cd ..
cd moveassign
mvn clean && mvn package
cd ..
```

![img_28.png](img_28.png)

![img_29.png](img_29.png)

![img_30.png](img_30.png)

![img_31.png](img_31.png)

- 도커 이미지 만들고 레지스트리에 등록하기
```
cd gateway
az acr build --registry skuser16 --image skuser16.azurecr.io/gateway:v1 .
cd ..
cd movecall
az acr build --registry skuser16 --image skuser16.azurecr.io/movecall:v1 .
cd ..
cd movemanage
az acr build --registry skuser16 --image skuser16.azurecr.io/movemanage:v1 .
cd ..
cd moveassign
az acr build --registry skuser16 --image skuser16.azurecr.io/moveassign:v1 .
cd ..
cd customer
az acr build --registry skuser16 --image skuser16.azurecr.io/customer:v1 .
```

![img_32.png](img_32.png)

![img_33.png](img_33.png)

![img_34.png](img_34.png)

![img_35.png](img_35.png)


- deployment.yml, service.yaml을 사용하여 배포
```
cd gateway/kubernetes
kubectl apply -f deployment.yml --namespace=skuser16ns
kubectl apply -f service.yaml --namespace=skuser16ns

cd ../../
cd movecall/kubernetes
kubectl apply -f deployment.yml --namespace=skuser16ns
kubectl apply -f service.yaml --namespace=skuser16ns

cd ../../
cd movemanage/kubernetes
kubectl apply -f deployment.yml --namespace=skuser16ns
kubectl apply -f service.yaml --namespace=skuser16ns

cd ../../
cd moveassign/kubernetes
kubectl apply -f deployment.yml --namespace=skuser16ns
kubectl apply -f service.yaml --namespace=skuser16ns

cd ../../
cd customer/kubernetes
kubectl apply -f deployment.yml --namespace=skuser16ns
kubectl apply -f service.yaml --namespace=skuser16ns
```

![img_36.png](img_36.png)

![img_37.png](img_37.png)

![img_38.png](img_38.png)

![img_39.png](img_39.png)

![img_40.png](img_40.png)


- 서비스확인
```
kubectl get services,deployments,pods -n skuser16ns
```

![img_41.png](img_41.png)

