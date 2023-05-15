import java.io.File;
import java.lang.String;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
import java.net.*;
import java.io.*;
import org.xml.sax.InputSource;
import java.net.URL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

class URLGrabber {

    public static InputStream getDocumentAsInputStream(URL url) throws IOException {
        InputStream in = url.openStream();
        return in;
    }

    public static InputStream getDocumentAsInputStream(String url) throws MalformedURLException, IOException {
        URL u = new URL(url);
        return getDocumentAsInputStream(u);
    }

    public static String getDocumentAsString(URL url) throws IOException {
        StringBuffer result = new StringBuffer();
        InputStream in = url.openStream();
        int c;
        while ((c = in.read()) != -1) result.append((char) c);
        return result.toString();
    }

    public static String getDocumentAsString(String url) throws MalformedURLException, IOException {
        URL u = new URL(url);
        return getDocumentAsString(u);
    }

    public static Document stringToDom(String xmlSource) throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlSource)));
    }

    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            try {
                String doc = URLGrabber.getDocumentAsString(args[i]);
                System.out.println(doc);
            } catch (MalformedURLException e) {
                System.err.println(args[i] + " cannot be interpreted as a URL.");
            } catch (IOException e) {
                System.err.println("Unexpected IOException: " + e.getMessage());
            }
        }
    }
}

public class RSS_parser_work {

    /**
	public static RSS_data[] parse_RSS_data() {
		try {
			System.out.println("Start RSS Parser");
			DocumentBuilderFactory document_builder_factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder document_builder = document_builder_factory.newDocumentBuilder();
			Document document = 
					document_builder.parse(
							new InputSource(
									new StringReader(
											URLGrabber.getDocumentAsString("http://www.comune.torino.it/cgi-bin/torss/rssfeed.cgi?id=93"))));
			document.getDocumentElement().normalize();
			System.out.println("Root element : " + document.getDocumentElement().getNodeName());
			System.out.println("title : " + document.getDocumentElement().getAttribute("title"));
			System.out.println("pubDate : " + document.getDocumentElement().getAttribute("pubDate"));
			NodeList node_list = document.getElementsByTagName("item");
			System.out.println("Information of RSS item");
			int node_number = node_list.getLength();
			System.out.println("Found " + node_number + " elements.");
			RSS_data[] result		= new RSS_data[node_number];
			RSS_data rss_unit		= null;
			String title			= "0";
			String link				= "0";
			String category			= "0";
			String lat				= "0";
			String lng				= "0";
			String description		= "0";
			Date pubDate			= new Date();
			for (int i = 0; i < node_number; i++) {
					Node node = node_list.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
							Element element = (Element)node;
							System.out.println("	 Element TagName: " + element.getTagName());
							
							System.out.println(">>>>>>>>>>" + node.getParentNode().getNodeName());  //channel
							System.out.println(">>>>>>>>>>" + node.getNodeName());								  //item
							System.out.println(">>>>>>>>>>" + node.getNodeValue());								 //
							//node.normalize();
							System.out.println(">>>>>>>>>>" + node.getChildNodes().getLength());	//11
							System.out.println(">>>>>>>>>>" + node.getFirstChild().getNodeName());  //#text
							System.out.println(">>>>>>>>>>" + node.getFirstChild().getNodeValue());
							System.out.println(">>>>>>>>>>" + node.getLastChild().getNodeValue());
							
							title					= element.getAttribute("title");
							link					= element.getAttribute("link");
							category				= element.getAttribute("category");
							//pubDate				= element.getAttribute("pubDate");
							description				= element.getAttribute("description");
							System.out.println("	title: " + title);
							System.out.println("	link: " + link);
							System.out.println("	category: " + category);
							//System.out.println("  pubDate: " +pubDate);
							System.out.println("	description: " + description);
							rss_unit = new RSS_data(
									title,  link,
									category, description, 
									new Double(lat), new Double(lng), 
									pubDate
									);
							result[i] = rss_unit;
							System.out.println("	---------------------------");
					}
			}
		return result;
		}
		catch (Exception e) {
				e.printStackTrace();
				return null;
		}
	}
	*/
    public static Hashtable parse_RSS_news_data() throws javax.xml.parsers.ParserConfigurationException, org.xml.sax.SAXException, java.io.IOException {
        System.out.println("Start RSS Parser");
        DocumentBuilderFactory document_builder_factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder document_builder = document_builder_factory.newDocumentBuilder();
        Document document = document_builder.parse(new InputSource(new StringReader(URLGrabber.getDocumentAsString("http://www.comune.torino.it/cgi-bin/torss/rssfeed.cgi?id=93"))));
        Node node;
        Node a_node;
        Node an_inner_node;
        Node an_inner_inner_node;
        NodeList node_list;
        NodeList inner_node_list;
        NodeList inner_inner_node_list;
        node = document.getFirstChild();
        node_list = node.getChildNodes();
        String channel_title = "";
        String channel_link = "";
        String channel_description = "";
        String channel_copyright = "";
        String channel_pubDate = "";
        String channel_category = "";
        String item_title = "";
        String item_link = "";
        String item_pubDate = "";
        String item_description = "";
        String unit = "";
        String lat = "0";
        String lng = "0";
        Hashtable rss_map = new Hashtable();
        ArrayList result = new ArrayList();
        System.out.println("[0] - [" + node.getNodeName() + "]");
        for (int i = 0; i < node_list.getLength(); i++) {
            a_node = node_list.item(i);
            System.out.println("  [1] - [" + a_node.getNodeName() + "]");
            if (a_node.getNodeType() == Node.ELEMENT_NODE) {
                inner_node_list = a_node.getChildNodes();
                for (int j = 0; j < inner_node_list.getLength(); j++) {
                    an_inner_node = inner_node_list.item(j);
                    if ((!an_inner_node.getNodeName().equals("item")) && (!an_inner_node.getNodeName().equals("#text"))) {
                        System.out.println("    [2] " + an_inner_node.getNodeName() + ": " + an_inner_node.getTextContent());
                        if (an_inner_node.getNodeName().equals("title")) channel_title = an_inner_node.getTextContent();
                        if (an_inner_node.getNodeName().equals("link")) channel_link = an_inner_node.getTextContent();
                        if (an_inner_node.getNodeName().equals("description")) channel_description = an_inner_node.getTextContent();
                        if (an_inner_node.getNodeName().equals("copyright")) channel_copyright = an_inner_node.getTextContent();
                        if (an_inner_node.getNodeName().equals("pubDate")) channel_pubDate = an_inner_node.getTextContent();
                        if (an_inner_node.getNodeName().equals("category")) channel_category = an_inner_node.getTextContent();
                    }
                    if (an_inner_node.getNodeName().equals("item")) {
                        System.out.println("    [2] - [" + an_inner_node.getNodeName() + "]");
                    }
                    if (an_inner_node.hasChildNodes() && (an_inner_node.getNodeName().equals("item"))) {
                        inner_inner_node_list = an_inner_node.getChildNodes();
                        for (int k = 0; k < inner_inner_node_list.getLength(); k++) {
                            an_inner_inner_node = inner_inner_node_list.item(k);
                            if (!(an_inner_inner_node.getTextContent().equals(""))) {
                                if (!(an_inner_inner_node.getNodeName().equals("#text")) && !(an_inner_inner_node.getNodeName().equals("link"))) {
                                    System.out.println("        [3] " + an_inner_inner_node.getNodeName() + ": " + an_inner_inner_node.getTextContent());
                                    if (an_inner_inner_node.getNodeName().equals("title")) item_title = an_inner_inner_node.getTextContent();
                                    if (an_inner_inner_node.getNodeName().equals("pubDate")) item_pubDate = an_inner_inner_node.getTextContent();
                                    if (an_inner_inner_node.getNodeName().equals("description")) item_description = an_inner_inner_node.getTextContent();
                                }
                                if (an_inner_inner_node.getNodeName().equals("link")) {
                                    String tmp_link = an_inner_inner_node.getTextContent();
                                    tmp_link = tmp_link.replace("http://www.torinocultura.it/servizionline/memento/include.php?urlDest=", "");
                                    System.out.println("        [3] " + an_inner_inner_node.getNodeName() + ": " + tmp_link);
                                    if (an_inner_inner_node.getNodeName().equals("link")) item_link = an_inner_inner_node.getTextContent();
                                }
                            }
                        }
                        unit = "Unit data: [" + item_title + " - " + item_pubDate + " - " + item_description + " - " + item_link + "]";
                        result.add(unit);
                        rss_map.put(item_title, unit);
                    }
                    System.out.println("--------------");
                }
            }
        }
        return rss_map;
    }

    public static void main(String[] args) throws javax.xml.parsers.ParserConfigurationException, org.xml.sax.SAXException, java.io.IOException {
        System.out.println(":: Snippet Code parser ::");
        System.out.println("Parsed " + parse_RSS_news_data().size() + " news");
    }
}
