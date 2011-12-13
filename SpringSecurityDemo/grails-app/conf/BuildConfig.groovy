import org.apache.ivy.plugins.resolver.*
import org.apache.ivy.core.settings.*

grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.plugins.dir="C:\\Documents and Settings\\All Users\\.grails\\1.3.7\\projects\\garage-service\\plugins"
grails.project.plugin.class.dir="C:\\Documents and Settings\\All Users\\.grails\\1.3.7\\projects\\garage-service\\plugin-classes"

//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "info" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
		resolver createProductServicesResolver()
		grailsRepo "http://resolver/all"
		mavenRepo "http://resolver/all"
		grailsCentral 
		flatDir name:'plugins', dirs:'\\\\carfax.cfx\\cfxgroups\\moappinfo\\Repository\\Ivy\\ProductServices\\grails-plugins'
	

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenLocal()
        //mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
		provided 'org.apache.ivy:ivy:2.2.0'
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // runtime 'mysql:mysql-connector-java:5.1.13'
		compile('carfax:build-levels:1.042') {
			dependencyConfiguration('jar')
		}
		 compile 'org.springframework.security:spring-security-core:3.0.7.RELEASE'
		 compile 'org.springframework.security:spring-security-web:3.0.7.RELEASE'
		 compile 'org.springframework.security:spring-security-config:3.0.7.RELEASE'
    }
	 
	 plugins {
		 compile ':garage-domain:0.8'
//		 compile 'grails:spring-security-core:1.2.1'
		 
//		 runtime 'grails:hibernate:1.3.7'
		 runtime 'grails:rest:0.6.1'
//		 runtime 'grails:tomcat:1.3.7'
//		 runtime 'grails:ui-performance:1.2.2'
		 
//		 test 'grails:code-coverage:1.2.5'
//		 test 'grails:gmetrics:0.3.1'
//		 test 'grails:functional-test:1.2.7'
		 
	 }
}

private DependencyResolver createProductServicesResolver() {
	def ivySettings = new IvySettings()
	ivySettings.load(new File("\\\\carfax.cfx\\cfxgroups\\moappinfo\\repository\\Ivy\\ProductServices\\ivysettings-no-external.xml"))
	def ivyResolver = ivySettings.getDefaultResolver()
	ivyResolver.name = "Carfax"
	return ivyResolver
}

