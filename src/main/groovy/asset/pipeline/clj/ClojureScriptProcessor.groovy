package asset.pipeline.clj


import asset.pipeline.AssetFile
import clojure.lang.IFn
import asset.pipeline.AbstractProcessor
import asset.pipeline.AssetCompiler

import static clojure.java.api.Clojure.read
import static clojure.java.api.Clojure.var
import static java.lang.Thread.currentThread

class ClojureScriptProcessor extends AbstractProcessor {
	private final IFn emptyEnv
	private final IFn analyze
	private final IFn emit

	/**
	 * Constructor for building a Processor
	 *
	 * @param precompiler - An Instance of the AssetCompiler class compiling the file or NULL for dev mode.
	 */
	ClojureScriptProcessor(AssetCompiler precompiler) {
		super(precompiler)

		// FIX with class loader: http://dev.clojure.org/jira/browse/CLJ-260?focusedCommentId=23453&page=com.atlassian.jira.plugin.system.issuetabpanels:comment-tabpanel#comment-23453
		currentThread().setContextClassLoader(this.class.classLoader)

		var("clojure.core", "require").with {
			invoke(read("cljs.analyzer.api"))
			invoke(read("cljs.compiler.api"))
		}

		emptyEnv = var("cljs.analyzer.api", "empty-env")
		analyze = var("cljs.analyzer.api", "analyze")
		emit = var("cljs.compiler.api", "emit")
	}

	@Override
	String process(String inputText, AssetFile assetFile) {
		parseStatement(inputText).collect { statement -> emit(statement) }.join("")
	}

	private Object emit(String statement) {
		try {
			emit.invoke(
					analyze.invoke(
							emptyEnv.invoke(), read(statement)
					)
			)
		} catch (Exception e) {
			throw new IllegalArgumentException("Statement \"$statement\" is broken!")
		}
	}

	private static List<String> parseStatement(String inputText) {
		def open = 0
		inputText.inject([]) { List<String> statements, letter ->
			if (letter == "(") {
				if (open == 0) statements.add("")
				open++
			} else if (letter == ")") {
				open--
			}
			statements[statements.size() - 1] = statements.last() + letter
			statements
		} as List<String>
	}
}
