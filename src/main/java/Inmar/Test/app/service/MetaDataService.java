package Inmar.Test.app.service;

import Inmar.Test.app.dto.response.*;
import Inmar.Test.app.jpa.model.*;
import Inmar.Test.app.repository.MetaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MetaDataService {
    @Autowired
    private MetaDataRepository metaDataRepository;
    @Autowired
    private LocationService locationService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubCategoryService subCategoryService;

    public String savedMetaData() {
        List<String> rowsOfMetaData = getMetaDataFromFile();
        rowsOfMetaData.remove(0);
        if (!rowsOfMetaData.isEmpty()) {
            //Delete meteData and its related data if any exists in Database

            locationService.deleteAllLocations();
            departmentService.deleteAllDepartments();
            categoryService.deleteAllCategories();
            subCategoryService.deleteAllSubCategories();
            metaDataRepository.deleteAll();

            rowsOfMetaData.stream().filter(metaDataRow -> !StringUtils.isEmpty(metaDataRow)).forEach(metaDataRow -> {
                List<String> columns = Arrays.asList(metaDataRow.split(","));
                MetaData metaData = new MetaData();
                if (!columns.isEmpty()) {
                    metaData.setLocation(columns.get(0));
                    metaData.setDepartment(columns.get(1));
                    metaData.setCategory(columns.get(2));
                    metaData.setSubcategory(columns.get(3));
                    metaDataRepository.save(metaData);
                    saveOtherDetailsOfMetaData(columns);
                }
            });
        }
        return "Metadata loaded sucessfully";

    }

    public DepartmentsResponse getDepartmentFromMetadata(long locationId) {
        DepartmentsResponse response = new DepartmentsResponse();
        Location locationData = locationService.getLocationById(locationId);
        if (locationData != null) {
            List<String> metadata = metaDataRepository.findDepartmentsFromMetaData(locationData.getLocation()).stream().distinct().collect(Collectors.toList());
            List<Department> departments = metadata.stream().filter(meta -> meta != null && !StringUtils.isEmpty(meta)).map(meta -> departmentService.getDepartmentByName(meta)).collect(Collectors.toList());
            List<DepartmentResponse> departmentResponses = departments.stream().map(dept -> new DepartmentResponse(dept.getDepartmentId(), dept.getDepartment(), dept.getDescription())).collect(Collectors.toList());
            response.setDepartments(departmentResponses);
        } else {
            response.setDepartments(new ArrayList<>());
        }

        return response;
    }

    public CategoriesResponse getCategoriesFromMetadata(long locationId, long departmentId) {
        CategoriesResponse response = new CategoriesResponse();
        Location locationData = locationService.getLocationById(locationId);
        Department departmentData = departmentService.getDepartmentById(departmentId);
        if (locationData != null && departmentData != null) {
            List<String> metadata = metaDataRepository.findCategoriesFromMetaData(locationData.getLocation(), departmentData.getDepartment()).stream().distinct().collect(Collectors.toList());
            List<Category> categories = metadata.stream().filter(meta -> meta != null && !StringUtils.isEmpty(meta)).map(meta -> categoryService.getCategoryByName(meta)).collect(Collectors.toList());
            List<CategoryResponse> departmentResponses = categories.stream().map(cat -> new CategoryResponse(cat.getCategoryId(), cat.getCategory(), cat.getDescription())).collect(Collectors.toList());
            response.setCategories(departmentResponses);
        } else {
            response.setCategories(new ArrayList<>());
        }

        return response;
    }

    public MetadataDetailsResponse getMetaDataFromParameters(long locationId, long departmentId, long categoryId, long subCategoryId) {
        MetadataDetailsResponse response = new MetadataDetailsResponse();
        Location locationData = locationService.getLocationById(locationId);
        Department departmentData = departmentService.getDepartmentById(departmentId);
        Category categoryData = categoryService.getCategoryById(categoryId);
        SubCategory subCategoryData = subCategoryService.getSubCategoryById(subCategoryId);
        if (locationData != null && departmentData != null && categoryData != null && subCategoryData != null) {
            List<MetaData> metadata = metaDataRepository.findMetaDataDetails(locationData.getLocation(), departmentData.getDepartment(), categoryData.getCategory(), subCategoryData.getSubCategory());
            List<MetadataResponse> metaDataResponse = metadata.stream().map(meta -> new MetadataResponse(meta.getMetaDataId(), meta.getLocation(), meta.getDepartment(), meta.getCategory(), meta.getSubcategory())).collect(Collectors.toList());
            response.setMetadataDetails(metaDataResponse);
        } else {
            response.setMetadataDetails(new ArrayList<>());
        }
        return response;
    }

    private void saveOtherDetailsOfMetaData(List<String> columns) {
        if (!StringUtils.isEmpty(columns.get(0))) {
            locationService.saveALocation(columns.get(0));
        }
        if (!StringUtils.isEmpty(columns.get(1))) {
            departmentService.saveDepartment(columns.get(1));
        }
        if (!StringUtils.isEmpty(columns.get(2))) {
            categoryService.saveCategory(columns.get(2));
        }
        if (!StringUtils.isEmpty(columns.get(3))) {
            subCategoryService.saveSubCategory(columns.get(3));
        }
    }

    public List<String> getMetaDataFromFile() {
        try {
            ClassPathResource classPathResource = new ClassPathResource("static/metadata.txt");
            byte[] data = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
            String rawMetaData = new String(data, StandardCharsets.UTF_8);
            if (!StringUtils.isEmpty(rawMetaData)) {
                return new LinkedList<>(Arrays.asList(rawMetaData.split("\\r\\n")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new LinkedList<>();
    }
}
