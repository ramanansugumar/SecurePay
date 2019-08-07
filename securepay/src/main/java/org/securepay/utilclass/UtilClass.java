package org.securepay.utilclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.github.javafaker.Faker;


/* utility class to get the date
 * to randomly generate name, email and DOB data.
 */


public class UtilClass {
	
	public static String firstName;
	public static String lastName;
	public static String email;
	public static String company;
	private String propName;
	private Properties prop;
	
	
	
	
	 public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public static String getDate()
	{
		DateFormat dateFormat=new SimpleDateFormat("yyyyMMddhhmmss");
		Date date=new Date();
		return dateFormat.format(date);
	}
	
	public static String capture(WebDriver driver,String screenShotName) throws IOException
    {
		UtilClass util = new UtilClass();
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String dest = System.getProperty("user.dir") +"\\Screenshots\\"+screenShotName+getDate()+".png";
        File destination = new File(dest);
        FileUtils.copyFile(source, destination); 
                     
        return dest;
    }
	// browser details stored in properties to support multiple browsers
	public String getAppPropValue(String key)
	{
		Properties prop=new Properties();
		try {
			prop.load(new FileInputStream(new File(System.getProperty("user.dir")+"/resources/app.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return prop.getProperty(key);
	}
	
	//object repoistory is stored in properties file
	public void loadRepo(String fileName)
	{
		Properties prop=new Properties();
		try {
			prop.load(new FileInputStream(new File(System.getProperty("user.dir")+"/resources/"+fileName+".properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    setProp(prop);
	}
	
	public String getObjectFromRepoistory(String key)
	{
		return getProp().getProperty(key);
	}
	
	//Javafaker library is used to use valid names, since facebook is not accepting randomly generated string because of its policy
	public static void setName()
	{
		UtilClass util=new UtilClass();
		Faker fake=new Faker();
		firstName= fake.name().firstName();
		lastName= fake.name().lastName();
		 System.out.println("Name--->"+firstName+" "+lastName);
		 email= firstName+lastName+util.getEmailNum()+util.getAppPropValue("emailprov");
		 System.out.println("email--->"+email);
		 company=fake.company().name();
		 System.out.println("company-->"+company);
	}
	
	public int getDateDay()
	  {
		  Random random= new Random();
		  int num= random.nextInt(29)+1;
		  if(num==0)
		  {
			  num=1;
		  }
		  return num;
	  }
	  
	  public int getDateMonth()
	  {
		  Random random= new Random();
		  int num= random.nextInt(12)+1;
		  if(num==0)
		  {
			  num=1;
		  }
		  return num;
	  }
	  
	  public String getDateYear()
	  {
		  int max= 2005;
		  int min=1950;
		  Random r = new Random();
			int num= r.nextInt((max - min) + 1) + min;
			
			System.out.println("the year is -->"+num);
			return String.valueOf(num);
	  }

	//method to randomly generate string with length min 5 and max 15.
	public String getUsername(int n) 
		{ 
			int lowerLimit = 97; 
			int upperLimit = 122; 
            int min =5;
            if(min>n)
            {
            	n=min;
            }
			Random random = new Random(); 
			StringBuffer r = new StringBuffer(n); 

			for (int i = 0; i < n; i++) { 
				int nextRandomChar = lowerLimit + (int)(random.nextFloat() * (upperLimit - lowerLimit + 1)); 
				r.append((char)nextRandomChar); 
			} 
			return r.toString(); 
		} 
	
  public int getEmailNum()
  {
	  Random random= new Random();
	  return random.nextInt(10000)+1;
  }


  public int getCharSeqSize()
  {
	  Random random= new Random();
	 return random.nextInt(15)+1;
  }
  
  public static String getPh()
  {
	  Random random = new Random();
	  StringBuilder sb=new StringBuilder();
	  for(int i=0;i<=9;i++)
	  {
		  int num= random.nextInt(10);
		  Integer num1=num;
		  String snum=num1.toString();
		  sb.append(snum);
	  }
	  return sb.toString();
  }
  
  public static void main(String[] args)
  {
	 System.out.println( getPh());
  }
  
}