package com.tracker.trackerapi.service;

import com.tracker.trackerapi.api.model.Lot;
import com.tracker.trackerapi.api.model.Order;
import com.tracker.trackerapi.api.model.dto.DistributerDto;
import com.tracker.trackerapi.api.model.dto.LotDto;
import com.tracker.trackerapi.api.model.dto.SupplierDto;
import com.tracker.trackerapi.exeption.HttpErrorException;
import com.tracker.trackerapi.repository.LotRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.tracker.trackerapi.api.model.mapper.DistributerMapper.mapDistributerDtoByLot;
import static com.tracker.trackerapi.api.model.mapper.SuppplierMapper.mapSupplierDtoByLot;

@Service
public class LotServiceImpl implements LotService {

    public final LotRepository lotRepository;

    public LotServiceImpl(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    @Override
    public Lot createLot(Lot lot) {
        try {
            lotRepository.save(lot);
            return lot;
        } catch (Exception e) {
            throw new HttpErrorException("Error occurred while creating the lot.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public Lot getLot(String numLot) {
        try {
            Lot lot = lotRepository.getLotByNumLot(numLot);
            return lot;
        } catch (Exception e) {
            throw new HttpErrorException("Error occurred while getting the lot. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public Lot updateLot(Lot lot) {
        try {
            Lot nwlot = getLot(lot.getNumLot());
            nwlot.setSupplier(lot.getSupplier());
            createLot(nwlot);
            return nwlot;
        } catch (Exception e) {
            throw new HttpErrorException("Error occurred while updating the lot.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public void deleteLot(String numLot) {
        try {
            Lot lot = getLot(numLot);
            lotRepository.delete(lot);
        } catch (Exception e) {
            throw new HttpErrorException("Error occurred while deleting the lot.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public List<LotDto> getLotsBySupplierSiretNumber(Long numSiret) {
        try {

            List<Lot> lotList = this.lotRepository.getLotsBySupplier_SiretNumber(numSiret);
            List<LotDto> lotDtoList = lotList.stream()
                    .map(lot -> {
                        SupplierDto supplierDto = mapSupplierDtoByLot(lot);
                        DistributerDto distributerDto = mapDistributerDtoByLot(lot);
                        Long orderId = lot.getOrder() != null ? lot.getOrder().getId() : null;
                        return new LotDto(
                                lot.getNumLot(),
                                lot.getName(),
                                supplierDto,
                                distributerDto,
                                lot.getCreation_date(),
                                orderId
                        );
                    }).collect(Collectors.toList());
            return lotDtoList;

        } catch (Exception e) {
            throw new HttpErrorException("Error occurred while getting the lots. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public List<Lot> getLotsByOrder(Order order) {
        return this.lotRepository.getLotsByOrder(order);
    }

    @Override
    public List<LotDto> getLotsWithoutOrderBySupplierSiretNumber(Long numSiret) {
        try {

            List<Lot> lotList = this.lotRepository.getLotsBySupplier_SiretNumber(numSiret);
            List<LotDto> lotDtoList = lotList.stream()
                    .filter(lot -> lot.getDistributer() == null && lot.getOrder() == null)
                    .map(lot -> {
                        SupplierDto supplierDto = mapSupplierDtoByLot(lot);
                        DistributerDto distributerDto = mapDistributerDtoByLot(lot);
                        Long orderId = lot.getOrder() != null ? lot.getOrder().getId() : null;
                        return new LotDto(
                                lot.getNumLot(),
                                lot.getName(),
                                supplierDto,
                                distributerDto,
                                lot.getCreation_date(),
                                orderId
                        );
                    }).collect(Collectors.toList());
            return lotDtoList;

        } catch (Exception e) {
            throw new HttpErrorException("Error occurred while getting the lots. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public List<LotDto> getLotsByDistributerAndSiretNumber(Long numSiret) {
        try {
            List<Lot> lots = lotRepository.getLotsByDistributer_SiretNumber(numSiret);

            List<LotDto> lotDtoList = lots.stream()
                    .map(lot -> {
                        SupplierDto supplierDto = mapSupplierDtoByLot(lot);
                        DistributerDto distributerDto = mapDistributerDtoByLot(lot);
                        Long order = lot.getOrder() != null ? lot.getOrder().getId() : null;
                        return new LotDto(
                                lot.getNumLot(),
                                lot.getName(),
                                supplierDto,
                                distributerDto,
                                lot.getCreation_date(),
                                order
                        );
                    }).collect(Collectors.toList());

            return lotDtoList;


        } catch (Exception e) {
            throw new HttpErrorException("Error occurred while getting the lots By Distributer. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());

        }
    }

    @Override
    public List<LotDto> getLotsByOrder(int order_id) {
      try{
          List<Lot> lots   = lotRepository.getLotsByOrder(order_id);

          List<LotDto> lotDtoList = lots.stream()
                  .map(lot -> {
                      SupplierDto supplierDto = mapSupplierDtoByLot(lot);
                      DistributerDto distributerDto = mapDistributerDtoByLot(lot);
                      Long orderId = lot.getOrder() != null ? lot.getOrder().getId() : null;
                      return new LotDto(
                              lot.getNumLot(),
                              lot.getName(),
                              supplierDto,
                              distributerDto,
                              lot.getCreation_date(),
                              orderId
                      );
                  }).collect(Collectors.toList());

          return lotDtoList;

      }catch (Exception e){
          throw new HttpErrorException("getLotsByOrder " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());

      }
    }

}
