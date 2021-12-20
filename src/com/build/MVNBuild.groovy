package com.build;

public class MVNBuild{
  def steps
  
  public MVNBuild(steps) {
    this.steps = steps
  }
  
  public void startBuild() {
     steps.echo "Build called......${steps}"
    steps.bat "mvn clean package"
  }
}
