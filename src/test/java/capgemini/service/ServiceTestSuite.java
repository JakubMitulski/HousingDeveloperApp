package capgemini.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@SpringBootTest
@SuiteClasses({ApartmentServiceTest.class, BuildingServiceTest.class, CustomerServiceTest.class})
public class ServiceTestSuite {

}
