import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/* Ensimag - 4MM1CAW */
public class EnsimagServlet extends HttpServlet {
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 1L;
	
	// L'attribut cours contient la partie du document listant l'ensemble des cours référencés sur le site de l'Ensimag
	private Element cours;
	// L'attribut p est un objet initialisé à la création décrivant sous la forme d'une expression régulière le format d'une ligne décrivant un cours
	private Pattern p;
	
	//Lors de la première invocation de la Servlet la méthode init est invoquée, ici sont initialisées les attributs
	public void init() throws ServletException 
    {
    	try {
    		// Récupération depuis de la page contenant l'ensemble des liens des cours 
	    	Document doc = Jsoup.connect("http://ensimag.grenoble-inp.fr/formation/liste-des-cours-version-francaise-385937.kjsp").get();
	    	// cours est la partie du document qui contient ces cours.
    		cours=doc.getElementsByTag("table").first();
    		// p est une expression régulière permettant de décrire le format de description d'un cours 
    		// - (CODE_APOGEE_DU_COURS) (NOM_DU_COURS)
    		// en utilisant ensuite la méthode matcher(String s) il sera possible d'isoler 
    		// CODE_APOGEE_DU_COURS et NOM_DU_COURS
    		p = Pattern.compile("-\\s([^:]*):(.*)$");	
    		
    	} catch(IOException e){ }
    }
	private void recherche(HttpServletResponse resp,String recherche) throws ServletException,
	IOException {
		// A decommenter pour avoir l'entete http correspondant à une réponse au format JSON
		//resp.setContentType("/application/json; charset=UTF8");
		// Fin a commenter
		
		PrintWriter out=resp.getWriter();
		out.print("[");
		Matcher m = null;
		Elements rows = cours.getElementsByTag("tr");
		String result = "";
		
		if (!recherche.isEmpty()) {
			for (int i = 2; i < rows.size()-1; i++) {
		
				Elements cells = rows.get(i).getElementsByTag("td");
				
				if (cells.size() > 2){
					m = p.matcher((cells.get(0).text()));
					
					String strApo 	= "\"apogee\":\"";
					String strNom 	= "\"\"nom\":\"";
					String strEcts 	= "\"ects\":\"" + cells.get(1).text() + "\"";
					String strUrl 	= "\"url\":\"" + cells.get(0).getElementsByTag("a").get(0).attributes().get("href") + "\""; //EASY
					while (m.find()) {
						strApo 	+= m.group(1) + "\"";
						strNom  += m.group(2) + "\""; 
					}
					
					if(strApo.toLowerCase().contains(recherche.toLowerCase()) 
						|| strNom.toLowerCase().contains(recherche.toLowerCase()) 
						|| strEcts.toLowerCase().contains(recherche.toLowerCase()) 
						|| strUrl.toLowerCase().contains(recherche.toLowerCase()))			
						result += "{" + strApo + "," + strEcts + "," + strNom + "," + strUrl + "},\n";
				}
			}
			if (result.length() > 2) 
				out.print(result.substring(0, result.length() - 2));
		}
		out.print("]");
		
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
			//Récupère le chemin dans l'URL
			try {
			String [] valeurs=req.getRequestURI().substring(req.getContextPath().length()+1).split("/");
			if (valeurs.length==2){
				String recherche=URLDecoder.decode(valeurs[1],"UTF-8");
				if ("recherche".equals(valeurs[0])) {
					recherche(resp,recherche);
				}
			}
			}
			catch (Exception e ){System.out.println(e);e.printStackTrace();}
	}
}
