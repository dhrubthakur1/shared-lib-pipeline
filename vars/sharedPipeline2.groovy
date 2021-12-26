import com.checkout.CheckOut;
import com.build.MVNBuild;
import com.deploy.DeployToTomcat;

def call(def conf=[:]) {
	def checkOut = new CheckOut(this)
	def mvnBuild = new MVNBuild(this)
	def deployToTomcat = new DeployToTomcat(this)
	
  pipeline {
       agent any
       stages {         	   
	   stage("Checkout Code") {
               steps {		       
                 script{                   
                   bat 'echo "${checkOut}"'
                   bat "echo ${conf.url}"
		   checkOut.startBuild(conf)
		   bat 'echo "read yml start"'
		   
                 }
               }
           }  
	 stage("Set Config") {
               steps {		       
                 script{  
		   def buildData = readYaml (file: 'build.yml') 
		   def deployData = readYaml (file: 'deploy.yml') 
		   bat 'echo "read yml start"'
		   bat "echo ${buildData}"
		   bat "echo ${buildData.application.buildRequired}"
	           bat "echo ${buildData.application.buildType}"
		   bat "echo ${env.buildRequired}"
	           env.buildRequired="${buildData.application.buildRequired}"
		   conf.put('isBuildRequired', buildData.application.buildRequired);
		   conf.put('buildType', buildData.application.buildType);
		   conf.put('deployRequired', deployData.application.buildType);
	           conf.put('tomcatId', deployData.application.buildType);
		   conf.put('tomcatUrl', deployData.application.buildType);
		   conf.put('contextPath', deployData.application.buildType);	 
		   bat "echo ${conf.isBuildRequired}"
		   bat "echo ${env.buildRequired}"
		   bat "echo hi....${conf}" 
		 
		}
	       }
	 }
	stage("Build Process start"){		
	when {
        	expression { conf.buildType == "Java" && conf.isBuildRequired == "Yes" }
          }
		agent any
			tools {
           			maven 'MAVEN_PATH'
          			jdk 'JAVA_HOME'
       				}	  

			stages{
				stage("Tools initialization") {
				       steps {
					   bat "mvn --version"
					   bat "java -version"
				       }
				   }
				stage("Checkout Code") {
				       steps {		       
					       cleanWs()
					 script{                   
					   bat 'echo "${checkOut}"'
					   bat "echo ${conf.url}"
					   checkOut.startBuild(conf)					   
					 }
				       }
				   }  
				stage("Running Testcase") {
				      steps {					   
					script{
					   mvnBuild.mvnTest()
					  }					
				       }
           			}
				stage("Packing Application") {
				       steps {					  
					 script{					   
					  mvnBuild.startBuild()					  
					 }
				       }
				   }
				stage ('Archive Artifacts') {
				  steps {
				    script{
				      mvnBuild.archive()
				    }				   
				  }
				}			
			}		
		}       
       }
   }
}
