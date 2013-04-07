package org.jee.test.integration;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jee.service.CoverageBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * JacocoInegrationTestCase
 */
@RunWith(Arquillian.class)
public class JacocoIntegrationTestIT {

    @Deployment
    public static JavaArchive createDeployment() throws Exception {
        return ShrinkWrap.create(JavaArchive.class, "test.jar").addClasses(CoverageBean.class,
                JacocoIntegrationTestIT.class);
    }

    @EJB
    private CoverageBean bean;

    @Test
    public void shouldBeAbleToGenerateSomeTestCoverage() throws Exception {
        Assert.assertNotNull(bean);
        bean.test(true);
    }
}
