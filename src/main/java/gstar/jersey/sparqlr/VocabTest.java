package gstar.jersey.sparqlr;

public class VocabTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vocab v = new Vocab();
		Gettr thisGettr = new Gettr();
    	thisGettr.setQueryString(v.buildQuery(thisGettr.parseParam("Object%20Classification",false)));
    	System.out.println(thisGettr.getQueryString());
		
	}

}
