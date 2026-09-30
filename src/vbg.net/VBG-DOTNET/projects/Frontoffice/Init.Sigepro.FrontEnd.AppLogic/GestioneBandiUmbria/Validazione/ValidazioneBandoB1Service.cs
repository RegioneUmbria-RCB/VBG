// -----------------------------------------------------------------------
// <copyright file="ValidazioneBandoB1Service.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
	using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;

	public interface IValidazioneBandoB1Service : IValidazioneBandoService
	{
	}

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ValidazioneBandoB1Service : IValidazioneBandoB1Service
	{
		IConfigurazioneValidazioneReader _configurazioneReader;

		public ValidazioneBandoB1Service(IConfigurazioneValidazioneReader configurazioneReader)
		{
			this._configurazioneReader = configurazioneReader;
		}

		private class DatiCampoDaVerificare
		{
			public readonly string Nome;
			public readonly string Etichetta;
			public readonly DatiPdfCompilabile DatiModello;

			public DatiCampoDaVerificare(string nome, string etichetta, DatiPdfCompilabile datiModello)
			{
				this.Nome = nome;
				this.Etichetta = etichetta;
				this.DatiModello = datiModello;
			}
		}

		public IEnumerable<string> GetErrori(IDomandaOnlineReadInterface domanda, DatiPdfCompilabile datiModello3, DatiPdfCompilabile datiModello4)
		{
			var configurazione = this._configurazioneReader.Read();
			var campiDaModificare = new[]{
				new DatiCampoDaVerificare(configurazione.Modello3.NomeCampoCategoriaPosseduta,"Categoria posseduta", datiModello3),
				new DatiCampoDaVerificare(configurazione.Modello3.NomeCampoDenominazioneStruttura, "Denominazione struttura", datiModello3),
				new DatiCampoDaVerificare(configurazione.Modello4.NomeCampoImportoLavoriStrutturali, "Importo lavori strutturali, impianti e finiture", datiModello4),
				new DatiCampoDaVerificare(configurazione.Modello4.NomeCampoImportoSpeseArredamenti, "Importo arredamenti hardware e software", datiModello4),
				new DatiCampoDaVerificare(configurazione.Modello4.NomeCampoImportoSpeseTecniche, "Importo spese tecniche (max 8%)", datiModello4),
				new DatiCampoDaVerificare(configurazione.Modello4.NomeCampoImportoSpeseFideiussioni, "Costi, per la presentazione di fidejussioni, nella misura massima del 2% a valere sugli importi garantiti", datiModello4),
				new DatiCampoDaVerificare(configurazione.Modello4.NomeCampoObiettiviPropostaAggregazione, "Illustrare in dettaglio gli obbiettivi della proposta di aggregazione", datiModello4),
				new DatiCampoDaVerificare(configurazione.Modello4.NomeCampoSottocriterio1, "Sottocriterio 1", datiModello4),
				new DatiCampoDaVerificare(configurazione.Modello4.NomeCampoSottocriterio2a, "Sottocriterio 2a", datiModello4),
				new DatiCampoDaVerificare(configurazione.Modello4.NomeCampoSottocriterio2b, "Sottocriterio 2b", datiModello4),
				new DatiCampoDaVerificare(configurazione.Modello4.NomeCampoSottocriterio2c, "Sottocriterio 2c", datiModello4),
				new DatiCampoDaVerificare(configurazione.Modello4.NomeCampoSottocriterio2d, "Sottocriterio 2d", datiModello4)
			};

			foreach (var campo in campiDaModificare)
			{
				if (!new ValoreCampoPresente(campo.Nome).IsSatisfiedBy(campo.DatiModello))
				{
					yield return String.Format("Immettere un valore per il campo \"{0}\"",campo.Etichetta);
				}
			}
						
			// Le spese ammissibili a contributo devono essere comprese tra 50.000 e 90.000
			var nomeCampoTotaleSpeseAmmissibili = configurazione.Modello3.NomeCampoTotaleSpeseAmmissibili;
			var valoreMinSpeseAmmissibili = configurazione.Modello3.ValoreMinSpeseAmmissibili;
			var valoreMaxSpeseAmmissibili = configurazione.Modello3.ValoreMaxSpeseAmmissibili;

			if (!new ValoreCampoPresente(nomeCampoTotaleSpeseAmmissibili).IsSatisfiedBy(datiModello3))
			{
				yield return String.Format("Immettere un valore per il campo \"Importo totale della spesa ammissibile a contributo\"");
			}

			if (!new ValoreCampoInRange(nomeCampoTotaleSpeseAmmissibili, valoreMinSpeseAmmissibili, valoreMaxSpeseAmmissibili).IsSatisfiedBy(datiModello3))
			{
				yield return String.Format("Le spese amissibili a contributo devono essere comprese tra {0:c} e {1:c}", valoreMinSpeseAmmissibili, valoreMaxSpeseAmmissibili);
			}
		}

		private bool VerificaCompilazioneCampo(string nomeCampo, DatiPdfCompilabile datiModello)
		{
			return !new ValoreCampoPresente(nomeCampo).IsSatisfiedBy(datiModello);
		}

		public IEnumerable<string> GetAvvertimenti(IDomandaOnlineReadInterface domanda, DatiPdfCompilabile datiModello4)
		{
			var configurazione = this._configurazioneReader.Read();

			// per singola impresa le spese relative a perizie tecniche devono essere minori dell 8% del valore delle spese per investimenti
			var nomeCampoSpeseInvestimenti = configurazione.Modello4.NomeCampoImportoSpeseInvestimenti;
			var nomeCampoSpeseTecniche = configurazione.Modello4.NomeCampoImportoSpeseTecniche;
			var percentualeSpeseTecnicheSuInvestimenti = configurazione.Modello4.PercentualeSpeseTecnicheSuInvestimenti;

			if (!new ValoreCampoMinoreDiPercentualeAltroCampo(nomeCampoSpeseTecniche, nomeCampoSpeseInvestimenti, percentualeSpeseTecnicheSuInvestimenti).IsSatisfiedBy(datiModello4))
			{
				yield return String.Format("Le spese relative a perizie tecniche sono superiori all' {0}% del totale delle spese per investimenti", percentualeSpeseTecnicheSuInvestimenti);
			}

			// pil valore delle spese per fidejussioni deve essere minore del 2% del 50% del 50% della spesa per investimenti
			var nomeCampoSpeseFideiussioni = configurazione.Modello4.NomeCampoImportoSpeseFideiussioni;
			var percentualeValoreAnticipo = configurazione.Modello4.PercentualeValoreAnticipo;
			var percentualeValoreContributo = configurazione.Modello4.PercentualeValoreContributo;
			var percentualeSpeseFidejussioni = configurazione.Modello4.PercentualeSpeseFidejussioni;

			if (!new SommatoriaSpesa1MinoreDiPercentualeSommatoriaSpesa2(nomeCampoSpeseFideiussioni, nomeCampoSpeseInvestimenti, 1, percentualeValoreAnticipo, percentualeValoreContributo, percentualeSpeseFidejussioni).IsSatisfiedBy(datiModello4))
			{
				yield return String.Format("I costi per la presentazione di fideiussioni sono superiori al {0}% dell'importo massimo dell'anticipo richiedibile", percentualeSpeseFidejussioni);
			}
			
		}
	}
}
