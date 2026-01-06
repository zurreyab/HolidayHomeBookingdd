package com.holidaybookingproject.HolidayHomeBooking.controller;

import com.holidaybookingproject.HolidayHomeBooking.entity.Room;
import com.holidaybookingproject.HolidayHomeBooking.repository.RoomRepository;
import com.holidaybookingproject.HolidayHomeBooking.response.RoomResponse;
import com.holidaybookingproject.HolidayHomeBooking.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/auth")

public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


    @PostMapping("/add/new-room")
    public ResponseEntity<?>  addNewRoom(@RequestParam("photo") MultipartFile photo,
                             @RequestParam("roomType") String roomType,
                             @RequestParam("roomPrice") BigDecimal roomPrice) throws SQLException, IOException {
        Room savedRoom = roomService.addNewRoom(photo,roomType, roomPrice);

        RoomResponse roomResponse = new RoomResponse(savedRoom.getId(),
                savedRoom.getRoomType(), savedRoom.getRoomPrice());


        return ResponseEntity.ok("Room Created");

    }

    @GetMapping("/room/types")
    public List<String> getRoomTypes(@RequestParam("roomType") String roomType){

       return roomService.getAllRoomTypes(roomType);
    }
}
