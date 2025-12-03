package com.app.stats;

import com.app.appUser.UserService;
import com.app.enums.AgeCategory;
import com.app.enums.Gender;
import com.app.enums.Role;
import com.app.system.Result;
import com.app.system.StatusCode;
import com.app.ticket.Ticket;
import com.app.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.app.util.Global.ADMIN;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
@Secured({ADMIN})
public class StatsController {

    private final UserService userService;
    private final TicketService ticketService;

    @GetMapping("/ageCategories")
    public Result ageCategories() {
        Map<String, List<?>> res = new HashMap<>();

        List<String> names = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        for (AgeCategory i : AgeCategory.values()) {
            names.add(i.getName());
            values.add(userService.findAllByRoleAndProfile_AgeCategory(Role.CLIENT, i).size());
        }

        res.put("names", names);
        res.put("values", values);

        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Stats Age Categories",
                Collections.unmodifiableMap(res)
        );
    }

    @GetMapping("/genders")
    public Result genders() {
        Map<String, List<?>> res = new HashMap<>();

        List<String> names = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        for (Gender i : Gender.values()) {
            names.add(i.getName());
            values.add(userService.findAllByRoleAndProfile_Gender(Role.CLIENT, i).size());
        }

        res.put("names", names);
        res.put("values", values);

        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Stats Genders",
                Collections.unmodifiableMap(res)
        );
    }

    @GetMapping("/tickets")
    public Result tickets() {
        Map<String, List<?>> res = new HashMap<>();

        List<String> names = new ArrayList<>();
        List<Float> values = new ArrayList<>();

        List<Ticket> tickets = ticketService.findAll();

        for (int i = 0; i < tickets.size(); i++) {
            if (i == 5) break;
            names.add(tickets.get(i).getName());
            values.add(tickets.get(i).getIncome());
        }

        res.put("names", names);
        res.put("values", values);

        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Stats Tickets",
                Collections.unmodifiableMap(res)
        );
    }

}
