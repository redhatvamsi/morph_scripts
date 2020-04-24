
import java.nio.file.Files

def call(config){
    println "Printing from Pipeline call"
    node {
        checkout([$class: 'GitSCM', 
          branches: [[name: '*/master']], 
          userRemoteConfigs: [[credentialsId: 'jenkins-gitlab', url: 'http://gitlab.example.com/redhatvamsi/morpheus-scripts.git']]])
        
        def files = findFiles(glob: 'dev/*.sh') 
        files.each { item ->  
            println item.name+'\n'
            def data = readFile(file: 'dev/'+item.name)
            writeFile(file: 'prod/'+item.name, text: data)
         }        
        githubUpdate(config, 'update repo with changes') 
    }    
}