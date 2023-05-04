package Inmar.Test.app.repository;

import Inmar.Test.app.jpa.model.MetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaDataRepository extends JpaRepository<MetaData, Long> {

    @Query(value = "select meta.department from MetaData meta WHERE meta.location = :location")
    public List<String> findDepartmentsFromMetaData(@Param("location") String location);

    @Query(value = "select meta.category from MetaData meta WHERE meta.location = :location And meta.department = :department")
    public List<String> findCategoriesFromMetaData(@Param("location") String location,@Param("department") String department);

    @Query(value = "select meta from MetaData meta WHERE meta.location = :location AND meta.department = :department AND meta.category = :category AND meta.subcategory = :subcategory")
    public List<MetaData> findMetaDataDetails(@Param("location") String location, @Param("department") String department, @Param("category") String category, @Param("subcategory") String subcategory);
}

