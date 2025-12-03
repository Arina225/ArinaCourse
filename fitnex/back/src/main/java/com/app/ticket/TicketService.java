package com.app.ticket;

import com.app.system.exception.BadRequestException;
import com.app.system.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.app.util.Global.saveFile;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository repository;

    public List<Ticket> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Ticket find(String id) {
        return repository.findById(Long.parseLong(id)).orElseThrow(() -> new ObjectNotFoundException("Не найден абонемент по ИД: " + id));
    }

    public Ticket save(Ticket save) {
        return repository.save(save);
    }

    public Ticket update(String id, Ticket update) {
        Ticket old = find(id);
        old.update(update);
        return repository.save(old);
    }

    public Ticket updateImg(String id, MultipartFile img) {
        Ticket ticket = find(id);

        try {
            ticket.setImg(saveFile(img, "ticket"));
        } catch (IOException e) {
            if (ticket.getImg().isEmpty()) repository.deleteById(ticket.getId());
            throw new BadRequestException("Некорректное изображение");
        }

        return repository.save(ticket);
    }

    public Ticket updateFile(String id, MultipartFile file) {
        Ticket ticket = find(id);

        try {
            ticket.setFile(saveFile(file, "ticket"));
        } catch (IOException e) {
            if (ticket.getFile().isEmpty()) repository.deleteById(ticket.getId());
            throw new BadRequestException("Некорректный файл");
        }

        return repository.save(ticket);
    }

    public void delete(String id) {
        repository.deleteById(find(id).getId());
    }

}
