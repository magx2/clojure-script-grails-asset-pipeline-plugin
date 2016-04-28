package asset.pipeline.clj


import asset.pipeline.AssetFile
import clojure.java.api.Clojure
import clojure.lang.IFn
import asset.pipeline.AbstractProcessor
import asset.pipeline.AssetCompiler

class ClojureScriptProcessor extends AbstractProcessor {
	/**
	 * Constructor for building a Processor
	 *
	 * @param precompiler - An Instance of the AssetCompiler class compiling the file or NULL for dev mode.
	 */
	ClojureScriptProcessor(AssetCompiler precompiler) {
		super(precompiler)
	}

	@Override
	String process(String inputText, AssetFile assetFile) {
		IFn require = Clojure.var("clojure.core", "require");
		require.invoke(Clojure.read("cljs.analyzer.api"))
		require.invoke(Clojure.read("cljs.compiler.api"))

		IFn emptyEnv = Clojure.var("cljs.analyzer.api", "empty-env")
		IFn analyze = Clojure.var("cljs.analyzer.api", "analyze")
		IFn emit = Clojure.var("cljs.compiler", "emit")

		emit.invoke(
				analyze.invoke(
						emptyEnv.invoke(), "'$inputText".toString()
				)
		)
	}
}
