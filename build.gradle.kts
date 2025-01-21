plugins {
	java
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.liquibase.gradle") version "2.1.0"
}

group = "dev.uliana"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security:3.4.1")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.opencsv:opencsv:5.7.1")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springframework.boot:spring-boot-starter-logging")
	implementation("org.liquibase:liquibase-core")
	implementation("com.fasterxml.jackson.core:jackson-databind")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")
	implementation("io.swagger.core.v3:swagger-annotations:2.2.28")
	implementation("com.fasterxml.jackson.core:jackson-annotations")
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
	implementation("org.springframework.boot:spring-boot-starter-mail:3.4.1")

	liquibaseRuntime("org.liquibase:liquibase-core")
	liquibaseRuntime("org.postgresql:postgresql")

	compileOnly("org.projectlombok:lombok:1.18.30")

	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

	annotationProcessor("org.projectlombok:lombok:1.18.30")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.testcontainers:junit-jupiter:1.16.3")
	testImplementation("org.testcontainers:postgresql:1.16.3")
	testImplementation("org.mockito:mockito-inline:5.2.0")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

liquibase {
	activities {
		register("main") {
			arguments = mapOf(
				"changelogFile" to "src/main/resources/postgres.db.changelog/db.changelog-master.yaml",
				"url" to "jdbc:postgresql://localhost:5435/postgres_db",
				"username" to "user",
				"password" to "pass"
			)
		}
	}
	runList = "main"
}
