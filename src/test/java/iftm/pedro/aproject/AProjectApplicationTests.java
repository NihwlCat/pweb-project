package iftm.pedro.aproject;

import iftm.pedro.aproject.repositories.OrderRepository;
import iftm.pedro.aproject.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


//@ExtendWith(SpringExtension.class)
@DataJpaTest
class AProjectApplicationTests {

    @Autowired
    private OrderRepository oRepository;

    @Autowired
    private ProductRepository pRepository;

    @Test
    void setUp() throws InterruptedException {


    }

}
