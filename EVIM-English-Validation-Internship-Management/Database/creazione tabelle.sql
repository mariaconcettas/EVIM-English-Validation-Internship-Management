DROP DATABASE IF EXISTS evim;
CREATE DATABASE evim;
USE evim;

CREATE TABLE USER (
EMAIL varchar(50) not null,
NAME varchar(50) not null,
SURNAME varchar(50) not null,
SEX char not null,
PASSWORD varchar(50) not null,
USER_TYPE tinyint(1) not null,
tipoCorso varchar(30),
Luogo_Nascita char(100) not null,
Data_Nascita varchar(100) not null,
Residente varchar(100) not null,
Via varchar(100),
Telefono int not null,
Matricola int not null,
primary key (EMAIL)
);

CREATE TABLE ENTE (
ID_ENTE int(20) not null AUTO_INCREMENT,
EMAIL varchar(50) not null,
NAME varchar(100) not null,
SITE varchar(50) not null,
STATO TINYINT not null DEFAULT 0,
primary key (ID_ENTE)
);

CREATE TABLE STATE (
ID_STATE int(20) not null AUTO_INCREMENT, 
DESCRIPTION varchar(100) not null,
primary key (ID_STATE)
);

CREATE TABLE SYSTEM_ATTRIBUTE (
SLUG varchar(50) not null, 
VALUE varchar(100) not null, 
FK_USER varchar(50) not null,
primary key (SLUG),
foreign key (FK_USER) references USER(EMAIL)
ON UPDATE CASCADE
ON DELETE CASCADE
);


CREATE TABLE REQUEST (
ID_REQUEST int(20) not null AUTO_INCREMENT,
CERTIFICATE_SERIAL VARCHAR(50) not null,
LEVEL varchar(7) not null,
RELEASE_DATE date not null, 
EXPIRY_DATE date not null, 
YEAR year not null, 
REQUESTED_CFU tinyint(2) not null, 
SERIAL int(10) not null, 
VALIDATED_CFU tinyint(2) not null, 
FK_USER varchar(50) not null,
FK_CERTIFIER int(20) not null, 
FK_STATE int(20) not null, 
primary key(ID_REQUEST),
foreign key(FK_USER) references User(EMAIL)
ON UPDATE CASCADE
ON DELETE CASCADE,
foreign key(FK_CERTIFIER) references ENTE(ID_ENTE)
ON UPDATE CASCADE
ON DELETE CASCADE,
foreign key(FK_STATE) references STATE(ID_STATE)
ON UPDATE CASCADE
ON DELETE CASCADE
);

CREATE TABLE ATTACHED (
ID_ATTACHED int(20) not null AUTO_INCREMENT,
FILENAME varchar(50) not null,
FK_REQUEST int(20) not null,
FK_USER varchar(50) not null,
primary key(ID_ATTACHED),
foreign key(FK_REQUEST) references REQUEST(ID_REQUEST)
ON UPDATE CASCADE
ON DELETE CASCADE,
foreign key (FK_USER) references USER(EMAIL)
ON UPDATE CASCADE
ON DELETE CASCADE
);

create table Riconoscimento(
ID_Riconoscimento int not null auto_increment,
Email_User varchar(50) not null,
Ente_Azienda varchar(200) not null,
Profilo varchar(200) not null,
Indirizzo_Sede varchar(200) not null,
Tipo_Contratto varchar(200) not null,
Periodo varchar(200) not null,
Ore_Svolte int not null,
CFU_TirocinioObbligatorio int not null,
CFU_TirocinioEsterno int not null,
CFU_AccompagnamentoLavoro int not null,

primary key(ID_Riconoscimento),
foreign key(Email_User) references User(EMAIL)
ON UPDATE CASCADE
ON DELETE CASCADE
);

create table Azienda(
ID_Azienda int not null auto_increment,
CF char (11) not null,
Telefono varchar(13) not null,
Nome varchar(50) not null,
Password varchar(50),
Email varchar(50),
SitoWeb varchar(40),
Indirizzo varchar(100) not null,
Descizione varchar(500) not null,
primary key(ID_Azienda,CF) 
);

#id_Tutor pu� essere SIA Tutor Aziendale che il Tutor Accademico
#Nel primo caso: viene inserito dall azienda
#Nel secondo caso: verr� aggiunto automaticamente
#Non pu� essere referenziato in quando non si pu� sapere a priori il tipo di tutor

create table Proposta(
ID_Proposta int not null auto_increment, 
Obiettivi varchar(200) not null,
Competenze varchar(200) not null,
Attivita varchar(200) not null,
Modalita varchar(400) not null,
ID_Azienda int,
ID_Tutor int,
primary key(ID_Proposta),
foreign key(ID_Azienda) references Azienda(ID_Azienda)
ON UPDATE cascade
ON DELETE CASCADE
);
create table TutorAziendale(
ID_TutorAziendale int not null auto_increment,
ID_Azienda int not null,
Nome varchar(20) not null,
Cognome varchar(20) not null,
Email varchar(50) not null, 
Password varchar(20),
Telefono varchar(30),
PRIMARY KEY(ID_TutorAziendale),
FOREIGN KEY(ID_Azienda) REFERENCES Azienda(ID_Azienda)
ON UPDATE CASCADE
ON DELETE CASCADE
);
create table TutorAccademico(
ID_TutorAccademico int not null auto_increment,
Nome varchar(20) not null,
Cognome varchar(20) not null, 
Password varchar(20),
indirizzo varchar(40) not null,
email varchar(50) not null,
Telefono varchar(30),
primary key(ID_TutorAccademico)
);
create table TirocinioInterno(
ID_TirocinioInterno int not null auto_increment,
EMAIL varchar(50) not null,
ID_tutorAccademico int not null,
Data varchar(10),
OreTotali int not null,
status varchar(20) not null,
NumeroCFU int not null,
FirmaPdCD boolean not null DEFAULT false,
FirmaTutorAccademico boolean not null DEFAULT false,
ID_Proposta int not null,
primary key(ID_TirocinioInterno),
foreign key(EMAIL) references User(EMAIL)
ON UPDATE cascade
ON DELETE CASCADE,
foreign key(ID_TutorAccademico) references TutorAccademico(ID_TutorAccademico)
ON UPDATE cascade
ON DELETE CASCADE,
foreign key(ID_Proposta) references Proposta(ID_Proposta)
ON UPDATE cascade
ON DELETE CASCADE
);
create table TirocinioEsterno(
ID_TirocinioEsterno int not null auto_increment,
EMAIL varchar(50) not null,
ID_TutorAccademico int not null,
ID_TutorAziendale int not null,
Data varchar(10),
OreTotali int not null,
status varchar(20) not null,
CFU int not null,
FirmaAzienda boolean not null DEFAULT false,
FirmaTutorAziendale boolean not null DEFAULT false,
FirmaTutorAccademico boolean not null DEFAULT false,
FirmaPdCD boolean not null DEFAULT false,
ID_Proposta int not null,
primary key(ID_TirocinioEsterno),
foreign key(EMAIL) references User(EMAIL)
ON UPDATE cascade
ON DELETE CASCADE,
foreign key(ID_TutorAccademico) references TutorAccademico(ID_TutorAccademico)
ON UPDATE cascade
ON DELETE CASCADE,
foreign key(ID_TutorAziendale) references TutorAziendale(ID_TutorAziendale)
ON UPDATE cascade
ON DELETE CASCADE,
foreign key(ID_Proposta) references Proposta(ID_Proposta)
ON UPDATE cascade
ON DELETE CASCADE
);

#ID_Tirocino può essere l ID del tirocinio sia ESTERNO che INTERNO, quindi il tirocinio verrà identificato con una stringa "tipo" che indicherà il tipo di tirocinio(interno esterno)

create table Registro(
ID_Registro int not null auto_increment,
ID_Tirocinio int not null,
Status varchar(20) not null,
FirmaResponsabile boolean not null DEFAULT false,
FirmaTutorAccamico boolean not null DEFAULT false,
Tipo varchar(20) not null,
primary key(ID_Registro)
);

create table Attivita(
ID_Attivita int not null auto_increment,
ID_Registro int not null,
Descrizione varchar(200) not null,
OrarioIngresso int not null,
OrarioUscita int not null,
FirmaResponsabile boolean not null DEFAULT false,
primary key(ID_Attivita),
foreign key(ID_Registro) references Registro(ID_registro)
ON UPDATE cascade
ON DELETE CASCADE
);
create table Relazione(
ID_Relazione int not null auto_increment,
Descrizione varchar(200) not null,
Email varchar(50) not null,
status varchar(20) not null DEFAULT "Non compilato",
ID_TutorAziendale int not null,
primary key(ID_relazione),
foreign key(ID_TutorAziendale) references TutorAziendale(ID_TutorAziendale)
ON UPDATE cascade
ON DELETE cascade,
foreign key(Email) references User(EMAIL)
ON UPDATE cascade
ON DELETE cascade
);
create table Questionario_S(
ID_Questionario int not null auto_increment,
Email varchar(50) not null,
AssistenzaDisp tinyint not null,
Informazioni tinyint not null,
Servizi tinyint not null,
Assistenza tinyint not null,
Logistica tinyint not null,
Ambiente tinyint not null,
Durata tinyint not null,
Mansioni tinyint not null,
Attivita tinyint not null,
Formazione tinyint not null,
Possibilita tinyint not null,
Valutazione tinyint not null,
Competenze tinyint not null,
primary key(ID_questionario),
foreign key(Email) references User(EMAIL)
ON UPDATE cascade
ON DELETE cascade
);
create table Questionario_T(
ID_Questionario int not null auto_increment,
Email varchar(50) not null,
ID_TutorAziendale int not null,
ComptenzeIngresso tinyint not null,
CompetenzeAcquisite tinyint not null,
Utilita tinyint not null,
Motivazione tinyint not null,
Capacita tinyint not null,
Informazioni tinyint not null,
Obiettivi tinyint not null,
Servizi tinyint not null,
Assistenza tinyint not null,
Collaborazione tinyint not null,
primary key(ID_questionario),
foreign key(Email) references User(EMAIL)
ON UPDATE cascade
ON DELETE cascade,
foreign key(ID_TutorAziendale) references TutorAziendale(ID_TutorAziendale)
ON UPDATE cascade
ON DELETE cascade
);
