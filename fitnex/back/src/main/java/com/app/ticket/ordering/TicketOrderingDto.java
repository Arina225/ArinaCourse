package com.app.ticket.ordering;

public record TicketOrderingDto(
        Long id,

        float price,

        String status,
        String statusName,

        String clientFio,

        Long ticketId,
        String ticketName,
        String ticketImg
) {
}
