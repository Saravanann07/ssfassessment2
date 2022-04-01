package vttp2022.ssf.assessment.videosearch.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vttp2022.ssf.assessment.videosearch.models.Game;
import vttp2022.ssf.assessment.videosearch.service.SearchService;

@RestController
@RequestMapping(path="")
public class SearchController {

    @Autowired
    private SearchService searchSvc;

    @GetMapping(path="/games")
    public String getGame(@RequestParam(required = true) String name, 
    @RequestParam(name = "count", required = false, defaultValue = "10") Integer count, Model model) {
        List<Game> gameList = searchSvc.search(name, count);

        if (gameList.isEmpty())
        return "not_found";

        model.addAttribute("game", gameList);
        return "search_result";
    }
    
}
