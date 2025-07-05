package com.project.masp.Controlers;

import com.project.masp.Models.Users.TripManager;
import com.project.masp.Repository.TripManagerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ManagerController {
    private final TripManagerRepository tripManagerRepository;

    public ManagerController(TripManagerRepository tripManagerRepository) {
        this.tripManagerRepository = tripManagerRepository;
    }

    @GetMapping("/manager/{companyId}")
    public List<TripManager> tripManagers(@PathVariable Long companyId){
        return tripManagerRepository.findByCompanyId(companyId);
    }

}
