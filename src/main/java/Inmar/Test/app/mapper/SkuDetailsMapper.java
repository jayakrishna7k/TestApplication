package Inmar.Test.app.mapper;

import Inmar.Test.app.dto.response.SkuDataResponse;
import Inmar.Test.app.dto.response.SkuDetailsResponse;
import Inmar.Test.app.jpa.model.SkuData;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkuDetailsMapper {
    public SkuDetailsResponse mapListOfSkuDetails(List<Long> skus) {
        SkuDetailsResponse skuDetailsResponse = new SkuDetailsResponse();
        if (!skus.isEmpty()) {
            List<SkuDataResponse> skuList = skus.stream().map(SkuDataResponse::new).collect(Collectors.toList());
            skuDetailsResponse.setSkuDetailsResponse(skuList);
        }
        else{
            skuDetailsResponse.setSkuDetailsResponse(new ArrayList<>());
        }
        return skuDetailsResponse;
    }
}
