package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.exchanges.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.service.AdminExchangeOfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exchanges")
public class AdminExchangeOfferController {

    private final AdminExchangeOfferService adminExchangeOfferService;

    @GetMapping
    public ResponseEntity<List<ShowExchangeOfferDTO>> obtenerIntercambios(){
        List<ShowExchangeOfferDTO> ofertasIntercambio = adminExchangeOfferService.obtenerTodosLosIntercambios();
        return new ResponseEntity<List<ShowExchangeOfferDTO>>(ofertasIntercambio, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowExchangeOfferDTO> obtenerIntercambioPorId(@PathVariable Integer id){
        ShowExchangeOfferDTO ofertaIntercambio = adminExchangeOfferService.obtenerIntercambioPorId(id);
        return new ResponseEntity<ShowExchangeOfferDTO>(ofertaIntercambio, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<ShowExchangeOfferDTO> crearIntercambio(@Valid @RequestBody ExchangeOfferDTO intercambioDTO){
        ShowExchangeOfferDTO ofertaIntercambio = adminExchangeOfferService.crearIntercambio(intercambioDTO);
        return new ResponseEntity<ShowExchangeOfferDTO>(ofertaIntercambio, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShowExchangeOfferDTO> actualizarIntercambio(@PathVariable Integer id, @Valid @RequestBody ExchangeOfferDTO intercambioDTO){
        ShowExchangeOfferDTO ofertaIntercambio = adminExchangeOfferService.actualizarIntercambio(id, intercambioDTO);
        return new ResponseEntity<ShowExchangeOfferDTO>(ofertaIntercambio, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ShowExchangeOfferDTO>> obtenerIntercambiosPorUsuario(@PathVariable Integer id){
        List<ShowExchangeOfferDTO> ofertasIntercambio = adminExchangeOfferService.obtenerIntercambiosPorUsuario(id);
        return new ResponseEntity<List<ShowExchangeOfferDTO>>(ofertasIntercambio, HttpStatus.OK);
    }

    @GetMapping("/seller/{id}")
    public ResponseEntity<List<ShowExchangeOfferDTO>> obtenerIntercambiosPorVendedor(@PathVariable Integer id){
        List<ShowExchangeOfferDTO> ofertasIntercambio = adminExchangeOfferService.obtenerIntercambiosPorVendedor(id);
        return new ResponseEntity<List<ShowExchangeOfferDTO>>(ofertasIntercambio, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<ShowProductDTO> eliminarIntercambio(@PathVariable Integer id) {
        adminExchangeOfferService.eliminarIntercambio(id);
        return new ResponseEntity<ShowProductDTO>(HttpStatus.NO_CONTENT);
    }

}
