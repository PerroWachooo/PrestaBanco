pipeline {
    agent any
    tools {
        maven "maven"
    }
    stages {
        stage("Checkout") {
            steps {
                bat '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
                
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/PerroWachooo/PrestaBanco']])
            }
        }
        stage('Build JAR File') {
            steps {
                bat 'build-maven.bat'
            }
        }
        stage("Test") {
            steps {
                dir("backend/demo") {
                    bat "mvn test" // Usa 'sh' si estás en un entorno Unix/Linux
                }
            }
        }
        stage("Build and Push Docker Image") {
            steps {
                dir("backend/demo") {
                    script {
                        withDockerRegistry(credentialsId: 'docker-credentials') {
                            bat "docker build -t bastianbrito/spring-image ." // Usa 'sh' si estás en un entorno Unix/Linux
                            bat "docker push bastianbrito/spring-image" // Usa 'sh' si estás en un entorno Unix/Linux
                        }
                    }
                }
            }
        }
    }
}