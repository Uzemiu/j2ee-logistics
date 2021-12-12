import com.example.logistics.model.entity.Client;
import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.param.LoginParam;
import com.example.logistics.service.ClientService;
import com.example.logistics.service.EmployeeService;
import com.example.logistics.utils.SecurityUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

public class TestUtils{

    public static Pageable defaultPageable(){
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        return PageRequest.of(0,10,sort);
    }

    public static Client loginClient(ClientService clientService){
        LoginParam param = new LoginParam();
        param.setUsername("ab1234");
        param.setPassword("ab1234");
        Client client = clientService.login(param);
        SecurityUtil.setCurrentUser(client);
        return client;
    }

    public static Employee loginAdmin(EmployeeService employeeService){
        LoginParam param = new LoginParam();
        param.setUsername("admin");
        param.setPassword("admin");
        Employee employee = employeeService.login(param);
        SecurityUtil.setCurrentUser(employee);
        return employee;
    }
}
