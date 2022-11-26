plugins {
	java
	id("org.springframework.boot") version "2.7.6"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "br.com.srmourasilva"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

extra["testcontainersVersion"] = "1.17.6"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

	implementation("com.google.guava:guava:31.1-jre")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	implementation("de.mkammerer:argon2-jvm:2.11") {
		because("Hashing password in argon2id format")
	}

	//implementation("org.springdoc:springdoc-openapi-webmvc-core:1.6.13") { because("Without Swagger UI") }
	implementation("org.springdoc:springdoc-openapi-ui:1.6.13") { because("With Swagger UI") }
	implementation("org.springdoc:springdoc-openapi-security:1.6.13")
	implementation("org.springdoc:springdoc-openapi-data-rest:1.6.13")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:mongodb")
}

dependencyManagement {
	imports {
		mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
