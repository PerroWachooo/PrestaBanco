pipeline {
    agent any
    tools {
        maven "maven"
    }
    stages {
        stage("Checkout") {
            steps {
                
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/PerroWachooo/PrestaBanco']])
            }
        }
        stage('Build JAR File') {
    steps {
        bat '''
            echo Directorio actual antes del CD:
            cd
            
            echo Cambiando al directorio backend\\demo:
            cd backend\\demo
            
            echo Directorio actual después del CD:
            cd
            
            echo Verificando existencia de pom.xml:
            if exist pom.xml (
                echo pom.xml encontrado, procediendo con el build
                mvn clean install
            ) else (
                echo ERROR: pom.xml no encontrado en %CD%
                exit /b 1
            )
        '''
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