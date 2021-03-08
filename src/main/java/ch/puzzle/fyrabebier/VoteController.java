package ch.puzzle.fyrabebier;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VoteController {
    @RequestMapping(value = {"/vote", "/vote/{group}"})
    public String welcome(@PathVariable("group") Optional<String> group) {
        return "/vote.html";
    }
}
