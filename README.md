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

서비스에 대한 하나의 접점을 만들기 위한 게이트웨이의 설정은 8088로 설정 하였으며, 다음 마이크로서비스에 대한 설정 입니다.
```
이사업체 호출 서비스 : 8081
이사업체 관리 서비스 : 8082
이사업체 할당 서비스 : 8084
```

gateway > applitcation.yml 설정

![img_14.png](img_14.png)
![img_15.png](img_15.png)


gateway 테스트

```
http localhost:8080/택시호출s
-> gateway 를 호출하나 8081 로 호출됨
```
![gateway_3](https://user-images.githubusercontent.com/78134019/109480424-da504280-7abe-11eb-988e-2a6d7a1f7cea.png)



## 동기식 호출 과 Fallback 처리

호출(taxicall)->택시관리(taximanage) 간의 호출은 동기식 일관성을 유지하는 트랜잭션으로 처리함.
호출 프로토콜은 이미 앞서 Rest Repository 에 의해 노출되어있는 REST 서비스를 FeignClient 를 이용하여 호출하도록 한다. 


```
# external > 택시관리Service.java


package taxiguider.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(name="taximanage", url="http://localhost:8082")
@FeignClient(name="taximanage", url="http://localhost:8082", fallback = 택시관리ServiceFallback.class)
public interface 택시관리Service {

    @RequestMapping(method= RequestMethod.POST, path="/택시관리s")
    public void 택시할당요청(@RequestBody 택시관리 택시관리);

}

```

```
# external > 택시관리ServiceFallback.java


package taxiguider.external;

import org.springframework.stereotype.Component;

@Component
public class 택시관리ServiceFallback implements 택시관리Service {
	 
	//@Override
	//public void 택시할당요청(택시관리 택시관리) 
	//{	
	//	System.out.println("Circuit breaker has been opened. Fallback returned instead.");
	//}
	
	
	@Override
	public void 택시할당요청(택시관리 택시관리) {
		// TODO Auto-generated method stub
		System.out.println("Circuit breaker has been opened. Fallback returned instead. " + 택시관리.getId());
	}

}

```

![동기식](https://user-images.githubusercontent.com/78134019/109463569-97837000-7aa8-11eb-83c4-6f6eff1594aa.jpg)


- 택시호출을 하면 택시관리가 호출되도록..
```
# 택시호출.java

 @PostPersist
    public void onPostPersist(){    	
    	System.out.println("휴대폰번호 " + get휴대폰번호());
        System.out.println("호출위치 " + get호출위치());
        System.out.println("호출상태 " + get호출상태());
        System.out.println("예상요금 " + get예상요금());
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.   	
    	if(get휴대폰번호() != null)
		{
    		System.out.println("SEND###############################" + getId());
			택시관리 택시관리 = new 택시관리();
	        
			택시관리.setOrderId(String.valueOf(getId()));
	        택시관리.set고객휴대폰번호(get휴대폰번호());
	        if(get호출위치()!=null) 
	        	택시관리.set호출위치(get호출위치());
	        if(get호출상태()!=null) 
	        	택시관리.set호출상태(get호출상태());
	        if(get예상요금()!=null) 
	        	택시관리.set예상요금(get예상요금());
	        
	        // mappings goes here
	        TaxicallApplication.applicationContext.getBean(택시관리Service.class).택시할당요청(택시관리);
		}
```

![동기식2](https://user-images.githubusercontent.com/78134019/109463985-47f17400-7aa9-11eb-8603-c1f83e17951d.jpg)

- 동기식 호출 적용으로 택시 관리 시스템이 정상적이지 않으면 , 택시콜도 접수될 수 없음을 확인 
```
# 택시 관리 시스템 down 후 taxicall 호출 

#taxicall

C:\Users\Administrator>http localhost:8081/택시호출s 휴대폰번호="01012345678" 호출상태="호출"
```

![택시관리죽으면택시콜놉](https://user-images.githubusercontent.com/78134019/109464780-905d6180-7aaa-11eb-9c90-e7d1326deea1.jpg)

```
# 택시 관리 (taximanage) 재기동 후 주문하기

#주문하기(order)
http localhost:8081/택시호출s 휴대폰번호="01012345678" 호출상태="호출"
```

![택시관리재시작](https://user-images.githubusercontent.com/78134019/109464984-e5997300-7aaa-11eb-9363-b7bfe15de105.jpg)

-fallback 

![fallback캡쳐](https://user-images.githubusercontent.com/78134019/109480299-b5f46600-7abe-11eb-906e-9e1e6da245b2.png)


## 비동기식 호출 / 장애격리  / 성능

택시 관리 (Taxi manage) 이후 택시 할당(Taxi Assign) 은 비동기식 처리이므로 , 택시 호출(Taxi call) 의 서비스 호출에는 영향이 없다
 
고객이 택시 호출(Taxi call) 후 상태가 [호출]->[호출중] 로 변경되고 할당이 완료되면 [호출확정] 로 변경이 되지만 , 택시 할당(Taxi Assign)이 정상적이지 않으므로 [호출중]로 남아있음. 
--> (시간적 디커플링)
<고객 택시 호출 Taxi call>
![비동기_호출2](https://user-images.githubusercontent.com/78134019/109468467-f4365900-7aaf-11eb-877a-049637b5ee6a.png)

<택시 할당이 정상적이지 않아 호출중으로 남아있음>
![택시호출_택시할당없이_조회](https://user-images.githubusercontent.com/78134019/109471791-99ebc700-7ab4-11eb-924f-03715de42eba.png)



## 성능 조회 / View 조회
고객이 호출한 모든 정보는 조회가 가능하다. 

![고객View](https://user-images.githubusercontent.com/78134019/109483385-80ea1280-7ac2-11eb-9419-bf3ff5a0dbbc.png)


---mvn MSA Service
<gateway>
	
![mvn_gateway](https://user-images.githubusercontent.com/78134019/109744124-244b3c80-7c15-11eb-80a9-bed42413aa58.png)
	
<taxicall>
	
![mvn_taxicall](https://user-images.githubusercontent.com/78134019/109744165-31682b80-7c15-11eb-9d94-7bc23efca6b6.png)

<taximanage>
	
![mvn_taximanage](https://user-images.githubusercontent.com/78134019/109744195-3b8a2a00-7c15-11eb-9554-1c3ba088af52.png)

<taxiassign>
	
![mvn_taxiassign](https://user-images.githubusercontent.com/78134019/109744226-46dd5580-7c15-11eb-8b47-5100ed01e3ae.png)


# 운영

## Deploy / Pipeline

- az login
```
{
    "cloudName": "AzureCloud",
    "homeTenantId": "6011e3f8-2818-42ea-9a63-66e6acc13e33",
    "id": "718b6bd0-fb75-4ec9-9f6e-08ae501f92ca",
    "isDefault": true,
    "managedByTenants": [],
    "name": "2",
    "state": "Enabled",
    "tenantId": "6011e3f8-2818-42ea-9a63-66e6acc13e33",
    "user": {
      "name": "skTeam03@gkn2021hotmail.onmicrosoft.com",
      "type": "user"
    }
  }
```


- account set 
```
az account set --subscription "종량제2"
```


- 리소스그룹생성
```
그룹명 : skccteam03-rsrcgrp
```


- 클러스터 생성
```
클러스터 명 : skccteam03-aks
```

- 토큰 가져오기
```
az aks get-credentials --resource-group skccteam03-rsrcgrp --name skccteam03-aks
```

- aks에 acr 붙이기
```
az aks update -n skccteam03-aks -g skccteam03-rsrcgrp --attach-acr skccteam03
```

![aks붙이기](https://user-images.githubusercontent.com/78134019/109653395-540e2c00-7ba4-11eb-97dd-2dcfdf5dc539.jpg)



-deployment.yml을 사용하여 배포 
--> 도커 이미지 만들기 붙이기 
- deployment.yml 편집
```
namespace, image 설정
env 설정 (config Map) 
readiness 설정 (무정지 배포)
liveness 설정 (self-healing)
resource 설정 (autoscaling)
```
![deployment_yml](https://user-images.githubusercontent.com/78134019/109652001-9171ba00-7ba2-11eb-8c29-7128ceb4ec97.jpg)

- deployment.yml로 서비스 배포
```
cd app
kubectl apply -f kubernetes/deployment.yml
```
<Deploy cutomer>
![deploy_customer](https://user-images.githubusercontent.com/78134019/109744443-a471a200-7c15-11eb-94c9-a0c0a7999d04.png)

<Deploy gateway>
![deploy_gateway](https://user-images.githubusercontent.com/78134019/109744457-acc9dd00-7c15-11eb-8502-ff65e779e9d2.png)

<Deploy taxiassign>
![deploy_taxiassign](https://user-images.githubusercontent.com/78134019/109744471-b3585480-7c15-11eb-8d68-bba9c3d8ce01.png)

<Deploy taxicall>
![deploy_taxicall](https://user-images.githubusercontent.com/78134019/109744487-bb17f900-7c15-11eb-8bd0-ff0a9fc9b2e3.png)

<Deploy_taximanage>
![deploy_taximanage](https://user-images.githubusercontent.com/78134019/109744591-e69ae380-7c15-11eb-834a-44befae55092.png)

## 동기식 호출 / 서킷 브레이킹 / 장애격리

* 서킷 브레이킹 프레임워크의 선택: Spring FeignClient + Hystrix 옵션을 사용하여 구현함

시나리오는 단말앱(app)-->결제(pay) 시의 연결을 RESTful Request/Response 로 연동하여 구현이 되어있고, 결제 요청이 과도할 경우 CB 를 통하여 장애격리.

- Hystrix 를 설정:  요청처리 쓰레드에서 처리시간이 610 밀리가 넘어서기 시작하여 어느정도 유지되면 CB 회로가 닫히도록 (요청을 빠르게 실패처리, 차단) 설정
```
# application.yml
feign:
  hystrix:
    enabled: true

# To set thread isolation to SEMAPHORE
#hystrix:
#  command:
#    default:
#      execution:
#        isolation:
#          strategy: SEMAPHORE

hystrix:
  command:
    # 전역설정
    default:
      execution.isolation.thread.timeoutInMilliseconds: 610

```
![hystrix](https://user-images.githubusercontent.com/78134019/109652345-0218d680-7ba3-11eb-847b-708ba071c119.jpg)


-----------------------------------------
* siege 툴 사용법:
```
 siege가 생성되어 있지 않으면:
 kubectl run siege --image=apexacme/siege-nginx -n phone82
 siege 들어가기:
 kubectl exec -it pod/siege-5c7c46b788-4rn4r -c siege -n phone82 -- /bin/bash
 siege 종료:
 Ctrl + C -> exit
```
* 부하테스터 siege 툴을 통한 서킷 브레이커 동작 확인:
- 동시사용자 100명
- 60초 동안 실시

```
siege -c100 -t60S -r10 -v --content-type "application/json" 'http://app:8080/orders POST {"item": "abc123", "qty":3}'
```
- 부하 발생하여 CB가 발동하여 요청 실패처리하였고, 밀린 부하가 pay에서 처리되면서 다시 order를 받기 시작 

![image](https://user-images.githubusercontent.com/73699193/98098702-07eefb80-1ed2-11eb-94bf-316df4bf682b.png)

- report

![image](https://user-images.githubusercontent.com/73699193/98099047-6e741980-1ed2-11eb-9c55-6fe603e52f8b.png)

- CB 잘 적용됨을 확인


### 오토스케일 아웃

- 대리점 시스템에 대한 replica 를 동적으로 늘려주도록 HPA 를 설정한다. 설정은 CPU 사용량이 15프로를 넘어서면 replica 를 10개까지 늘려준다:

```
# autocale out 설정
store > deployment.yml 설정
```
![image](https://user-images.githubusercontent.com/73699193/98187434-44fbd200-1f54-11eb-9859-daf26f812788.png)

```
kubectl autoscale deploy store --min=1 --max=10 --cpu-percent=15 -n phone82
```
![image](https://user-images.githubusercontent.com/73699193/98100149-ce1ef480-1ed3-11eb-908e-a75b669d611d.png)


-
- CB 에서 했던 방식대로 워크로드를 2분 동안 걸어준다.
```
kubectl exec -it pod/siege-5c7c46b788-4rn4r -c siege -n phone82 -- /bin/bash
siege -c100 -t120S -r10 -v --content-type "application/json" 'http://store:8080/storeManages POST {"orderId":"456", "process":"Payed"}'
```
![image](https://user-images.githubusercontent.com/73699193/98102543-0d9b1000-1ed7-11eb-9cb6-91d7996fc1fd.png)

- 오토스케일이 어떻게 되고 있는지 모니터링을 걸어둔다:
```
kubectl get deploy store -w -n phone82
```
- 어느정도 시간이 흐른 후 스케일 아웃이 벌어지는 것을 확인할 수 있다. max=10 
- 부하를 줄이니 늘어난 스케일이 점점 줄어들었다.

![image](https://user-images.githubusercontent.com/73699193/98102926-92862980-1ed7-11eb-8f19-a673d72da580.png)

- 다시 부하를 주고 확인하니 Availability가 높아진 것을 확인 할 수 있었다.

![image](https://user-images.githubusercontent.com/73699193/98103249-14765280-1ed8-11eb-8c7c-9ea1c67e03cf.png)


## 무정지 재배포

* 먼저 무정지 재배포가 100% 되는 것인지 확인하기 위해서 Autoscale 이나 CB 설정을 제거함


- seige 로 배포작업 직전에 워크로드를 모니터링 함.
```
kubectl apply -f kubernetes/deployment_readiness.yml
```
- readiness 옵션이 없는 경우 배포 중 서비스 요청처리 실패

![image](https://user-images.githubusercontent.com/73699193/98105334-2a394700-1edb-11eb-9633-f5c33c5dee9f.png)


- deployment.yml에 readiness 옵션을 추가 

![image](https://user-images.githubusercontent.com/73699193/98107176-75ecf000-1edd-11eb-88df-617c870b49fb.png)

- readiness적용된 deployment.yml 적용

```
kubectl apply -f kubernetes/deployment.yml
```
- 새로운 버전의 이미지로 교체
```
cd acr
az acr build --registry admin02 --image admin02.azurecr.io/store:v4 .
kubectl set image deploy store store=admin02.azurecr.io/store:v4 -n phone82
```
- 기존 버전과 새 버전의 store pod 공존 중

![image](https://user-images.githubusercontent.com/73699193/98106161-65884580-1edc-11eb-9540-17a3c9bdebf3.png)

- Availability: 100.00 % 확인

![image](https://user-images.githubusercontent.com/73699193/98106524-c152ce80-1edc-11eb-8e0f-3731ca2f709d.png)



## Config Map

- apllication.yml 설정

* default쪽

![image](https://user-images.githubusercontent.com/73699193/98108335-1c85c080-1edf-11eb-9d0f-1f69e592bb1d.png)

* docker 쪽

![image](https://user-images.githubusercontent.com/73699193/98108645-ad5c9c00-1edf-11eb-8d54-487d2262e8af.png)

- Deployment.yml 설정

![image](https://user-images.githubusercontent.com/73699193/98108902-12b08d00-1ee0-11eb-8f8a-3a3ea82a635c.png)

- config map 생성 후 조회
```
kubectl create configmap apiurl --from-literal=url=http://pay:8080 --from-literal=fluentd-server-ip=10.xxx.xxx.xxx -n phone82
```
![image](https://user-images.githubusercontent.com/73699193/98107784-5bffdd00-1ede-11eb-8da6-82dbead0d64f.png)

- 설정한 url로 주문 호출
```
http POST http://app:8080/orders item=dfdf1 qty=21
```

![image](https://user-images.githubusercontent.com/73699193/98109319-b732cf00-1ee0-11eb-9e92-ad0e26e398ec.png)

- configmap 삭제 후 app 서비스 재시작
```
kubectl delete configmap apiurl -n phone82
kubectl get pod/app-56f677d458-5gqf2 -n phone82 -o yaml | kubectl replace --force -f-
```
![image](https://user-images.githubusercontent.com/73699193/98110005-cf571e00-1ee1-11eb-973f-2f4922f8833c.png)

- configmap 삭제된 상태에서 주문 호출   
```
http POST http://app:8080/orders item=dfdf2 qty=22
```
![image](https://user-images.githubusercontent.com/73699193/98110323-42f92b00-1ee2-11eb-90f3-fe8044085e9d.png)

![image](https://user-images.githubusercontent.com/73699193/98110445-720f9c80-1ee2-11eb-851e-adcd1f2f7851.png)

![image](https://user-images.githubusercontent.com/73699193/98110782-f4985c00-1ee2-11eb-97a7-1fed3c6b042c.png)



## Self-healing (Liveness Probe)

- store 서비스 정상 확인

![image](https://user-images.githubusercontent.com/27958588/98096336-fb1cd880-1ece-11eb-9b99-3d704cd55fd2.jpg)


- deployment.yml 에 Liveness Probe 옵션 추가
```
cd ~/phone82/store/kubernetes
vi deployment.yml

(아래 설정 변경)
livenessProbe:
	tcpSocket:
	  port: 8081
	initialDelaySeconds: 5
	periodSeconds: 5
```
![image](https://user-images.githubusercontent.com/27958588/98096375-0839c780-1ecf-11eb-85fb-00e8252aa84a.jpg)

- store pod에 liveness가 적용된 부분 확인

![image](https://user-images.githubusercontent.com/27958588/98096393-0a9c2180-1ecf-11eb-8ac5-f6048160961d.jpg)

- store 서비스의 liveness가 발동되어 13번 retry 시도 한 부분 확인

![image](https://user-images.githubusercontent.com/27958588/98096461-20a9e200-1ecf-11eb-8b02-364162baa355.jpg)

