See the guide [here](https://guides.hazelcast.org/hazelcast-embedded-springboot).

hazelcast 의 springboot kubernetes 의 구현체로
resources/hazelcast.yaml 에 2가지 종류의 맵이 구현되어 있다.
10초 후 캐시가 사라지는 것 (/put, /get) 과 영구 보존형이다. (put2/get2)

k8s deployment 에서 liveness 가 중요.
새로운 pod 이 뜨기 전에 이전 팟이 죽으면 캐시가 모두 날아가므로
새로운 pod 이 뜨고, 캐시를 복제 받은 후에 roll out 되어야 한다.

