package com.app.ticket.ordering.converter;

import com.app.ticket.ordering.TicketOrdering;
import com.app.ticket.ordering.TicketOrderingDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TicketOrderingToTicketOrderingDtoConverter implements Converter<TicketOrdering, TicketOrderingDto> {
    @Override
    public TicketOrderingDto convert(TicketOrdering source) {
        return new TicketOrderingDto(
                source.getId(),

                source.getPrice(),

                source.getStatus().name(),
                source.getStatus().getName(),

                source.getClient().getProfile().getFio(),

                source.getTicket().getId(),
                source.getTicket().getName(),
                source.getTicket().getImg()
        );
    }
}
