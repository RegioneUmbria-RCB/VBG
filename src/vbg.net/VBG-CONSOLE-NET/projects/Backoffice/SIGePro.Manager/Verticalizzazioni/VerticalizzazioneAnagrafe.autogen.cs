
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione ANAGRAFE il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attiva, si potranno personalizzare alcune configurazioni per le anagrafiche ( permettere la trasformazione da PG a PG e viceversa, mostrare la sezione del legale rappresentante nella scheda dell'anagrafica, visualizzare la forma giuridica sulle ricerche, ... )
				/// </summary>
				public partial class VerticalizzazioneAnagrafe : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "ANAGRAFE";

                    public VerticalizzazioneAnagrafe()
                    {

                    }

					public VerticalizzazioneAnagrafe(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Se abilitato, si potrà gestire il legale rappresentante nella scheda dell'anagrafica delle persone giuridiche.
/// Valori ammessi: S = abilitato, N o non impostato = non abilitato
					/// </summary>
					public string Anagrafelegalerappresentante
					{
						get{ return GetString("ANAGRAFELEGALERAPPRESENTANTE");}
						set{ SetString("ANAGRAFELEGALERAPPRESENTANTE" , value); }
					}
					
					/// <summary>
					/// Se abilitato, nella gestione delle anagrafiche compare un pulsante che permette di trasformare una persona fisica in giuridica e viceversa.
/// Valori ammessi: S = attiva, N o non impostato = non attiva
					/// </summary>
					public string AnagrafePgToPf
					{
						get{ return GetString("ANAGRAFE_PG_TO_PF");}
						set{ SetString("ANAGRAFE_PG_TO_PF" , value); }
					}
					
					/// <summary>
					/// Se abilitato, nelle liste visualizzate a seguito di una ricerca di una persona giuridica, verrà mostrata anche la forma giuridica in fondo alla ragione sociale.
/// Es:
/// CAVALLARO F. E REGA A. & C. S.N.C. (12542231568) - [PG].
/// La forma giuridica, di norma, è parte della ragione sociale per cui non ci dovrebbe essere il bisogno di attivare il parametro della verticalizzazione.
/// Valori ammessi:
/// S = mostra la forma giuridica, N o non impostato = non mostrare la forma giuridica
					/// </summary>
					public string MostraFormaGiuridica
					{
						get{ return GetString("MOSTRA_FORMA_GIURIDICA");}
						set{ SetString("MOSTRA_FORMA_GIURIDICA" , value); }
					}
					
					
				}
			}
			