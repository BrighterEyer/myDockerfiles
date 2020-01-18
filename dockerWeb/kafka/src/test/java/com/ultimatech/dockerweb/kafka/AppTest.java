package com.ultimatech.dockerweb.kafka;

import com.ultimatech.dockerweb.kafka.service.IKafkaService;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        ApplicationContext cxt = new AnnotationConfigApplicationContext(
                TestKafkaConfig.class);
        IKafkaService service = (IKafkaService) cxt.getBean("kafkaService");
        KafkaMessage msg = new KafkaMessage();
        msg.setMsg("test");
        msg.setSendTime(new Date());
        service.sendMessage("test", msg);
        assertTrue(true);
    }
}
