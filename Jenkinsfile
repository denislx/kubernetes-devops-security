pipeline {
  agent any

//  environment {
//    dockerhub=credentials('dockerhub')
//  }

  stages {
    stage('Build Artifact') {
      steps {
        sh "mvn clean package -DskipTests=true"
        archiveArtifacts 'target/*.jar'
      }
    }   

    stage('Unit Tests - JUnit and Jacoco') {
      steps {
        sh "mvn test"
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml'
          jacoco execPattern: 'target/jacoco.exec'
        }
      }
    }

    stage('Docker image build and push') {
      steps {
        sh 'docker build -t denislx/java-app:latest .'
        sh 'echo PWS: $dockerhub_PWS'
        sh 'echo USR: $dockerhub_USR'
        sh 'echo $dockerhub_PWS | docker login -u $dockerhub_USR --password-stdin'
        sh 'docker push denislx/java-app:latest'
      }
    }
  }
}
