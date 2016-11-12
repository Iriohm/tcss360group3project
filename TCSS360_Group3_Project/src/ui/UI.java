package ui;

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.Date;

public class UI {
static String header = "AuctionCentral: the auctioneer for non-profit organizations.";
static Format formatter = new SimpleDateFormat("MMMM dd, yyyy"); 
static Date todayDate = new Date();
static String currentDay = formatter.format(todayDate);

}
