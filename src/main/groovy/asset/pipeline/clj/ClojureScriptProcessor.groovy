package asset.pipeline.clj


import asset.pipeline.AssetFile
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable
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
		return null
	}
}
