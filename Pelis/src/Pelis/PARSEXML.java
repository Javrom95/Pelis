package Pelis;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PARSEXML {

	Visual visual = new Visual();

	private String id;

	public Visual getDataToSee(Document doc) {
		try {
			NodeList movieList = doc.getElementsByTagName("movie");
			Node p = movieList.item(0);
			
			if (p.getNodeType() == Node.ELEMENT_NODE) {
				Element movie = (Element) p;

				visual.setTitle(movie.getAttribute("title"));
				visual.setType(movie.getAttribute("type"));
				visual.setDate(movie.getAttribute("year"));
				visual.setLenght(movie.getAttribute("runtime"));
				visual.setGenre(movie.getAttribute("genre"));
				visual.setSynopsis(movie.getAttribute("plot"));
				visual.setLanguage(movie.getAttribute("language"));
				visual.setDirector(movie.getAttribute("director"));
				visual.setActors(movie.getAttribute("actors"));
			}

		} catch (NullPointerException e) {
			System.out.println("Error 404 Not Found.");
		}

		return visual;
	}

	public void getDataToWrite(Document doc) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document docu = builder.newDocument();
			Element root = docu.createElement("movie");	
			docu.appendChild(root);
			
			NodeList movieList = doc.getElementsByTagName("movie");
			Node p = movieList.item(0); 
			

			if (p.getNodeType() == Node.ELEMENT_NODE) {
				Element movie = (Element) p;
				
				root.setAttribute("title", movie.getAttribute("title"));
				root.setAttribute("type",  movie.getAttribute("type"));
				root.setAttribute("year", movie.getAttribute("year"));
				root.setAttribute("runtime", movie.getAttribute("runtime"));
				root.setAttribute("genre", movie.getAttribute("genre"));
				root.setAttribute("plot", movie.getAttribute("plot"));
				root.setAttribute("language", movie.getAttribute("language"));
				root.setAttribute("director", movie.getAttribute("director"));
				root.setAttribute("actors", movie.getAttribute("actors"));
				
			}

			
			DOMSource source = new DOMSource(docu);	
			Result result = new StreamResult("resources/movie.xml");
			
				TransformerFactory transf = TransformerFactory.newInstance();
				Transformer transformer = transf.newTransformer();
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.transform(source, result);
			
				System.out.println("Archivo escrito.");
				

				
			

		} catch (NullPointerException e) {
			System.out.println("Error 404 Not Found.");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		PARSEXML read = new PARSEXML();
		RESTAPI api = new RESTAPI();
		//Visual visual = read.getDataToSee(api.transformXML());
		//System.out.println(visual.toString());
		read.getDataToWrite(api.transformXML());
		
	}

}
