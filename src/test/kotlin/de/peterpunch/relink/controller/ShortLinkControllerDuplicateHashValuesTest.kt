package de.peterpunch.relink.controller

import com.fasterxml.jackson.databind.ObjectMapper
import de.peterpunch.relink.BaseIntegrationTest
import de.peterpunch.relink.domain.service.HashGenerator
import de.peterpunch.relink.dto.CreateShortLinkDto
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.net.URI

class ShortLinkControllerDuplicateHashValuesTest @Autowired constructor(
    val objectMapper: ObjectMapper
) : BaseIntegrationTest() {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ShortLinkControllerDuplicateHashValuesTest::class.java)
    }

    @MockitoBean
    private lateinit var hashGenerator: HashGenerator

    @Test
    @Sql("/database/clear.sql")
    fun `postShortLink returns 201 CREATED and handle duplicate hash values`() {

        Mockito.`when`(hashGenerator.generate()).thenReturn("ABCD", "1234")

        val createShortLinkDto = CreateShortLinkDto(URI.create("http://example.com").toURL())
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
            .andExpect(MockMvcResultMatchers.jsonPath("\$.url").value("http://example.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.hash").value(Matchers.hasLength(4)))
    }
}
