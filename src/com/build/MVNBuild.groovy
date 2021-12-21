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
    steps.bat "mvn clean package"
  }
}
