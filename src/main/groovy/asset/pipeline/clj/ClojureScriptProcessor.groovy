package asset.pipeline.clj


import asset.pipeline.AssetFile
import clojure.java.api.Clojure
import clojure.lang.IFn
import asset.pipeline.AbstractProcessor
import asset.pipeline.AssetCompiler

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

		IFn require = Clojure.var("clojure.core", "require");
		require.invoke(Clojure.read("cljs.analyzer.api"))
		require.invoke(Clojure.read("cljs.compiler.api"))

		emptyEnv = Clojure.var("cljs.analyzer.api", "empty-env")
		analyze = Clojure.var("cljs.analyzer.api", "analyze")
		emit = Clojure.var("cljs.compiler.api", "emit")
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
