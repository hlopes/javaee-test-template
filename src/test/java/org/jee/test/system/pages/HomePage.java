package org.jee.test.system.pages;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;

import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.openqa.selenium.WebElement;

/**
 * PageObject for the initial web application page.
 * 
 * @author Hugo
 */
public class HomePage {

    @FindBy(id = "reg:name")
    private WebElement name;

    @FindBy(id = "reg:email")
    private WebElement email;

    @FindBy(id = "reg:phoneNumber")
    private WebElement phone;

    @FindBy(id = "reg:register")
    private WebElement register;

    @FindBy(jquery = "ul.messages li.valid")
    private WebElement validMessage;

    public String addMember(String name, String email, Integer phoneNumber) {
        this.name.sendKeys(name);
        this.email.sendKeys(email);
        this.phone.sendKeys(String.valueOf(phoneNumber));
        guardHttp(this.register).click();

        return validMessage.getText().trim();
    }
}
