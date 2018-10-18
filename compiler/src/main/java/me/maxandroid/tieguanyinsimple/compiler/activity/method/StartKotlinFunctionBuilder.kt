package me.maxandroid.tieguanyinsimple.compiler.activity.method

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier.PUBLIC
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.UNIT
import me.maxandroid.tieguanyinsimple.compiler.activity.ActivityClass
import me.maxandroid.tieguanyinsimple.compiler.activity.ActivityClassBuilder
import me.maxandroid.tieguanyinsimple.compiler.activity.entity.OptionalField
import me.maxandroid.tieguanyinsimple.compiler.prebuilt.ACTIVITY_BUILDER
import me.maxandroid.tieguanyinsimple.compiler.prebuilt.CONTEXT
import me.maxandroid.tieguanyinsimple.compiler.prebuilt.INTENT

class StartKotlinFunctionBuilder(private val activityClass: ActivityClass) {

    fun build(fileBuilder: FileSpec.Builder) {
        val name = ActivityClassBuilder.METHOD_NAME + activityClass.simpleName
        val functionBuilder = FunSpec.builder(name)
                .receiver(CONTEXT.kotlin)
                .addModifiers(PUBLIC)
                .returns(UNIT)
                .addStatement("val intent = %T(this, %T::class.java)", INTENT.kotlin, activityClass.typeElement)

        activityClass.fields.forEach { field ->
            val name = field.name
            val className = field.asKotlinTypeName()
            if(field is OptionalField){
                functionBuilder.addParameter(ParameterSpec.builder(name, className).defaultValue("null").build())
            } else {
                functionBuilder.addParameter(name, className)
            }

            functionBuilder.addStatement("intent.putExtra(%S, %L)", name, name)
        }

        functionBuilder.addStatement("%T.INSTANCE.startActivity(this, intent)", ACTIVITY_BUILDER.kotlin)
        fileBuilder.addFunction(functionBuilder.build())
    }

}