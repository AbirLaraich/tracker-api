package com.tracker.trackerapi.api.controller;

import com.tracker.trackerapi.api.model.Distributer;
import com.tracker.trackerapi.api.model.dto.DistributerDto;
import com.tracker.trackerapi.api.model.dto.SupplierDto;
import com.tracker.trackerapi.api.model.mapper.DistributerMapper;
import com.tracker.trackerapi.service.DistributerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DistributerController {
    private final DistributerService distributerService;
    private final BCryptPasswordEncoder passwordEncoder;
    public DistributerController(DistributerService distributerService, BCryptPasswordEncoder passwordEncoder) {
        this.distributerService = distributerService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/distributers")
    public List<DistributerDto> getDistributes() {
        return this.distributerService.getDistributers();
    }

    @PostMapping("/distributer")
    public ResponseEntity<String> createDistributer(@RequestBody Distributer distributer) {
        try {
            distributer.setPassword(passwordEncoder.encode(distributer.getPassword()));
            distributerService.createDistributer(distributer);
            return new ResponseEntity<>("Utilisateur créé avec succès", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création de l'utilisateur", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login/distributer")
    public ResponseEntity<?> loginDistributer(@RequestBody DistributerDto userDto) {
        try {
            Distributer existingUser = distributerService.getDistributerByEmail(userDto.getEmail());
            if (existingUser != null && passwordEncoder.matches(userDto.getPassword(), existingUser.getPassword())) {
                SupplierDto loggedInUserDto = new SupplierDto(existingUser.getEmail(), null,  existingUser.getAdresse(), existingUser.getName(),existingUser.getSiretNumber());
                return new ResponseEntity<>(loggedInUserDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Identifiants incorrects", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la connexion", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
