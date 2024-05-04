package com.tracker.trackerapi.service;

import com.tracker.trackerapi.api.model.Distributer;
import com.tracker.trackerapi.api.model.dto.DistributerDto;
import com.tracker.trackerapi.exeption.HttpErrorException;
import com.tracker.trackerapi.repository.DistributerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistributerServiceImpl implements DistributerService{
    private final DistributerRepository distributerRepository;

    @Autowired
    public DistributerServiceImpl(DistributerRepository distributerRepository) {
        this.distributerRepository = distributerRepository;
    }

    @Override
    public void createDistributer(Distributer distributer) {
        try {
            distributerRepository.save(distributer);
        } catch (Exception e) {
            throw new HttpErrorException("Error occurred while saving the user.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public List<DistributerDto> getDistributers() {
        List<Distributer> distributers = this.distributerRepository.findAll();
        return distributers.stream()
                .map(distributer -> new DistributerDto
                        (distributer.getEmail(),
                                distributer.getPassword(),
                                distributer.getAdresse(),
                                distributer.getName(),
                                distributer.getSiretNumber()))
                .collect(Collectors.toList());
    }

    @Override
    public Distributer getDistributerByEmail(String email) {
        return this.distributerRepository.findByEmail(email);
    }

    @Override
    public Distributer findBySiretNumber(int siretNumber) {
        try{

            Distributer distributer =  distributerRepository.findBySiretNumber(siretNumber);
            return  distributer;

        }catch (Exception e ){
            throw new HttpErrorException("Error occurred while saving the user.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }
}
