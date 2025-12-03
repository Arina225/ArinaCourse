package com.app.ticket.converter;

import com.app.ticket.Ticket;
import com.app.ticket.TicketDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TicketDtoToTicketConverter implements Converter<TicketDto, Ticket> {
    @Override
    public Ticket convert(TicketDto source) {
        return new Ticket(
                source.name(),
                source.price()
        );
    }
}
