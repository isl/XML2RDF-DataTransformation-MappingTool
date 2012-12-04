/* 
 *  COPYRIGHT (c) 2010-2012 by Institute of Computer Science, 
 *  Foundation for Research and Technology - Hellas
 *  Contact: 
 *      POBox 1385, Heraklio Crete, GR-700 13 GREECE
 *      Tel:+30-2810-391632
 *      Fax: +30-2810-391638
 *      E-mail: isl@ics.forth.gr
 *      http://www.ics.forth.gr/isl
 *
 *   Authors :  Maria Koutraki, Dimitra Zografistou, Evangelia Daskalaki, Elias Zabetakis
 *
 *   This file is part of XML2RDF Data Transformation Tool (Mapping Tool).
 *
 *    XML2RDF Data Transformation Tool (Mapping Tool) is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *   XML2RDF Data Transformation Tool (Mapping Tool) is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with XML2RDF Data Transformation Tool (Mapping Tool).  If not, see <http://www.gnu.org/licenses/>.
 * 
 */



 

/*
 * To change this template, choose Tools , Templates
 * and open the template in the editor.
 */
package URIidevelopment;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Dimitra
 */
public class URIPolicies {

    private String urn;
    private String crmISO;
    private ArrayList<String> tem;
    final static String[] hex = {
        "%00", "%01", "%02", "%03", "%04", "%05", "%06", "%07",
        "%08", "%09", "%0a", "%0b", "%0c", "%0d", "%0e", "%0f",
        "%10", "%11", "%12", "%13", "%14", "%15", "%16", "%17",
        "%18", "%19", "%1a", "%1b", "%1c", "%1d", "%1e", "%1f",
        "%20", "%21", "%22", "%23", "%24", "%25", "%26", "%27",
        "%28", "%29", "%2a", "%2b", "%2c", "%2d", "%2e", "%2f",
        "%30", "%31", "%32", "%33", "%34", "%35", "%36", "%37",
        "%38", "%39", "%3a", "%3b", "%3c", "%3d", "%3e", "%3f",
        "%40", "%41", "%42", "%43", "%44", "%45", "%46", "%47",
        "%48", "%49", "%4a", "%4b", "%4c", "%4d", "%4e", "%4f",
        "%50", "%51", "%52", "%53", "%54", "%55", "%56", "%57",
        "%58", "%59", "%5a", "%5b", "%5c", "%5d", "%5e", "%5f",
        "%60", "%61", "%62", "%63", "%64", "%65", "%66", "%67",
        "%68", "%69", "%6a", "%6b", "%6c", "%6d", "%6e", "%6f",
        "%70", "%71", "%72", "%73", "%74", "%75", "%76", "%77",
        "%78", "%79", "%7a", "%7b", "%7c", "%7d", "%7e", "%7f",
        "%80", "%81", "%82", "%83", "%84", "%85", "%86", "%87",
        "%88", "%89", "%8a", "%8b", "%8c", "%8d", "%8e", "%8f",
        "%90", "%91", "%92", "%93", "%94", "%95", "%96", "%97",
        "%98", "%99", "%9a", "%9b", "%9c", "%9d", "%9e", "%9f",
        "%a0", "%a1", "%a2", "%a3", "%a4", "%a5", "%a6", "%a7",
        "%a8", "%a9", "%aa", "%ab", "%ac", "%ad", "%ae", "%af",
        "%b0", "%b1", "%b2", "%b3", "%b4", "%b5", "%b6", "%b7",
        "%b8", "%b9", "%ba", "%bb", "%bc", "%bd", "%be", "%bf",
        "%c0", "%c1", "%c2", "%c3", "%c4", "%c5", "%c6", "%c7",
        "%c8", "%c9", "%ca", "%cb", "%cc", "%cd", "%ce", "%cf",
        "%d0", "%d1", "%d2", "%d3", "%d4", "%d5", "%d6", "%d7",
        "%d8", "%d9", "%da", "%db", "%dc", "%dd", "%de", "%df",
        "%e0", "%e1", "%e2", "%e3", "%e4", "%e5", "%e6", "%e7",
        "%e8", "%e9", "%ea", "%eb", "%ec", "%ed", "%ee", "%ef",
        "%f0", "%f1", "%f2", "%f3", "%f4", "%f5", "%f6", "%f7",
        "%f8", "%f9", "%fa", "%fb", "%fc", "%fd", "%fe", "%ff"
    };
    ArrayList<String> uniqueEvents = new ArrayList<String>();

    public URIPolicies(ArrayList<String> tem) {
        this.tem = tem;
        urn = "urn";
        crmISO = "iso21127";

        addUniqueEvents();
    }

    private void addUniqueEvents() {
        uniqueEvents.add("Birth");
        uniqueEvents.add("Transformation");
        uniqueEvents.add("Production");
        uniqueEvents.add("Creation");
        uniqueEvents.add("Formation");
        uniqueEvents.add("Destruction");
        uniqueEvents.add("Dissolution");
        uniqueEvents.add("Death");
        uniqueEvents.add("Transformation");
    }

    /**
     * returns a URI for the mainly described object.
     *
     * @param className the name of the corresponding CIDOC class
     * @param nameOfMuseum the name of the organization keeping the object.
     * @param entry the id given to the object by the organization
     * @return the URI of the form
     * "urn:iso21127:(Main_Object)nameOfMuseum:entry" (e.g.
     * "urn:iso21127:(Main_Object)Germanic_National_Museum,_Graphical_Collection_(Nuremberg):H_3672")
     * if nameOFMuseum and entry is not null, or a uuid otherwise.
     */
    public String uriForPhysicalObjects(String className, String nameOfMuseum, String entry) {
        tem = reading("uriForPhysicalObject");

        if (!nameOfMuseum.equals("") && !entry.equals("")) {
            String uri = tem.get(0);


            // return encode(urn + ":" + crmISO +tem.get(0) + tem.get(1)+ ":(Main_Object)" + nameOfMuseum + "-" + entry);
            for (int i = 1; i < tem.size(); i++) {
                uri = uri + tem.get(i);
            }
            uri = uri + nameOfMuseum + "-" + entry;



            return encode(uri);
            //return encode(tem.get(0) + tem.get(1) +":"+ nameOfMuseum + "-" + entry);
        } else {
            return uuid("");
        }
    }

    private String listOfStringsToString(ArrayList<String> list) {
        String string = "";
        int i;
        for (i = 0; i < list.size() - 1; i++) {
            string = string.concat(list.get(i) + "-");
        }
        string = string.concat(list.get(i));
        return string;
    }

    /**
     * returns a uri for a specific place according to the available information
     * about the geographical spaces or coordinates it belongs.
     *
     * @param className the name of the corresponding CIDOC class
     * @param placeName the name of the Place
     * @param authority the authority that has assigned an id to this place
     * @param placeID the id given to this place by the authority
     * @param coordinates the geographical coordinates of the current place
     * @param spaces the geographical spaces the place belongs
     * @return a URI in the form "urn:iso21127:(Place)Authority-id" (e.g.
     * "urn:iso21127:(Place)TGN:7001393") if the authority and placeID are not
     * null, a URI in the form "urn:iso21127:(Place)longitude_latidute"
     * (e.g."urn:iso21127:(Place)37°58'N_23°46'E") if the structure coordinates
     * is not null a URI in the form
     * "urn:iso21127:(Place)inhabitedPlace-region-nation-continent"(e.g.
     * "urn:iso21127:(Place)Athens-Perifereia_Protevousis-Greece-Europe")if the
     * structure spaces is not null or a uuid otherwise
     *
     */
    public String uriForPlaces(String className, String placeName, String authority, String placeID,
            ArrayList<String> coordinates, ArrayList<String> spaces) {
        tem = reading("uriForPlaces");
        String uri = tem.get(0);
        System.out.println(className);
        System.out.println(placeName);


        for (int i = 1; i < tem.size(); i++) {
            uri = uri + tem.get(i);
        }

        if (!placeID.equals("")) {
            if (!authority.equals("")) {
                uri = uri + placeName + "," + authority + ":" + placeID;
            } else {
                uri = uri + placeName + "," + placeID;
            }
            return encode(uri);
        } else if (!coordinates.isEmpty()) {
            uri = uri + placeName + ",";
            uri = uri.concat(listOfStringsToString(coordinates));
            return encode(uri);
        } else if (!spaces.isEmpty()) {

            uri.concat(listOfStringsToString(spaces));
            return encode(uri);
        } else {
            return uuid("");
        }
    }

    //creates a URI for actor from a given authority and ID
    private String uriActorFromAuthority(String className, String authority, String id) {
        tem = reading("uriActorFromAuthority");
        String uri = tem.get(0);
        if (!className.equals("Actor")) {


            for (int i = 1; i < tem.size(); i++) {
                uri = uri + tem.get(i);
            }
            uri = urn + ":" + crmISO + ":(Actor)" + uri + authority + ":" + id;
            return encode(uri);
        } else {
            uri = urn + ":" + crmISO + ":(Actor)" + authority + "-" + id;
            return encode(uri);
        }
    }

    //creates a URI for Actor from his Name and vitalsDate
    public String uriNameDate(String className, String name, String vitalDates) {
        System.out.println("URIND=" + className);
        System.out.println("URIND=" + name);
        System.out.println("URIND=" + vitalDates);


        tem = reading("uriNameDate");
        String uri = tem.get(0);

        if (!className.equals("Actor")) {
            for (int i = 1; i < tem.size(); i++) {
                uri = uri + tem.get(i);
            }

            uri = urn + ":" + crmISO + ":(Actor-" + uri + ")" + name + ",b." + vitalDates;
            // String uri = urn + ":" + crmISO + ":(Actor-" + tem.get(2) + ")" + name + ",b." + vitalDates;
            return encode(uri);
        } else {
            uri = urn + ":" + crmISO + ":(Actor)" + uri + name + ",b." + vitalDates;
            return encode(uri);
        }
    }

    /**
     * returns a URI for Actor depending on the available information about him
     * (an already registered identifier or his name combined with his vital
     * Dates), or a UUID if there is no such information available.
     *
     * @param className the name of the corresponding CIDOC class
     * @param authority if not null, it represents an authority that has
     * registered a specific identifier to the current Actor
     * @param id the identifier registered to the Actor
     * @param name the Name of the Actor
     * @param birthDate the date Actor was born
     * @return a URI of the form "urn:iso21127:(Actor)authority:id" (e.g.
     * "urn:iso21127:(Actor)DISKUS-KUE-Datei:10153461")if authority and id are
     * not empty, a URI of the form "urn:iso21127:(Actor)name,b.birthDate" (e.g.
     * urn:iso21127:(Actor)Camerlohr,_Joseph_von,b.1820) if the name and the
     * vitalDates are not empty, or a UUID otherwise
     */
    public String uriForActors(String className, String authority, String id, String name, String birthDate) {
        if (!authority.equals("") || !id.equals("")) {
            return uriActorFromAuthority(className, authority, id);
        } else if (!birthDate.equals("")) {
            return uriNameDate(className, name, birthDate);
        } else {
            return uuid("");
        }
    }

    /**
     * returns a URI for Physical Things
     *
     * @param className the name of the corresponding CIDOC class
     * @param thing the name of the thing
     * @return a URI of the form "urn:iso21127:(Thing-ClassName)thing" (e.g.
     * "urn:iso21127:(Thing-Document)obj_00120252")
     */
    public String uriPhysThing(String className, String thing) {

        tem = reading("uriPhysicThing");
        String uri = tem.get(0);
        for (int i = 1; i < tem.size(); i++) {
            uri = uri + tem.get(i);
        }



        uri = uri + thing;

        return encode(uri);
    }

    /**
     * returns a URI for Conceptual Things
     *
     * @param className the name of the corresponding CIDOC class
     * @param thing the name of the thing
     * @return a URI of the form "urn:iso21127:(Conceptual-ClassName)thing"
     * (e.g. "urn:iso21127:(Conceptual-Right)Copyright")
     */
    public String uriConceptual(String className, String thing) {
        tem = reading("uriConceptual");
        String uri = tem.get(0);

        //uri = urn + ":" + crmISO + ":(Conceptual-" + className + ")" + thing;



        for (int i = 1; i < tem.size(); i++) {
            uri = uri + tem.get(i);
        }
        uri = uri + thing;


        return encode(uri);


    }

    /**
     * returns a URI for a specific Type
     *
     * @param className the name of the corresponding CIDOC class
     * @param type the type
     * @return a URI of the form "urn:iso21127:(Type-ClassName)type"
     * (e.g."urn:iso21127:(Type)Paper")
     */
    public String uriType(String className, String type) {
        tem = reading("uriType");

        System.out.println("className: " + className + " Type: " + type);
        //  System.out.println("className: " + tem.get(5) + " Type: " + type);
        String uri = tem.get(0);
        if (type.equals("")) {
            return uuid("");
        } else {
            if (!className.equals("Type")) {

                for (int i = 1; i < tem.size(); i++) {
                    uri = uri + tem.get(i);
                }

                uri = uri + className + type;

            } else {
                uri = uri + type;
            }
            return encode(uri);
        }
    }

    /**
     * returns a URI for a specific appellation about an entity determined by
     * the subject URI given
     *
     * @param className the name of the corresponding CIDOC class
     * @param subjUri the URI of the subject to which the appellation
     * corresponds
     * @param appellation the appellation of the subject
     * @return a URI if the form:
     * "urn:iso21127:(Appellation-ClassName)@subjUri@appellation" (e.g.
     * "urn:iso21127:(Appellation-Actor_Appellation)@urn:iso21127:(Actor)DISKUS-KUE-Datei:10153461@Camerlohr,_Joseph_von")
     */
    public String appellationURI(String className, String subjUri, String appellation) {
        tem = reading("appellationURI");
        String uri = tem.get(0);

        if (subjUri == null || appellation.equals("")) {
            return uuid("");
        } else {
            if (!className.equals("Appellation")) {
                for (int i = 1; i < tem.size(); i++) {
                    uri = uri + tem.get(i);
                }

                uri = uri + subjUri + "@" + appellation;
                // uri = urn + ":" + crmISO + ":(Appellation-" + tem.get(6) + ")@" + subjUri + "@" + appellation;
            } else {
                uri = uri + "Appellation/" + subjUri + "@" + appellation;
            }
            return encode(uri);
        }
    }

    /**
     * returns a URI for a specific dimensions about an entity determined by the
     * subject URI given
     *
     * @param className the name of the corresponding CIDOC class
     * @param subjUri the URI of the subject to which the dimensions correspond
     * @param dimensions the dimensions of the subject
     * @return a URI of the form: "urn:iso21127:(Dimensions)@subjUri@dimensions"
     * (e.g.
     * "urn:iso21127:(Dimensions)@urn:iso21127:(Main_Object)Germanic_National_Museum,_Graphical_Collection_(Nuremberg):H_3672@44,3x35,4_cm)
     */
    public String dimensionURI(String className, String subjUri, String dimensions) {
        tem = reading("dimensionURI");
        String uri = tem.get(0);
        if (subjUri == null || dimensions.equals("")) {
            return uuid("");
        } else {
            for (int i = 1; i < tem.size(); i++) {
                uri = uri + tem.get(i);
            }

            uri = uri + crmISO + ":(Dimensions)@" + subjUri + "@" + dimensions;
            return encode(uri);
        }
    }

    /**
     * returns a URI for a TimeSpan defined by the argument timespan
     *
     * @param className the name of the corresponding CIDOC class
     * @param timespan the period of time
     * @return a URI of the form: "urn:iso21127:(TimeSpan)timespan" (e.g.
     * "urn:iso21127:(TimeSpan)1871")
     */
    public String uriTimeSpan(String className, String timespan) {
        tem = reading("uriTimeSpan");
        String uri = tem.get(0);
        if (timespan.equals("")) {
            return uuid("");
        } else {
            for (int i = 1; i < tem.size(); i++) {
                uri = uri + tem.get(i);
            }
            uri = urn + ":" + crmISO + ":(TimeSpan)" + uri + timespan;
            return encode(uri);
            // return urn + ":" +tem.get(7) + crmISO + ":(TimeSpan)" + timespan;
        }
    }

    /**
     * returns a URI for an Event based either on an already registered
     * identifier for it, or on the type of the event determined by the
     * className - if it is unique for the subject it is referred to(Birth,
     * Death, Creation, etc.), or a UUID otherwise
     *
     * @param className the name of the corresponding CIDOC class
     * @param authority the qualified authority for the event identifier
     * @param eventID the event identifier
     * @param subjUri the URI of the subject to which the event corresponds
     * @return a uri of the form "urn:iso21127:(Event)Authority-eventID" (e.g.
     * "urn:iso21127:(Event)KB:1334")if an identifier already exists, one of the
     * form "urn:iso21127:(Event)@subjUri@className" (e.g.
     * "urn:iso21127:(Event)@urn:iso21127:(Actor)DISKUS-KUE-Datei:10153461@Birth)
     * if the className is one of the Birth, Death, Creation, etc. in other
     * words if the event is unique for the referred subject, or a UUID
     * otherwise
     */
    public String uriEvents(String className, String authority, String eventID, String subjUri) {
        tem = reading("uriEvents");
        String uri = tem.get(0);
        // String uri = "";
        if (!eventID.equals("")) {
            uri = urn + ":" + crmISO + ":(Event)" + authority + ":" + eventID;
        } else if (uniqueEvents.contains(className) && !subjUri.equals("")) {

            for (int i = 1; i < tem.size(); i++) {
                uri = uri + tem.get(i);
            }
            uri = urn + ":" + crmISO + ":(Event)@" + subjUri + "@" + uri;
            //   uri = urn + ":" + crmISO + ":(Event)@" + subjUri + "@" + tem.get(8);
        } else {
            uri = uuid("");
        }

        return uri;
    }

    /**
     * returns a random UUID
     *
     * @param className the name of the corresponding CIDOC class
     * @return a random UUID e.g.
     * "urn:uuid:9a9bccf9-e530-4ab7-a51b-5ac9ce1067e0"
     */
    public String uuid(String className) {
        return "uuid:" + UUID.randomUUID().toString();
    }

    /**
     * returns a Literal. That rule refers to any information being represented
     * as a String. This is not a urn form.
     *
     * @param className the name of the corresponding CIDOC class
     * @param type the type of information described
     * @param note the String representation
     * @return a URI of the form "literal:type:note" (e.g.
     * "literal:recordType:Item")
     */
    public String createLiteral(String className, String type, String note) {
        //return "literal:" + type + ":" + note;
        return note; //MaTh
    }

    private static String encode(String s) {
        StringBuffer sbuf = new StringBuffer();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int ch = s.charAt(i);
            if (ch != '/') { //added by MaTh in order to solve the http addresses that were modified
                if ('A' <= ch && ch <= 'Z') {		// 'A'..'Z'
                    sbuf.append((char) ch);
                } else if ('a' <= ch && ch <= 'z') {	// 'a'..'z'
                    sbuf.append((char) ch);
                } else if ('0' <= ch && ch <= '9') {	// '0'..'9'
                    sbuf.append((char) ch);
                } else if (ch == ' ') {			// space
                    sbuf.append('_');
                } else if (ch == '-' || ch == '_' // unreserved
                        || ch == '.' || ch == '!' || ch == '*' || ch == '(' || ch == ')'
                        || ch == '+' || ch == ',' || ch == ':' || ch == '=' || ch == '@' || ch == ';'
                        || ch == '$' || ch == '[' || ch == ']') {
                    sbuf.append((char) ch);
                } else if (ch <= 0x007f) {		// other ASCII
                    sbuf.append(hex[ch]);
                } else if (ch <= 0x07FF) {		// non-ASCII <= 0x7FF
                    sbuf.append(hex[0xc0 | (ch >> 6)]);
                    sbuf.append(hex[0x80 | (ch & 0x3F)]);
                } else {// 0x7FF < ch <= 0xFFFF
                    sbuf.append(hex[0xe0 | (ch >> 12)]);
                    sbuf.append(hex[0x80 | ((ch >> 6) & 0x3F)]);
                    sbuf.append(hex[0x80 | (ch & 0x3F)]);
                }
            } else {
                sbuf.append((char) ch);
            }
        }
        return sbuf.toString();
    }

    
    /**
     * Returns a list for specific arguments from the file argumentsfile.xml
     * @param pointer the name of the corresponding method.
     * @return an ArrayList from strings. 
     */
    
    
    
    public ArrayList<String> reading(String pointer) {
        System.out.println("sdsa" + pointer);
        ArrayList<String> a = new ArrayList<String>();

        if (pointer == "uriForPhysicalObject") {
            System.out.println("mpike stin 1");

            try {



                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(true);
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                Document doc = builder.parse("argumentsfile.xml");

                XPathFactory factory = XPathFactory.newInstance();
                XPath xpath = factory.newXPath();

                NodeList children = (NodeList) xpath.evaluate("//mapping/uriForPhysicalObject/child::*", doc, XPathConstants.NODESET);
                System.out.println("Bat is working");

                for (int i = 0; i < children.getLength(); i++) {
                    Node c = children.item(i);
                    System.out.println("C=" + c.getTextContent());
                    a.add(c.getTextContent());
                }


                for (int i = 0; i < a.size(); i++) {

                    System.out.println("periexomena 1" + a.get(i));


                }

            } catch (SAXParseException err) {
                System.out.println("** Parsing error" + ", line "
                        + err.getLineNumber() + ", uri " + err.getSystemId());
                System.out.println(" " + err.getMessage());

            } catch (SAXException e) {
                Exception x = e.getException();
                ((x == null) ? e : x).printStackTrace();

            } catch (Throwable t) {
                t.printStackTrace();
            }
            //System.exit (0);
            return a;
        }


        if (pointer == "uriPhysicThing") {
            System.out.println("mpike stin 2" + pointer);
            try {



                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(true);
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                Document doc = builder.parse("argumentsfile.xml");

                XPathFactory factory = XPathFactory.newInstance();
                XPath xpath = factory.newXPath();

                NodeList children = (NodeList) xpath.evaluate("//mapping/uriPhysicThing/child::*", doc, XPathConstants.NODESET);
                System.out.println("Bat is working");

                for (int i = 0; i < children.getLength(); i++) {
                    Node c = children.item(i);
                    System.out.println("C=" + c.getTextContent());
                    a.add(c.getTextContent());
                }


                for (int i = 0; i < a.size(); i++) {

                    System.out.println("periexomena 1" + a.get(i));


                }

            } catch (SAXParseException err) {
                System.out.println("** Parsing error" + ", line "
                        + err.getLineNumber() + ", uri " + err.getSystemId());
                System.out.println(" " + err.getMessage());

            } catch (SAXException e) {
                Exception x = e.getException();
                ((x == null) ? e : x).printStackTrace();

            } catch (Throwable t) {
                t.printStackTrace();
            }
            //System.exit (0);
            return a;


        }

        if (pointer == "uriActorFromAuthority") {
            System.out.println("mpike stin actor" + pointer);
            try {



                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(true);
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                Document doc = builder.parse("argumentsfile.xml");

                XPathFactory factory = XPathFactory.newInstance();
                XPath xpath = factory.newXPath();

                NodeList children = (NodeList) xpath.evaluate("//mapping/uriActorFromAuthority/child::*", doc, XPathConstants.NODESET);
                System.out.println("Bat is working");

                for (int i = 0; i < children.getLength(); i++) {
                    Node c = children.item(i);
                    System.out.println("C=" + c.getTextContent());
                    a.add(c.getTextContent());
                }


                for (int i = 0; i < a.size(); i++) {

                    System.out.println("periexomena 1" + a.get(i));


                }

            } catch (SAXParseException err) {
                System.out.println("** Parsing error" + ", line "
                        + err.getLineNumber() + ", uri " + err.getSystemId());
                System.out.println(" " + err.getMessage());

            } catch (SAXException e) {
                Exception x = e.getException();
                ((x == null) ? e : x).printStackTrace();

            } catch (Throwable t) {
                t.printStackTrace();
            }
            //System.exit (0);
            return a;


        }

        if (pointer == "uriConceptual") {
            System.out.println("mpike stin actor" + pointer);
            try {



                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(true);
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                Document doc = builder.parse("argumentsfile.xml");

                XPathFactory factory = XPathFactory.newInstance();
                XPath xpath = factory.newXPath();

                NodeList children = (NodeList) xpath.evaluate("//mapping/uriConceptual/child::*", doc, XPathConstants.NODESET);
                System.out.println("Bat is working");

                for (int i = 0; i < children.getLength(); i++) {
                    Node c = children.item(i);
                    System.out.println("C=" + c.getTextContent());
                    a.add(c.getTextContent());
                }


                for (int i = 0; i < a.size(); i++) {

                    System.out.println("periexomena 1" + a.get(i));


                }

            } catch (SAXParseException err) {
                System.out.println("** Parsing error" + ", line "
                        + err.getLineNumber() + ", uri " + err.getSystemId());
                System.out.println(" " + err.getMessage());

            } catch (SAXException e) {
                Exception x = e.getException();
                ((x == null) ? e : x).printStackTrace();

            } catch (Throwable t) {
                t.printStackTrace();
            }
            //System.exit (0);
            return a;


        }

        if (pointer == "uriType") {
            System.out.println("mpike stin actor" + pointer);
            try {



                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(true);
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                Document doc = builder.parse("argumentsfile.xml");

                XPathFactory factory = XPathFactory.newInstance();
                XPath xpath = factory.newXPath();

                NodeList children = (NodeList) xpath.evaluate("//mapping/uriType/child::*", doc, XPathConstants.NODESET);
                System.out.println("Bat is working");

                for (int i = 0; i < children.getLength(); i++) {
                    Node c = children.item(i);
                    System.out.println("C=" + c.getTextContent());
                    a.add(c.getTextContent());
                }


                for (int i = 0; i < a.size(); i++) {

                    System.out.println("periexomena 1" + a.get(i));


                }

            } catch (SAXParseException err) {
                System.out.println("** Parsing error" + ", line "
                        + err.getLineNumber() + ", uri " + err.getSystemId());
                System.out.println(" " + err.getMessage());

            } catch (SAXException e) {
                Exception x = e.getException();
                ((x == null) ? e : x).printStackTrace();

            } catch (Throwable t) {
                t.printStackTrace();
            }
            //System.exit (0);
            return a;


        }
        if (pointer == "appellationURI") {
            System.out.println("mpike stin actor" + pointer);
            try {



                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(true);
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                Document doc = builder.parse("argumentsfile.xml");

                XPathFactory factory = XPathFactory.newInstance();
                XPath xpath = factory.newXPath();

                NodeList children = (NodeList) xpath.evaluate("//mapping/appellationURI/child::*", doc, XPathConstants.NODESET);
                System.out.println("Bat is working");

                for (int i = 0; i < children.getLength(); i++) {
                    Node c = children.item(i);
                    System.out.println("C=" + c.getTextContent());
                    a.add(c.getTextContent());
                }


                for (int i = 0; i < a.size(); i++) {

                    System.out.println("periexomena 1" + a.get(i));


                }

            } catch (SAXParseException err) {
                System.out.println("** Parsing error" + ", line "
                        + err.getLineNumber() + ", uri " + err.getSystemId());
                System.out.println(" " + err.getMessage());

            } catch (SAXException e) {
                Exception x = e.getException();
                ((x == null) ? e : x).printStackTrace();

            } catch (Throwable t) {
                t.printStackTrace();
            }
            //System.exit (0);
            return a;


        }

        if (pointer == "dimensionURI") {
            System.out.println("mpike stin actor" + pointer);
            try {



                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(true);
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                Document doc = builder.parse("argumentsfile.xml");

                XPathFactory factory = XPathFactory.newInstance();
                XPath xpath = factory.newXPath();

                NodeList children = (NodeList) xpath.evaluate("//mapping/dimensionURI/child::*", doc, XPathConstants.NODESET);
                System.out.println("Bat is working");

                for (int i = 0; i < children.getLength(); i++) {
                    Node c = children.item(i);
                    System.out.println("C=" + c.getTextContent());
                    a.add(c.getTextContent());
                }


                for (int i = 0; i < a.size(); i++) {

                    System.out.println("periexomena 1" + a.get(i));


                }

            } catch (SAXParseException err) {
                System.out.println("** Parsing error" + ", line "
                        + err.getLineNumber() + ", uri " + err.getSystemId());
                System.out.println(" " + err.getMessage());

            } catch (SAXException e) {
                Exception x = e.getException();
                ((x == null) ? e : x).printStackTrace();

            } catch (Throwable t) {
                t.printStackTrace();
            }
            //System.exit (0);
            return a;


        }

        if (pointer == "uriTimeSpan") {
            System.out.println("mpike stin actor" + pointer);
            try {



                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(true);
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                Document doc = builder.parse("argumentsfile.xml");

                XPathFactory factory = XPathFactory.newInstance();
                XPath xpath = factory.newXPath();

                NodeList children = (NodeList) xpath.evaluate("//mapping/uriTimeSpan/child::*", doc, XPathConstants.NODESET);
                System.out.println("Bat is working");

                for (int i = 0; i < children.getLength(); i++) {
                    Node c = children.item(i);
                    System.out.println("C=" + c.getTextContent());
                    a.add(c.getTextContent());
                }


                for (int i = 0; i < a.size(); i++) {

                    System.out.println("periexomena 1" + a.get(i));


                }

            } catch (SAXParseException err) {
                System.out.println("** Parsing error" + ", line "
                        + err.getLineNumber() + ", uri " + err.getSystemId());
                System.out.println(" " + err.getMessage());

            } catch (SAXException e) {
                Exception x = e.getException();
                ((x == null) ? e : x).printStackTrace();

            } catch (Throwable t) {
                t.printStackTrace();
            }
            //System.exit (0);
            return a;


        }

        if (pointer == "uriEvents") {
            System.out.println("mpike stin actor" + pointer);
            try {



                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(true);
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                Document doc = builder.parse("argumentsfile.xml");

                XPathFactory factory = XPathFactory.newInstance();
                XPath xpath = factory.newXPath();

                NodeList children = (NodeList) xpath.evaluate("//mapping/uriEvents/child::*", doc, XPathConstants.NODESET);
                System.out.println("Bat is working");

                for (int i = 0; i < children.getLength(); i++) {
                    Node c = children.item(i);
                    System.out.println("C=" + c.getTextContent());
                    a.add(c.getTextContent());
                }


                for (int i = 0; i < a.size(); i++) {

                    System.out.println("periexomena 1" + a.get(i));


                }

            } catch (SAXParseException err) {
                System.out.println("** Parsing error" + ", line "
                        + err.getLineNumber() + ", uri " + err.getSystemId());
                System.out.println(" " + err.getMessage());

            } catch (SAXException e) {
                Exception x = e.getException();
                ((x == null) ? e : x).printStackTrace();

            } catch (Throwable t) {
                t.printStackTrace();
            }
            //System.exit (0);
            return a;


        }

        if (pointer == "uriNameDate") {
            System.out.println("mpike stin actor" + pointer);
            try {



                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(true);
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                Document doc = builder.parse("argumentsfile.xml");

                XPathFactory factory = XPathFactory.newInstance();
                XPath xpath = factory.newXPath();

                NodeList children = (NodeList) xpath.evaluate("//mapping/uriNameDate/child::*", doc, XPathConstants.NODESET);
                System.out.println("Bat is working");

                for (int i = 0; i < children.getLength(); i++) {
                    Node c = children.item(i);
                    System.out.println("C=" + c.getTextContent());
                    a.add(c.getTextContent());
                }


                for (int i = 0; i < a.size(); i++) {

                    System.out.println("periexomena 1" + a.get(i));


                }

            } catch (SAXParseException err) {
                System.out.println("** Parsing error" + ", line "
                        + err.getLineNumber() + ", uri " + err.getSystemId());
                System.out.println(" " + err.getMessage());

            } catch (SAXException e) {
                Exception x = e.getException();
                ((x == null) ? e : x).printStackTrace();

            } catch (Throwable t) {
                t.printStackTrace();
            }
            //System.exit (0);
            return a;


        }

        if (pointer == "uriForPlaces") {
            System.out.println("mpike stin actor" + pointer);
            try {



                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(true);
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                Document doc = builder.parse("argumentsfile.xml");

                XPathFactory factory = XPathFactory.newInstance();
                XPath xpath = factory.newXPath();

                NodeList children = (NodeList) xpath.evaluate("//mapping/uriForPlaces/child::*", doc, XPathConstants.NODESET);
                System.out.println("Bat is working");

                for (int i = 0; i < children.getLength(); i++) {
                    Node c = children.item(i);
                    System.out.println("C=" + c.getTextContent());
                    a.add(c.getTextContent());
                }


                for (int i = 0; i < a.size(); i++) {

                    System.out.println("periexomena 1" + a.get(i));


                }

            } catch (SAXParseException err) {
                System.out.println("** Parsing error" + ", line "
                        + err.getLineNumber() + ", uri " + err.getSystemId());
                System.out.println(" " + err.getMessage());

            } catch (SAXException e) {
                Exception x = e.getException();
                ((x == null) ? e : x).printStackTrace();

            } catch (Throwable t) {
                t.printStackTrace();
            }
            //System.exit (0);
            return a;


        } else {
            return null;
        }

    }
}
