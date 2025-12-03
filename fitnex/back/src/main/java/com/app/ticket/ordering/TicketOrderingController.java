package com.app.ticket.ordering;

import com.app.system.Result;
import com.app.system.StatusCode;
import com.app.ticket.ordering.converter.TicketOrderingToTicketOrderingDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.app.util.Global.ADMIN;
import static com.app.util.Global.CLIENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets/orderings")
public class TicketOrderingController {

    private final TicketOrderingService service;
    private final TicketOrderingToTicketOrderingDtoConverter toDtoConverter;

    @Secured({ADMIN})
    @GetMapping
    public Result findAll() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find All",
                service.findAll().stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @Secured({CLIENT})
    @GetMapping("/my")
    public Result findAllMy() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find All My",
                service.findAllMy().stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @Secured({CLIENT})
    @PostMapping
    public Result save(@RequestParam String ticketId) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Save",
                toDtoConverter.convert(service.save(ticketId))
        );
    }

    @Secured({ADMIN})
    @GetMapping("/{id}/confirmed")
    public Result confirmed(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Confirmed",
                toDtoConverter.convert(service.confirmed(id))
        );
    }

    @Secured({ADMIN})
    @GetMapping("/{id}/rejected")
    public Result rejected(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Rejected",
                toDtoConverter.convert(service.rejected(id))
        );
    }

}
