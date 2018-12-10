package lab8

import java.io.File
import java.io.InputStream
import java.lang.Exception
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory
import javax.xml.validation.Validator

val xml = """

    <gem_collection>
        <gem id="T1">
            <name>Topaz</name>
            <preciousness>precious</preciousness>
            <origin>Egypt</origin>
            <visual_parameters>
                <color>green</color>
                <opacity>25</opacity>
                <faces>5</faces>
            </visual_parameters>
            <value>25</value>
        </gem>
        <gem id="A1">
            <name>Almaz</name>
            <preciousness>precious</preciousness>
            <origin>Brazil</origin>
            <visual_parameters>
                <color>red</color>
                <opacity>78</opacity>
                <faces>15</faces>
            </visual_parameters>
            <value>8</value>
        </gem>
    </gem_collection>


""".trimIndent()

fun main(args: Array<String>) {
    val schemaStream = SchemaProvider("gem_collection.xsd")
    println(SchemaValidator(schemaStream).validate(xml.byteInputStream()))
}