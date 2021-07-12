package applicationPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class MultimodalPage extends BasePage {
    private WebDriver driver;

    //Page URL
    private static String PAGE_URL = "https://www.w3.org/standards/webofdevices/multimodal";

    //Locators
    @FindBy(className = "title")
    private WebElement modal;

    public MultimodalPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened() {
        return modal.getText().contains("MULTIMODAL ACCESS");
    }
}

