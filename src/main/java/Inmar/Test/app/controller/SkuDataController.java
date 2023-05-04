package Inmar.Test.app.controller;

import Inmar.Test.app.dto.request.MetaDataRequest;
import Inmar.Test.app.service.SKUDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*", exposedHeaders = "If-Match")

@RestController
@RequestMapping("/api/v1/skudata")
public class SkuDataController {

    @Autowired
    private SKUDataService skuDataService;

    @GetMapping("/load")
    public ResponseEntity<?> loadSkuData(){

        return new ResponseEntity<>(skuDataService.savedSkuData(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> getSkuDetailsByMetaData(@RequestBody MetaDataRequest request){

        return new ResponseEntity<>(skuDataService.getSkuDetailsByMetaData(request), HttpStatus.OK);
    }
}
