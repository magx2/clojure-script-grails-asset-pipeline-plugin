package asset.pipeline.clj

import spock.lang.Specification

class ClojureScriptProcessorTest extends Specification {

	void "should compile clojure script"() {
		given:
		final processor = new ClojureScriptProcessor(null)

		when:
		final javaScript = processor.process(clojureScript, null)

		then:
		println "out> [$javaScript]"
		true

		where:
		clojureScript << [
		        "(defn plus [a b] (+ a b))",
		        "(ns hello-world.core) (enable-console-print!) (defn plus [a b] (+ a b)) (println (plus 1 2))",
				"(ns hello-world.core) (enable-console-print!) (println (+ 1 2))",
				"(+ 1 2)"
		]
	}
}
