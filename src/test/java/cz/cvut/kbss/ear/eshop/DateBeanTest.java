package cz.cvut.kbss.ear.eshop;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
// DataJpaTest does not load all the application beans, it starts only persistence-related stuff
@DataJpaTest
// Exclude SystemInitializer from the startup, we don't want the admin account here
@ComponentScan(basePackageClasses = EShopApplication.class)
public class DateBeanTest {

    @Autowired
    protected ApplicationContext applicationContext;

    @Test
    public void testPrototypeDateBean(){
        Date date1 = applicationContext.getBean(Date.class);
        Date date2 = applicationContext.getBean(Date.class);
        Assert.assertNotSame(date1, date2);
    }
}
