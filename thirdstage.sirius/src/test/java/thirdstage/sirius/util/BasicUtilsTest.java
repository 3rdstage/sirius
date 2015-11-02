package thirdstage.sirius.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import thirdstage.sirius.util.BasicUtils;

public class BasicUtilsTest {
	@BeforeMethod
	public void beforeMethod() {
	}

	@AfterMethod
	public void afterMethod() {
	}

	@BeforeClass
	public void beforeClass() {
	}

	@AfterClass
	public void afterClass() {
	}


	@Test
	public void testGetClassPath() {

		Set<String> classpath = BasicUtils.getClassPath();
		for(String element: classpath){
			System.out.println(element);
		}

		Assert.assertTrue(true);
	}
	
	@Test
	public void testListFilesRecursive(){
		
		File dir = new File("C:\\WINDOWS\\system32\\drivers");
		List<File> files = BasicUtils.listFilesRecursive(dir);
		
		for(File f: files){
			System.out.println(f);
		}
		
		Collection<File> files2 = FileUtils.listFiles(dir, null, true);
		
		Assert.assertEquals(files2.size(), files.size());
	}
	
	@Test
	public void testGetClassNamesInClassPath(){
		
		List<String> names = BasicUtils.getClassNamesInClassPath();
		
		System.out.println("All classes in classpath : ");
		int cnt = 1;
		for(String name: names){
			System.out.printf("  %1$4d. %2$s%n", cnt++, name);
		}
		
		Assert.assertTrue(true);
	}
	
	/**
	 * <p>This test requires that the classpath includes <code>hsqldb-2.0.0.jar</code>
	 * and <code>aopalliance-1.0.jar</code>.
	 * 
	 */
	@Test
	public void testGetClassNamesInJar(){
		
		final String jar1 = "hsqldb-2.0.0.jar";
		final String jar2 = "aopalliance-1.0.jar";
		
		Set<String> classpath = BasicUtils.getClassPath();
		String str1 = null;
		String str2 = null;
		
		for(String str: classpath){
			if(str.endsWith(jar1)) str1 = str;
			else if(str.endsWith(jar2)) str2 = str;
			if(str1 != null && str2 != null) break;
		}
		
		if(str1 == null || str2 == null){
			throw new IllegalStateException("The necessary jar files - hsqldb-2.0.0.jar and aopalliance-1.0.jar - are not on classpath.");
		}
		
		int num1 = -1; // # of classes in jar1
		int num2 = -1; // # of classes in jar2
		try{
			String[] cmd1 = {"cmd", "/C", System.getenv("JAVA_HOME") + "\\bin\\jar", "-tvf", "\"" + str1 + "\"", "|", "find", "/c", "\".class\""};
			Process p1 = Runtime.getRuntime().exec(cmd1);
			num1 = Integer.valueOf(new BufferedReader(new InputStreamReader(p1.getInputStream())).readLine());
			System.out.printf("%1$d : %2$s%n", num1, StringUtils.join(cmd1, ' '));
			p1.destroy();			
			
			String[] cmd2 = {"cmd", "/C", System.getenv("JAVA_HOME") + "\\bin\\jar", "-tvf", "\"" + str2 + "\"", "|", "find", "/c", "\".class\""};
			Process p2 = Runtime.getRuntime().exec(cmd2);
			num2 = Integer.valueOf(new BufferedReader(new InputStreamReader(p2.getInputStream())).readLine());
			System.out.printf("%1$d : %2$s%n", num2, StringUtils.join(cmd2, ' '));
			p2.destroy();			
		}
		catch(Exception ex){
			ex.printStackTrace(System.err);
			return;
		}
		
		List<String> names1 = null;
		List<String> names2 = null;
		try{
			names1 = BasicUtils.getClassNamesInJar(new File(str1));
			names2 = BasicUtils.getClassNamesInJar(new File(str2));
			
			System.out.println("Classes in hsqldb-2.0.0.jar : ");
			int cnt = 1;
			for(String name1: names1){
				System.out.printf("  %1$4d. %2$s%n", cnt++, name1);
			}
			
			System.out.println("Classes in aopalliance-1.0.jar : ");
			cnt = 1;
			for(String name2: names2){
				System.out.printf("  %1$4d. %2$s%n", cnt++, name2);
			}
			
			Assert.assertEquals(num1, names1.size(), 
					"The number of classes in " + jar1 + " is " + num1);
			Assert.assertEquals(num2, names2.size(), 
					"The number of classes in " + jar2 + " is " + num2);
		}
		catch(IOException ex){
			Assert.assertTrue(false);
			ex.printStackTrace(System.err);
			
		}
		
		return;
	}
	
	
	@Test
	public void testRumtimeExecOnWindows1() throws Exception{
		
		String[] cmd = {"cmd", "/C", "dir", "c:\\", "|", "find", "/c", "\".log\""};
		
		Process p = Runtime.getRuntime().exec(cmd);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
		while((line = br.readLine()) != null){
			System.out.println(line);
		}
		
		p.destroy();	
		
	}
}
