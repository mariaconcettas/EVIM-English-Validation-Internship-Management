package controller.GestioneAccount;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AttivitaDAO;
import model.Azienda;
import model.TutorAziendaleDAO;
import model.User;

/**
 * Servlet implementation class CreaAccount
 */
@SuppressWarnings("serial")
@WebServlet("/CreaAccount")
public class CreaAccount extends HttpServlet {

	public CreaAccount() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession sessione = request.getSession();

		if (sessione.getAttribute("utenteLoggato") == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
			request.setAttribute("utenteCreato", false);
			dispatcher.forward(request, response);
		} else {

			String tipoUtente = sessione.getAttribute("utenteLoggato").getClass().getName();

			// in base all'oggetto nella sessione , controllo se l'utente � abilitat
			if (tipoUtente.equalsIgnoreCase("model.Azienda")) {
				// � loggato un tipo "azienda"
				//
				Azienda azienda = (Azienda) sessione.getAttribute("utenteLoggato");
				String nome = request.getParameter("nome");
				String cognome = request.getParameter("cognome");
				String telefono = request.getParameter("telefono");
				String email = request.getParameter("emaila");
				String password = request.getParameter("passworda");
				String confermaPassword = request.getParameter("confermaPassword");
				int rs;
				if (nome == null || cognome == null || password == null || confermaPassword == null
						|| !password.equals(confermaPassword) || email == null || telefono == null) {
					rs = 3; /* rs=3 significa qualche campo sbagliato */
					request.setAttribute("risultato", rs);
					RequestDispatcher dispatcher = request.getRequestDispatcher("gestioneAccount.jsp");
					request.setAttribute("utenteCreato", false);
					dispatcher.forward(request, response);
				} else {

					/* rs=1 significa account creato con successo */
					if ((rs = new TutorAziendaleDAO().doSave(azienda.getID_Azienda(), nome, cognome, email, password,
							telefono)) == 1) {
						request.setAttribute("risultato", rs);
						RequestDispatcher dispatcher = request.getRequestDispatcher("gestioneAccount.jsp");
						request.setAttribute("utenteCreato", true);
						dispatcher.forward(request, response);
					} else
					/* rs=0 significa account non creato */
					{
						rs = 0;
						request.setAttribute("risultato", rs);
						RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/error.jsp");
						request.setAttribute("utenteCreato", false);
						dispatcher.forward(request, response);
					}
				}
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("permissionDenied.jsp");
				request.setAttribute("utenteCreato", false);
				dispatcher.forward(request, response);
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
