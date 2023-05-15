import ch.rolandschaer.ascrblr.scrobbler.AudioscrobblerService;
import ch.rolandschaer.ascrblr.data.*;
import ch.rolandschaer.ascrblr.util.ServiceException;
import java.util.*;
import java.net.*;
import java.io.*;
import javax.xml.parsers.ParserConfigurationException;
import org.jdom.JDOMException;

public class AlbumDownloader {

    org.jdom.Document doc;

    String album;

    String artist;

    public AlbumDownloader(String album, String artist) throws ServiceException {
        try {
            this.album = album;
            this.artist = artist;
            AudioscrobblerService service = new AudioscrobblerService();
            Feed f = service.getFeed(new AlbumFeed.Info(artist, album));
            java.io.InputStream instream = getXMLStream(f.getUrl());
            doc = toJDOM(instream);
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (JDOMException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean saveArt(String path) {
        try {
            File file = AlbumHelper.songbirdToJavaFile(path, artist + " - " + album, "jpg");
            org.jdom.Element e = doc.getRootElement();
            org.jdom.Namespace ns = e.getNamespace();
            e = e.getChild("coverart", ns).getChild("large", ns);
            System.out.println("Attempting download");
            return download(new URL(e.getText()), file);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean download(URL url, File file) {
        OutputStream out = null;
        URLConnection conn = null;
        InputStream in = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(file));
            conn = url.openConnection();
            in = conn.getInputStream();
            byte[] buffer = new byte[4096];
            int numRead;
            long numWritten = 0;
            while ((numRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, numRead);
                numWritten += numRead;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException ioe) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<String> getTracksForAlbum() {
        AudioscrobblerService service = new AudioscrobblerService();
        try {
            org.jdom.Element e = doc.getRootElement();
            org.jdom.Namespace ns = e.getNamespace();
            List<org.jdom.Element> elementList = e.getChild("tracks", ns).getChildren("track");
            ArrayList<String> trackList = new ArrayList<String>();
            for (org.jdom.Element element : elementList) {
                trackList.add(element.getAttributeValue("title"));
            }
            return trackList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }

    public static org.jdom.Document toJDOM(java.io.InputStream in) throws org.jdom.JDOMException, java.io.IOException {
        org.jdom.input.SAXBuilder builder = new org.jdom.input.SAXBuilder();
        return builder.build(in);
    }

    public static java.io.InputStream getXMLStream(String url) throws java.io.IOException {
        java.net.URL u = new java.net.URL(url);
        java.net.URLConnection conn = u.openConnection();
        return conn.getInputStream();
    }
}
