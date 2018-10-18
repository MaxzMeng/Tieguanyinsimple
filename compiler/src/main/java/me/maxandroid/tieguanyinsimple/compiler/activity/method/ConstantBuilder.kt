package me.maxandroid.tieguanyinsimple.compiler.activity.method


import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeSpec
import me.maxandroid.tieguanyinsimple.compiler.activity.ActivityClass
import me.maxandroid.tieguanyinsimple.compiler.activity.entity.Field
import me.maxandroid.tieguanyinsimple.compiler.utils.camelToUnderline
import javax.lang.model.element.Modifier.*

class ConstantBuilder(private val activityClass: ActivityClass) {
    fun build(typeBuilder: TypeSpec.Builder) {
        activityClass.fields.forEach { field: Field ->
            typeBuilder.addField(
                FieldSpec.builder(
                    String::class.java, field.prefix + field.name.camelToUnderline().toUpperCase(),
                    PUBLIC, STATIC, FINAL
                ).initializer("\$S", field.name)
                    .build())
        }
    }
}