
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione GENERA_COD_PRATICA_TELEMATICA il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attivato consente di generare il codice pratica telematica nella fase di inserimento dell'istanza nel caso questo non venga passato.
				/// </summary>
				public partial class VerticalizzazioneGeneraCodPraticaTelematica : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "GENERA_COD_PRATICA_TELEMATICA";
					
					public VerticalizzazioneGeneraCodPraticaTelematica(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					
				}
			}
			