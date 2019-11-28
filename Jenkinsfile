pipeline {
	agent any
	
	stages {
		stage ('Build Stage') {
			steps {
				withMaven(maven : 'MAVEN_HOME') {
					bat 'mvn clean install'
				}
			}
		}
		stage ('Unit Test Stage') {
			steps {
				withMaven(maven : 'MAVEN_HOME') {
					bat 'mvn clean install -P unit-test'
				}
			}
		}
		stage ('Integration Test Stage') {
			steps {
				withMaven(maven : 'MAVEN_HOME') {
					bat 'mvn clean install -P integration-test'
				}
			}
		}
		stage ('Deployment Stage') {
			steps {
				withCredentials([[$class          : 'UsernamePasswordMultiBinding',
                                  credentialsId   : 'Cloud foundry',
                                  usernameVariable: 'USERNAME',
                                  passwordVariable: 'PASSWORD']]) {
                    			bat 'cf login -a https://api.cf.sap.hana.ondemand.com -u %USERNAME% -p %PASSWORD%'
					bat 'cf push'
                		}
			}
		}
	}
}
