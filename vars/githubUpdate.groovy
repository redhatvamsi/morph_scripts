def call(config, commitMessage) {

      def noOp = true
      try {
          sh "git commit -am 'update through pipeline'"
          noOp = false
      } catch (e) {
          //could be a noop
      }
      if (!noOp) {
          //sh "git remote -v"
          sshagent(credentials: ['jenkins-gitlab']) {
              sh('git push origin master')
          }
      }
    }

return this

