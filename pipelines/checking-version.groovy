pipeline {
    agent any

    stages {
        stage('git version') {
            steps {
                sh "git version"
            }
        }
        stage('maven version') {
            steps {
                sh "mvn -v"
            }
        }
        stage('docker version') {
            steps {
                sh "docker -v"
            }
        }
        stage('kubernetes version') {
            steps {
                sh "kubectl version --short"
            }
        }
    }
}