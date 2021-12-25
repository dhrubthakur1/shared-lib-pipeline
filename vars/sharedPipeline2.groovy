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
		   def datas = readYaml (file: 'build.yml') 
		   bat 'echo "read yml start"'
		   bat "echo ${datas}"
		   bat "echo ${datas.application.buildRequired}"
	           bat "echo ${datas.application.buildType}"
		   bat "echo ${env.buildRequired}"
	           env.buildRequired="${datas.application.buildRequired}"
			 conf.put('aa', datas.application.buildRequired);
			    bat "echo ${conf.aa}"
		  bat "echo ${env.buildRequired}"
                 }
               }
           }       
       }
   }
}
