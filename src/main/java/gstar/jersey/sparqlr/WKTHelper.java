/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Copyright (C) 2010, 2011, 2012, Pyravlos Team
 * 
 * http://www.strabon.di.uoa.gr/
 */
//package org.openrdf.query.algebra.evaluation.function.spatial;
package gstar.jersey.sparqlr;

// remove dependency on GeoConstants
//import eu.earthobservatory.constants.GeoConstants;


/**
 * 
 * @author Charalampos Nikolaou <charnik@di.uoa.gr>
 * @author Panayiotis Smeros <psmeros@di.uoa.gr>
 *
 */
public class WKTHelper {

	public static String  STRDF_SRID_DELIM 	= ";";
	private static String CUT_DELIM 	= "/";
	private static String URI_ENDING	= ">";
	
	/**
	 * Returns the given WKT without the SRID (if any).
	 * 
	 * @param wkt
	 * @return
	 */
	public static String getWithoutSRID(String wkt) {
		if (wkt == null) return wkt;
		
		//int pos = wkt.lastIndexOf(STRDF_SRID_DELIM);
		int pos = wkt.lastIndexOf(URI_ENDING);
		if (pos != -1) {
			//return wkt.substring(0, pos);
			return wkt.substring(pos+2);
		} else {
			return wkt;
		}
	}
	
	/**
	 * Returns the SRID of the given WKT (if any). If the WKT
	 * does not contain any, then the default is returned (specified in
	 * org.openrdf.query.algebra.evaluation.function.spatial.StrabonPolyhedron.defaultSRID).
	 * 
	 * @param wkt
	 * @return
	 */
	public static Integer getSRID(String wkt) {
		// remove ref to GeoConstants and replace with hardcoded BNG EPSG code
		//int srid = GeoConstants.defaultSRID;
		//int srid = 27700;
		int srid = 666;
		
		if (wkt == null) return srid;
		/*
		 * Format slightly different. Need to get val between the final / and the final >
		 */
		// int pos = wkt.lastIndexOf(STRDF_SRID_DELIM);
		int pos = wkt.lastIndexOf(CUT_DELIM); 
		if (pos != -1) {
			try {
				//srid = Integer.parseInt(wkt.substring(wkt.lastIndexOf(CUT_DELIM) + 1).replace(URI_ENDING, ""));
				srid = Integer.parseInt(wkt.substring(wkt.lastIndexOf(CUT_DELIM) + 1,wkt.lastIndexOf(URI_ENDING)));
			} catch (NumberFormatException e) {
				// not using a logger, dump stack trace instead.
				//logger.warn("[Strabon.WKTHelper] Was expecting an integer. The URL of the SRID was {}. Continuing with the default SRID, {}", wkt.substring(pos + 1), srid);
				e.printStackTrace();
			}
		}
		
		return srid;

	}
}
