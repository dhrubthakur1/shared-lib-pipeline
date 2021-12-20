import com.checkout.CheckOut;
import com.build.MVNBuild;

def checkOut = new CheckOut(this)
def mvnBuild = new MVNBuild(this)

def call() {
  pipeline {
       agent any
       tools {
           maven 'Maven 3.5.0'
           jdk 'jdk8'
       }
       stages {
           stage("Tools initialization") {
               steps {
                   sh "mvn --version"
                   sh "java -version"
               }
           }
           stage("Checkout Code") {
               steps {
                   checkOut.startBuild()
               }
           }
           stage("Cleaning workspace") {
               steps {
                   sh "mvn clean"
               }
           }
           stage("Running Testcase") {
              steps {
                   sh "mvn test"
               }
           }
           stage("Packing Application") {
               steps {
                   sh "mvn package -DskipTests"
               }
           }
       }
   }
}
