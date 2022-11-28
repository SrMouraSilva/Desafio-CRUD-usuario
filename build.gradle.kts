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
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Security
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.bouncycastle:bcpkix-jdk18on:1.72") { because("Spring Security Crypo - Argon2") }

	compileOnly("io.jsonwebtoken:jjwt-api:0.11.5") { because("Auth with JWT") }
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5") { because("Auth with JWT") }
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5") { because("Auth with JWT") }

	// Database
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

	// Utils
	implementation("com.google.guava:guava:31.1-jre")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// Documentation
	//implementation("org.springdoc:springdoc-openapi-webmvc-core:1.6.13") { because("Without Swagger UI") }
	implementation("org.springdoc:springdoc-openapi-ui:1.6.13") { because("With Swagger UI") }
	implementation("org.springdoc:springdoc-openapi-security:1.6.13")
	implementation("org.springdoc:springdoc-openapi-data-rest:1.6.13")

	implementation("org.springframework.boot:spring-boot-starter-webflux") { because("WebClient and WebTestClient") }

	implementation("org.openapitools:jackson-databind-nullable:0.2.4") { because("Patch updates") }

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:mongodb")
	testImplementation("com.tngtech.archunit:archunit-junit5:1.0.0")
}

dependencyManagement {
	imports {
		mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
