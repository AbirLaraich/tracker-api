package com.tracker.trackerapi.service;

import com.tracker.trackerapi.api.model.Lot;
import com.tracker.trackerapi.api.model.Order;
import com.tracker.trackerapi.api.model.dto.LotDto;
import com.tracker.trackerapi.api.model.dto.ProductDto;

import java.util.List;


public interface LotService {

    Lot createLot(Lot lot);
    Lot getLot(String numLot);
    Lot updateLot(Lot lot);
    void deleteLot(String numLot) ;
    List<LotDto> getLotsBySupplierSiretNumber(Long numSiret);
    List<Lot> getLotsByOrder(Order order);
    List<LotDto> getLotsWithoutOrderBySupplierSiretNumber(Long numSiret);
    List<LotDto> getLotsByDistributerAndSiretNumber(Long numSiret);

    List<LotDto> getLotsByOrder(int order_id);

}
