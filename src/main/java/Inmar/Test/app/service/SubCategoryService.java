package Inmar.Test.app.service;

import Inmar.Test.app.jpa.model.Category;
import Inmar.Test.app.jpa.model.SubCategory;
import Inmar.Test.app.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubCategoryService {
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    public void saveSubCategory(String subCategoryName){
        SubCategory subCategory=new SubCategory();
        SubCategory existedSubCategory=subCategoryRepository.findBySubCategory(subCategoryName);
        if(existedSubCategory==null) {
            subCategory.setSubCategory(subCategoryName);
            subCategory.setDescription("Test Description for" + subCategoryName);
            subCategoryRepository.save(subCategory);
        }
    }

    public SubCategory getSubCategoryByName(String name) {
        return subCategoryRepository.findBySubCategory(name);
    }

    public SubCategory getSubCategoryById(long id) {
        return subCategoryRepository.findById(id).orElse(null);
    }

    public void deleteAllSubCategories(){
        subCategoryRepository.deleteAll();
    }

}
