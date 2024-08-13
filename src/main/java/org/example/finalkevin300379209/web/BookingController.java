package org.example.finalkevin300379209.web;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.finalkevin300379209.entities.Booking;
import org.example.finalkevin300379209.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class BookingController {
    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping(path = "/")
    public String index(Model model){
        List<Booking> bookings = bookingRepository.findAll();

        int seatsLeft = 20;
        List<ArrayList> seats = new ArrayList<>();
        List<String> rowHeader = new ArrayList<>();
        rowHeader.add("A");
        rowHeader.add("B");
        rowHeader.add("C");
        rowHeader.add("D");
        rowHeader.add("E");

        for(int row=1; row<=4;row++){
            ArrayList<String> tmp = new ArrayList<>();
            for(int col=0; col<rowHeader.size(); col++){
                boolean booked = false;
                String seatNo = String.valueOf(row) + rowHeader.get(col);

                //check if the seats booked
                for(int i=0; i<bookings.size(); i++){
                    if(bookings.get(i).getSeatNo().equals(seatNo)){
                        tmp.add(bookings.get(i).getName());
                        booked = true;
                        seatsLeft--;
                    }
                }

                if(!booked){
                    tmp.add(seatNo);
                }
            }

            seats.add(tmp);
        }

        model.addAttribute("seats", seats);
        model.addAttribute("seatsLeft", seatsLeft);
        model.addAttribute("bookings", bookings);
        model.addAttribute("booking", new Booking());

        return "index";
    }

    @PostMapping(path = "/book")
    public String addCustomer(Model model, Booking booking, BindingResult bindingResult, ModelMap mm, HttpSession session){
        if(bindingResult.hasErrors()){
            return "index";
        }
        else {
            booking.setBooked(true);
            bookingRepository.save(booking);
        }

        return "redirect:/";
    }

    @GetMapping(path = "/edit")
    public String editPage(Model model, int number) {
        Booking booking = bookingRepository.findById(number).orElse(null);

        if(booking != null){
            model.addAttribute("booking", booking);
            return "edit";
        }
        else{
            return "redirect:/";
        }
    }

    @PostMapping(path = "/edit")
    public String editSave(Model model, Booking booking, BindingResult bindingResult, ModelMap mm, HttpSession session){
        if(bindingResult.hasErrors()){
            return "addCustomer";
        }

        Booking bookingExisting = bookingRepository.findById(booking.getId()).orElse(null);

        if(bookingExisting!= null){
            bookingExisting.setDate(booking.getDate());
            bookingExisting.setSeatNo(booking.getSeatNo());
            bookingExisting.setName(booking.getName());

            bookingRepository.save(bookingExisting);
        }

        return "redirect:/";
    }

    @GetMapping(path = "/delete")
    public String deleteBooking(Model model, int number) {
        Booking booking = bookingRepository.findById(number).orElse(null);

        if(booking != null){
            bookingRepository.delete(booking);
        }

        return "redirect:/";
    }
}
