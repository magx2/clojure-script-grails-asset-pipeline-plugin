package asset.pipeline.clj


import asset.pipeline.AssetFile
import clojure.lang.IFn
import asset.pipeline.AbstractProcessor
import asset.pipeline.AssetCompiler

import static clojure.java.api.Clojure.read
import static clojure.java.api.Clojure.var
import static java.lang.Thread.currentThread

class ClojureScriptProcessor extends AbstractProcessor {
	private final IFn compile
	private final IFn inputs

	/**
	 * Constructor for building a Processor
	 *
	 * @param precompiler - An Instance of the AssetCompiler class compiling the file or NULL for dev mode.
	 */
	ClojureScriptProcessor(AssetCompiler precompiler) {
		super(precompiler)

		// FIX with class loader: http://dev.clojure.org/jira/browse/CLJ-260?focusedCommentId=23453&page=com.atlassian.jira.plugin.system.issuetabpanels:comment-tabpanel#comment-23453
		currentThread().setContextClassLoader(this.class.classLoader)

		var("clojure.core", "require")
				.invoke(read("cljs.build.api"))

		compile = var("cljs.build.api", "compile")
		inputs = var("cljs.build.api", "inputs")
	}

	@Override
	String process(String inputText, AssetFile assetFile) {
		final file = createTmpFile(assetFile, inputText)
		final compilable = getCompilable(file.absolutePath)
		final javaScripts = getJavaScripts(compilable)

		if (javaScripts.size() != 1) {
			throw new IllegalStateException("Compiled list need to have only one element! Was ${javaScripts.size()}.")
		}

		javaScripts[0]
	}

	private def getJavaScripts(compilable) {
		try {
			compile.invoke(
					read("{}"),
					compilable
			)
		} catch (Error e) {
			throw new RuntimeException(e)
		}
	}

	private def getCompilable(path) {
		try {
			inputs.invoke(read("\"$path\""))
		} catch (Error e) {
			throw new RuntimeException(e)
		}
	}

	private static File createTmpFile(AssetFile assetFile, String inputText) {
		final file = File.createTempFile(assetFile?.path ?: 'Tmp', '.cljs')
		file.withWriter { writer -> writer.write(inputText) }
		file
	}
}
