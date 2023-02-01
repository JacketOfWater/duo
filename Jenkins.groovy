pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh '''
                docker build -t jacketofwater/duo:latest -t jacketofwater/duo:build-$BUILD_NUMBER .
                '''
            }
        }
        stage('Push') {
            steps {
                sh ''' 
                docker push jacketofwater/duo:latest
                docker push jacketofwater/duo:build-$BUILD_NUMBER
                '''
            }
        }
        stage('Deploy') {
            steps {
                sh '''
                ssh -i "~/.ssh/id_rsa" jenkins@35.228.153.41 << EOF
                docker stop duo
                docker rm duo
                docker rmi jacketofwater/duo:latest
                docker run -d -p 80:5500 --name duo jacketofwater/duo:latest
                '''
            }
        }
    }
}