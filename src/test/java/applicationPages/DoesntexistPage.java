package applicationPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class DoesntexistPage extends BasePage{
    private WebDriver driver;

    //Page URL
    private static String PAGE_URL = "https://www.kraken.com/doesntexist";

    @FindBy(tagName="h1")
    private WebElement heading;

    public DoesntexistPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened() {
        return heading.getText().toString().contains("404");
    }
}
