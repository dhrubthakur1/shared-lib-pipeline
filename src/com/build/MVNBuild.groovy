package com.build;

public class MVNBuild{
  def steps
  
  public MVNBuild(steps) {
    this.steps = steps
  }
  
  public void clean() {
     steps.echo "clean called......${steps}"
    steps.bat "mvn clean"
  }
  
 public void mvnTest() {
     steps.echo "Test called......${steps}"
    steps.bat "mvn test"
  }
  
  public void startBuild() {
     steps.echo "Build called......${steps}"
    steps.bat "mvn clean package  -DskipTests"
  }
  
  public void archive() {
     steps.echo "Archive called......${steps}"
    steps.archiveArtifacts artifacts: 'target/*.war', fingerprint: true
  }
  
  public void deploy(def conf = [:]) {
     steps.echo "Deploy called......${steps}"
    steps.deploy adapters: [tomcat8(credentialsId: 'tomcatadmin', path: '', url: conf.tomcatUrl)], contextPath: 'spring4', onFailure: false, war: 'target/helloworld.war'
  }
}
