package com.test.controller.ticket.v1;

import com.test.model.Ticket;
import com.test.repository.ticket.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.Optional;

import static com.test.controller.ticket.v1.TicketController.TICKET_CONTROLLER_MAPPING_V1;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
class TicketControllerTest {

    private static final int TICKET_ID = 200;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private TicketRepository ticketRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    //Verify that ticketController bean exists
    @Order(1)
    @Test
    public void givenWac_whenServletContext_thenItProvidesTicketController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("ticketController"));
    }

    @Test
    public void given_CheckTicketAvailabilityAPI_when_TicketExists_Expect_AvailableTrue() throws Exception {
        Ticket ticket = new Ticket(TICKET_ID);

        when(ticketRepository.findById(any())).thenReturn(Optional.of(ticket));

        mockMvc.perform(get(String.format("%s/%s", TICKET_CONTROLLER_MAPPING_V1, TICKET_ID)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("available").value(true));
    }

    @Test
    public void given_CheckTicketAvailabilityAPI_when_TicketNotExists_Expect_AvailableFalse() throws Exception {
        when(ticketRepository.findById(any())).thenReturn(Optional.empty());

        mockMvc.perform(get(String.format("%s/%s", TICKET_CONTROLLER_MAPPING_V1, TICKET_ID)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("available").value(false));
    }
}