import com.checkout.CheckOut;
import com.build.MVNBuild;

def checkOut = new CheckOut(this)
def mvnBuild = new MVNBuild(this)
def call() {
  echo "Start..."
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
			checkOut.startBuild;
		  }
               }
           }
           stage("Cleaning workspace") {
               steps {
                   script{
			mvnBuild.startBuild;
		 }
               }
           }
           stage("Running Testcase") {
              steps {
                   bat "mvn test"
               }
           }
           stage("Packing Application") {
               steps {
                   bat "mvn package -DskipTests"
               }
           }
       }
   }
}
