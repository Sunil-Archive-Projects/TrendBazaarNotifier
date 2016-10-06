//Author : Sunil L
//Email : sunilandroidnayak@gmail.com

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public class Trend_Bazaar_Notif
{
	static public WebDriver driver;
	static public Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

//	favorite zerodha scripts with 10x leverage
	static public String[] x10 = 
		{
		"BANKBEES","BHARATFORG",
		"CAIRN","CEATLTD",
		"FEDERALBNK",
		"GOLDBEES",
		"HEXAWARE","HINDALCO","HINDZINC",
		"INFRATEL",
		"NCC","NIFTYBEES",
		"ORIENTBANK",
		"PFC",
		"RELINFRA",
		"SUNTV",
		"TATAMOTORS","TATASTEEL","TVSMOTOR",
		"WOCKPHARMA"
		};

	//favorite zerodha scripts with 11x leverage
	static public String[] x11 = 
		{
		"ADANIPORTS","ALBK","AMBUJACEM","ANDHRABANK",
		"APOLLOTYRE","ARVIND","ASHOKLEY","AUROPHARMA","AXISBANK",
		"BANKBARODA","BANKINDIA","BATAINDIA","BHARTIARTL","BHEL","BPCL",
		"CANBK","CESC","CIPLA","COALINDIA","COLPAL","CROMPGREAV",
		"DABUR","DISHTV",
		"EICHERMOT","ENGINERSIN","EXIDEIND",
		"GAIL","GODREJIND",
		"HAVELLS","HDFC","HINDPETRO","HINDUNILVR",
		"ICICIBANK","IDEA","INFY","IOC","IRB","ITC",
		"JUBLFOOD",
		"KTKBANK",
		"LICHSGFIN","LT",
		"M&MFIN","MARUTI","MINDTREE",
		"NHPC","NMDC",
		"OIL","ONGC",
		"PETRONET","PNB",
		"RELCAPITAL","SBIN",
		"TATACHEM","TATACOMM","TATAMTRDVR","TECHM","TITAN",
		"UNIONBANK","UPL",
		"VOLTAS",
		"WIPRO",
		"ZEEL"
		};


	
	public static void main(String[] args) 
	{
		setupLogger();

		loginToTrendBazaar();

		//run the below three in loop if you want to get notifications regularly
		getStatUpdates();
		
		getUptrendUpdates();
		
		getDowntrendUpdates();
	
		sendNotifications();

		driver.quit();


	}


	//navigate to TrendBazaar
	static void loginToTrendBazaar()
	{
		File file = new File("../phantomjs.exe");				
		System.setProperty("phantomjs.binary.path", file.getAbsolutePath());		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setJavascriptEnabled(true);
		capabilities.chrome();
		capabilities.setCapability("takesScreenshot", false);
		driver = new PhantomJSDriver(capabilities);
		String url = "http://www.bazaartrend.com/nsecharts/intraday-free.php?";
		driver.get(url);

	}

	static void setupLogger()
	{
		// to write log into file
		FileHandler fh;
		// initialize logger object
		try 
		{
			// true flag in constructor to set append into log as true
			fh = new FileHandler("./trend_log.log", true);
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

			// begin logging
			logger.info("\n-----------------------------\n");
		}

		catch (Exception e) 
		{
			e.printStackTrace();
		}

	}

	static void getStatUpdates()
	{        
		WebElement equityNewsTable = driver.findElement(By.id("nseeq-actionwatch"));

		String newsSet = equityNewsTable.getText();

		String[] news = newsSet.split("\n");
		
		System.out.println("\n--------------------\n\tNews\n--------------------");


		for(int i=0;i<news.length;i++)
		{
			if(checkIfLeverageable(news[i]))
			{
				System.out.println(news[i] + " -Leverage");
				//logger.info(news[i] + " -news");
			}
		}
	}
	
	static void getUptrendUpdates()
	{        
		WebElement equityBullishTable = driver.findElement(By.id("nseeq-slow-bullish"));

		String bullSet = equityBullishTable.getText();

		String[] bull = bullSet.split("\n");
		
		System.out.println("\n--------------------\n\tBull\n--------------------");

		for(int i=0;i<bull.length;i++)
		{
			if(checkIfLeverageable(bull[i]))
			{
				System.out.println(bull[i] + " -Bull");
				//logger.info(bull[i] + " -Bull");
			}
		}
		
		System.out.println("\n--------------------\n");
	}
	
	static void getDowntrendUpdates() 
	{
		WebElement equityBearishTable = driver.findElement(By.id("nseeq-slow-bearish"));

		String bearSet = equityBearishTable.getText();

		String[] bear = bearSet.split("\n");
		
		System.out.println("\n--------------------\n\tBear\n--------------------");

		for(int i=0;i<bear.length;i++)
		{
			if(checkIfLeverageable(bear[i]))
			{
				System.out.println(bear[i] + " -bear");
				//logger.info(bear[i] + " -bear");
			}
		}
		
		System.out.println("\n--------------------\n");
		
	}

	static Boolean checkIfLeverageable(String newsString)
	{

		for(int i=0;i<x11.length;i++)
		{
			if(newsString.startsWith(x11[i] + " "))
			{
				return true;
			}

		}

		for(int i=0;i<x10.length;i++)
		{
			if(newsString.startsWith(x10[i]))
			{
				return true;
			}
		}
		
		return false;


	}

	static void sendNotifications()
	{
		//will push into pushbullet
	}



}
