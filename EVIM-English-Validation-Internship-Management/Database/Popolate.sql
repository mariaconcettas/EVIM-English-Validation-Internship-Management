#Inserimento utenti
INSERT INTO `user` (`EMAIL`,`NAME`,`SURNAME`,`SEX`,`PASSWORD`,`USER_TYPE`) VALUES ('fferrucci@unisa.it','Filomena','Ferrucci','F','Ferrucci11',1);
INSERT INTO `user` (`EMAIL`,`NAME`,`SURNAME`,`SEX`,`PASSWORD`,`USER_TYPE`) VALUES ('mconetta@studenti.unisa.it','Maria Concetta','Schiavone','F','mconcetta1998',0);
INSERT INTO `user` (`EMAIL`,`NAME`,`SURNAME`,`SEX`,`PASSWORD`,`USER_TYPE`) VALUES ('simonagrieco@studenti.unisa.it','Simona','Grieco','F','grieco1998',0);

#Inserimento aziende
INSERT INTO `azienda` (`CF`,`Telefono`,`Nome`,`Password`,`Email`,`SitoWeb`,`Indirizzo`,`Descizione`) VALUES ('0764352056C','3214565780','Microsoft','Xboxthebest','microsoftofficial@tiscali.it','https://www.microsoft.com/it-it','Viale Pasubio, 21, 20154 Milano MI','I suoi prodotti principali sono il sistema operativo desktop Microsoft Windows e la suite di produttivit� personale Microsoft Office, per i quali � al primo posto nel rispettivo mercato. Altre linee di produzione comprendono: sistemi di sviluppo software (IDE e compilatori), DBMS, periferiche di input (tastiere e mouse), console di gioco (Xbox, Xbox 360 e Xbox One), periferiche di gioco (joystick e cloche per il pilotaggio di velivoli, volanti e altro).');
INSERT INTO `azienda` (`CF`,`Telefono`,`Nome`,`Password`,`Email`,`SitoWeb`,`Indirizzo`,`Descizione`) VALUES ('0854334056C','3335678903','Samsung','SamsungUnisa','samsungitalia@gmail.com','https://www.samsung.com/it/',' Via Mike Bongiorno, 9, 20124 Milano MI','Ci dedichiamo costantemente allo sviluppo e all�ampliamento della gamma di prodotti strategici nella nostra divisione dell�elettronica di consumo.');

#Inserimento tutor aziendali
INSERT INTO `evim`.`tutorAZIENDALE` (`ID_Azienda`, `Nome`, `Cognome`, `Email`, `Password`, `Telefono`) VALUES ('1', 'ugo', 'ughi', 'uughi@microsoft.it', 'ciaociao1', '3330333000');
INSERT INTO `evim`.`tutorAZIENDALE` (`ID_Azienda`, `Nome`, `Cognome`, `Email`, `Password`, `Telefono`) VALUES ('1', 'anna', 'aster', 'aaster@microsoft.it', 'ciaociao1', '3331333111');
INSERT INTO `evim`.`tutorAZIENDALE` (`ID_Azienda`, `Nome`, `Cognome`, `Email`, `Password`, `Telefono`) VALUES ('2', 'ada', 'ino', 'aino@samsung.it', 'ciaociao1', '3332333222');
INSERT INTO `evim`.`tutorAZIENDALE` (`ID_Azienda`, `Nome`, `Cognome`, `Email`, `Password`, `Telefono`) VALUES ('2', 'imma', 'bobbo', 'ibobbo@samsung.it', 'ciaociao1', '3333333333');

#Inserimento tutor accademici
INSERT INTO `tutoraccademico` (`Nome`,`Cognome`,`Password`,`indirizzo`,`email`,`Telefono`) VALUES ('Mario','Giorgio','umpalumpa2','via dalle cicogne Salerno 22344 64','mariogiorgio@unisa.it','3334333444');
INSERT INTO `tutoraccademico` (`Nome`,`Cognome`,`Password`,`indirizzo`,`email`,`Telefono`) VALUES ('Antonio','Sultani','radiomaria11','via roma 82933 Salerno 81','antoniosultani@unisa.it','3335333555');

#Inserimento proposte
INSERT INTO `proposta` (`Obiettivi`,`Competenze`,`Attivit�`,`Modalit�`,`ID_Azienda`,`ID_Tutor`) VALUES ('I tirocinanti avranno la possibilit� di lavorare su digital media, big data, seminari.','Il tirocinio Microsoft Research 2018 sono rivolti ai laurendi in informatica(conoscenza app e web)','Svilippo app e siti web','Quali sono i contenuti e lelogiche di un sistema di controllo di gestione: il sistema direzionale, l�efficacia, l�efficienza, nonch� gli attori in gioco e l\'utilit� che reca tale funzione all\'azienda nel suo complesso;',1,2);
INSERT INTO `proposta` (`Obiettivi`,`Competenze`,`Attivit�`,`Modalit�`,`ID_Azienda`,`ID_Tutor`) VALUES ('I tirocinanti dovranno elaborare algoritmi di gestione delle immagini','I tirocinanti dovranno elaborare algoritmi di gestione delle immagini ','Sviluppo immagini','25 ore settimanali presso ufficio del professore',NULL,1);
INSERT INTO `proposta` (`Obiettivi`,`Competenze`,`Attivit�`,`Modalit�`,`ID_Azienda`,`ID_Tutor`) VALUES ('I tirocinanti dovranno elaborare algoritmi di biometria','I tirocinanti dovranno elaborare algoritmi di gestione delle immagini','Algoritmi di biometria','25 ore settimanali presso ufficio del professore',NULL,2);