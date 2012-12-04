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
public class MapInfos {

    public Condition cnd;
    public ArrayList<CIDOCTriples> triplesList;
    public String srcRange;

    public MapInfos() {

    }
    
	public MapInfos(Condition cnd, ArrayList<CIDOCTriples> triplesList, String srcRange) {
        this.cnd = cnd;
        this.triplesList = triplesList;
        this.srcRange = srcRange;
    }

    public Condition getCnd() {
		return cnd;
	}

	public void setCnd(Condition cnd) {
		this.cnd = cnd;
	}

	public ArrayList<CIDOCTriples> getTriplesList() {
		return triplesList;
	}

	public void setTriplesList(ArrayList<CIDOCTriples> triplesList) {
		this.triplesList = triplesList;
	}

	public void setSrcRange(String srcRange) {
		this.srcRange = srcRange;
	}

	public Condition getConditions() {
        return cnd;
    }

    public ArrayList<CIDOCTriples> getTriples() {
        return triplesList;
    }
    
    public String getSrcRange() {
		return srcRange;
	}

}
