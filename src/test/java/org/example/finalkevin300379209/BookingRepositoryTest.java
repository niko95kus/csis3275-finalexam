package org.example.finalkevin300379209;

import org.example.finalkevin300379209.entities.Booking;
import org.example.finalkevin300379209.repositories.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookingRepositoryTest {
    @MockBean
    private BookingRepository bookingRepository;

    private List<Booking> bookingList;

    @BeforeEach
    public void addTestData(){
        LocalDate today = LocalDate.now();
        bookingList = new ArrayList<>();

        bookingList.add(new Booking(1, "Kevin", "1A", true, today));
        bookingList.add(new Booking(2, "Paul", "4C", true, today));
        bookingList.add(new Booking(3, "James", "3A", true, today));
        bookingList.add(new Booking(4, "Ruth", "4DA", true, today));
    }

    @Test
    public void displayRecords(){
        Mockito.when(bookingRepository.findAll()).thenReturn(bookingList);

        assertEquals(bookingList, bookingRepository.findAll());
    }

    @Test
    public void addData(){
        Booking newBooking = new Booking(99, "test add", "1A", true, LocalDate.now());

        Mockito.when(bookingRepository.save(newBooking)).thenReturn(newBooking);

        assertEquals(newBooking, bookingRepository.save(newBooking));
    }

    @Test
    public void deleteData(){
        doNothing().when(bookingRepository).delete(bookingList.get(0));

        bookingRepository.delete(bookingList.get(0));

        verify(bookingRepository, times(1)).delete(bookingList.get(0));
    }
}
