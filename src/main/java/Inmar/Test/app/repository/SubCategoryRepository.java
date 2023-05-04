package Inmar.Test.app.repository;

import Inmar.Test.app.jpa.model.Category;
import Inmar.Test.app.jpa.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    SubCategory findBySubCategory(String subCategory);

}
