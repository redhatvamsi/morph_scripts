def call(commitMessage) {
    dir('morpheus-pipeline-library') {
      git url: 'git@gitlab.example.com:openshift/example-inventory.git',
            credentialsId: "bitbucketsecret", branch: 'master'
    }
  
    dir('morpheus-pipeline-library') {
      def noOp = true
      try {
          sh "git commit -am '${commitMessage}'"
          noOp = false
      } catch (e) {
          //could be a noop
      }
      if (!noOp) {
          sh "git remote -v"
          sshagent(credentials: ['git-automation']) {
              sh('git push origin master')
          }
      }
    }
}

return this

inventoryUpdate(' morpheus tasks list updated')
