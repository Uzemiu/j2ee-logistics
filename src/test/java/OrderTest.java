import com.example.logistics.App;
import com.example.logistics.controller.OrderController;
import com.example.logistics.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class OrderTest {

    @Resource
    OrderController orderController;

    @Resource
    ClientService clientService;

    @Before
    public void given(){
    }
}
