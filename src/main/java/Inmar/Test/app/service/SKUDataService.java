package Inmar.Test.app.service;

import Inmar.Test.app.dto.request.MetaDataRequest;
import Inmar.Test.app.dto.response.SkuDetailsResponse;
import Inmar.Test.app.exception.SkuDataNotFoundException;
import Inmar.Test.app.jpa.model.SkuData;
import Inmar.Test.app.mapper.SkuDetailsMapper;
import Inmar.Test.app.repository.SkuDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class SKUDataService {
    @Autowired
    private SkuDataRepository skuDataRepository;
    @Autowired
    private SkuDetailsMapper skuDetailsMapper;

    public String savedSkuData() {
        List<String> rowsOfSkuData = getSkuDataFromFile();
        rowsOfSkuData.remove(0);
        if (rowsOfSkuData.isEmpty()) {
            if (rowsOfSkuData.isEmpty()) {

                throw new SkuDataNotFoundException("Sku Data Not found in the given file");
            }
        }
        //Delete, if any skudata exists in Database
        skuDataRepository.deleteAll();
        rowsOfSkuData.stream().filter(skuDataRow -> !StringUtils.isEmpty(skuDataRow)).forEach(skuDataRow -> {
            List<String> columns = Arrays.asList(skuDataRow.split(","));
            SkuData skuData = new SkuData();
            if (!columns.isEmpty()) {
                skuData.setSkuDataId(Integer.parseInt(columns.get(0)));
                skuData.setSkuName(columns.get(1));
                skuData.setLocation(columns.get(2));
                skuData.setDepartment(columns.get(3));
                skuData.setCategory(columns.get(4));
                skuData.setSubcategory(columns.get(5));
                skuDataRepository.save(skuData);
            }
        });
        return "SkuData saved successfully";
    }

    public SkuDetailsResponse getSkuDetailsByMetaData(MetaDataRequest metaDataRequest) {
        List<Long> skuDataList = skuDataRepository.findSkuDetailsByMetaDataParameters(metaDataRequest.getLocation(), metaDataRequest.getDepartment(), metaDataRequest.getCategory(), metaDataRequest.getSubCategory());
        if (skuDataList.isEmpty()) {

            throw new SkuDataNotFoundException("Sku Data Not found for the requested Parameters");
        }
        return skuDetailsMapper.mapListOfSkuDetails(skuDataList);
    }

    public List<String> getSkuDataFromFile() {
        try {
            ClassPathResource classPathResource = new ClassPathResource("static/skudata.txt");
            byte[] data = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
            String rawSkuData = new String(data, StandardCharsets.UTF_8);
            if (!StringUtils.isEmpty(rawSkuData)) {
                return new LinkedList<>(Arrays.asList(rawSkuData.split("\\r\\n")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new LinkedList<>();
    }
}
