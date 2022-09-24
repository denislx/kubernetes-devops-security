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

    // // Alternative less secure method
    // stage('Docker image build and push') {
    //   steps {
    //     sh 'docker build -t $registry:latest .'

    //   	withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
    //     	sh "echo ${env.dockerHubPassword} | docker login -u ${env.dockerHubUser} --password-stdin "
    //       sh 'docker push $registry:latest'
    //     }
    //   }
    // }
 
    // stage('Docker image build and push') {
    //   steps {
    //     script {
    //       docker.withRegistry( '', registryCredential ) {
    //         dockerImage = docker.build registry + ":latest"
    //         dockerImage.push()
    //       }
    //     }
    //   }
    // }

    stage('Docker image build and push') {
      steps {
          withDockerRegistry([credentialsId: registryCredential, url: ""]) {
            sh "docker build -t $registry:latest ."
            sh "docker push $registry:latest"
          }
        }
      }
    }

    // stage('Remove Unused docker image') {
    //   steps{
    //     sh "docker rmi $registry:latest"
    //   }
    // }

    stage('Kubernetes Deployment - DEV') {
      steps {
        sh "sed -i 's#REPLACE_ME#$registry:latest#g' k8s_deployment_service.yaml"
        withKubeConfig([credentialsId: 'kubeconfig']) {
          sh "kubectl apply -f k8s_deployment_service.yaml"
        }
      }
    }    
  }
}
