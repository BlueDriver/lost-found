package cpwu.ecut.web;

import cpwu.ecut.common.constant.enums.ApplyKindEnum;
import cpwu.ecut.common.utils.EnumUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebApplicationTests {

    @Test
    public void contextLoads() throws Exception {

    }

    @Test
    public void test1() throws Exception {
        Integer i = EnumUtils.checkAndGetCode(6, ApplyKindEnum.values());
        System.out.println(i);
    }

}
