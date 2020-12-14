import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.7.RELEASE"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.4.21"
	kotlin("plugin.spring") version "1.4.21"
}


group = "com.wenance"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	jcenter()
	mavenCentral()
	maven { url = uri("https://plugins.gradle.org/m2/")}

	flatDir {
		dirs("lib")
	}

}

dependencies {
	implementation("org.springframework.cloud:spring-cloud-starter-config")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation (group = "com.fasterxml.jackson.dataformat", name = "jackson-dataformat-yaml", version = "2.11.0")

	//implementation("org.springframework.boot:spring-boot-starter-redis")

	implementation("org.springframework.boot:spring-boot-starter-data-redis")//:2.0.4.RELEASE
	//{
	//		exclude(group= "io.lettuce", module="lettuce-core")
	//	}
	implementation("redis.clients:jedis:3.3.0")

	//implementation("org.springframework.data:spring-data-redis:2.3.3.RELEASE")
}

dependencyManagement {
	imports {
		val springCloudVersion = "Greenwich.RELEASE"
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

springBoot {
	buildInfo()
}