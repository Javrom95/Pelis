package Pelis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.w3c.dom.Document;

public class RESTAPI {

	private String finalURL, Name, Type;
	private URLConnection conn;
	private Document doc = null;
	private Scanner sc = new Scanner(System.in);

	private String Name() {
		System.out.println("Write the name of the title:");
		Name = sc.nextLine();
		return Name;
	}

	private String Type() {
		System.out.println("series or movie ?");
		Type = sc.nextLine();
		return Type;
	}

	// Gets the name of the Movie or TV program and adds it to the final U.R.L.
	public String formURL(String name, String type) {
		name = name.replace(' ', '+');
		finalURL = "http://www.omdbapi.com/?t=" + name + "&y=&plot=full&type=" + type + "&r=xml";
		return finalURL;
	}

	// Gets the connection to the A.P.I. with the U.R.L. previously formed.
	private InputStream getXMLInputStream(String urlString) {
		URL url = null;
		InputStream stream = null;

		try {
			url = new URL(urlString);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.connect();

			stream = connection.getInputStream();


		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stream;
	}

	
	public Document transformXML() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = (Document) builder.parse(getXMLInputStream(formURL(Name(), Type())));
		} catch (Exception e) {
			System.out.println("Error in transformX.M.L.");
			e.printStackTrace();
		}
		return doc;
	}

}
