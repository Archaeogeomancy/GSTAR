package gstar.jersey.sparqlr;

public class WKTHelperTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * Methods:
		 * 	getWithoutSRID(String wkt)
		 * 	getSRID(String wkt)
		 * 
		 */
		String  STRDF_SRID_DELIM 	= ";";
		String CUT_DELIM 	= "/";
		String URI_ENDING	= ">";
		
		
		WKTHelper h = new WKTHelper();
		String wkt = "<http://www.opengis.net/def/crs/EPSG/0/27700> MULTIPOLYGON (((409066.45119999925 140647.37900000086,409066.45119999925 140631.56110000136,409088.88420000026 140605.77260000154,409081.15720000101 140591.62309999901,409106.914 140573.61460000198,409126.23159999872 140419.2561,409079.8693 140273.90190000093,408921.46459999902 140288.05140000125,408903.43479999964 140335.64530000126,408843.13849999954 140378.71179999926,408764.72279999941 140408.44609999965,408737.21719999943 140413.44119999959,408690.54100000014 140503.35319999987,408678.03839999955 140568.28970000049,408679.70539999922 140579.11240000039,408888.91489999922 140651.54160000075,408923.08850000013 140639.88629999891,408997.27039999905 140664.02940000041,409056.44909999933 140667.3594000006,409066.45119999925 140647.37900000086)),((408888.91489999922 140652.32340000026,408715.54609999951 140593.21460000146,408679.70539999922 140730.58020000055,408766.38979999913 140768.87609999956,408957.40059999976 140784.00479999845,408946.29179999913 140684.14359999986,408889.74840000016 140668.97379999977,408888.91489999922 140652.32340000026)))";
		
		String subs = wkt.substring(wkt.lastIndexOf(CUT_DELIM) + 1,wkt.lastIndexOf(URI_ENDING));
		System.out.println(subs);
		
		System.out.println("wkt: " + h.getWithoutSRID(wkt));
		System.out.println("srid: " + h.getSRID(wkt));
		
		
	}

}