
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione REPLICAISTANZE il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Funzionalità che permette di generare nuove istanze a partire dai dati presenti in una pratica precedentemente salvata.
				/// </summary>
				public partial class VerticalizzazioneReplicaistanze : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "REPLICAISTANZE";

                    public VerticalizzazioneReplicaistanze()
                    {

                    }

					public VerticalizzazioneReplicaistanze(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					
				}
			}
			