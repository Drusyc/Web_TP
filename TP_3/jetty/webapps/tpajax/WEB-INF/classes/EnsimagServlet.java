import java.io.*;
import java.net.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	//Informations relatives à la base de données h2 (utilisées par la fonction getConnection)
	private final String jdbcUrl = "jdbc:h2:tcp://localhost/~/test";
	private final String jdbcUser = "guntzt";
	private final String jdbcPassword = "howsecureisthispassword";
	
	
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
		resp.setContentType("/application/json; charset=UTF8");
		// Fin a commenter
		
		PrintWriter out=resp.getWriter();
		out.print("[");
		Matcher m = null;
		Elements rows = cours.getElementsByTag("tr");
		String result = "";
		int nbResult = 0;
		
		if (!recherche.isEmpty()) {
			for (int i = 2; i < rows.size()-1 && nbResult < 10; i++) {
		
				Elements cells = rows.get(i).getElementsByTag("td");
				
				if (cells.size() > 2){
					
					m = p.matcher((cells.get(0).text()));
					
					String strApo 	= "\"apogee\":\"";
					String strNom 	= "\"nom\":\"";
					String strEcts 	= "\"ects\":\"" + cells.get(1).text() + "\"";
					String strUrl 	= "\"url\":\"" + cells.get(0).getElementsByTag("a").get(0).attributes().get("href") + "\"";
					while (m.find()) {
						strApo 	+= m.group(1) + "\"";
						strNom  += m.group(2) + "\""; 
					}
					
					if(strApo.toLowerCase().contains(recherche.toLowerCase()) 
						|| strNom.toLowerCase().contains(recherche.toLowerCase()) 
						|| strEcts.toLowerCase().contains(recherche.toLowerCase()) 
						|| strUrl.toLowerCase().contains(recherche.toLowerCase())) {	
						result += "{" + strApo + "," + strEcts + "," + strNom + "," + strUrl + "},\n";
						nbResult++;
					}
				}//if cells.size>2
			}//for()
			if (result.length() > 2) 
				out.print(result.substring(0, result.length() - 2));
		}
		out.print("]");
		
	}
	
	
	private void getObjectifs(HttpServletResponse resp,String urlMatiere) throws ServletException,
	IOException {
		try {
		resp.setContentType("/application/json; charset=UTF8");
		PrintWriter out=resp.getWriter();
		
		/* urlMatiere ne contient que la fin de l'url, il faut rajouter le début (adresse de l'ensimag) et concater la fin. */
		Connection con = Jsoup.connect("http://ensimag.grenoble-inp.fr/cursus-ingenieur/" + urlMatiere).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").timeout(10000);
		Connection.Response response = con.execute();
		//Document doc = Jsoup.connect("http://ensimag.grenoble-inp.fr/cursus-ingenieur/" + urlMatiere).get();
        if (response.statusCode() != 200) {
            return;
        }
        Document doc = con.get();
		Element page = doc.getElementById("contenus_page");
		Element objectifs = null;
		
		/* Récupère les objectifs contenus dans un <div class="hsep element_deco">, fils de l'element page */
		for (int i = 0; i < page.children().size(); i++) {
			if (page.child(i).hasClass("hsep")) {
				objectifs = page.child(i);
			}			
		}
		
		out.print("[");
		out.print("{\"Objectifs\" : \"");
		
		/* Split le texte de l'Objectifs par rapport au caractère '"' 
		 * Le JSON n'aime pas avoir des '"' en trop dans les valeurs des éléments.. */
		String[] sub = objectifs.text().split("\"");
		for (int i = 0; i < sub.length; i++) {
			
			/* On retire le premier mot "Objectifs" (si il y est) */
			if (i == 0 && (sub[i].substring(0, "Objectifs".length()).equals("Objectifs"))) {
				out.print(sub[i].substring("Objectifs".length(), sub[i].length()-1));
			} else {
				out.print(sub[i]);
			}
		}
		out.print("\"}");
		out.print("]");
		}
		catch (HttpStatusException e) {
	        //Adresse incorrecte..
	        System.out.println(e);
		}
		catch(Exception e ) {System.out.println(e);e.printStackTrace();}		
	}
	
	/*
	 * Renvoie le dernier commentaire de la table COMMENTAIRES 
	 */
	private void getLastComment(HttpServletResponse resp,String code) throws ServletException, IOException {
		resp.setContentType("/application/json; charset=UTF-8");
		//resp.setContentType("/text/html; charset=UTF-8");
		PrintWriter out=resp.getWriter();	
				
		/* Vérification des paramètres */
		Boolean isReqCorrect = checkCommentairesArg(out, code, null, null, null);
		if (!isReqCorrect) {
			return;
		}//check
		
		java.sql.Connection conn;
		try {
			conn = getConnection();
			int rset = selectCommentFromCode(conn,out, code, "json");
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
			//Récupère le chemin dans l'URL
			try {
			System.out.println("GET" + req.toString());
			String [] valeurs=req.getRequestURI().substring(req.getContextPath().length()+1).split("/");
			
			if (valeurs.length==2){
				String parametre=URLDecoder.decode(valeurs[1],"UTF-8");
				if ("recherche".equals(valeurs[0])) {
					recherche(resp,parametre);
				}
				else if ("objectifs".equals(valeurs[0])){
					getObjectifs(resp,parametre);				
				}
				else if ("commentaires".equals(valeurs[0])){
					getLastComment(resp,parametre);
				}
			}
			
			}
			catch (Exception e ){System.out.println(e);e.printStackTrace();}
	}
	
	/* Fonction renvoyant une connexion vers la base de donnée h2 */
	private java.sql.Connection getConnection() throws SQLException {
		java.sql.Connection conn = null;
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
			//conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/mybd", "guntzt", "howsecureisthispassword");
		    System.out.println("Connected to database");
		    
		    
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error database");
			e.printStackTrace();
		}
		return conn;
	}//getConnection()

	/*
	 * Fonction envoyant une requête de type INSERT INTO à la table COMMENTAIRES 
	 */ 
	private int insertIntoCommentaires(java.sql.Connection conn, String codeMat, String mail, Integer age, String comment) {
		/* Code de la matière */
		final int indexCode = 1;
		/* mail du redacteur */
		final int indexMail = 2;
		/* Age du redacteur */
		final int indexAge = 3;		
		/* Commentaires du redacteur */
		final int indexComment = 4;	
		
		int rs = -1;
		
		/* PreparedStatement pour insérer un commentaire */
		PreparedStatement statement;
		try {
			statement = conn.prepareStatement("INSERT INTO COMMENTAIRES (CODE, CDATE, REDACTEUR, AGE, COMMENTAIRES) "
															  + "VALUES(?, CURRENT_TIMESTAMP, ?, ?, ?);");
			statement.setString(indexCode, codeMat);
			statement.setString(indexMail, mail);
			statement.setInt(indexAge, age);
			statement.setString(indexComment, comment);
			
			rs = statement.executeUpdate();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return rs;
	}
	
	/* 
	 * Fonction envoyant une requête SELECT * à la table COMMENTAIRES.
	 * codeMat est le code sur lequel effectuer la requête
	 * typeReturn peut être soit "html" soit "json", définie sous quelle forme le serveur renvoie les résultats de la requête 
	 */
	private int selectCommentFromCode (java.sql.Connection conn,PrintWriter out, String codeMat, String typeReturn) {
		ResultSet rset = null;
		
		/* Index code */
		final int indexCode = 1;
		PreparedStatement statementCheck, statementSelect;
		try {
			
//			statementCheck = conn.prepareStatement("SELECT DISTINCT CODE FROM COMMENTAIRES WHERE CODE = ?;");
//			statementCheck.setString(indexCode, codeMat);
//			rset = statementCheck.executeQuery();			
			
			statementSelect = conn.prepareStatement("SELECT * FROM COMMENTAIRES WHERE CODE = ? ORDER BY CDATE DESC;");
			statementSelect.setString(indexCode, codeMat);
			rset = statementSelect.executeQuery();
			
			if ("html".equals(typeReturn)) {
				String htmlResponse = "";
				int cpt = 1;
				while(rset.next()) {
					System.out.println(rset.toString());
					htmlResponse += "<li id=\"com_" + cpt + "\">";
					htmlResponse += rset.getNString("REDACTEUR") + " a écrit le [" + rset.getDate("CDATE", Calendar.getInstance()) + "] : <br><br>"; 				
					htmlResponse += "<em>" + rset.getNString("COMMENTAIRES") + "</em> <br><br>" ;
					htmlResponse += "</li>";
					
					cpt++;
				} 
				
				out.println(htmlResponse);
			} else if ("json".equals(typeReturn)) {
				out.print("[");
				out.print("{");
				
				/* Si aucun résultat .. */
				if (!rset.next()) {
					out.print("\"nbComment\":\"" + "0" + "\"");
					out.print("}");
					out.print("]");
					return 0;
				}
				
				/* Récupère le dernier commentaire posté */

				out.print("\"redacteur\":\"" + rset.getNString("REDACTEUR") + "\",");
				out.print("\"date\":\"" + rset.getDate("CDATE", Calendar.getInstance()) + "\",");
				out.print("\"commentaires\":\"" + rset.getNString("COMMENTAIRES") + "\",");
				
				/* Récupère le nombre de commentaires */
				rset.last();
				out.print("\"nbComment\":\"" + rset.getRow() + "\"");
				
				out.print("}");
				out.print("]");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return 0;
	}
	
	
	
	/* Fonction de vérifications des valeurs données pour la table COMMENTAIRES 
	 * Renvoie false si un élément ne respecte pas la forme attendue 
	 * La fonction peut être utilisée juste pour tester l'attribut CODE (utile pour les requêtes de type SELECT.. WHERE CODE = ?)
	 * Pour cela, passer simplement codeMat et mettre les autres à null (mail, age, comment)
	 */
	private Boolean checkCommentairesArg(PrintWriter out, String codeMat, String mail, Integer age, String comment) {
		
		Pattern p;
		Matcher m;
		
		/* Check codeMat */
		p = Pattern.compile("[a-zA-Z0-9]{5,10}+");
		m = p.matcher(codeMat);
		if (!m.find()) {
			out.println("<li id=\"error\" style=\"color:red\">Ce code ne correspond pas au format attendu !</li>");
			return false;
		}//fi condi codeMat
		
		
		if (mail != null) {
			/* Check email */    /*    abz7ab         (.ab89a)         @ ab89bb          .fr */
			p = Pattern.compile("^[A-Za-z_0-9]+(\\.[A-Za-z_0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z]{2,5})$");
			m = p.matcher(mail);
			if (!m.find()) {
				out.println("<li id=\"error\" style=\"color:red\">L'email ne correspond pas au format attendu !</li>");
				return false;
			}//fi condi mail
		}
		
		if (age != null) {
			/* Check age */
			if (age < 18 || age > 100) {
				out.println("<li id=\"error\" style=\"color:red\">Vous devez rentrer un âge entre 18 et 100ans!</li>");
				return false;
			}//fi condi age
		}
		
		if (comment != null) {
			/* Check commentaires */
			if (comment.length() > 500) {
				out.println("<li id=\"error\" style=\"color:red\">Vos commentaires ne doivent "
						+ "pas excéder les 500 caractères (actuels : "+ comment.length() + "!)</li>");
				return false;
			}//fi condi taille commentaires 
			p = Pattern.compile("[;_§\\{\\}\\|]");
			m = p.matcher(comment);
			if (m.find()) {
				out.println("<li id=\"error\" style=\"color:red\">Le commentaire ne correspond pas au format attendu !</li>");
				return false;
			}//fi condi mail
		}
		return true;
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		try {
			
			String [] valeurs=req.getRequestURI().substring(req.getContextPath().length()+1).split("/");
			
			if (valeurs.length==2){
				String parametre=URLDecoder.decode(valeurs[1],"UTF-8");
				
				if ("select".equals(parametre)) {
					/* ** Récupération des données du POST ** */
					String codeMat = req.getParameter("codeMatiere");
					resp.setContentType("text/html;charset=UTF-8");
					PrintWriter out=resp.getWriter();			
					
					/* Vérification des paramètres */
					Boolean isReqCorrect = checkCommentairesArg(out, codeMat, null, null, null);
					if (!isReqCorrect) {
						return;
					}//check
					
					java.sql.Connection conn = getConnection();
					int rset = selectCommentFromCode(conn,out, codeMat, "html");
					conn.close();
					
				} else if ("insert".equals(parametre)) {
					/* ** Récupération des données du POST ** */
					String codeMat = req.getParameter("codeMatiere");
					String mail = req.getParameter("mailRedacteur");
					Integer age = Integer.parseInt(req.getParameter("ageRedacteur"));
					String comment = req.getParameter("commentaires");
					/* ** ** */
					
					resp.setContentType("text/html;charset=UTF-8");
					PrintWriter out=resp.getWriter();			
					
					/* Vérification des paramètres */
					Boolean isReqCorrect = checkCommentairesArg(out, codeMat, mail, age, comment);
					if (!isReqCorrect) {
						return;
					}//check
					
					java.sql.Connection conn = getConnection();
					int rs = insertIntoCommentaires(conn, codeMat, mail, age, comment);
					System.out.println("Statement result : " + rs);
					
					int rset = selectCommentFromCode(conn,out, codeMat, "html");
					conn.close();
				}
			}
			
			System.out.println("POST" + req.toString());
			
		}
		catch (Exception e ){System.out.println(e);e.printStackTrace();}
	}
}
