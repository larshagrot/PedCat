<%  
	java.util.Properties props = System.getProperties();
	
	java.util.Enumeration<String> e= (java.util.Enumeration<String>) props.propertyNames();
	out.println("<pre>");
	String[] keys = new String[props.size()];
	int i=0;
	while (e.hasMoreElements())
	{	
			keys[i++] = e.nextElement();
	}
	java.util.Arrays.sort(keys);
	for (String key: keys)
	{	
		out.println(key + ":" + props.getProperty(key));
	}
	
	
	System.gc();   
	Runtime rt = Runtime.getRuntime();
	long usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;  
	out.println("memory total " + rt.totalMemory()/1024/1024 +  " MB");
	out.println("memory free  " + rt.freeMemory()/1024/1024 +  " MB");
	out.println("memory usage " + usedMB +  " MB");
	
	out.println("</pre>");
	
%>

