import com.checkout.CheckOut;
import com.build.MVNBuild;
import com.deploy.DeployToTomcat;
//import groovy.yaml.YamlSlurper;

def call(def conf=[:]) {
	def checkOut = new CheckOut(this)
	def mvnBuild = new MVNBuild(this)
	def deployToTomcat = new DeployToTomcat(this)
	
  pipeline {
       agent any
       tools {
           maven 'MAVEN_PATH'
          jdk 'JAVA_HOME'
       }
       stages {
           stage("Tools initialization") {
               steps {
                   bat "mvn --version"
                   bat "java -version"
               }
           }
         stage("Cleaning workspace") {
               steps {
                  
                 cleanWS()
               }
           }
	   stage("Checkout Code") {
               steps {		       
                 script{                                      
                   bat "echo ${conf.url}"
		               checkOut.startBuild(conf)
		              }
               }
           }
           stage("Running Testcase") {
              steps {
                   //bat "mvn test"
                script{
                  if(conf.isBuildRequired == "Yes"){
                  mvnBuild.mvnTest()
                  }
                }
               }
           }
           stage("Packing Application") {
               steps {
                   //bat "mvn package -DskipTests"
                 script{
                   if(conf.isBuildRequired == "Yes"){
                  mvnBuild.startBuild()
                  }
                  
                 }
               }
           }
        stage ('Archive Artifacts') {
          steps {
            script{
              mvnBuild.archive()
            }
            //archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            //cleanWs()
          }
        }
       stage("Deploy"){
            steps{              
              script{
		//ployToTomcat.deploy(conf)
              }
                //deploy adapters: [tomcat8(credentialsId: 'tomcatadmin', path: '', url: 'http://localhost:7070')], contextPath: 'spring4', onFailure: false, war: 'target/helloworld.war'                
            }
        }
       }
   }
}
