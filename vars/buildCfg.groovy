def call() {
  Map pipelineCfg = readYaml(file: "build.yml")
  return pipelineCfg
}
