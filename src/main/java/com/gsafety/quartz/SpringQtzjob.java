package com.gsafety.quartz;  

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class SpringQtzjob {

	
	@Autowired
	private  EquipText EquipText;
	private static int counter = 0;  
    protected void execute() throws UnsupportedEncodingException, Exception  {  
    	
    	EquipText.init();
        long ms = System.currentTimeMillis();  
        System.out.println("\t\t" + new Date(ms));  
        System.out.println("(" + counter++ + ")");  
    }  
}
