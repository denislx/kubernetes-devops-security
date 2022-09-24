pipeline {
  agent any

  environment {
    registry = "denislx/java-app"
    registryCredential = 'dockerhub'
    dockerImage = ''
  }

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

/*  // Alternative mathod
    stage('Docker image build and push') {
      steps {
        sh 'docker build -t $registry:latest .'

      	withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
        	sh "echo ${env.dockerHubPassword} | docker login -u ${env.dockerHubUser} --password-stdin "
          sh 'docker push $registry:latest'
        }
      }
    }
 */
    stage('Docker image build and push') {
      steps {
        docker.build registry + ":latest"
        // docker.withRegistry( '', registryCredential ) {
        //   dockerImage.push()
        // }
      }
    }
  }
}
