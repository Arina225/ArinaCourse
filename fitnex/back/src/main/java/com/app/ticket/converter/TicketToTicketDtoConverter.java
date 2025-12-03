package com.app.ticket.converter;

import com.app.ticket.Ticket;
import com.app.ticket.TicketDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TicketToTicketDtoConverter implements Converter<Ticket, TicketDto> {
    @Override
    public TicketDto convert(Ticket source) {
        return new TicketDto(
                source.getId(),

                source.getName(),
                source.getPrice(),

                source.getImg(),
                source.getFile()
        );
    }
}
