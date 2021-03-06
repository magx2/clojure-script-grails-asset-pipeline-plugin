package asset.pipeline.clj

import asset.pipeline.CacheManager
import asset.pipeline.AbstractAssetFile
import asset.pipeline.AssetHelper
import java.util.regex.Pattern

class ClojureScriptAssetFile extends AbstractAssetFile {
	static final contentType = ['application/javascript','application/x-javascript','text/javascript']
	static extensions = ['clj', 'cljs']
	static final String compiledExtension = 'js'
	static processors = [ClojureScriptProcessor]
	Pattern directivePattern = ~/(?m)#=(.*)/
}
