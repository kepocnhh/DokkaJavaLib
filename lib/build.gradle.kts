import java.net.URL

version = "0.0.1"

repositories.mavenCentral()

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.dokka") version Version.dokka
}

tasks.getByName<JavaCompile>("compileJava") {
    targetCompatibility = Version.jvmTarget
}

val compileKotlinTask = tasks.getByName<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>("compileKotlin") {
    kotlinOptions {
        jvmTarget = Version.jvmTarget
        freeCompilerArgs = freeCompilerArgs + setOf("-module-name", rootProject.name)
    }
}

task<org.jetbrains.dokka.gradle.DokkaTask>("assembleDocumentation") {
    outputDirectory.set(buildDir.resolve("documentation"))
    moduleName.set(rootProject.name)
    moduleVersion.set(version.toString())
    dokkaSourceSets.getByName("main") {
        val path = "src/$name/kotlin"
        reportUndocumented.set(false)
        sourceLink {
            localDirectory.set(file(path))
            val url = "https://github.com/kepocnhh/DokkaJavaLib"
            remoteUrl.set(URL("$url/tree/${moduleVersion.get()}/lib/$path"))
        }
        jdkVersion.set(Version.jvmTarget.toInt())
    }
}
