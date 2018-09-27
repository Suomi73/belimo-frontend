@Library('semantic_releasing') _

podTemplate(label: 'mypod', containers: [
  containerTemplate(name: 'docker', image: 'docker', ttyEnabled: true, command: 'cat'),
  containerTemplate(name: 'kubectl', image: 'lachlanevenson/k8s-kubectl:v1.8.0', command: 'cat', ttyEnabled: true),
  containerTemplate(name: 'curl', image: 'khinkali/jenkinstemplate:0.0.3', command: 'cat', ttyEnabled: true),
  containerTemplate(name: 'npm-jdk', image: 'khinkali/npm-java:0.0.4', ttyEnabled: true, command: 'cat'),
  containerTemplate(name: 'maven', image: 'maven:3.5.2-jdk-8', command: 'cat', ttyEnabled: true)
],
  volumes: [
    hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock'),
  ]) {
  node('mypod') {

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

    stage('build image & git tag & docker push') {
      env.VERSION = semanticReleasing()
      currentBuild.displayName = env.VERSION

      sh "git config user.email \"jenkins@khinkali.ch\""
      sh "git config user.name \"Jenkins\""
      sh "git tag -a ${env.VERSION} -m \"${env.VERSION}\""
      withCredentials([usernamePassword(credentialsId: 'github', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
        sh "git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/adessoSchweiz/belimo-frontend.git --tags"
      }

      container('docker') {
        sh "docker build -t robertbrem/belimo-frontend:${env.VERSION} ."
        withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
          sh "docker login --username ${DOCKER_USERNAME} --password ${DOCKER_PASSWORD}"
        }
        sh "docker push robertbrem/belimo-frontend:${env.VERSION}"
      }
    }

    stage('deploy') {
      sh "sed -i -e 's~image: robertbrem/belimo-frontend:todo~image: robertbrem/belimo-frontend:${env.VERSION}/' kubeconfig.yml"
      container('kubectl') {
        sh "kubectl apply -f kubeconfig.yml"
      }
    }
  }
}
