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
				"(defn plus [a b] (+ a b))",
				"(+ 1 2)",
				"""(defn plus [a b] (+ a b))
(defn minus [a b] (- a b))"""
		]
		parsedClojureScript << [
				"cljs.user.plus = (function cljs\$user\$plus(a,b){\nreturn (a + b);\n});\n",
				"((1) + (2));\n",
				"cljs.user.plus = (function cljs\$user\$plus(a,b){\nreturn (a + b);\n});\n" +
						"cljs.user.minus = (function cljs\$user\$minus(a,b){\nreturn (a - b);\n});\n"
		]
	}

	void "should parse one statement"() {
		given:
		final statement = "(def pi 3.14)"

		when:
		final parsed = processor.parseStatement(statement)

		then:
		parsed.size() == 1
		parsed[0] == statement
	}

	void "should parse one statement (with one statement inside)"() {
		given:
		final statement = "(defn plus [a b] (+ a b))"

		when:
		final parsed = processor.parseStatement(statement)

		then:
		parsed.size() == 1
		parsed[0] == statement
	}

	void "should parse two statements (with two statement inside)"() {
		given:
		final statement = "(defn plus [a b] (+ a b))(defn minus [a b] (plus a (- b b)))"

		when:
		final parsed = processor.parseStatement(statement)

		then:
		parsed.size() == 2
		parsed[0] == "(defn plus [a b] (+ a b))"
		parsed[1] == "(defn minus [a b] (plus a (- b b)))"
	}

	void "should parse two statements"() {
		given:
		final statement = "(def pi 3.14)(def one 1)"

		when:
		final parsed = processor.parseStatement(statement)

		then:
		parsed.size() == 2
		parsed[0] == "(def pi 3.14)"
		parsed[1] == "(def one 1)"
	}

	void "should parse three statements (and one enter)"() {
		given:
		final statement = "(def pi 3.14)(def one 1)\n(def two 2)"

		when:
		final parsed = processor.parseStatement(statement)

		then:
		parsed.size() == 3
		parsed[0] == "(def pi 3.14)"
		parsed[1] == "(def one 1)\n"
		parsed[2] == "(def two 2)"
	}
}
