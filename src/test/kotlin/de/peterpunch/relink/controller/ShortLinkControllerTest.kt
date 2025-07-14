package de.peterpunch.relink.controller

import com.fasterxml.jackson.databind.ObjectMapper
import de.peterpunch.relink.BaseIntegrationTest
import de.peterpunch.relink.dto.CreateShortLinkDto
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.net.URI

class ShortLinkControllerTest @Autowired constructor(
    val objectMapper: ObjectMapper
) : BaseIntegrationTest() {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ShortLinkControllerTest::class.java)
    }

    @Test
    @Sql("/database/clear.sql", "/database/add-short-link.sql")
    fun `postShortLink returns 201 CREATED`() {

        val createShortLinkDto = CreateShortLinkDto(URI.create("https://example.com").toURL())
        logger.info(objectMapper.writeValueAsString(createShortLinkDto))
        mockMvc.perform(
            MockMvcRequestBuilders.post("/relink/api/short-links")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(createShortLinkDto))
                .accept(MediaType.APPLICATION_JSON_VALUE)

        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.url").value("https://example.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.hash").value(Matchers.hasLength(4)))
    }

    @Test
    @Sql("/database/clear.sql", "/database/add-short-link.sql")
    fun `getShortLink returns 200 OK`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/relink/api/short-links/ABCD"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.url").value("https://example.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.hash").value(Matchers.hasLength(4)))

    }

    @Test
    @Sql("/database/clear.sql", "/database/add-short-link.sql")
    fun `getShortLink returns 404 Not Found`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/relink/api/short-links/1234"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.status").value(404))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("ShortLink with hash 1234 not found"))
    }
}
