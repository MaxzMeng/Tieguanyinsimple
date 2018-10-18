package me.maxandroid.tieguanyinsimple.compiler

import com.bennyhuo.aptutils.AptContext
import com.bennyhuo.aptutils.logger.Logger
import com.bennyhuo.aptutils.types.isSubTypeOf
import com.sun.tools.javac.code.Symbol
import me.maxandroid.tieguanyinsimple.annotations.Builder
import me.maxandroid.tieguanyinsimple.annotations.Optional
import me.maxandroid.tieguanyinsimple.annotations.Required
import me.maxandroid.tieguanyinsimple.compiler.activity.ActivityClass
import me.maxandroid.tieguanyinsimple.compiler.activity.entity.Field
import me.maxandroid.tieguanyinsimple.compiler.activity.entity.OptionalField
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement

class BuilderProcessor : AbstractProcessor() {
    private val supportAnnotations = setOf(Builder::class.java, Optional::class.java, Required::class.java)

    override fun getSupportedSourceVersion() = SourceVersion.RELEASE_7

    override fun getSupportedAnnotationTypes() = supportAnnotations.mapTo(HashSet<String>(), Class<*>::getCanonicalName)

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        AptContext.init(processingEnv)
    }

    override fun process(annotations: MutableSet<out TypeElement>, env: RoundEnvironment): Boolean {

        val activityClasses = HashMap<Element, ActivityClass>()
        env.getElementsAnnotatedWith(Builder::class.java)
            .filter { it.kind.isClass }
            .forEach { element: Element ->
                try {
                    if (element.asType().isSubTypeOf("android.app.Activity")) {
                        activityClasses[element] = ActivityClass(element as TypeElement)
                    } else {
                        Logger.error(element, "Unsupported typeElement:${element.simpleName}")
                    }
                } catch (e: Exception) {
                    Logger.logParsingError(element, Builder::class.java, e)
                }
            }
        env.getElementsAnnotatedWith(Required::class.java)
            .filter { it.kind == ElementKind.FIELD }
            .forEach { element: Element ->
                activityClasses[element.enclosingElement]?.fields?.add(Field(element as Symbol.VarSymbol))
                    ?: Logger.error(element, "Field $element annotated as Required while ${element.enclosingElement} not annotated")
            }

        env.getElementsAnnotatedWith(Optional::class.java)
            .filter {
                it.kind == ElementKind.FIELD
            }.forEach { element: Element ->
                activityClasses[element.enclosingElement]?.fields?.add(OptionalField(element as Symbol.VarSymbol))
                    ?: Logger.error(
                        element,
                        "Field $element annotated as Required while ${element.enclosingElement} not annotated"
                    )
            }

        activityClasses.values.forEach {
            it.builder.build(AptContext.filer)
        }
        return true
    }
}