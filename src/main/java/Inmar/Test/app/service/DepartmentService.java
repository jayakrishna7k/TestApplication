package Inmar.Test.app.service;

import Inmar.Test.app.jpa.model.Department;
import Inmar.Test.app.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public Department getDepartmentById(long id){

        return departmentRepository.findById(id).orElse(null);
    }

    public Department getDepartmentByName(String name){

        return departmentRepository.findByDepartment(name);
    }

    public void saveDepartment(String departmentName){
        Department department=new Department();
        Department existedDepartment=departmentRepository.findByDepartment(departmentName);
        if(existedDepartment==null) {
            department.setDepartment(departmentName);
            department.setDescription("Test Description for" + departmentName);
            departmentRepository.save(department);
        }
    }
    public void deleteAllDepartments(){
        departmentRepository.deleteAll();
    }
}
