import com.checkout.CheckOut;
import com.build.MVNBuild;

def call(def conf=[:]) {
  def checkOut = new CheckOut(this)
def mvnBuild = new MVNBuild(this)
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
                   /*checkout([
                    $class: 'GitSCM',
                    branches: [[name:  conf.branch ]],
                    userRemoteConfigs: [[ url: conf.url ]]
                  ])*/
                 }
               }
           }
           stage("Cleaning workspace") {
               steps {
                   //bat "mvn clean"
                 script{                   
                   bat 'echo "${mvnBuild}"'
                   bat 'echo "Build...."' 
                    mvnBuild.clean()
                 }
               }
           }
           stage("Running Testcase") {
              steps {
                   //bat "mvn test"
                script{
                  mvnBuild.mvnTest()
                }
               }
           }
           stage("Packing Application") {
               steps {
                   //bat "mvn package -DskipTests"
                 script{
                  mvnBuild.startBuild()
                 }
               }
           }
        stage ('Archive Artifacts') {
          steps {
            archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            cleanWs()
      }
    }
       }
   }
}
