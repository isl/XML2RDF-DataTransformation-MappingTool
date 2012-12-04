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

import java.util.ArrayList;

/**
 *
 * @author Daskalaki Evangelia (eva@ics.forth.gr)
 */
public class MapObject {

	public ArrayList<MapInfos> mapinf;
   public ArrayList<MapInfos> getMapinf() {
		return mapinf;
	}

	public void setMapinf(ArrayList<MapInfos> mapinf) {
		this.mapinf = mapinf;
	}

	public String srcDomain;

    public String getSrcDomain() {
		return srcDomain;
	}

	public void setSrcDomain(String srcDomain) {
		this.srcDomain = srcDomain;
	}

	public MapObject( ArrayList <MapInfos> mapinf, String srcDomain) {

        this.mapinf = mapinf;
        this.srcDomain = srcDomain;
    }
	public MapObject(  String srcDomain) {

       
        this.srcDomain = srcDomain;
    }


}
