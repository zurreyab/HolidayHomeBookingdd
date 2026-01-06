package com.holidaybookingproject.HolidayHomeBooking.dto;

import java.util.Set;

public record JwtResponse(String token,
                          long   expiresInMs,
                          String username,
                          Set<String> roles) {}
