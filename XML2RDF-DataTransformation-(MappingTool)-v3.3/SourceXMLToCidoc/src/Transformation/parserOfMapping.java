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
 *
 * @author Koutraki Maria (kutraki@ics.forth.gr)
 */


import java.io.*;
import javax.xml.parsers.*;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.*;


import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.SAXException;

public class parserOfMapping {

    public ArrayList<CIDOCTriples> triples;
    public ArrayList<ElementObject> extractingDataMapping;
    public ArrayList<MapObject> extractingDataMapping2;
    public ArrayList elementsOfMapping;/*elements of mapping file*/

    private CIDOCTriples trips;
    private CIDOCTriples trips2;
    private CIDOCTriples trips3;
    private CIDOCTriples trips4;
    private String src_range = null;
    private String target_domain = null;
    public String src_domain = null;
    private String target_range = null;
    private String add_link_rangeMap = null;
    private String add_entity_rangeMap = null;
    private String add_entity_attribute = null;
    private String int_link1_targetPath = null;
    private String int_link2_targetPath = null;
    private String int_entity_targetPath = null;
    private String add_link_targetPath = null;
    private String add_entity_targetPath = null;
    private String add_entity_targetPath_attribute = null;
    private String range_conditionPath = null;
    private String range_conditionhasValue = null;
    private String path_conditionPath = null;
    private String path_conditionhasValue = null;

    public parserOfMapping() {
        extractingDataMapping = new ArrayList<ElementObject>();
        extractingDataMapping2 = new ArrayList<MapObject>();
        elementsOfMapping = new ArrayList();
    }

    public void parsing(File file) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);

        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("map");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node fstNode = nodeList.item(i);

            if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                Element fstElmnt = (Element) fstNode;

                target_domain = getSpesificNodeValue(fstElmnt, "target_domain");
                src_domain = getSpesificNodeValue(fstElmnt, "src_domain");

                MapObject mapObject=new MapObject(src_domain);
                
                NodeList nodeListLinkMap = fstElmnt.getElementsByTagName("link_map");
                ArrayList<MapInfos> mapInf=new ArrayList<MapInfos>();
                
                
                for (int cnt = 0; cnt < nodeListLinkMap.getLength(); cnt++) {
                    Node nodeLinkMap = nodeListLinkMap.item(cnt);
                    if (nodeLinkMap.getNodeType() == Node.ELEMENT_NODE) {
                        Element fsrLinkElement = (Element) nodeLinkMap;

                        NodeList nodeListRangeMap = fsrLinkElement.getElementsByTagName("range_map");
                        Node fstnodeListRangeMap = nodeListRangeMap.item(0);
                        if (fstnodeListRangeMap.getNodeType() == Node.ELEMENT_NODE) {
                            Element fstRangeMapElmnt = (Element) fstnodeListRangeMap;
                            range_conditionPath = getSpesificNodeValue(fstRangeMapElmnt, "path");
                            range_conditionhasValue = getSpesificNodeValue(fstRangeMapElmnt, "has_value");
                            src_range = getSpesificNodeValue(fstRangeMapElmnt, "src_range");
                            target_range = getSpesificNodeValue(fstRangeMapElmnt, "target_range");
                            add_link_rangeMap = getSpesificNodeValue(fstRangeMapElmnt, "add_link");


                            NodeList nodeListadd_entity = fstRangeMapElmnt.getElementsByTagName("add_entity");
                            Node nodeAdd_entity = nodeListadd_entity.item(0);
                            if(nodeAdd_entity != null){
                                Element frstAdd_entity = (Element) nodeAdd_entity;
                                add_entity_rangeMap = getSpesificNodeValue(frstAdd_entity, "value");
                                add_entity_attribute = getSpesificNodeAttribute(frstAdd_entity, "value", "value_binding");

                            }
                        }

                        NodeList nodeListPathMap = fsrLinkElement.getElementsByTagName("target_path");
                        Node fstnodeListPathMap = nodeListPathMap.item(0);
                        if (fstnodeListPathMap.getNodeType() == Node.ELEMENT_NODE) {
                            Element fstPathMapElmnt = (Element) fstnodeListPathMap;
                            path_conditionPath = getSpesificNodeValue(fstPathMapElmnt, "path");
                            path_conditionhasValue = getSpesificNodeValue(fstPathMapElmnt, "has_value");
                            int_link1_targetPath = getSpesificNodeValue(fstPathMapElmnt, "int_link");
                            int_entity_targetPath = getSpesificNodeValue(fstPathMapElmnt, "int_entity");
                            add_link_targetPath = getSpesificNodeValue(fstPathMapElmnt, "add_link");
                            NodeList nodeListadd_entityTargetPath = fstPathMapElmnt.getElementsByTagName("add_entity");
                            Node nodeAdd_entityTargetPath = nodeListadd_entityTargetPath.item(0);
                            if(nodeAdd_entityTargetPath != null){
                                Element frstAdd_entityTargetPath = (Element) nodeAdd_entityTargetPath;
                                add_entity_rangeMap = getSpesificNodeValue(frstAdd_entityTargetPath, "value");
                                add_entity_attribute = getSpesificNodeAttribute(frstAdd_entityTargetPath, "value", "value_binding");

                            }
                            int_link2_targetPath = getIntLink2NodeValue(fstPathMapElmnt, "int_link");
                        }
                    }
                    int x = cnt + 1;
                    int y=i+1;

                    /*case 1*/
                    if (target_domain != null && int_link1_targetPath != null && target_range != null && (add_link_rangeMap == null && int_entity_targetPath == null && add_link_targetPath == null)) { //vasiki periptwsi mias tripletas
                        elementsOfMapping.add(src_range);

                        /*xpath expression searching in mapping.xml file for uri function name for subject class*/
                        //NodeList ndNameSubject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/domain_map/uri_rules/uri_function/name/text()");
                        NodeList ndNameSubject = getUriRules(file, "//map[" + y + "]/domain_map/uri_rules/uri_function/name/text()");
                        //NodeList ndArgumentsSubject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/domain_map/uri_rules/uri_function/arguments/text()");
                        NodeList ndArgumentsSubject = getUriRules(file, "//map[" + y + "]/domain_map/uri_rules/uri_function/arguments/text()");
                        /*xpath expression searching in mapping.xml file for uri function name for object class*/
                        //NodeList ndNameObject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/range_map/uri_rules/uri_function/name/text()");
                        NodeList ndNameObject = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/range_map/uri_rules/uri_function/name/text()");
                       //NodeList ndArgumentsObject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/range_map/uri_rules/uri_function/arguments/text()");
                        NodeList ndArgumentsObject = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/range_map/uri_rules/uri_function/arguments/text()");


                        SubjectClass sbj = subjectOfTriple(target_domain, ndNameSubject, ndArgumentsSubject,src_domain);
                        ObjectClass obj = objectOfTriple(target_range, ndNameObject, ndArgumentsObject, "null", src_range);

                        Condition cnd = hasCondition(range_conditionPath, range_conditionhasValue, path_conditionPath, path_conditionhasValue);
              

                        trips = new CIDOCTriples(sbj, int_link1_targetPath, obj);
                        triples = new ArrayList<CIDOCTriples>();
                        triples.add(trips);
                        MapInfos mapInfo=new MapInfos(cnd, triples,src_range);
                        mapInf.add(mapInfo);
                        ElementInfos elinf = new ElementInfos(cnd, triples);
                        ElementObject elObj = new ElementObject(src_range, elinf, src_domain);
                        extractingDataMapping.add(elObj);
                    } /*case 2*/ else if (target_domain != null && int_link1_targetPath != null && target_range != null && add_entity_rangeMap != null && add_link_rangeMap != null && int_link2_targetPath == null && add_link_targetPath == null && int_entity_targetPath == null) { //deuteri periptwsi (2 tripletes)
                        elementsOfMapping.add(src_range);

                        /*xpath expression searching in mapping.xml file for uri function name for subject class*/
                        //NodeList ndNameSubject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/domain_map/uri_rules/uri_function/name/text()");
                        NodeList ndNameSubject = getUriRules(file, "//map[" + y + "]/domain_map/uri_rules/uri_function/name/text()");
                        /*xpath expression searching in mapping.xml file for uri function arguments for subject class*/
                       // NodeList ndArgumentsSubject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/domain_map/uri_rules/uri_function/arguments/text()");
                        NodeList ndArgumentsSubject = getUriRules(file, "//map[" + y + "]/domain_map/uri_rules/uri_function/arguments/text()");
                        /*xpath expression searching in mapping.xml file for uri function name for object class*/
                        //NodeList ndNameObject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/range_map/uri_rules[1]/uri_function/name/text()");
                        NodeList ndNameObject = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/range_map/uri_rules[1]/uri_function/name/text()");
                        /*xpath expression searching in mapping.xml file for uri function arguments for object class*/
                        //NodeList ndArgumentsObject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/range_map/uri_rules[1]/uri_function/arguments/text()");
                        NodeList ndArgumentsObject = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/range_map/uri_rules[1]/uri_function/arguments/text()");
                        /*xpath expression searching in mapping.xml file for uri function name for additinal class*/
                        //NodeList ndNameAdditional = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/range_map/add_entity/uri_rules/uri_function/name/text()");
                        NodeList ndNameAdditional = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/range_map/add_entity/uri_rules/uri_function/name/text()");
                        /*xpath expression searching in mapping.xml file for uri function arguments for additional class*/
                        //NodeList ndArgumentsAdditional = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/range_map/add_entity/uri_rules/uri_function/arguments/text()");
                        NodeList ndArgumentsAdditional = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/range_map/add_entity/uri_rules/uri_function/arguments/text()");


                        SubjectClass sbj1 = subjectOfTriple(target_domain, ndNameSubject, ndArgumentsSubject, src_domain);


                        ObjectClass obj1 = objectOfTriple(target_range, ndNameObject, ndArgumentsObject, "null", src_range);

                        SubjectClass sbj2 = subjectOfTriple(target_range, ndNameObject, ndArgumentsObject, src_domain);


                        ObjectClass obj2 = objectOfTriple(add_entity_rangeMap, ndNameAdditional, ndArgumentsAdditional, add_entity_attribute, src_range);

                        Condition cnd = hasCondition(range_conditionPath, range_conditionhasValue, path_conditionPath, path_conditionhasValue);
     

                        trips = new CIDOCTriples(sbj1, int_link1_targetPath, obj1);
                        trips2 = new CIDOCTriples(sbj2, add_link_rangeMap, obj2);
                        triples = new ArrayList<CIDOCTriples>();
                        triples.add(trips);
                        triples.add(trips2);
                        MapInfos mapInfo=new MapInfos(cnd, triples,src_range);
                      	mapInf.add(mapInfo);
                      	ElementInfos elinf = new ElementInfos(cnd, triples);
                      	ElementObject elObj = new ElementObject(src_range, elinf,src_domain);
                        extractingDataMapping.add(elObj);
                    } /*case 3*/ else if (target_domain != null && int_link1_targetPath != null && target_range != null && int_link2_targetPath != null && int_entity_targetPath != null && add_link_rangeMap == null && add_link_targetPath == null) {
                        elementsOfMapping.add(src_range);


                        /*xpath expression searching in mapping.xml file for uri function name for subject class*/
                        //NodeList ndNameSubject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/domain_map/uri_rules/uri_function/name/text()");
                        NodeList ndNameSubject = getUriRules(file, "//map[" + y + "]/domain_map/uri_rules/uri_function/name/text()");
                        /*xpath expression searching in mapping.xml file for uri function arguments for subject class*/
                       // NodeList ndArgumentsSubject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/domain_map/uri_rules/uri_function/arguments/text()");
                        NodeList ndArgumentsSubject = getUriRules(file, "//map[" + y + "]/domain_map/uri_rules/uri_function/arguments/text()");
                        /*xpath expression searching in mapping.xml file for uri function name for object class*/
                       // NodeList ndNameObject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/range_map/uri_rules/uri_function/name/text()");
                        NodeList ndNameObject = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/range_map/uri_rules/uri_function/name/text()");
                        /*xpath expression searching in mapping.xml file for uri function arguments for object class*/
                        //NodeList ndArgumentsObject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/range_map/uri_rules/uri_function/arguments/text()");
                        NodeList ndArgumentsObject = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/range_map/uri_rules/uri_function/arguments/text()");
                        /*xpath expression searching in mapping.xml file for uri function name for intermidiate class*/
                        //NodeList ndNameIntermidiate = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/path_map/target_path/uri_rules/uri_function/name/text()");
                        NodeList ndNameIntermidiate = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/path_map/target_path/uri_rules/uri_function/name/text()");
                        /*xpath expression searching in mapping.xml file for uri function arguments for intermidiate class*/
                        //NodeList ndArgumentsIntermidiate = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/path_map/target_path/uri_rules/uri_function/arguments/text()");
                        NodeList ndArgumentsIntermidiate = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/path_map/target_path/uri_rules/uri_function/arguments/text()");




                        SubjectClass sbj1 = subjectOfTriple(target_domain, ndNameSubject, ndArgumentsSubject, src_domain);
                        ObjectClass obj2 = objectOfTriple(target_range, ndNameObject, ndArgumentsObject, "null", src_range);
                        ObjectClass obj1 = objectOfTriple(int_entity_targetPath, ndNameIntermidiate, ndArgumentsIntermidiate, "null", src_range);
                        SubjectClass sbj2 = subjectOfTriple(int_entity_targetPath, ndNameIntermidiate, ndArgumentsIntermidiate, src_domain);

                        Condition cnd = hasCondition(range_conditionPath, range_conditionhasValue, path_conditionPath, path_conditionhasValue);

                        trips = new CIDOCTriples(sbj1, int_link1_targetPath, obj1);
                        trips2 = new CIDOCTriples(sbj2, int_link2_targetPath, obj2);
                        triples = new ArrayList<CIDOCTriples>();
                        triples.add(trips);
                        triples.add(trips2);
                        MapInfos mapInfo=new MapInfos(cnd, triples,src_range);
                      	mapInf.add(mapInfo);
                        ElementInfos elinf = new ElementInfos(cnd, triples);
                        ElementObject elObj = new ElementObject(src_range, elinf, src_domain);
                        extractingDataMapping.add(elObj);

                    } /*case 4*/ else if (target_domain != null && int_link1_targetPath != null && target_range != null && int_link2_targetPath != null && int_entity_targetPath != null && add_link_rangeMap != null && add_entity_rangeMap != null && add_link_targetPath == null) {
                        elementsOfMapping.add(src_range);


                        /*xpath expression searching in mapping.xml file for uri function name for subject class*/
                        //NodeList ndNameSubject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/domain_map/uri_rules/uri_function/name/text()");
                        NodeList ndNameSubject = getUriRules(file, "//map[" + y + "]/domain_map/uri_rules/uri_function/name/text()");
                        /*xpath expression searching in mapping.xml file for uri function arguments for subject class*/
                       // NodeList ndArgumentsSubject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/domain_map/uri_rules/uri_function/arguments/text()");
                        NodeList ndArgumentsSubject = getUriRules(file, "//map[" + y + "]/domain_map/uri_rules/uri_function/arguments/text()");
                        /*xpath expression searching in mapping.xml file for uri function name for object class*/
                        //NodeList ndNameObject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/range_map/uri_rules/uri_function/name/text()");
                        NodeList ndNameObject = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/range_map/uri_rules/uri_function/name/text()");
                        /*xpath expression searching in mapping.xml file for uri function arguments for object class*/
                       // NodeList ndArgumentsObject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/range_map/uri_rules/uri_function/arguments/text()");
                        NodeList ndArgumentsObject = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/range_map/uri_rules/uri_function/arguments/text()");
                        /*xpath expression searching in mapping.xml file for uri function name for intermidiate class*/
                        //NodeList ndNameIntermidiate = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/path_map/target_path/uri_rules/uri_function/name/text()");
                        NodeList ndNameIntermidiate = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/path_map/target_path/uri_rules/uri_function/name/text()");
                        /*xpath expression searching in mapping.xml file for uri function arguments for intermidiate class*/
                       // NodeList ndArgumentsIntermidiate = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/path_map/target_path/uri_rules/uri_function/arguments/text()");
                        NodeList ndArgumentsIntermidiate = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/path_map/target_path/uri_rules/uri_function/arguments/text()");
						/*xpath expression searching in mapping.xml file for uri function name for additional class*/
                       // NodeList ndNameAdditional = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/range_map/add_entity/uri_rules/uri_function/name/text()");
                        NodeList ndNameAdditional = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/range_map/add_entity/uri_rules/uri_function/name/text()");
                        /*xpath expression searching in mapping.xml file for uri function arguments for additional class*/
                        //NodeList ndArgumentsAdditional = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/range_map/add_entity/uri_rules/uri_function/arguments/text()");
                        NodeList ndArgumentsAdditional = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/range_map/add_entity/uri_rules/uri_function/arguments/text()");


                        SubjectClass sbj1 = subjectOfTriple(target_domain, ndNameSubject, ndArgumentsSubject, src_domain);
                        ObjectClass obj2 = objectOfTriple(target_range, ndNameObject, ndArgumentsObject, "null", src_range);
                        ObjectClass obj1 = objectOfTriple(int_entity_targetPath, ndNameIntermidiate, ndArgumentsIntermidiate, "null", src_range);
                        SubjectClass sbj2 = subjectOfTriple(int_entity_targetPath, ndNameIntermidiate, ndArgumentsIntermidiate, src_domain);
                        SubjectClass sbj3 = subjectOfTriple(target_range, ndNameObject, ndArgumentsObject, src_domain);
                        ObjectClass obj3 = objectOfTriple(add_entity_rangeMap, ndNameAdditional, ndArgumentsAdditional, add_entity_attribute, src_range);
                        Condition cnd = hasCondition(range_conditionPath, range_conditionhasValue, path_conditionPath, path_conditionhasValue);

                        trips = new CIDOCTriples(sbj1, int_link1_targetPath, obj1);
                        trips2 = new CIDOCTriples(sbj2, int_link2_targetPath, obj2);
                        trips3 = new CIDOCTriples(sbj3, add_link_rangeMap, obj3);
                        triples = new ArrayList<CIDOCTriples>();
                        triples.add(trips);
                        triples.add(trips2);
                        triples.add(trips3);
                        MapInfos mapInfo=new MapInfos(cnd, triples,src_range);
                      	mapInf.add(mapInfo);
                        ElementInfos elinf = new ElementInfos(cnd, triples);
                        ElementObject elObj = new ElementObject(src_range, elinf, src_domain);
                        extractingDataMapping.add(elObj);

                    } /*case 5*/ else if (target_domain != null && int_link1_targetPath != null && target_range != null && int_link2_targetPath != null && int_entity_targetPath != null && add_link_targetPath != null && add_entity_targetPath != null && add_link_rangeMap == null) {
                        elementsOfMapping.add(src_range);


                        /*xpath expression searching in mapping.xml file for uri function name for subject class*/
                        //NodeList ndNameSubject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/domain_map/uri_rules/uri_function/name/text()");
                        NodeList ndNameSubject = getUriRules(file, "//map[" + y + "]/domain_map/uri_rules/uri_function/name/text()");
                        /*xpath expression searching in mapping.xml file for uri function arguments for subject class*/
                        //NodeList ndArgumentsSubject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/domain_map/uri_rules/uri_function/arguments/text()");
                        NodeList ndArgumentsSubject = getUriRules(file, "//map[" + y + "]/domain_map/uri_rules/uri_function/arguments/text()");
                       /*xpath expression searching in mapping.xml file for uri function name for object class*/
                        //NodeList ndNameObject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/range_map/uri_rules/uri_function/name/text()");
                        NodeList ndNameObject = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/range_map/uri_rules/uri_function/name/text()");
                        /*xpath expression searching in mapping.xml file for uri function arguments for object class*/
                       // NodeList ndArgumentsObject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/range_map/uri_rules/uri_function/arguments/text()");
                        NodeList ndArgumentsObject = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/range_map/uri_rules/uri_function/arguments/text()");
                        /*xpath expression searching in mapping.xml file for uri function name for intermidiate class*/
                        //NodeList ndNameIntermidiate = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/path_map/target_path/uri_rules[1]/uri_function/name/text()");
                        NodeList ndNameIntermidiate = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/path_map/target_path/uri_rules[1]/uri_function/name/text()");
                        /*xpath expression searching in mapping.xml file for uri function arguments for intermidiate class*/
                       //NodeList ndArgumentsIntermidiate = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/path_map/target_path/uri_rules[1]/uri_function/arguments/text()");
                        NodeList ndArgumentsIntermidiate = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/path_map/target_path/uri_rules[1]/uri_function/arguments/text()");
						/*xpath expression searching in mapping.xml file for uri function name for additional class*/
                        //NodeList ndNameAdditional = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/path_map/target_path/add_entity/uri_rules/uri_function/name/text()");
                        NodeList ndNameAdditional = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/path_map/target_path/add_entity/uri_rules/uri_function/name/text()");
                        /*xpath expression searching in mapping.xml file for uri function arguments for additional class*/
                        //NodeList ndArgumentsAdditional = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/path_map/target_path/add_entity/uri_rules/uri_function/arguments/text()");
                        NodeList ndArgumentsAdditional = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/path_map/target_path/add_entity/uri_rules/uri_function/arguments/text()");

                        SubjectClass sbj1 = subjectOfTriple(target_domain, ndNameSubject, ndArgumentsSubject, src_domain);
                        ObjectClass obj2 = objectOfTriple(target_range, ndNameObject, ndArgumentsObject, "null", src_range);
                        ObjectClass obj1 = objectOfTriple(int_entity_targetPath, ndNameIntermidiate, ndArgumentsIntermidiate, "null", src_range);
                        SubjectClass sbj2 = subjectOfTriple(int_entity_targetPath, ndNameIntermidiate, ndArgumentsIntermidiate, src_domain);
                        SubjectClass sbj3 = subjectOfTriple(int_entity_targetPath, ndNameIntermidiate, ndArgumentsIntermidiate, src_domain);
                        ObjectClass obj3 = objectOfTriple(add_entity_targetPath, ndNameAdditional, ndArgumentsAdditional, add_entity_targetPath_attribute, src_range);

                        Condition cnd = hasCondition(range_conditionPath, range_conditionhasValue, path_conditionPath, path_conditionhasValue);


                        trips = new CIDOCTriples(sbj1, int_link1_targetPath, obj1);
                        trips2 = new CIDOCTriples(sbj2, int_link2_targetPath, obj2);
                        trips3 = new CIDOCTriples(sbj3, add_link_targetPath, obj3);
                        triples = new ArrayList<CIDOCTriples>();
                        triples.add(trips);
                        triples.add(trips2);
                        triples.add(trips3);
                        MapInfos mapInfo=new MapInfos(cnd, triples,src_range);
                      	mapInf.add(mapInfo);
                        ElementInfos elinf = new ElementInfos(cnd, triples);
                        ElementObject elObj = new ElementObject(src_range, elinf, src_domain);
                        extractingDataMapping.add(elObj);

                    }/*case 6*/ else if (target_domain != null && int_link1_targetPath != null && target_range != null && int_link2_targetPath != null && int_entity_targetPath != null && add_link_targetPath != null && add_entity_targetPath != null && add_link_rangeMap != null && add_entity_rangeMap != null) {
                        elementsOfMapping.add(src_range);

                        /*xpath expression searching in mapping.xml file for uri function name for subject class*/
                        //NodeList ndNameSubject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/domain_map/uri_rules/uri_function/name/text()");
                        NodeList ndNameSubject = getUriRules(file, "//map[" + y + "]/domain_map/uri_rules/uri_function/name/text()");
                        /*xpath expression searching in mapping.xml file for uri function arguments for subject class*/
                        //NodeList ndArgumentsSubject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/domain_map/uri_rules/uri_function/arguments/text()");
                        NodeList ndArgumentsSubject = getUriRules(file, "//map[" + y + "]/domain_map/uri_rules/uri_function/arguments/text()");
                        /*xpath expression searching in mapping.xml file for uri function name for object class*/
                        //NodeList ndNameObject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/range_map/uri_rules[1]/uri_function/name/text()");
                        NodeList ndNameObject = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/range_map/uri_rules[1]/uri_function/name/text()");
                        /*xpath expression searching in mapping.xml file for uri function arguments for object class*/
//                        NodeList ndArgumentsObject = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/range_map/uri_rules[1]/uri_function/arguments/text()");
//                        NodeList ndNameIntermidiate = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/path_map/target_path/uri_rules[1]/uri_function/name/text()");
//                        NodeList ndArgumentsIntermidiate = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/path_map/target_path/uri_rules[1]/uri_function/arguments/text()");
                        NodeList ndArgumentsObject = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/range_map/uri_rules[1]/uri_function/arguments/text()");
                        NodeList ndNameIntermidiate = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/path_map/target_path/uri_rules[1]/uri_function/name/text()");
                        NodeList ndArgumentsIntermidiate = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/path_map/target_path/uri_rules[1]/uri_function/arguments/text()");

//                        NodeList ndNameAdditionalTargetPath = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/path_map/target_path/add_entity/uri_rules/uri_function/name/text()");
//                        NodeList ndArgumentsAdditionalTargetPath = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/path_map/target_path/add_entity/uri_rules/uri_function/arguments/text()");
                        NodeList ndNameAdditionalTargetPath = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/path_map/target_path/add_entity/uri_rules/uri_function/name/text()");
                        NodeList ndArgumentsAdditionalTargetPath = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/path_map/target_path/add_entity/uri_rules/uri_function/arguments/text()");

//                        NodeList ndNameAdditionalRangeMap = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/range_map/add_entity/uri_rules/uri_function/name/text()");
//                        NodeList ndArgumentsAdditionalRangeMap = getUriRules(file, "//map[link_map/range_map/src_range='" + src_range + "']/link_map[" + x + "]/range_map/add_entity/uri_rules/uri_function/arguments/text()");
                        NodeList ndNameAdditionalRangeMap = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/range_map/add_entity/uri_rules/uri_function/name/text()");
                        NodeList ndArgumentsAdditionalRangeMap = getUriRules(file, "//map[" + y + "]/link_map[" + x + "]/range_map/add_entity/uri_rules/uri_function/arguments/text()");

                        SubjectClass sbj1 = subjectOfTriple(target_domain, ndNameSubject, ndArgumentsSubject, src_domain);
                        ObjectClass obj2 = objectOfTriple(target_range, ndNameObject, ndArgumentsObject, "null", src_range);
                        ObjectClass obj1 = objectOfTriple(int_entity_targetPath, ndNameIntermidiate, ndArgumentsIntermidiate, "null", src_range);
                        SubjectClass sbj2 = subjectOfTriple(int_entity_targetPath, ndNameIntermidiate, ndArgumentsIntermidiate,src_domain);
                        SubjectClass sbj3 = subjectOfTriple(int_entity_targetPath, ndNameIntermidiate, ndArgumentsIntermidiate, src_domain);
                        ObjectClass obj3 = objectOfTriple(add_entity_targetPath, ndNameAdditionalTargetPath, ndArgumentsAdditionalTargetPath, add_entity_targetPath_attribute, src_range);
                        SubjectClass sbj4 = subjectOfTriple(target_range, ndNameObject, ndArgumentsObject, src_domain);
                        ObjectClass obj4 = objectOfTriple(add_entity_rangeMap, ndNameAdditionalRangeMap, ndArgumentsAdditionalRangeMap, add_entity_attribute, src_range);

                        Condition cnd = hasCondition(range_conditionPath, range_conditionhasValue, path_conditionPath, path_conditionhasValue);

                        trips = new CIDOCTriples(sbj1, int_link1_targetPath, obj1);
                        trips2 = new CIDOCTriples(sbj2, int_link2_targetPath, obj2);
                        trips3 = new CIDOCTriples(sbj3, add_link_targetPath, obj3);
                        trips4 = new CIDOCTriples(sbj4, add_link_rangeMap, obj4);
                        triples = new ArrayList<CIDOCTriples>();
                        triples.add(trips);
                        triples.add(trips2);
                        triples.add(trips3);
                        triples.add(trips4);
                        MapInfos mapInfo=new MapInfos(cnd, triples,src_range);
                      	mapInf.add(mapInfo);
                        ElementInfos elinf = new ElementInfos(cnd, triples);
                        ElementObject elObj = new ElementObject(src_range, elinf, src_domain);
                        extractingDataMapping.add(elObj);
                    }
                }
                mapObject.setMapinf(mapInf);
                extractingDataMapping2.add(mapObject);
            }
        }
    }

    public Condition hasCondition(String range_conditionPath, String range_conditionhasValue, String path_conditionPath, String path_conditionhasValue) {
        Condition cnd = null;
        if (range_conditionPath != null) {
            cnd = new Condition(range_conditionPath, range_conditionhasValue);
        } else if (path_conditionPath != null) {
            cnd = new Condition(path_conditionPath, path_conditionhasValue);
        } else {
            cnd = new Condition("null", "null");
        }
        return cnd;
    }

    public void printMap() {
        ElementInfos elinf = null;
        ArrayList<CIDOCTriples> ar = null;
        for (int i = 0; i < extractingDataMapping.size(); i++) {
            System.out.println(i + " " + "ELEMENT:" + extractingDataMapping.get(i).getElement());
            System.out.println("CONDITION_PATH: " + extractingDataMapping.get(i).getElementInfos().getConditions().getPath() + "CONDITION_VALUE: " + extractingDataMapping.get(i).getElementInfos().getConditions().getValue());
            ar = extractingDataMapping.get(i).getElementInfos().getTriples();
            for (int j = 0; j < ar.size(); j++) {
                System.out.println("Entity:" + ar.get(j).getSubject().getEntity() + " " + "FUNCNAME:" + ar.get(j).getSubject().getUriFunctionName());
                for (int k = 0; k < ar.get(j).getSubject().getUriFunctionArguments().size(); k++) {
                    System.out.println("FUNCArgument:" + ar.get(j).getSubject().getUriFunctionArguments().get(k));
                }
                System.out.println("Predicate:" + ar.get(j).getPredicate() + " " + "Object:" + ar.get(j).getObject().getEntity() + " " + "FUNCNAME:" + ar.get(j).getObject().getUriFunctionName());
                for (int k = 0; k < ar.get(j).getObject().getUriFunctionArguments().size(); k++) {
                    System.out.println("FUNCArgument:" + ar.get(j).getObject().getUriFunctionArguments().get(k));
                }
            }
            System.out.println("\n");
        }
    }

    public SubjectClass subjectOfTriple(String node, NodeList uriFunctionName, NodeList uriArguments, String src_domain) {
        String functionNameSubject = null;
        ArrayList functionArgumentsSubject = new ArrayList();
        if (uriFunctionName.item(0) != null) {
            functionNameSubject = uriFunctionName.item(0).getNodeValue();
        }
        for (int j = 0; j < uriArguments.getLength(); j++) {
            functionArgumentsSubject.add(uriArguments.item(j).getNodeValue());
        }
        SubjectClass sbj = new SubjectClass(node, functionNameSubject, functionArgumentsSubject, src_domain);
        return sbj;
    }

    public ObjectClass objectOfTriple(String node, NodeList uriFunctionName, NodeList uriArguments, String attribute, String src_range) {
        String functionNameObject = null;
        ArrayList functionArgumentsObject = new ArrayList();
        if (uriFunctionName.item(0) != null) {
            functionNameObject = uriFunctionName.item(0).getNodeValue();
        }
        for (int k = 0; k < uriArguments.getLength(); k++) {
            functionArgumentsObject.add(uriArguments.item(k).getNodeValue());
        }
        ObjectClass obj = new ObjectClass(node, functionNameObject, functionArgumentsObject, attribute, src_range);

        return obj;
    }

    public NodeList getUriRules(File file, String xpathExpression) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true); 
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse(file);

        NamespaceContext ctx = new NamespaceContext() {

            public String getNamespaceURI(String prefix) {
                String uri = "http://museum.zib.de/museumdat";
                return uri;
            }

            public String getPrefix(String namespaceURI) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Iterator getPrefixes(String namespaceURI) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr = xpath.compile(xpathExpression);

        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        return nodes;
    }

    public static String getSpesificNodeValue(Element fstElmnt, String node) {
        NodeList fstNmElmntLst = fstElmnt.getElementsByTagName(node);
        Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
        if (fstNmElmnt != null) {

            NodeList fstNm = fstNmElmnt.getChildNodes();
            return ((Node) fstNm.item(0)).getNodeValue();
        }
        return null;
    }

    public static String getSpesificNodeAttribute(Element fstElmnt, String node, String attributeName) {
        NodeList fstNmElmntLst = fstElmnt.getElementsByTagName(node);
        Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
        if (fstNmElmnt != null) {

            return fstNmElmnt.getAttribute(attributeName);
        }
        return null;
    }

    public static String getIntLink2NodeValue(Element fstElmnt, String node) {
        NodeList Childs = fstElmnt.getElementsByTagName(node);
        int size = Childs.getLength();
        if (size == 1) {
            return null;
        } else {
            Node e = Childs.item(size - 1);
            NodeList fstNm = e.getChildNodes();
            return ((Node) fstNm.item(0)).getNodeValue();
        }
    }
}
