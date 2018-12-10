package lab8

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GemConverterTest {

    private lateinit var gemConverter: GemConverter

    @Before
    fun setUp() {
        val schemaValidator = SchemaValidator(SchemaProvider(salamiXSD))
        gemConverter = GemConverter(schemaValidator)
    }

    @Test fun `valid xml is converted to gem collection`() {

        val gemCollectionFromXml = gemConverter.fromXML(xml)

        Assert.assertEquals(gemCollection, gemCollectionFromXml)
    }


    @Test fun `gem collection is converted to json`() {
        Assert.assertEquals(json, gemConverter.toJSON(gemCollection))
    }

    val gemCollection: GemCollection by lazy {
        val topaz = Gem(
                id = "T1",
                name = "Topaz",
                preciousness = "precious",
                origin = "Egypt",
                visualParameters = VisualParameters(
                        color = "green",
                        opacity = 25,
                        faces = 5
                ),
                value = 25
        )
        val almaz = Gem(
                id = "A1",
                name = "Almaz",
                preciousness = "precious",
                origin = "Brazil",
                visualParameters = VisualParameters(
                        color = "red",
                        opacity = 78,
                        faces = 15
                ),
                value = 8
        )
        val gemsList = mutableListOf(topaz, almaz)
        GemCollection(gemsList)
    }
}

val json = """
{"gems":[{"id":"T1","name":"Topaz","preciousness":"precious","origin":"Egypt","visual_parameters":{"color":"green","opacity":25,"faces":5},"value":25},{"id":"A1","name":"Almaz","preciousness":"precious","origin":"Brazil","visual_parameters":{"color":"red","opacity":78,"faces":15},"value":8}]}
""".trimIndent()

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
