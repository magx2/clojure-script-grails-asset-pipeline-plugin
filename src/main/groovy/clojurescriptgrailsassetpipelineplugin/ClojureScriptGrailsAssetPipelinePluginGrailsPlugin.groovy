package clojurescriptgrailsassetpipelineplugin

import grails.plugins.*

class ClojureScriptGrailsAssetPipelinePluginGrailsPlugin extends Plugin {
    def grailsVersion = "3.0.9 > *"
    def pluginExcludes = []

    def title = "Clojure Script Grails Asset Pipeline Plugin"
    def author = "Martin Grześlowski (MAGx2)"
    def authorEmail = "martin.grzeslowski@gmail.com"
    def description = '''\
Asset Pipeline plugin to compile ClojureScript into JavaScript.
'''
    def documentation = "https://github.com/magx2/clojure-script-grails-asset-pipeline-plugin"
    def license = "MIT"
    def developers = [ [ name: "Martin Grześlowski (MAGx2)", email: "martin.grzeslowski@gmail.com" ]]
    def issueManagement = [ system: "GitHub", url: "https://github.com/magx2/clojure-script-grails-asset-pipeline-plugin/issues" ]
    def scm = [ url: "https://github.com/magx2/clojure-script-grails-asset-pipeline-plugin/" ]

    Closure doWithSpring() { {->
        // TODO Implement runtime spring config (optional)
    }
    }

    void doWithDynamicMethods() {
        // TODO Implement registering dynamic methods to classes (optional)
    }

    void doWithApplicationContext() {
        // TODO Implement post initialization spring config (optional)
    }

    void onChange(Map<String, Object> event) {
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    void onConfigChange(Map<String, Object> event) {
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    void onShutdown(Map<String, Object> event) {
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
