pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh '''
                docker build -t zebra779/duo:latest -t zebra779/duo:build-$BUILD_NUMBER .
                '''
            }
        }
        stage('Push') {
            steps {
                sh ''' 
                docker push zebra779/duo:latest
                docker push zebra779/duo:build-$BUILD_NUMBER
                '''
            }
        }
        stage('Deploy') {
            steps {
                sh '''
                ssh -i "~/.ssh/id_rsa" jenkins@35.228.153.41 << EOF
                docker stop duo
                docker rm duo
                docker rmi zebra779/duo:latest
                docker run -d -p 80:5500 --name duo zebra779/duo:latest
                '''
            }
        }
    }
}