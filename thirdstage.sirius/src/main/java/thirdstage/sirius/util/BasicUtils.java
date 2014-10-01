package thirdstage.sirius.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.apache.commons.io.FileUtils;

/**
 * 
 * @author Sangmoon Oh
 * @versio 0.8, Sangmoon Oh, 2011-07-01, Added getNameClassPairsUnderContext
 */
public class BasicUtils {
	
	public static Set<String> getClassPath(){
		
		String classpath = System.getProperty("java.class.path");
		StringTokenizer st = new StringTokenizer(classpath, File.pathSeparator); 
		
		Set<String> result = new HashSet<String>();
		while(st.hasMoreElements()){
			result.add(st.nextToken());
		}
		
		return result;
	}
	
	
	public static List<File> listFilesRecursive(File directory){
		
		if(directory == null) throw new IllegalArgumentException("null is unacceptable.");
		if(!directory.isDirectory()) throw new IllegalArgumentException("Specified one is not a directory.");
		
		List<File> files = new ArrayList<File>();
		File[] fs = null;
		try{
			fs = directory.listFiles();
		}
		catch(SecurityException ex){
			return files;
		}

		for(File f: fs){
			if(f.isFile()) files.add(f);
			else if(f.isDirectory()) files.addAll(listFilesRecursive(f));
		}
			
		return files;
	}
	
	
	/**
	 * @return
	 * @throws SecurityException
	 */
	public static List<String> getClassNamesInClassPath(){
		
		List<String> names = new ArrayList<String>();
		Set<String> classpath = BasicUtils.getClassPath();
		
		File f= null;
		int len = 0;
		String name = null;
		Collection<File> files = null;
		for(String pathElement: classpath){
			f = new File(pathElement);
			
			if(!f.exists()){ continue; }
			if(f.isDirectory()){
				len = pathElement.length();
				files = FileUtils.listFiles(f, new String[]{"class"}, true);
				for(File f2: files){
					name = f2.getPath().substring(len + 1);
					names.add(name.substring(0, name.length() - 6).replace(File.separatorChar, '.'));
				}
			}
			else if(f.isFile() && pathElement.endsWith("jar")){
				try{
					names.addAll(BasicUtils.getClassNamesInJar(f));
				}
				catch(IOException ex){ //do nothing and just skip
				}
			}
			else if(f.isFile() && pathElement.endsWith("zip")){
				//@todo
			}
		}
		
		return names;
	}
	
	public static List<String> getClassNamesUnderDirectory(File file) throws IOException, SecurityException, IllegalArgumentException{
		//@todo
		return null;
	}

	
	/**
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws SecurityException
	 * @throws IllegalArgumentException if specified <code>file</code> is <code>null</code>
	 */
	public static List<String> getClassNamesInJar(File file) throws IOException, SecurityException, IllegalArgumentException{
		if(file == null) throw new IllegalArgumentException("Null can't be specified.");
		
		List<String> names = new ArrayList<String>();
		
		JarFile jf = new JarFile(file);
		Enumeration<JarEntry> entries = jf.entries();
		JarEntry entry = null;
		String name = null;
		while(entries.hasMoreElements()){
			entry = entries.nextElement();
			if(!entry.isDirectory()){
				name = entry.getName();
				if(name.endsWith(".class")){
					names.add(name.substring(0, name.length() - 6).replace('/', '.'));
				}
			}
		}
		
		return names;
	}
	
	
	public static List<String> getClassNamesInZip(File file) throws IOException, SecurityException, IllegalArgumentException{
		
		//@todo
		return null;
	}
	
	
	
	/**
	 * Add a directory or a file to the classpath of system class loader. 
	 * 
	 * 
	 * @param f the File object representing a file or a directory to add into the current classpath. 
	 * @throws RuntimeException if there's any problem to change classpath
	 */
	public static void addElementToClassPath(File f){
		
		try{
			URLClassLoader sysloader = (URLClassLoader)(ClassLoader.getSystemClassLoader());
			Class<URLClassLoader> clazz = URLClassLoader.class;
		
			Method method = clazz.getDeclaredMethod("addURL", URL.class);
			method.setAccessible(true);
			method.invoke(sysloader, f.toURI().toURL());
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	public static void printEnv(OutputStream os) throws Exception{

		Map<String, String> map = System.getenv();
		Set<String> keys = map.keySet();
		PrintWriter pw = null;

		try{
			pw = new PrintWriter(os, true);

			for(String key : keys){
				pw.println("" + key + "=" + map.get(key));
			}
		}catch(Exception ex){
			throw ex;
		}finally{
			if(pw != null && os != (OutputStream)(System.out)) pw.close();
		}
	}

	public static void printSystemProperties(OutputStream os) throws Exception{
		Map props = System.getProperties();

		Set keys = props.keySet();
		List keyList = new ArrayList(keys);
		Collections.sort(keyList);
		PrintWriter pw = null;

		try{
			pw = new PrintWriter(os, true);

			String key = null;
			for(Iterator itr = keyList.iterator(); itr.hasNext();){ // for JDK 1.4
				key = (String) (itr.next());
				pw.println("" + key + "=" + props.get(key));
			}
		}catch(Exception ex){
			throw ex;
		}finally{
			if(pw != null && os != (OutputStream)(System.out)){
				pw.close();
			}
		}
	}

	public static void printAvailableCharsets(OutputStream os) throws Exception{
		Map<String, Charset> charsets = Charset.availableCharsets();

		Set<String> keys = charsets.keySet();
		List<String> keyList = new ArrayList(keys);
		Collections.sort(keyList);
		
		Set<String> aliases = null;
		PrintWriter pw = null;

		try{
			pw = new PrintWriter(os, true);
			pw.println("# of available charsets : " + charsets.size());
			
			for(String key : keyList){ // from JDK 5
				aliases = charsets.get(key).aliases();
				pw.print(charsets.get(key).displayName() + " : ");
				for(String alias : aliases){
					pw.print(alias + ", ");
				}
				pw.println("");
			}
		}catch(Exception ex){
			throw ex;
		}finally{
			if(pw != null && os != (OutputStream)(System.out)) pw.close();
		}

	}

	public static void printAvailableLocales(OutputStream os) throws Exception{
		Locale[] locales = Locale.getAvailableLocales();

		String name = null, country = null, lang = null, currencySb = null;
		String countryCd = null, langCd = null, currencyCd = null;
		Currency currency = null;

		PrintWriter pw = null;

		try{
			pw = new PrintWriter(os, true);

			for(Locale locale : locales){
				name = locale.getDisplayName();
				country = locale.getDisplayCountry();
				countryCd = locale.getISOCountries()[0];
				lang = locale.getDisplayLanguage();
				langCd = locale.getISOLanguages()[0];

				try{
					currency = Currency.getInstance(locale);
					currencySb = currency.getSymbol();
					currencyCd = currency.getCurrencyCode();
				}catch(IllegalArgumentException ex){
					currencySb = "";
					currencyCd = "";
				}

				pw.print("name : " + name + ", ");
				pw.print("country : " + country + "(" + countryCd + "), ");
				pw.print("lang : " + lang + "(" + langCd + "), ");
				pw.println("currency : " + currencySb + "(" + currencyCd + ")");
			}
		}catch(Exception ex){
			throw ex;
		}finally{
			if(pw != null && os != (OutputStream)(System.out)) pw.close();
		}

	}
	
	/**
	 * Retrieve all name class pairs recursively under the given naming context.
	 *  
	 * @return
	 * @since 0.8
	 */
	public static Map<String, NameClassPair> getNameClassPairsUnderContext(String name, Context cntx) throws NamingException{
		
		if(cntx == null) throw new IllegalArgumentException("Context shouldn't be null.");
		
		Map<String, NameClassPair> result = new HashMap<String, NameClassPair>();  
		NamingEnumeration<NameClassPair> pairs = cntx.list("");
		NameClassPair pair = null;
		Object obj = null;
		String path = null;
		while(pairs.hasMore()){
			pair = pairs.next();
			obj = cntx.lookup(pair.getName());
			path = name + "/" + pair.getName();
			
			if(obj instanceof javax.naming.Context){
				result.put(path, pair);
				result.putAll(getNameClassPairsUnderContext(path, (Context)obj));
			}
			else{ result.put(path, pair);}
		}
		
		return result;
	}
	

	/**
	 * Gets list of ancestor class loaders of specified class loader up to SystemClassLoader.
	 * <p>The list includes given class loader at the last index and
	 * SystemClassLoader at the first index.
	 * 
	 * @param cl
	 * @return
	 */
	public static List<ClassLoader> getAncestorClassLoadersUptoSystemClassLoader(ClassLoader cl){
		
		if(cl == null) throw new IllegalArgumentException("");
		
		ClassLoader sysClassLoader = cl.getSystemClassLoader();
		
		List<ClassLoader> ancestors = new ArrayList<ClassLoader>();
		ClassLoader current = cl;

		while(current != null && !current.equals(sysClassLoader)){
			ancestors.add(current);
			current = current.getParent();
		}
		ancestors.add(sysClassLoader);
		
		Collections.reverse(ancestors);
		return ancestors;
	}
	
	
	/**
	 * Gets list of ancestor class loaders of specified class loader up to 
	 * bootstrap class loader or null.
	 * <p>The list includes given class loader at the last index and
	 * the top most class loader at the first index.
	 * 
	 * @param cl
	 * @return
	 */
	public static List<ClassLoader> getAncestorClassLoaders(ClassLoader cl){
		
		if(cl == null) throw new IllegalArgumentException("");
		
		List<ClassLoader> ancestors = new ArrayList<ClassLoader>();
		ClassLoader current = cl;

		while(current != null){
			ancestors.add(current);
			System.out.println("ClassLoader Added : " + current.toString());
			current = current.getParent();
		}
		
		Collections.reverse(ancestors);
		return ancestors;
	}
	
}
