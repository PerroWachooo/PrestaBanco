pipeline {
    agent any
    tools {
        maven "maven"
    }
    stages {
        stage("Build JAR File") {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/PerroWachooo/PrestaBanco']])
                dir("back/managment") {
                    bat "mvn clean install"
                }
            }
        }
        stage("Test") {
            steps {
                dir("back/managment") {
                    bat "mvn test"
                }
            }
        }        
        stage("Build and Push Docker Image") {
            steps {
                dir("back/managment") {
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