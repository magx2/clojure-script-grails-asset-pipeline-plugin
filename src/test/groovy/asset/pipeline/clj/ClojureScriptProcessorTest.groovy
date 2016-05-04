package asset.pipeline.clj

import spock.lang.Specification

class ClojureScriptProcessorTest extends Specification {
	final processor = new ClojureScriptProcessor(null)

	void "should compile clojure script"() {
		when:
		final javaScript = processor.process(clojureScript, null)

		then:
		javaScript == parsedClojureScript

		where:
		clojureScript << [
				"(ns test.ns)(defn plus [a b] (+ a b))",
				"(+ 1 2)",
				"""(defn plus [a b] (+ a b))
(defn minus [a b] (- a b))"""
		]
		parsedClojureScript << [
				"""\
goog.provide('test.ns');
goog.require('cljs.core');
test.ns.plus = (function test\$ns\$plus(a,b){
return (a + b);
});
""",
				"((1) + (2));\n",
				"cljs.user.plus = (function cljs\$user\$plus(a,b){\nreturn (a + b);\n});\n" +
						"cljs.user.minus = (function cljs\$user\$minus(a,b){\nreturn (a - b);\n});\n"
		]
	}
}
