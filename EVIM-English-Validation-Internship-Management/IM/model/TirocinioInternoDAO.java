package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.GestioneRichiesta.VisualizzaRichieste.TirocinioQueryInternoStudente;
import controller.GestioneRichiesta.VisualizzaRichieste.TirocinioQueryInternoTutorAcc;
import controller.GestioneTirocinio.ListaTirocini.RegistroQuery;

public class TirocinioInternoDAO {

	/**
	 * Metodo per inserire un nuovo tirocinio interno
	 * 
	 * @author Emilio Schiavo
	 * @return boolean success/fail
	 */
	public static boolean doInsert(String email, int idTutAcc, String data, int oreTot, String status, int numCFU,
			int IDProp) {
		try (Connection con = DriverManagerConnectionPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO `evim`.`TirocinioInterno` (`EMAIL`, `ID_tutorAccademico`, `Data`, `OreTotali`, `status`, `NumeroCFU`, `ID_Proposta`) VALUES (?, ?, ?, ?, ?, ?, ?);");
			ps.setString(1, email);
			ps.setInt(2, idTutAcc);
			ps.setString(3, data);
			ps.setInt(4, oreTot);
			ps.setString(5, status);
			ps.setInt(6, numCFU);
			ps.setInt(7, IDProp);

			if (ps.executeUpdate() > 0)
				return true;
			else
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo per visualizzare le richieste effettuate effettuate dallo studente in
	 * fase di valutazione dato uno studente
	 * 
	 * @author Simone Auriemma
	 * @return ArrayList<TirocinioInterno>
	 */
	public ArrayList<TirocinioQueryInternoStudente> doRetriveAllByStudent(String EMAIL) {
		try (Connection con = DriverManagerConnectionPool.getConnection()) {
			// query per la visualizzazione della pagina da parte dello studente
			// query per visualizzare le richieste in valutazione
			String inValutazione = "in approvazione";
			PreparedStatement ps = con
					.prepareStatement("select ID_TirocinioInterno,tiro.EMAIL, tutor.Nome,tutor.Cognome, Data, OreTotali, status, NumeroCFU, FirmaPdCD, FirmaTutorAccademico, ID_Proposta from TirocinioInterno AS tiro JOIN TutorAccademico as tutor ON tiro.ID_tutorAccademico = tutor.ID_TutorAccademico where tiro.EMAIL=? AND status=?");
			ps.setString(1, EMAIL);
			ps.setString(2, inValutazione);
			ArrayList<TirocinioQueryInternoStudente> richieste = new ArrayList<TirocinioQueryInternoStudente>();
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TirocinioQueryInternoStudente a = new TirocinioQueryInternoStudente();
				a.setID_TirocinioInterno(rs.getInt(1));
				a.setEMAIL(rs.getString(2));
				a.setNomeTutorAcc(rs.getString(3));
				a.setCognomeTutorAcc(rs.getString(4));
				a.setData(rs.getString("Data"));
				a.setOreTotali(rs.getInt(6));
				a.setStatus(rs.getString("status"));
				a.setNumeroCFU(rs.getInt(8));
				a.setFirmaPdCD(rs.getBoolean("FirmaPdCD"));
				a.setFirmaTutorAccademico(rs.getBoolean("FirmaTutorAccademico"));
				a.setID_Proposta(rs.getInt(11));
				richieste.add(a);
			}
			return richieste;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Query che restituisce tutti i tirocini in fase di valutazione
	 * 
	 * @author Simone Auriemma
	 * @return ArrayList<TirocinioQueryInternoTutorAcc>
	 */
	public ArrayList<TirocinioQueryInternoTutorAcc> doRetriveAllByTutorAcc(int IDTutor) {
		try (Connection con = DriverManagerConnectionPool.getConnection()) {
			// query per la visualizzazione della pagina da parte dello studente
			// query per visualizzare le richieste in valutazione
			String inValutazione = "in approvazione";
			PreparedStatement ps = con.prepareStatement("select ID_TirocinioEsterno,tiro.EMAIL, User.NAME as nomeStud,User.SURNAME as cognomeStud, Data, OreTotali, status, CFU, FirmaPdCD, FirmaTutorAccademico, ID_Proposta from TirocinioEsterno AS tiro " + 
					"JOIN User ON tiro.EMAIL = User.EMAIL JOIN TutorAziendale as tutorAz ON tiro.ID_TutorAziendale = tutorAz.ID_TutorAziendale " + 
					"where status=? AND tiro.ID_TutorAccademico=?");
			ps.setString(1, inValutazione);
			ps.setInt(2, IDTutor);
			ArrayList<TirocinioQueryInternoTutorAcc> richieste = new ArrayList<TirocinioQueryInternoTutorAcc>();
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TirocinioQueryInternoTutorAcc a = new TirocinioQueryInternoTutorAcc();
				a.setID_TirocinioInterno(rs.getInt(1));
				a.setEMAIL(rs.getString("EMAIL"));
				a.setNomeStud(rs.getString(3));
				a.setCognomeStud(rs.getString(4));
				a.setData(rs.getString("Data"));
				a.setOreTotali(rs.getInt("OreTotali"));
				a.setStatus(rs.getString("status"));
				a.setNumeroCFU(rs.getInt("CFU"));
				a.setFirmaPdCD(rs.getBoolean("FirmaPdCD"));
				a.setFirmaTutorAccademico(rs.getBoolean("FirmaTutorAccademico"));
				a.setID_Proposta(rs.getInt("ID_Proposta"));
				richieste.add(a);
			}
			return richieste;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * @author Antonio Giano
	 * @param id si tratta il campo id,chiave primaria del tirocinio interno del
	 *           database
	 * @return un progetto formativo in pdf
	 */
	public static PDFProgettoFormativo getProgettoFormativoInterno(int id) {
		try (Connection con = DriverManagerConnectionPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(
					"select USER.NAME as NomeStudente,USER.SURNAME as CognomeStudente,USER.EMAIL as EmailStudente,TutorAccademico.Nome as NomeTutor,TutorAccademico.Cognome as CognomeTutor,TutorAccademico.email as EmailTutor, Proposta.Obiettivi as Obiettivi,Proposta.Attivita as Attivita,Proposta.Modalita as Modalita, USER.tipoCorso as corsoLaurea\r\n"
							+ "from TirocinioInterno join evim.USER on TirocinioInterno.EMAIL=USER.EMAIL\r\n"
							+ "join Proposta on TirocinioInterno.ID_Proposta=Proposta.ID_Proposta\r\n"
							+ "join TutorAccademico on TutorAccademico.ID_TutorAccademico=TirocinioInterno.ID_tutorAccademico\r\n"
							+ "where ID_TirocinioInterno=?");
			ps.setInt(1, id);
			PDFProgettoFormativo pdf = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("okay");
				pdf = new PDFProgettoFormativo();
				pdf.setNomeStudente(rs.getString(1));
				System.out.println("rs.getString(1)-->"+rs.getString(1));
				pdf.setCognomeStudente(rs.getString(2));
				pdf.setEmailStudente(rs.getString(3));
				pdf.setNomeTutorAccademico(rs.getString(4));
				pdf.setCognomeTutorAccademico(rs.getString(5));
				pdf.setEmailTutorAccademico(rs.getString(6));
				pdf.setObiettivi(rs.getString(7));
				pdf.setAttivita(rs.getString(8));
				pdf.setModalita(rs.getString(9));
				pdf.setCorsoLaurea(rs.getString(10));
			}
			System.out.println("Nome-->"+pdf.getNomeStudente());
			System.out.println("Cognome-->"+pdf.getCognomeStudente());
			return pdf;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * @author Simone Auriemma
	 * @return
	 */
	public ArrayList<TirocinioInterno> doRetriveAllValutazionePdCD() {
		try (Connection con = DriverManagerConnectionPool.getConnection()) {
			String inValutazione = "in approvazione";
			PreparedStatement ps = con.prepareStatement(
					" select * from EVIM.TirocinioInterno where status=? AND FirmaTutorAccademico=true");
			ps.setString(1, inValutazione);
			ArrayList<TirocinioInterno> richieste = new ArrayList<TirocinioInterno>();
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TirocinioInterno a = new TirocinioInterno();
				a.setID_TirocinioInterno(rs.getInt("ID_tirocinioInterno"));
				a.setID_tutorAccademico(rs.getInt("ID_tutorAccademico"));
				a.setData(rs.getString("Data"));
				a.setStatus(rs.getString("status"));
				a.setFirmaTutorAccademico(rs.getBoolean("FirmaTutorAccademico"));
				a.setFirmaPdCD(rs.getBoolean("FirmaPdCD"));
				richieste.add(a);
			}
			return richieste;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Questo metodo inserisce la firma del tutor accademico nella tabella
	 * "tirocinio interno" inserendo "true"
	 * 
	 * @author Simone Auriemma
	 * @param firma
	 * @param idTirocinio
	 * @param idTutorAccademico
	 * @return int
	 */
	public int updateFirmaTrue(boolean firma, int idTirocinio, int idTutorAccademico) {
		try (Connection con = DriverManagerConnectionPool.getConnection()) {
			// query per la visualizzazione della pagina da parte dello studente
			// query per visualizzare le richieste in valutazione
			String inValutazione = "in approvazione";
			PreparedStatement ps = con.prepareStatement(
					"update EVIM.TirocinioInterno SET FirmaTutorAccademico=? where ID_tutorAccademico=? AND status=? AND ID_TirocinioInterno=?");
			ps.setBoolean(1, firma);
			ps.setInt(2, idTutorAccademico);
			ps.setString(3, inValutazione);
			ps.setInt(4, idTirocinio);
			int rs = ps.executeUpdate();

			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Rifiuta la richiesta di uno studente
	 * 
	 * @author Simone Auriemma
	 * @param firma
	 * @param idTirocinio
	 * @param idTutorAccademico
	 * @return
	 */
	public int updateFirmaFalse(boolean firma, int idTirocinio, int idTutorAccademico) {
		try (Connection con = DriverManagerConnectionPool.getConnection()) {
			String inValutazione = "in approvazione";
			PreparedStatement ps = con.prepareStatement(
					"update EVIM.TirocinioInterno SET FirmaTutorAccademico=?,status=? where ID_tutorAccademico=? AND status=? AND ID_TirocinioInterno=?");
			ps.setBoolean(1, firma);
			ps.setString(2, "rifiutato");
			ps.setInt(3, idTutorAccademico);
			ps.setString(4, inValutazione);
			ps.setInt(5, idTirocinio);
			int rs = ps.executeUpdate();

			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public ArrayList<TirocinioInterno> doRetriveTirocinioInSvolgimentoStudente(String EMAIL) {
		try (Connection con = DriverManagerConnectionPool.getConnection()) {
			// query per la visualizzazione della pagina da parte dello studente
			// query per visualizzare le richieste in valutazione
			String inSvolgimento = "in svolgimento";
			PreparedStatement ps = con
					.prepareStatement(" select * from EVIM.TirocinioInterno where EMAIL=? AND status=?");
			ps.setString(1, EMAIL);
			ps.setString(2, inSvolgimento);
			ArrayList<TirocinioInterno> richieste = new ArrayList<TirocinioInterno>();
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TirocinioInterno a = new TirocinioInterno();
				a.setID_TirocinioInterno(rs.getInt("ID_tirocinioInterno"));
				a.setID_tutorAccademico(rs.getInt("ID_tutorAccademico"));
				a.setData(rs.getString("Data"));
				a.setStatus(rs.getString("status"));
				a.setFirmaTutorAccademico(rs.getBoolean("FirmaTutorAccademico"));
				a.setFirmaPdCD(rs.getBoolean("FirmaPdCD"));
				richieste.add(a);
			}
			return richieste;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public ArrayList<RegistroQuery> doRetriveTirocinioInSvolgimentoStudenteRegistro(String email) {
		try (Connection con = DriverManagerConnectionPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(
					"select TirocinioInterno.ID_TirocinioInterno, Registro.FirmaResponsabile, TirocinioInterno.status, TirocinioInterno.NumeroCFU,"
							+ "TirocinioInterno.OreTotali, Registro.ID_Registro "
							+ "from TirocinioInterno, Registro "
							+ " where TirocinioInterno.ID_TirocinioInterno = Registro.ID_Tirocinio AND "
							+ "TirocinioInterno.EMAIL = ? AND TirocinioInterno.status='in svolgimento'" );
			ps.setString(1, email);
			ArrayList<RegistroQuery> lista = new ArrayList<RegistroQuery>();
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				RegistroQuery a = new RegistroQuery();
				a.setID_Tirocinio(rs.getInt("ID_TirocinioInterno"));
				a.setFirmaResponsabile(rs.getBoolean("FirmaResponsabile"));
				a.setStatus(rs.getString("status"));
				a.setNumeroCFU(rs.getInt("CFU"));
				a.setOreTotali(rs.getInt("OreTotali"));
				a.setID_Registro(rs.getInt("ID_Registro"));
				lista.add(a);
			}
			return lista;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	
	public ArrayList<RegistroQuery> doRetriveTirocinioInSvolgimentoTutorAccRegistro(int id) {
		try (Connection con = DriverManagerConnectionPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(
					"select TirocinioInterno.ID_TirocinioInterno, Registro.FirmaResponsabile, TirocinioInterno.status, TirocinioInterno.NumeroCFU,"
							+ "TirocinioInterno.OreTotali, Registro.ID_Registro "
							+ "from TirocinioInterno, Registro, tutoraccademico "
							+ "where TirocinioInterno.ID_TirocinioInterno = Registro.ID_Tirocinio AND "
							+ "TirocinioInterno.ID_tutorAccademico = ? AND tirociniointerno.status='in svolgimento'" );
			ps.setInt(1, id);

			ArrayList<RegistroQuery> lista = new ArrayList<RegistroQuery>();
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				RegistroQuery a = new RegistroQuery();
				a.setID_Tirocinio(rs.getInt("ID_TirocinioInterno"));
				a.setFirmaResponsabile(rs.getBoolean("FirmaResponsabile"));
				a.setStatus(rs.getString("status"));
				a.setNumeroCFU(rs.getInt("CFU"));
				a.setOreTotali(rs.getInt("OreTotali"));
				a.setID_Registro(rs.getInt("ID_Registro"));
				lista.add(a);
			}
			return lista;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	
	public ArrayList<RegistroQuery> doRetriveTirocinioInSvolgimentoPdcdRegistro() { 
		try (Connection con = DriverManagerConnectionPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(
					"select TirocinioInterno.ID_TirocinioInterno, Registro.FirmaResponsabile, TirocinioEsterno.status, TirocinioEsterno.CFU,"
							+ "TirocinioEsterno.OreTotali, Registro.ID_Registro "
							+ "from TirocinioInterno, Registro"
							+ "where TirocinioInterno.ID_TirocinioInterno = Registro.ID_Tirocinio"
							+ "AND TirocinioInterno.status='in svolgimento'" );
			

			ArrayList<RegistroQuery> lista = new ArrayList<RegistroQuery>();
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				RegistroQuery a = new RegistroQuery();
				a.setID_Tirocinio(rs.getInt("ID_TirocinioEsterno"));
				a.setFirmaResponsabile(rs.getBoolean("FirmaResponsabile"));
				a.setStatus(rs.getString("status"));
				a.setNumeroCFU(rs.getInt("CFU"));
				a.setOreTotali(rs.getInt("OreTotali"));
				a.setID_Registro(rs.getInt("ID_Registro"));
				lista.add(a);
			}
			return lista;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	
	
}
