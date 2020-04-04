package com.lrhb.utils;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

/**
 * UUID工具类
 */
public class UUIDUtil {

    /**
     * 产生紧凑型32位UUID
     * @return
     */
    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
    
    public static String getKuponoNum(String type){
    	StringBuilder sb = new StringBuilder();
    	String province ="37";
    	String city = "14";
    	String year = String.valueOf(LocalDate.now().getYear());
    	String squence =String.valueOf((int)(Math.random()*9000)+1000);
    	sb.append(year);
    	sb.append(province);
    	sb.append(city);
    	sb.append(type);
    	sb.append(squence);
        return sb.toString();
    }
    
    public static String PlanNum(){
    	StringBuilder sb = new StringBuilder();
    	String province ="37";
    	String city = "14";
    	String year = String.valueOf(LocalDate.now().getYear());
    	String squence =String.valueOf((int)(Math.random()*900000)+100000);
    	sb.append(year);
    	sb.append(province);
    	sb.append(city);
    	sb.append(squence);
        return sb.toString();
    }
    public static void main(String[] args) {
		System.out.println(LocalDate.now().getYear());

	}
}
