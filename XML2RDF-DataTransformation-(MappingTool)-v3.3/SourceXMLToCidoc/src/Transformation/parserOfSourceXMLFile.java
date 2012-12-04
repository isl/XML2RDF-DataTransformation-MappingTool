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



 
package Transformation;

/**
 * @author Koutraki Maria (kutraki@ics.forth.gr)
 * @author Evangelia Daskalaki (eva@ics.forth.gr)
 */
import URIidevelopment.URIPolicies;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/*
 * swkmmodel
 */
import gr.forth.ics.swkm.model2.Model;
import gr.forth.ics.swkm.model2.ModelBuilder;
import gr.forth.ics.swkm.model2.io.Format;
import gr.forth.ics.swkm.model2.io.RdfIO;
import gr.forth.ics.swkm.model2.vocabulary.Rdf;
import gr.forth.ics.swkm.model2.Literal;
import gr.forth.ics.swkm.model2.LiteralNode;
import java.io.*;
import java.lang.reflect.Method;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.SAXParseException;

public class parserOfSourceXMLFile implements Serializable {

    private Document doc = null;
    private ArrayList<SourceXMLElements> dom;
    private String StarEntity = null;
    private String StarEntityUri = null;
    private HashMap<String, String> uriMemory = new HashMap();
    public Element src_domain = null;
    private ArrayList<String> temp = null;

    public parserOfSourceXMLFile(parserOfMapping dpm, String file) {
        dom = new ArrayList<SourceXMLElements>();
        try {
            doc = parserXML(new File(file));
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void transform(parserOfMapping dpm, String Inputfile, String destinationPath) throws IOException {

        Model model = ModelBuilder.newSparse().build();



        // here the extractingDataMapping2 created by Evangelia Daskalaki, is a new version of the old extractingDataMapping variable 
        for (int t = 0; t < dpm.extractingDataMapping2.size(); t++) { /// for each map in the xml document
            String src_domain = dpm.extractingDataMapping2.get(t).getSrcDomain();

            NodeList src_domain_list = null;
            try {
                src_domain_list = getArgumentsAbsoluteXpathDoc(doc, src_domain);
            } catch (XPathExpressionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SAXException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            for (int i = 0; i < src_domain_list.getLength(); i++) { //for each src_domain that you will find in the xml document


                Element src_domain_element = null;
                NodeList src_dom_element = null;
                this.src_domain = null;
                this.src_domain = (Element) (src_domain_list.item(i)).cloneNode(true);
                src_domain_element = (Element) src_domain_list.item(i).cloneNode(true);
                int src_domain_position = i + 1;
                String src_domain_path = src_domain + "[" + src_domain_position + "]";

                ArrayList link_map = dpm.extractingDataMapping2.get(t).mapinf;
                for (int r = 0; r < link_map.size(); r++) {//for each link map in the xml file
                    MapInfos src_range = (MapInfos) link_map.get(r);
                    ArrayList<CIDOCTriples> triples = src_range.getTriplesList();
                    Condition cn = src_range.getCnd();
                    String src_range_path = src_range.getSrcRange();
                    //String src_range_path_new=src_range_path.replace(src_domain,src_domain_path);
                    String src_range_path_new = src_domain_path + src_range_path;
                    NodeList src_range_list = null;
                    try {
                        src_range_list = getArgumentsAbsoluteXpathDoc(doc, src_range_path_new);
                    } catch (XPathExpressionException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (SAXException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    for (int p = 0; p < src_range_list.getLength(); p++) { //for each src_range that you will find in the xml document
                        int src_range_position = p + 1;
                        Element src_range_element = (Element) src_range_list.item(p);

                        String source_range_path = src_range_path_new + "[" + src_range_position + "]";

                        // here the createTriple2 created by Evangelia Daskalaki, is a new version of the old createTriple method 
                        createTriples2(dpm, this.src_domain, src_domain_path, src_range_element, source_range_path, model, Inputfile, triples, cn);
                    }

                }

            }
        }
        String s = RdfIO.write(model, Format.RDFXML).withBase("http://baseUri").toString();
        System.err.println(s);
        fileCreation(destinationPath, "\\Results.rdf", s);
    }


    public Document parserXML(
            File file) throws SAXException, IOException, ParserConfigurationException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
    }

    public void printList() {
        for (int i = 0; i
                < dom.size(); i++) {
            System.out.println(dom.get(i).getNode() + "  " + dom.get(i).getElement());
        }

    }

    public void createTriples2(parserOfMapping dpm, Element src_domain_element, String src_domain_path,
            Element src_range_element, String src_range_path, Model model, String file, ArrayList<CIDOCTriples> triples, Condition cond) {


        ArrayList<ElementInfos> elementInfs = getElementInfosforNode(dpm, src_range_element.getChildNodes().item(0).getNodeValue());



        if (cond.getPath().equals("null") && cond.getValue().equals("null")) { //if "conditions" not exist
        } else { //if "conditions" exist
            try {
                String path = cond.getPath();
                Node conditionElementNode = getArgumentsRelativeXpath(this.src_domain, path);
                String conditionElement;
                if (conditionElementNode != null) {
                    conditionElement = conditionElementNode.getNodeValue();
                } else {
                    conditionElement = null;
                }

                if (conditionElement != null && conditionElement.equals(cond.getValue())) {
                    triples = elementInfs.get(0).getTriples();
                }
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (XPathExpressionException ex) {
                Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // here the produceTriple2 created by Evangelia Daskalaki, is a new version of the old produceTriple method 

        produceTriple2(triples, src_domain_element, src_range_element, src_domain_path, src_range_path, model, file);



    }

    public void createTriples(parserOfMapping dpm, String nodeName, String nodeValue, Element element, Model model, String file) {


        ArrayList<ElementInfos> elementInfs = getElementInfosforNode(dpm, nodeName);
        ArrayList<CIDOCTriples> triples = new ArrayList<CIDOCTriples>();
        if (elementInfs.size() == 1) {
            Condition cond = elementInfs.get(0).getConditions();
            if (cond.getPath().equals("null") && cond.getValue().equals("null")) { //if "conditions" not exist
                triples = elementInfs.get(0).getTriples();
            } else { //if "conditions" exist
                try {
                    String path = cond.getPath();
                    Node conditionElementNode = getArgumentsRelativeXpath(this.src_domain, path);
                    String conditionElement;
                    if (conditionElementNode != null) {
                        conditionElement = conditionElementNode.getNodeValue();
                    } else {
                        conditionElement = null;
                    }

                    if (conditionElement != null && conditionElement.equals(cond.getValue())) {
                        triples = elementInfs.get(0).getTriples();
                    }
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
                } catch (XPathExpressionException ex) {
                    Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            produceTriple(triples, element, nodeName, nodeValue, model, file);
        } else if (elementInfs.size() > 1) { /*
             * case there are more than one choices for the element. eg.
             * EventType
             */

            ArrayList<CIDOCTriples> triples2 = new ArrayList<CIDOCTriples>();
            int exist = 0;
            for (int c = 0; c < elementInfs.size(); c++) {

                Condition cond = elementInfs.get(c).getConditions();

                if (!cond.getPath().equals("null") && !cond.getValue().equals("null")) {//if "conditions" exist
                    try {
                        String path = cond.getPath();
                        Node conditionElementNode = getArgumentsRelativeXpath(this.src_domain, path);
                        String conditionElement;
                        if (conditionElementNode != null) {
                            conditionElement = conditionElementNode.getNodeValue();
                        } else {
                            conditionElement = null;
                        }
                        if (conditionElement != null && conditionElement.equals(cond.getValue())) {
                            exist = 1;
                            triples = elementInfs.get(c).getTriples();
                            break;
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SAXException ex) {
                        Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (XPathExpressionException ex) {
                        Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else { //if "conditions" don't exist
                    exist = 0;
                    triples2 = elementInfs.get(c).getTriples();
                }

            }
            if (exist == 1) {
                produceTriple(triples, element, nodeName, nodeValue, model, file);
            } else {
                produceTriple(triples2, element, nodeName, nodeValue, model, file);
            }
        }
    }

    private void produceTriple(ArrayList<CIDOCTriples> triples, Element element, String nodeName, String nodeValue, Model model, String file) {

        for (int i = 0; i < triples.size(); i++) {

            String SubjectEntity = triples.get(i).getSubject().getEntity();
            String SubjectFunctionName = triples.get(i).getSubject().getUriFunctionName();
            ArrayList<String> SubjectFunctionArguments = triples.get(i).getSubject().getUriFunctionArguments();

            String uriForSubject = createTripleforSubject(SubjectFunctionName, SubjectFunctionArguments, SubjectEntity, StarEntity, StarEntityUri, element, model, file);

            String ObjectEntity = triples.get(i).getObject().getEntity();
            String ObjectFunctionName = triples.get(i).getObject().getUriFunctionName();
            ArrayList<String> ObjectFunctionArguments = triples.get(i).getObject().getUriFunctionArguments();


            String uriForObject = computeUriforObject(ObjectFunctionName, ObjectFunctionArguments, ObjectEntity, uriForSubject, nodeValue, element);

            /*
             * object triple
             */


            model.add().s(uriForObject).p(Rdf.TYPE).o(ObjectEntity);

            if (nodeName.equals("lido:eventType/lido:term")) { //If node name is "lido:eventType/lido:term" the star entity should has specific value
                if (SubjectEntity.equals("E5_Event")) {
                    StarEntity = SubjectEntity;
                    StarEntityUri = uriForSubject;
                } else {
                    StarEntity = ObjectEntity;
                    StarEntityUri = uriForObject;
                }
            }

            model.add().s(uriForSubject).p(triples.get(i).getPredicate()).o(uriForObject);

        }
    }

    public ArrayList<String> specificRangeFunctionArguments(ArrayList<String> ObjectFunctionArguments, int range_position) {
        ArrayList<String> tmp = new ArrayList<String>();
        if (ObjectFunctionArguments != null) {
            for (int t = 0; t < ObjectFunctionArguments.size(); t++) {
                String argument = (String) ObjectFunctionArguments.get(t);
                if (argument.contains("/text()")) {
                    argument = argument.replace("/text()", "[" + range_position + "]/text()");
                } else {
                    argument = argument + "[" + range_position + "]";
                }
                tmp.add(argument);

            }
            return tmp;
        }
        return tmp;
    }

    private void produceTriple2(ArrayList<CIDOCTriples> triples, Element domain_element, Element range_element,
            String domain_element_path, String range_element_path, Model model, String file) {
        String uriForSubject = new String();
        String uriForObject = new String();

        for (int i = 0; i < triples.size(); i++) {

            String SubjectEntity = triples.get(i).getSubject().getEntity();

            String SubjectFunctionName = triples.get(i).getSubject().getUriFunctionName();
            ArrayList<String> SubjectFunctionArguments = triples.get(i).getSubject().getUriFunctionArguments();
            if (uriMemory.get(domain_element_path) != null) {
                uriForSubject = uriMemory.get(domain_element_path);
            } else {
                uriForSubject = createTripleforSubject(SubjectFunctionName, SubjectFunctionArguments, SubjectEntity, StarEntity, StarEntityUri, domain_element, model, file);
                uriMemory.put(domain_element_path, uriForSubject);
            }
            String ObjectEntity = triples.get(i).getObject().getEntity();

            String ObjectFunctionName = triples.get(i).getObject().getUriFunctionName();
            ArrayList<String> ObjectFunctionArguments = triples.get(i).getObject().getUriFunctionArguments();
//          ArrayList<String> specificRangeFunctionArguments=specificRangeFunctionArguments(ObjectFunctionArguments,range_position);
            if (uriMemory.get(range_element_path) != null) {
                uriForObject = uriMemory.get(range_element_path);
            } else {
                uriForObject = computeUriforObject(ObjectFunctionName, ObjectFunctionArguments, ObjectEntity, uriForSubject, range_element.getChildNodes().item(0).getNodeValue(), range_element);
                uriMemory.put(range_element_path, uriForObject);
            }
            /*
             * object triple
             */

            if (ObjectEntity.equals("Literal")) {
                // Literal lit = Literal.create(uriForObject);
                LiteralNode LitN = model.mapLiteral(Literal.create(uriForObject));

                // model.add().s(uriForObject).p(Rdf.TYPE).o(RdfSchema.LITERAL);
                // model.add().s(uriForSubject).p(Rdf.VALUE).o(uriForObject);
                //model.add().s(uriForObject).p(Rdf.TYPE).o(RdfSchema.LITERAL);

                //model.add().s("MARIA" + triples.get(i).getPredicate()).p(Rdf.VALUE).o(uriForObject);
//model.add().s(uriForSubject).p(uriForSubject).o(uriForObject);   

                model.add().s(uriForSubject).p(triples.get(i).getPredicate()).o(LitN);

            } else {




                model.add().s(uriForObject).p(Rdf.TYPE).o(ObjectEntity);

//          if (nodeName.equals("lido:eventType/lido:term")) { //If node name is "lido:eventType/lido:term" the star entity should has specific value
//              if (SubjectEntity.equals("E5_Event")) {
//                  StarEntity = SubjectEntity;
//                  StarEntityUri = uriForSubject;
//              } else {
//                  StarEntity = ObjectEntity;
//                  StarEntityUri = uriForObject;
//              }
//          }

                try {
                    model.add().s(uriForSubject).p(triples.get(i).getPredicate()).o(uriForObject);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private String createTripleforSubject(String SubjectFunctionName, ArrayList<String> SubjectFunctionArguments, String SubjectEntity,
            String StarEntity, String StarEntityUri, Element element, Model model, String file) {
        Node argNode = null;
        String uriResultSubject = null;
        if (SubjectFunctionName != null && !SubjectEntity.equals("*")) {

            try {
                ArrayList arguments = new ArrayList();
                String[] entity = SubjectEntity.split("_");
                String res = "";
                for (int j = 0; j < entity.length - 1; j++) {
                    res = res + entity[j + 1];
                }
                arguments.add(res);//the first argument in URI creation functions should be the name of CIDOC class.
                if (SubjectFunctionName.equals("uriEvents")) {
                    arguments.add("");
                }

                String arg = null;
                for (int i = 0; i < SubjectFunctionArguments.size(); i++) {
                    try {
                        if (SubjectEntity.equals("E19_Physical_Object")) {

                            NodeList ndL = getArgumentsAbsoluteXpath(file, SubjectFunctionArguments.get(i));
                            if (ndL.getLength() > 0) {
                                arg = ndL.item(0).getNodeValue();
                            } else {
                                arg = null;
                            }
                        } else if (SubjectFunctionName.equals("uriForPlaces")) {
                            if (i == SubjectFunctionArguments.size() - 1) {
                                Node lastArgumentForPlaces = getArgumentsRelativeXpath(element, SubjectFunctionArguments.get(i));
                                arguments = getPlaceSpaces(lastArgumentForPlaces);
                            }
                        } else {
                            argNode = getArgumentsRelativeXpath(element, SubjectFunctionArguments.get(i));
                            if (argNode != null) {
                                arg = argNode.getNodeValue();


                                if (arg == null) {
                                    arg = argNode.getFirstChild().getNodeValue();
                                }

                            } else {

                                arg = null;
                            }
                        }

                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SAXException ex) {
                        Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (XPathExpressionException ex) {
                        Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (arg != null) {
                        arguments.add(arg);
                    } else {
                        arguments.add("");
                    }
                } //for
                uriResultSubject = callUriFunction(SubjectFunctionName, arguments);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
            }

            model.add().s(uriResultSubject).p(Rdf.TYPE).o(SubjectEntity);


        } else if (SubjectEntity.equals("*")) {
            SubjectEntity = StarEntity;
            uriResultSubject = StarEntityUri;
            model.add().s(StarEntityUri).p(Rdf.TYPE).o(StarEntity);
        }

        return uriResultSubject;
    }

    private String computeUriforObject(String ObjectFunctionName, ArrayList<String> ObjectFunctionArguments,
            String ObjectEntity, String uriResultSubject, String node, Element element) {

        String uriResultObject = null;

        if (ObjectFunctionName != null) {
            ArrayList argumentsObject = new ArrayList();

            String[] entity = ObjectEntity.split("_");
            String res = "";
            for (int j = 0; j < entity.length - 1; j++) {
                res = res + entity[j + 1];
            }

            argumentsObject.add(res);

            if (ObjectFunctionName.equals("appellationURI") || ObjectFunctionName.equals("uriEvents")) {
                argumentsObject.add(uriResultSubject);
            } else if (ObjectFunctionName.equals("createLiteral")) {
                argumentsObject.add(element.getNodeName());
            }
            String arg = null;
            for (int i = 0; i < ObjectFunctionArguments.size(); i++) {
                try {
                    Node argNode = getArgumentsRelativeXpath(element, ObjectFunctionArguments.get(i));
                    if (argNode != null) {
                        arg = argNode.getNodeValue();
                    } else {
                        arg = null;
                    }
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
                } catch (XPathExpressionException ex) {
                    Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (arg != null) {
                    argumentsObject.add(arg);
                } else {
                    argumentsObject.add("");
                }
            }
            try {
                uriResultObject = callUriFunction(ObjectFunctionName, argumentsObject);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(parserOfSourceXMLFile.class.getName()).log(Level.SEVERE, null, ex);
            }

        }


        return uriResultObject;
    }

    public ArrayList getDom() {
        return dom;
    }

    private ArrayList<ElementInfos> getElementInfosforNode(parserOfMapping msd, String node) {
        ArrayList<ElementInfos> elementInfs = new ArrayList<ElementInfos>();
        for (int k = 0; k < msd.extractingDataMapping.size(); k++) {
            if (msd.extractingDataMapping.get(k).getElement().equals(node)) {
                ElementInfos el = msd.extractingDataMapping.get(k).getElementInfos();
                elementInfs.add(el);
            }
        }
        return elementInfs;
    }

    private String callUriFunction(String functionName, ArrayList functionArguments) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        if (functionName.equals("createLiteral")) {/*
             * if functions' name is createLiteral the first argument should be
             * the tag's name
             */
            //eva changes
            String[] nodeNAme = ((String) functionArguments.get(1)).split(",");
            // String[] nodeNAme = ((String) functionArguments.get(1)).split(":");
            // functionArguments.set(1, nodeNAme[1]);
            functionArguments.set(1, nodeNAme[0]);
        }

        int partypessize = functionArguments.size();
        Class cls = Class.forName("URIidevelopment.URIPolicies");
        Class partypes[] = new Class[partypessize];
        for (int i = 0; i
                < partypessize; i++) {
            partypes[i] = Class.forName("java.lang.String");
        }

        System.out.println(functionName);
        Method meth = cls.getMethod(functionName, partypes);
        URIPolicies methobj = new URIPolicies(temp);
        // methobj.getTem();
        Object arglist[] = new Object[partypessize];
        for (int j = 0; j
                < partypessize; j++) {
            arglist[j] = (String) functionArguments.get(j);
        }

        Object retobj = meth.invoke(methobj, arglist);
        String retval = (String) retobj;
        return retval;

    }

    public Node getArgumentsRelativeXpath(Element element, String xpathExpression) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        NamespaceContext ctx = new NamespaceContext() {

            public String getNamespaceURI(
                    String prefix) {

                String uri = "http://www.lido-schema.org";
                return uri;
            }

            public String getPrefix(
                    String namespaceURI) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Iterator getPrefixes(
                    String namespaceURI) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();

        xpath.setNamespaceContext(ctx);
        XPathExpression expr = xpath.compile(xpathExpression);
        Object result = expr.evaluate(element, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
//./_/aspect_parts/_/aspect_parts_part[2]/text()
        if (nodes.getLength() > 0) {
            return nodes.item(0).cloneNode(true);
        } else {
            return null;
        }
    }

    private NodeList getArgumentsAbsoluteXpath(String file, String xpathExpression) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document document = builder.parse(new File(file));

        NamespaceContext ctx = new NamespaceContext() {

            public String getNamespaceURI(
                    String prefix) {

                String uri = "http://www.lido-schema.org";
                return uri;
            }

            public String getPrefix(
                    String namespaceURI) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Iterator getPrefixes(
                    String namespaceURI) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();

        xpath.setNamespaceContext(ctx);
        XPathExpression expr = xpath.compile(xpathExpression);
        Object result = expr.evaluate(document, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;

        return nodes;
    }

    private NodeList getArgumentsAbsoluteXpathDoc(Document document, String xpathExpression) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {


        NamespaceContext ctx = new NamespaceContext() {

            public String getNamespaceURI(
                    String prefix) {

                String uri = "http://www.lido-schema.org";
                return uri;
            }

            public String getPrefix(
                    String namespaceURI) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Iterator getPrefixes(
                    String namespaceURI) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();

        xpath.setNamespaceContext(ctx);
        XPathExpression expr = xpath.compile(xpathExpression);
        Object result = expr.evaluate(document, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;

        return nodes;
    }

    private void fileCreation(String folderName, String fileName, String text) throws IOException {
        File file = new File(folderName, fileName);
        boolean exist = file.createNewFile();
        FileWriter fstream = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(text);
        out.close();
        System.err.println("File created successfully.");
    }

    private ArrayList<String> getPlaceSpaces(Node node) {
        ArrayList<String> places = new ArrayList<String>();

        NamespaceContext ctx = new NamespaceContext() {

            public String getNamespaceURI(String prefix) {

                String uri = "http://www.lido-schema.org";
                return uri;

            }

            public String getPrefix(String namespaceURI) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Iterator getPrefixes(String namespaceURI) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        try {
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            xpath.setNamespaceContext(ctx);
            XPathExpression expr;

            while (node != null) {
                String placeNamePath = "lido:namePlaceSet/lido:appellationValue/text()";

                expr = xpath.compile(placeNamePath);
                String placeName = (String) expr.evaluate(node, XPathConstants.STRING);
                places.add(placeName);
                String nodePath = "lido:partOfPlace";
                expr = xpath.compile(nodePath);
                node = (Node) expr.evaluate(node, XPathConstants.NODE);

            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return places;
    }
}
