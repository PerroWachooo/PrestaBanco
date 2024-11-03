pipeline {
    agent any
    tools {
        maven "maven"
    }
    stages {
        stage("Build JAR File") {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/PerroWachooo/PrestaBanco']])
                dir("backend/demo") {
                    bat "mvn clean install"
                }
            }
        }
        stage("Test") {
            steps {
                dir("backend/demo") {
                    bat "mvn test"
                }
            }
        }        
        stage("Build and Push Docker Image") {
            steps {
                dir("backend/demo") {
                    script {
                        bat "docker context use default"
                        withDockerRegistry(credentialsId: 'docker-credentials') {
                            bat "docker build -t bastianbrito/spring-image ."
                            bat "docker push bastianbrito/spring-image"
                        }
                    }
                }
            }
        }
    }
}