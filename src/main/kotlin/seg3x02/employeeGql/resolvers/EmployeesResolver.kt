package seg3x02.employeeGql.resolvers

import org.springframework.stereotype.Controller
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput

@Controller
class EmployeesResolver(@Autowired private val employeesRepository: EmployeesRepository) {

    @QueryMapping
    fun employees(): List<Employee> {
        return employeesRepository.findAll()
    }

    @QueryMapping
    fun employeeById(@Argument id: String): Employee? {
        return employeesRepository.findById(id).orElse(null)
    }

    @MutationMapping
    fun createEmployee(@Argument input: CreateEmployeeInput): Employee {
        val employee = Employee(
            name = input.name ?: "",
            dateOfBirth = input.dateOfBirth ?: "",
            city = input.city ?: "",
            salary = input.salary ?: 0.0f,
            gender = input.gender,
            email = input.email
        )
        return employeesRepository.save(employee)
    }

    @MutationMapping
    fun updateEmployee(@Argument id: String, @Argument input: CreateEmployeeInput): Employee? {
        val existingEmployee = employeesRepository.findById(id).orElse(null) ?: return null
        existingEmployee.apply {
            name = input.name ?: name
            dateOfBirth = input.dateOfBirth ?: dateOfBirth
            city = input.city ?: city
            salary = input.salary ?: salary
            gender = input.gender ?: gender
            email = input.email ?: email
        }
        return employeesRepository.save(existingEmployee)
    }

    @MutationMapping
    fun deleteEmployee(@Argument id: String): Boolean {
        return if (employeesRepository.existsById(id)) {
            employeesRepository.deleteById(id)
            true
        } else {
            false
        }
    }


}
