@Library('semantic_releasing') _

podTemplate(label: 'mypod', containers: [
  containerTemplate(name: 'docker', image: 'docker', ttyEnabled: true, command: 'cat'),
  containerTemplate(name: 'kubectl', image: 'lachlanevenson/k8s-kubectl:v1.11.2', command: 'cat', ttyEnabled: true),
  containerTemplate(name: 'npm-jdk', image: 'khinkali/npm-java:0.0.4', ttyEnabled: true, command: 'cat'),
],
  volumes: [
    hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock'),
  ]) {
  node('mypod') {

    properties([
      buildDiscarder(
        logRotator(artifactDaysToKeepStr: '',
          artifactNumToKeepStr: '',
          daysToKeepStr: '',
          numToKeepStr: '30'
        )
      ),
      pipelineTriggers([cron('0 12 * * *')])
    ])

    stage('UI Tests') {
      ansiColor('xterm') {
        container('npm-jdk') {
          sh '''
                   npm install nightwatch -g
                   nightwatch UIT --env integration
           '''
        }
        junit allowEmptyResults: true, testResults: '**/reports/*.xml'
      }
    }
  }
}
