package asset.pipeline.clj

import spock.lang.Specification

class ClojureScriptProcessorTest extends Specification {

	void "should compile clojure script"() {
		given:
		final processor = new ClojureScriptProcessor(null)

		when:
		final javaScript = processor.process(clojureScript, null)

		then:
		javaScript == parsedClojureScript

		where:
		clojureScript << [
		        "(defn plus [a b] (+ a b))",
				"(+ 1 2)"
		]
		parsedClojureScript << [
		        "cljs.user.plus = (function cljs\$user\$plus(a,b){\n" +
						"return (a + b);\n" +
						"});\n",
				"((1) + (2));\n"
		]
	}
}
