package com.tracker.trackerapi.api.model.mapper;

import com.tracker.trackerapi.api.model.Distributer;
import com.tracker.trackerapi.api.model.Lot;
import com.tracker.trackerapi.api.model.dto.DistributerDto;
import com.tracker.trackerapi.api.model.dto.SupplierDto;
import org.springframework.stereotype.Component;

@Component
public class DistributerMapper {
    public static DistributerDto mapToUserDto(Distributer distributer) {
        DistributerDto distributerDto = new DistributerDto();
        distributerDto.setEmail(distributer.getEmail());
        distributerDto.setPassword(distributer.getPassword());
        distributerDto.setAdresse(distributer.getAdresse());
        return distributerDto;
    }

    public static Distributer mapToUserEntity(DistributerDto distributerDto) {
        Distributer distributer = new Distributer();
        distributer.setEmail(distributerDto.getEmail());
        distributer.setPassword(distributerDto.getPassword());
        distributer.setAdresse(distributerDto.getAdresse());
        return distributer;
    }
    public static DistributerDto mapDistributerDtoByLot(Lot lot){
        if (lot.getDistributer() == null) { return null;}
        return   new DistributerDto(lot.getDistributer().getEmail(),
                lot.getDistributer().getPassword(),
                lot.getDistributer().getAdresse(),
                lot.getDistributer().getName(),
                lot.getDistributer().getSiretNumber());
    }
}
