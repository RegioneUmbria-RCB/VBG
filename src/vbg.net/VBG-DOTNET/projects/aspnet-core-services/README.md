# Domanda on line - Stili AGID

## Deploy su docker nel repository interno

### Prerequisiti

Occorre creare una variabile d'ambiente (di solito a livello utente) denominata PAT che contiene il Personal Access Token di devops

### Generazione Personal Access Token

Per generare un PAT su DevOps

- Accedere a DevOps
- Dal menu "User settings" (l'omino con la rotella in alto a dx) selezionare la voce "Personal access tokens"
- Fare click su "New Token"
- Utilizzare i seguenti parametri:
	- Name: VBG_Library
	- Organization: Lasciare come è
	- Expiration: Lasciare come è oppure immettere una data di scadenza a piacere. Alla scadenza sarà necessario rigenerare il token e aggiornare il file nuget.config
	- Scopes: Impostare su "Custom Defined"
	- Lasciare tutti i permessi vuoti tranne:
	- Build: Read
	- Release: Read
	- Packaging: Read


