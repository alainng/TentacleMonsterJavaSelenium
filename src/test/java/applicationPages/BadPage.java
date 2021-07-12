package applicationPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BadPage extends BasePage{
    private WebDriver driver;

    //Page URL
    private static String PAGE_URL = "https://www.w3.org/standards/badpage";

    @FindBy(className = "title")
    private WebElement heading;

    public BadPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened() {
        return heading.getText().contains("not found");
    }
}
