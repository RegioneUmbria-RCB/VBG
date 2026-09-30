
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROT_ACCESSO_OPERATORI_ATER il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			    
				/// <summary>
				/// Consente di visualizzare i dati degli operatori. Gli utenti loggati, se non sono AMMINISTRATORI, per la tutela della privacy possono visualizzare e modificare solamente i propri dati; se questo flag viene spuntato allora sarà possibile esaminare in sola lettura i dati di tutti gli altri utenti. Questa regola è usata solamente all'ATER.
				/// </summary>
				public partial class VerticalizzazioneProtAccessoOperatoriAter : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROT_ACCESSO_OPERATORI_ATER";

                    public VerticalizzazioneProtAccessoOperatoriAter()
                    {

                    }

                    public VerticalizzazioneProtAccessoOperatoriAter(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					
				}
			}
			