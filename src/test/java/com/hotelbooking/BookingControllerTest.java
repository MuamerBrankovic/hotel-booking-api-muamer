//package com.example.hotel_booking_api;
package com.hotelbooking;

import com.hotelbooking.dto.BookingRequest;
import com.hotelbooking.dto.BookingResponse;
import com.hotelbooking.security.JwtUtil;
import com.hotelbooking.service.BookingService;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
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

    @Test
    @WithMockUser(roles = "USER")
    void shouldCreateBooking() throws Exception{
        //Arrange
        String jsonRequest = """
        {
          "guest": {"name": "Muamer", "email": "test@test.com"},
          "numberOfGuests": 1,
          "roomType": "DOUBLE",
          "checkInDate": "2026-03-01",
          "checkOutDate": "2026-03-05"
        }
        """;
        //Act & Assert
        when(bookingService.createBooking(any(BookingRequest.class))).thenReturn(new BookingResponse());
        mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated());
    }
}
