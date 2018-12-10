package lab8

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.dataformat.xml.XmlMapper

class GemConverter(private val schemaValidator: SchemaValidator) {

    private val xmlMapper: XmlMapper = XmlMapper()
    private val jsonMapper = ObjectMapper();

    init {
        xmlMapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
        jsonMapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
    }

    fun fromXML(xml: String): GemCollection {
        if (!schemaValidator.validate(xml.byteInputStream()))
            throw IllegalArgumentException("Provided xml do not pass schema validation")

        return xmlMapper.readValue(xml, GemCollection::class.java)
    }

    fun toJSON(gems: GemCollection): String {
        return jsonMapper.writeValueAsString(gems)
    }

}