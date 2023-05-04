package Inmar.Test.app.service;

import Inmar.Test.app.jpa.model.Category;
import Inmar.Test.app.jpa.model.Department;
import Inmar.Test.app.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category getCategoryByName(String categoryName) {

        return categoryRepository.findByCategory(categoryName);
    }

    public Category getCategoryById(long id) {

        return categoryRepository.findById(id).orElse(null);
    }

    public void saveCategory(String categoryName) {
        Category category = new Category();
        Category existedCategory = categoryRepository.findByCategory(categoryName);
        if (existedCategory == null) {
            category.setCategory(categoryName);
            category.setDescription("Test Description for" + categoryName);
            categoryRepository.save(category);
        }
    }

    public void deleteAllCategories() {
        categoryRepository.deleteAll();
    }
}
