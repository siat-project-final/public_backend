# 베이스 이미지 pull
FROM eclipse-temurin:17-jdk-alpine

# 타임존 설정을 위한 패키지 설치
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
    echo "Asia/Seoul" > /etc/timezone

# jar 위치 지정
ARG JAR_FILE=build/libs/*.jar

# 카피
COPY ${JAR_FILE} /backend.jar

# 컨테이너 실행 환경에서 앱 실행 (JVM에도 타임존 설정)
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "/backend.jar"]