package com.app.ticket;

import com.app.system.Result;
import com.app.system.StatusCode;
import com.app.ticket.converter.TicketDtoToTicketConverter;
import com.app.ticket.converter.TicketToTicketDtoConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

import static com.app.util.Global.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService service;
    private final TicketToTicketDtoConverter toDtoConverter;
    private final TicketDtoToTicketConverter toConverter;

    @GetMapping
    public Result findAll() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find All",
                service.findAll().stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public Result find(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find",
                toDtoConverter.convert(service.find(id))
        );
    }

    @Secured({ADMIN})
    @PostMapping
    public Result save(@Valid @RequestBody TicketDto saveDto) {
        Ticket save = toConverter.convert(saveDto);
        Ticket saved = service.save(save);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Save",
                toDtoConverter.convert(saved)
        );
    }

    @Secured({ADMIN})
    @PutMapping("/{id}")
    public Result update(@PathVariable String id, @Valid @RequestBody TicketDto updateDto) {
        Ticket update = toConverter.convert(updateDto);
        Ticket updated = service.update(id, update);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update",
                toDtoConverter.convert(updated)
        );
    }

    @Secured({ADMIN})
    @PatchMapping("/{id}/img")
    public Result updateImg(@PathVariable String id, @RequestParam MultipartFile files) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update Img",
                toDtoConverter.convert(service.updateImg(id, files))
        );
    }

    @Secured({ADMIN})
    @PatchMapping("/{id}/file")
    public Result updateFile(@PathVariable String id, @RequestParam MultipartFile files) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update File",
                toDtoConverter.convert(service.updateFile(id, files))
        );
    }

    @Secured({ADMIN})
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        service.delete(id);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Delete"
        );
    }

}
