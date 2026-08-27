//package com.example.hotel_booking_api;
package com.hotelbooking;

import com.hotelbooking.dto.BookingResponse;
import com.hotelbooking.security.JwtUtil;
import com.hotelbooking.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(com.hotelbooking.controller.BookingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingService bookingService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnAllBookings() throws Exception {
        //Arrange
        BookingResponse booking = new BookingResponse();
        booking.setGuestName("Muamer");

        when(bookingService.getAllBookings()).thenReturn(List.of(booking));

        //Act & Assert
        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].guestName").value("Muamer"));


    }
}
