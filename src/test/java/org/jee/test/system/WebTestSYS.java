package org.jee.test.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.spi.annotations.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jee.controller.MemberController;
import org.jee.data.MemberListProducer;
import org.jee.data.MemberRepository;
import org.jee.model.Member;
import org.jee.service.MemberRegistration;
import org.jee.test.system.pages.HomePage;
import org.jee.util.Resources;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

/**
 * Test class for Graphene 2 - Selenium 2 (WD)
 * 
 * @author Hugo
 */
@RunWith(Arquillian.class)
public class WebTestSYS {
    private static final String WEBAPP_SRC = "src/main/webapp";

    @Page
    private HomePage homePage;

    @Drone
    WebDriver driver;

    @Deployment(testable = false)
    public static Archive<?> createTestArchive() throws IOException {
        // https://community.jboss.org/wiki/HowDoIAddAllWebResourcesToTheShrinkWrapArchive
        final WebArchive war = ShrinkWrap.create(WebArchive.class, "javaee-arquillian-jacoco.war");
        war.merge(
                ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class).importDirectory(WEBAPP_SRC)
                        .as(GenericArchive.class), "/", Filters.includeAll())
                .addClasses(MemberController.class, MemberListProducer.class, MemberRepository.class, Member.class,
                        MemberRegistration.class, Resources.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql", "import.sql").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml");

        exportArchive(war);

        return war;
    }

    @Test
    @InSequence(0)
    public void navigateToGoogle(@ArquillianResource URL baseURL) {
        driver.navigate().to(baseURL.toString());
        assertTrue("Java EE 6 Starter Application".equals(driver.getTitle()));
    }

    @Test
    @InSequence(1)
    public void addMember(@ArquillianResource URL baseURL) {
        assertEquals("Registered!", homePage.addMember("test", "test@ap.pt", 1234567891));
    }

    private static void exportArchive(Archive<?> archive) throws IOException {
        OutputStream out = new FileOutputStream("target/" + archive.getName() + ".zip");
        archive.as(ZipExporter.class).exportTo(out);
        out.close();
    }
}
