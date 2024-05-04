package com.tracker.trackerapi.api.controller;

import com.tracker.trackerapi.api.model.Supplier;
import com.tracker.trackerapi.api.model.dto.SupplierDto;
import com.tracker.trackerapi.api.model.mapper.SuppplierMapper;
import com.tracker.trackerapi.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SupplierController {
    private final SupplierService supplierService;
    private final SuppplierMapper suppplierMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public SupplierController(SupplierService supplierService, SuppplierMapper suppplierMapper, BCryptPasswordEncoder passwordEncoder) {
        this.supplierService = supplierService;
        this.suppplierMapper = suppplierMapper;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/suppliers")
    public List<SupplierDto> getSuppliers() {
        return this.supplierService.getSuppliers();
    }

    @PostMapping("/supplier")
    public ResponseEntity<String> createSupplier(@RequestBody Supplier supplier) {
        try {
            supplier.setPassword(passwordEncoder.encode(supplier.getPassword()));
            supplierService.createSupplier(supplier);
            return new ResponseEntity<>("Utilisateur créé avec succès", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création de l'utilisateur", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login/supplier")
    public ResponseEntity<?> loginSupplier(@RequestBody SupplierDto userDto) {
        try {
            Supplier existingUser = supplierService.getSupplierByEmail(userDto.getEmail());
            if (existingUser != null && passwordEncoder.matches(userDto.getPassword(), existingUser.getPassword())) {
                SupplierDto loggedInUserDto = new SupplierDto(existingUser.getEmail(), null,  existingUser.getAdresse() , existingUser.getName(), existingUser.getSiretNumber());
                return new ResponseEntity<>(loggedInUserDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Identifiants incorrects", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la connexion", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
