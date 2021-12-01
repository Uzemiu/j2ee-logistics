import com.example.logistics.App;
import com.example.logistics.controller.VehicleController;
import com.example.logistics.model.entity.Vehicle;
import com.example.logistics.model.enums.VehicleStatus;
import com.example.logistics.repository.VehicleRepository;
import com.example.logistics.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Slf4j
@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class LockTest {

    @Resource
    VehicleController vehicleController;

    @Resource
    VehicleRepository vehicleRepository;

    @Resource
    VehicleService vehicleService;

    @Test
    public void test(){
        Vehicle vehicle = new Vehicle();
        vehicle.setStatus(VehicleStatus.IDLE);
        vehicle.setVehicleNumber("VeNu");
        vehicleRepository.save(vehicle);
    }


}
