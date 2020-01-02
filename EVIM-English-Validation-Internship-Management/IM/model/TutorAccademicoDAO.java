package model;

import java.sql.Connection;
/**
 * 
 * @author Antonio Giano 
 *	Classe che esegue delle query per prelevare dati del tutor accademico.
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Servlet implementation class VisualizzaAziende * @author Antoio Giano Classe
 * che esegue delle query per prendere i dati del tutor accademico
 */
public class TutorAccademicoDAO {

	/**
	 * Metodo che prende tutti i tutor accademici
	 * 
	 * @return ArrayList<TutorAccademico>
	 */
	public static ArrayList<TutorAccademico> doRetrieveAll() {
		try (Connection connection = DriverManagerConnectionPool.getConnection()) {
			PreparedStatement ps = connection.prepareStatement("select * from EVIM.tutoraccademico");
			ArrayList<TutorAccademico> listeTutor = new ArrayList<TutorAccademico>();

			ResultSet risultato = ps.executeQuery();

			while (risultato.next()) {
				int id = risultato.getInt(1);
				String nome = risultato.getString(2);
				String cognome = risultato.getString(3);
				String password = risultato.getString(4);
				String sesso = risultato.getString(5);
				String email = risultato.getString(6);
				
				listeTutor.add(new TutorAccademico(id, nome, cognome, password, sesso, email));

			}
			return listeTutor;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo che restituisce un tutor accademico se presente nel db altrimenti
	 * restituisce null
	 * 
	 * @param email password
	 * @return ArrayList<TutorAccademico>
	 * @author Nicola Sisti
	 */
	public static TutorAccademico doRetrieveByLoginData(String email, String password) {

		try (Connection con = DriverManagerConnectionPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(
					"select * from EVIM.tutoraccademico where Email=? AND Password=?");
			TutorAccademico utente = new TutorAccademico();
			ps.setString(1, email);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				utente.setIdTutorAccademico(rs.getInt(1));
				utente.setNome(rs.getString(2));
				utente.setCognome(rs.getString(3));
				utente.setPassword(rs.getString(4));
				utente.setSesso(rs.getString(5));
				utente.setEmail(rs.getString(6));
				
				return utente;
			} else
				return null;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	
	public static boolean insertNewTutorAccademico(String nome, String cognome,String password,String sesso,String email) { 
		String query= "INSERT INTO EVIM.tutoraccademico(`Nome`,`Cognome`,`Password`,`indirizzo`,`email`,`telefono`) VALUES(?,?,?,?,?,?)";
		try (Connection con = DriverManagerConnectionPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setString(1, nome);
			ps.setString(2, cognome);
			ps.setString(3, password);
			ps.setString(4, sesso);
			ps.setString(5, email);
			
			
			
			if(ps.executeUpdate()==1) {
				return true;
			}
			else
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
			
			throw new RuntimeException(e);
			
		}

		
	}

}
