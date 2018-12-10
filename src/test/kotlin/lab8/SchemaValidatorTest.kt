package lab8

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

const val russianDollXSD = "gem_collection.xsd"
const val salamiXSD = "gem_collection_salami.xsd"

class SchemaValidatorTest {

    lateinit var schemaValidator: SchemaValidator

    @Before
    fun setUp() {
        val schemaStream = SchemaProvider(salamiXSD)
        schemaValidator = SchemaValidator(schemaStream)
    }

    @Test fun `full xml is valid`() =
        assertTrue(schemaValidator.validate(`full xml`))

    @Test fun `full xml with numeric only id is not valid`() =
            assertFalse(schemaValidator.validate(`full xml wrong id`))

    @Ignore
    @Test fun `xml without color is valid`() =
            assertTrue(schemaValidator.validate(`full xml`))

    @Test fun `xml with yellow color is not valid`() =
            assertFalse(schemaValidator.validate(`xml with yellow color`))

    @Test fun `xml with more than 16 faces is not valid`() =
            assertFalse(schemaValidator.validate(`xml with 20 faces`))

    @Test fun `xml with unknown preciousness is not valid`() =
            assertFalse(schemaValidator.validate(`xml with unknown preciousness`))

    @Test fun `xml with negative value is not valid`() =
            assertFalse(schemaValidator.validate(`xml with negative value`))

    @Test fun `xml with opacity greater than 100 is not valid`() =
            assertFalse(schemaValidator.validate(`xml with opacity 123`))

}

val `full xml` = """
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
""".trimIndent().byteInputStream()

val `xml with negative value` = """
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
            <value>-25</value>
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
""".trimIndent().byteInputStream()

val `xml with 20 faces` = """
    <gem_collection>
        <gem id="T1">
            <name>Topaz</name>
            <preciousness>precious</preciousness>
            <origin>Egypt</origin>
            <visual_parameters>
                <color>green</color>
                <opacity>25</opacity>
                <faces>20</faces>
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
""".trimIndent().byteInputStream()

val `full xml wrong id` = """
    <gem_collection>
        <gem id="12345">
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
        <gem id="3456">
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
""".trimIndent().byteInputStream()

val `xml with yellow color` = """
    <gem_collection>
        <gem id="T1">
            <name>Topaz</name>
            <preciousness>precious</preciousness>
            <origin>Egypt</origin>
            <visual_parameters>
                <color>yellow</color>
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
""".trimIndent().byteInputStream()

val `xml with opacity 123` = """
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
                <opacity>123</opacity>
                <faces>15</faces>
            </visual_parameters>
            <value>8</value>
        </gem>
    </gem_collection>
""".trimIndent().byteInputStream()

val `xml with unknown preciousness` = """
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
            <preciousness>unknown</preciousness>
            <origin>Brazil</origin>
            <visual_parameters>
                <color>red</color>
                <opacity>78</opacity>
                <faces>15</faces>
            </visual_parameters>
            <value>8</value>
        </gem>
    </gem_collection>
""".trimIndent().byteInputStream()

