package model;

/**
 * 
 * @author Antonio Giano
 * Classe che modella l'entit� del tutor accademico
 */

import java.util.ArrayList;

public class TutorAccademico {

	public TutorAccademico(int idTutorAccademico, String nome, String cognome, String password, String indirizzo,
			String email, String telefono) {

		this.idTutorAccademico = idTutorAccademico;
		this.nome = nome;
		this.cognome = cognome;
		this.password = password;
		this.indirizzo = indirizzo;
		this.telefono = telefono;
		this.email = email;
	}

	public TutorAccademico() {
	}

	public ArrayList<Proposta> getListeProposte() {
		return listeProposte;
	}

	public void setListeProposte(ArrayList<Proposta> listeProposte) {
		this.listeProposte = listeProposte;
	}

	public int getIdTutorAccademico() {
		return idTutorAccademico;
	}

	public void setIdTutorAccademico(int idTutorAccademico) {
		this.idTutorAccademico = idTutorAccademico;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}



	private int idTutorAccademico;
	private String nome, cognome, password, indirizzo, email, telefono;
	private ArrayList<Proposta> listeProposte;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
