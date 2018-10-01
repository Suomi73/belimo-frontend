@Library('semantic_releasing') _

podTemplate(label: 'mypod', containers: [
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

    stage('checkout & npm build') {
      git url: 'https://github.com/adessoSchweiz/belimo-frontend'
      container('npm-jdk') {
        sh """
              npm install -g @angular/cli@1.7.4
              npm install
              ng build
             """
      }
    }

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
