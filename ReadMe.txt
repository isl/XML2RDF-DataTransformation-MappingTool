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
 *    XML2RDF Data Transformation Tool (Mapping Tool) is free software: 
 *    you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *   XML2RDF Data Transformation Tool (Mapping Tool) is distributed in the 
 *   hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with XML2RDF Data Transformation Tool (Mapping Tool).  
 *   If not, see <http://www.gnu.org/licenses/>.
 * 
 */
 
 XML2RDF Data Transformation Tool (Mapping Tool): This generic data transformation 
 tool maps XML data files to RDF files, given a schema matching definition, 
 based on this Mapping Language Schema.

This distribution contains:
The XML2RDF-DataTransformation-Mapping Tool version 3.3 bundle 
	includes: source code (NetBeans project), binary, required libraries and javadocs.
	
In this distribution there are 4 folders:
- examples-documentation
- SourceXMLToCidoc
- URIpolicies
- Gui

In folder examples-documentation there are xml files used as input example
files.

In folders SourceXMLToCidoc, URIpolicies kai Gui there are the packages sources
(NetBeans projects), project batch files for compilation (compileXXX.bat...) 
and javadoc files discribing the packages.

Folder Gui\dist contains all the libraries needed (jar files) to run Mapping tool,
the library "SourceXMLToCidoc.jar" contains the classes periexei for the transformation
while the library "gui.jar" contains the gui interface and the tool main function.

You can invoke the Mapping tool, by double-click on "gui.jar" (or by calling run.bat) . 

You can also invoke the Mapping tool without its graphical user interface by 
using class-method "parserOfSourceXMLFile.transform" defined in "SourceXMLToCidoc.jar".
See more information on how to invoke it in javadoc: SourceXMLToCidoc\javadocs\index.html

