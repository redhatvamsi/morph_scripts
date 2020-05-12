
import java.nio.file.Files

def call(config){
    println "Printing from Pipeline call"
    node {
        dir('morpheus-scripts'){
        git url: 'http://gitlab.example.com/cloud-team/morpheus-scripts.git',
            credentialsId: "jenkins-gitlab", branch: 'master'
        
        def files = findFiles(glob: 'dev/*.sh') 
        files.each { item ->  
            println item.name+'\n'
            def data = readFile(file: 'dev/'+item.name)
            writeFile(file: 'prod/'+item.name, text: data)
         } 
        try {
          sh "git remote -v"
          sh "git config user.email 'jenkins@example.com'"
          sh "git config user.name 'redhatvamsi'"
          sshagent(credentials: ['jenkins-gitlab']) {
              //sh "cd morpheus-scripts"
              sh "git add --all"
              sh "git commit -m 'update through pipeline'"
              sh('git push origin master')
          }
      } catch (e) {
          //could be a noop
         }
        }
    }    
}
