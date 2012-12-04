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

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

   
/**
 *
 * @author Koutraki Maria (kutraki@ics.forth.gr)
 */
public class SourceXMLToCidocConvertion {

	 /**
     * Takes as input the mapping file in xml format and a Source xml file and produce a rdf file.
     * @param mappingFilePath the path of mapping file.
     * @param FilePath the path of the Source xml file.
     * @param destinationFolderPath the path where will create the Result.rdf file.
     */

    public SourceXMLToCidocConvertion(String mappingFilePath, String FilePath, String destinationFolderPath) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
        parserOfMapping mappingPars = new parserOfMapping();
        mappingPars.parsing(new File(mappingFilePath));

        parserOfSourceXMLFile Parser = new parserOfSourceXMLFile(mappingPars, FilePath);
     // here the transform created by Evangelia Daskalaki, is a new version of the old transform1 method 
        try {
			Parser.transform(mappingPars, FilePath, destinationFolderPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
    }

}
