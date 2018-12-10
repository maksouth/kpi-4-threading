package lab8

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import java.io.File
import java.io.InputStream
import java.lang.Exception
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory
import javax.xml.validation.Validator

class SchemaValidator(schemaStream: InputStream) {

    var validator: Validator

    init {
        val schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
        val schema = schemaFactory.newSchema(StreamSource(schemaStream))
        validator = schema.newValidator()
    }

    fun validate(xml: InputStream): Boolean =
            try {
                validator.validate(StreamSource(xml))
                true
            } catch (e: Exception) {
                println("Exception while validating schema: $e")
                e.printStackTrace()
                false
            }
}


fun main(args: Array<String>) {
    /*
     * 1. Convert String XML to Object
     */
    val xmlString = 	"<customer>" +
            "  <name>Mary</name>" +
            "  <age>37</age>" +
            "  <my_address>" +
            "    <street>NANTERRE CT</street>" +
            "    <postcode>77471</postcode>" +
            "  </my_address>" +
            "</customer>"

    val gemXml = """
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

    val customer = convertXmlString2DataObject(gemXml, GemCollection::class.java)

    println(customer)
    // -> Customer[name=Mary, age=37, MyAddress[street=NANTERRE CT, postcode=77471]]
}

fun convertXmlString2DataObject(xmlString: String, cls: Class<*>): Any {
    val xmlMapper = XmlMapper()
    xmlMapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
    return xmlMapper.readValue(xmlString, cls)
}

fun convertXmlFile2DataObject(pathFile: String, cls: Class<*>): Any{
    val xmlMapper = XmlMapper()
    xmlMapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
    return xmlMapper.readValue(File(pathFile), cls)
}

data class Customer(
        val name: String = "",
        val age: Int = -1,
        val myAddress: MyAddress? = null
)

data class MyAddress(
        val street: String = "",
        val postcode: String = ""
)