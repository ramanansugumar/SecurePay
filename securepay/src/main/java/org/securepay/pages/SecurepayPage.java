package org.securepay.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.securepay.utilclass.UtilClass;





public class SecurepayPage {
	
	WebDriver driver;
	UtilClass util;
	
	public SecurepayPage(WebDriver driver)
	{
		this.driver=driver;
		util = new UtilClass();
		util.loadRepo("securepay");
        UtilClass.setName();
	}
	
	
	public void clickSecurePay()
	{
		WebElement element = findElementIdentifier("searchlink");
		element.click();
	}
	
	public void gotoSupport()
	{
		WebElement element = findElementIdentifier("support");
		element.click();
		
	}
	
	public void gotoContact() throws InterruptedException
	{
		WebElement element = findElementIdentifier("contactus");
		element.click();			
	}
	
	public void contactPageVerification()
	{

		WebElement element = findElementIdentifier("firstname");
	}
	
	public void enterInformation()
	{
		WebElement element = findElementIdentifier("firstname");
		element.sendKeys(UtilClass.firstName);	
		
		WebElement lastElement = findElementIdentifier("lastname");
		lastElement.sendKeys(UtilClass.lastName);	
	
		WebElement emailElement = findElementIdentifier("emailaddress");
		emailElement.sendKeys(UtilClass.email);
		
		WebElement companyElement = findElementIdentifier("company");
		companyElement.sendKeys(UtilClass.company);
	}
	
	public void selectEnquiry()
	{
		WebElement element = findElementIdentifier("dropdown");
		Select sel=new Select(element);
		sel.selectByValue(util.getObjectFromRepoistory("dropdownValue"));
	}
	
	public void enterPh()
	{
		
		WebElement element = findElementIdentifier("phnumber");
		element.sendKeys(UtilClass.getPh());
	}
	
	public void enterUrl()
	{
		WebElement element = findElementIdentifier("url");
		element.sendKeys("http://"+UtilClass.company+".com");
	}
	
	public WebElement findElementIdentifier(String key)
	
	{
		String xpath = util.getObjectFromRepoistory(key);
		By locator = By.xpath(xpath);
		WebDriverWait wait=new WebDriverWait(driver, 20);
		WebElement element=wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return element;
	}

}
