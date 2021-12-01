import com.example.logistics.App;
import com.example.logistics.controller.ClientController;
import com.example.logistics.controller.ClientRateController;
import com.example.logistics.controller.OrderController;
import com.example.logistics.exception.BadRequestException;
import com.example.logistics.model.dto.ClientRateDTO;
import com.example.logistics.model.dto.OrderDTO;
import com.example.logistics.model.dto.PageDTO;
import com.example.logistics.model.entity.ClientRate;
import com.example.logistics.model.entity.Order;
import com.example.logistics.model.enums.OrderStatus;
import com.example.logistics.model.param.ClientRateParam;
import com.example.logistics.model.query.OrderQuery;
import com.example.logistics.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class ClientRateTest {

    @Resource
    ClientService clientService;

    @Resource
    ClientRateController clientRateController;

    @Resource
    OrderController orderController;

    List<OrderDTO> orders;

    @Before
    public void given(){
        // set client
          TestUtils.loginClient(clientService);
        // given current user's order;
        PageDTO<OrderDTO> dto = orderController.listSendingOrderByCurrentUser(new OrderQuery(),  TestUtils.defaultPageable()).getData();
        orders = dto.getContent();

        OrderDTO order1 = orders.get(0);
        if(order1.getStatus().lessThan(OrderStatus.RECEIPT_CONFIRMED)){
            orderController.confirmOrder(order1.getId());
        }
    }

    @Test
    public void illegalRate(){
        try{
            // not found
            ClientRateParam param = new ClientRateParam();
            param.setOrderId(2222L);
            clientRateController.rateOrder(param);
            TestUtils.notReached();
        } catch (RuntimeException ignored){

        }

        try{
            // not confirmed
            ClientRateParam param = new ClientRateParam();
            param.setOrderId(orders.get(1).getId());
            clientRateController.rateOrder(param);
            TestUtils.notReached();
        } catch (RuntimeException ignored){

        }

        try{
            // illegal range
            ClientRateParam param = new ClientRateParam();
            param.setOrderId(orders.get(0).getId());
            param.setItemScore(6);
            clientRateController.rateOrder(param);
            TestUtils.notReached();
        } catch (RuntimeException ignored){

        }
    }

    @Test
    public void legalRate(){
        ClientRateDTO clientRate = clientRateController.getByOrderId(orders.get(0).getId()).getData();
        if(clientRate != null) return;

        ClientRateParam param = new ClientRateParam();
        param.setOrderId(orders.get(0).getId());
        param.setItemScore(4);
        param.setAdvice("很好很给力");
        clientRateController.rateOrder(param);
    }

    @After
    public void then(){
        ClientRateDTO clientRate = clientRateController.getByOrderId(orders.get(0).getId()).getData();
        log.info(clientRate.toString());
    }
}
