
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione AUTENTICAZIONE_LDAP il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// (OBSOLETO) Se attiva, l'accesso a SIGePro sarà gestito da LDAP. Accesso tramite utenti di dominio. Ogni utente per accedere deve essere presente nel dominio e in SIGePro, la passowrd di accesso utilizzata sarà quella di dominio. La verticalizzazione è valida se il tipo di login non è con la combo, vedi la voce "Tipo Login" del menù CONFIGURAZIONE BACKOFFICE.
				/// </summary>
				public partial class VerticalizzazioneAutenticazioneLdap : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "AUTENTICAZIONE_LDAP";

                    public VerticalizzazioneAutenticazioneLdap()
                    {

                    }

					public VerticalizzazioneAutenticazioneLdap(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Nome del dominio al quale accedere, se non specificato prende quello del server su cui è installata la libreria che autentica.
					/// </summary>
					public string Domainname
					{
						get{ return GetString("DOMAINNAME");}
						set{ SetString("DOMAINNAME" , value); }
					}
					
					/// <summary>
					/// E' il nome del ruolo a cui appartengono gli utenti LDAP che hanno accesso a SIGEPRO. Può essere non specificato. Es. Users o SIGePro. ATTENZIONE è case sensitive.
					/// </summary>
					public string Groupname
					{
						get{ return GetString("GROUPNAME");}
						set{ SetString("GROUPNAME" , value); }
					}
					
					/// <summary>
					/// E' il nome della macchina che fa da Domain Server nel quale va ricercato il dominio DOMAINNAME. Se non specificato lo recupera dal dominio della macchina server dove è installato il componente che autentica.Il formato è Es: ldap://parsifal/ dove parsifal è il nome del domain server o l'indirizzo ip.
					/// </summary>
					public string Ldappath
					{
						get{ return GetString("LDAPPATH");}
						set{ SetString("LDAPPATH" , value); }
					}
					
					
				}
			}
			