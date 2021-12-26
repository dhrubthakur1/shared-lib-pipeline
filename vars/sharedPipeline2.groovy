import com.checkout.CheckOut;
import com.build.MVNBuild;
import com.deploy.DeployToTomcat;
//import groovy.yaml.YamlSlurper;
//import com.util.ParseFile;

def call(def conf=[:]) {
	def checkOut = new CheckOut(this)
	def mvnBuild = new MVNBuild(this)
	def deployToTomcat = new DeployToTomcat(this)
	//def parseFile = new ParseFile();
	
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
			def datas = readYaml (file: 'build.yml') 
		   bat 'echo "read yml start"'
		   bat "echo ${datas}"
		   bat "echo ${datas.application.buildRequired}"
	           bat "echo ${datas.application.buildType}"
		   bat "echo ${env.buildRequired}"
	           env.buildRequired="${datas.application.buildRequired}"
		   conf.put('isBuildRequired', datas.application.buildRequired);
		  conf.put('duildType', datas.application.buildType);
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
			stages{
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
				    //archiveArtifacts artifacts: 'target/*.war', fingerprint: true
				    //cleanWs()
				  }
				}
			
			}		
	}       

       }
   }
}
