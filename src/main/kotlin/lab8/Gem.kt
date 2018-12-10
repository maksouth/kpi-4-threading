package lab8

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement
data class GemCollection(
        @JsonProperty("")
        val gems: MutableList<Gem> = mutableListOf()
) {
    @JsonSetter
    fun setGem(gem: Gem) {
        gems.add(gem)
    }
}

data class Gem(
        val id: String = EMPTY,
        val name: String =EMPTY,
        val preciousness: String = EMPTY,
        val origin: String = EMPTY,
        val visualParameters: VisualParameters = EMPTY_VISUAL_PARAMETERS,
        val value: Int = 0
)

data class VisualParameters(
        val color: String = EMPTY,
        val opacity: Short = 0,
        val faces: Short = 0
)

private val EMPTY_VISUAL_PARAMETERS = VisualParameters()
private const val EMPTY = ""