import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2022.04"

project {

    vcsRoot(AppVcs)

    buildType(BuildEmpty)
    buildTypesOrder = arrayListOf(BuildEmpty)
}

object BuildEmpty : BuildType({
    name = "BuildEmpty"

    artifactRules = "build/libs/app.jar"

    vcs {
        root(AppVcs, "-:docker")

        cleanCheckout = true
    }

    steps {
        gradle {
            tasks = "clean build"
            buildFile = ""
            gradleWrapperPath = ""
        }
    }
})

object AppVcs : GitVcsRoot({
    name = "AppVcs"
    url = "https://github.com/semenova-ev/BuildActionsProject"
    branch = "master"
    checkoutPolicy = GitVcsRoot.AgentCheckoutPolicy.USE_MIRRORS
})
