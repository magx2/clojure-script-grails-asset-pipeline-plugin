package asset.pipeline.clj


import asset.pipeline.AssetFile
import clojure.lang.IFn
import asset.pipeline.AbstractProcessor
import asset.pipeline.AssetCompiler

import static clojure.java.api.Clojure.read
import static clojure.java.api.Clojure.var

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

		IFn require = var("clojure.core", "require")
		require.invoke(read("cljs.analyzer.api"))
		require.invoke(read("cljs.compiler.api"))

		emptyEnv = var("cljs.analyzer.api", "empty-env")
		analyze = var("cljs.analyzer.api", "analyze")
		emit = var("cljs.compiler.api", "emit")
	}

	@Override
	String process(String inputText, AssetFile assetFile) {
		emit.invoke(
				analyze.invoke(
						emptyEnv.invoke(), "'$inputText".toString()
				)
		)
	}
}
