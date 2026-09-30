// -----------------------------------------------------------------------
// <copyright file="ValidazioneBandoService.cs" company="">
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

    public interface  IValidazioneBandoA1Service : IValidazioneBandoService
    {
    }

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class ValidazioneBandoA1Service : IValidazioneBandoA1Service
    {
		IConfigurazioneValidazioneReader _configurazioneReader;

		public ValidazioneBandoA1Service(IConfigurazioneValidazioneReader configurazioneReader)
		{
			this._configurazioneReader = configurazioneReader;
		}

		public IEnumerable<string> GetErrori(IDomandaOnlineReadInterface domanda, DatiPdfCompilabile datiModello1, DatiPdfCompilabile datiModello2)
        {
			var configurazione = this._configurazioneReader.Read();

            // Almeno 6 imprese che richiedono contributo
            var numeroMinimoAziende = configurazione.Modello1.NumeroMinimoImpreseCheRichiedonoContributo;
            var nomeTipoSoggettoAziendaRichiedenteContributo = configurazione.Modello1.NomeTipoSoggettoAziendaRichiedenteContributo;
			var nomeTipoSoggettoAziendaCapofila = configurazione.Modello1.NomeTipoSoggettoAziendaCapofila;

            if (!new NumeroMinimoAziendePresenti(numeroMinimoAziende, nomeTipoSoggettoAziendaRichiedenteContributo, nomeTipoSoggettoAziendaCapofila).IsSatisfiedBy(domanda))
                yield return String.Format("Nella domanda devono essere presenti almeno {0} imprese richiedenti il contributo", numeroMinimoAziende);

            // var idModelloAllegato1 = configurazione.IdModelloAllegato1;
            // var datiModello1 = _pdfUtilsService.EstraiDatiPdf(domanda.Documenti, idModelloAllegato1);

            // il 10% del totale spese ammissibili deve essere riservato alla promozione
            var percentualePromozioneSuSpeseAmmissibili = configurazione.Modello1.PercentualePromozioneSuSpeseAmmissibili;
            var nomeCampoTotaleSpesePromozione = configurazione.Modello1.NomeCampoTotaleSpesePromozione;
            var nomeCampoTotaleSpeseAmmissibili = configurazione.Modello1.NomeCampoTotaleSpeseAmmissibili;

            if (!new ValoreCampoMaggioreDiPercentualeAltroCampo(nomeCampoTotaleSpesePromozione, nomeCampoTotaleSpeseAmmissibili, percentualePromozioneSuSpeseAmmissibili).IsSatisfiedBy(datiModello1))
            {
                yield return String.Format("Almeno il {0}% delle spese ammissibili (spese promozione + spese investimenti) devono riguardare la voce \"Promozione\"", percentualePromozioneSuSpeseAmmissibili);
            }

            // Le spese ammissibili a contributo devono essere comprese tra 300.000 e 600.000
            var valoreMinSpeseAmmissibili = configurazione.Modello1.ValoreMinSpeseAmmissibili;
            var valoreMaxSpeseAmmissibili = configurazione.Modello1.ValoreMaxSpeseAmmissibili;

            if (!new ValoreCampoInRange(nomeCampoTotaleSpeseAmmissibili, valoreMinSpeseAmmissibili, valoreMaxSpeseAmmissibili).IsSatisfiedBy(datiModello1))
            {
                yield return String.Format("Le spese amissibili a contributo devono essere comprese tra {0:c} e {1:c}", valoreMinSpeseAmmissibili, valoreMaxSpeseAmmissibili);
            }

            // A livello di singola impresa: la spesa ammissibile per ogni impresa deve essere compresa tra 50.000 e 90.000
			var nomeCampoCapofila = configurazione.Modello1.NomeCampoNominativoDittaCapofila;
            var valoreMinSpesaAmmissibileSingolaImpresa = configurazione.Modello1.ValoreMinSpesaAmmissibileSingolaImpresa;
            var valoreMaxSpesaAmmissibileSingolaImpresa = configurazione.Modello1.ValoreMaxSpesaAmmissibileSingolaImpresa;
            var nomeCampoNominativoDittaPartecipante = configurazione.Modello1.NomeCampoNominativoDittaPartecipante;
            var nomeCampoSpesePromozione = configurazione.Modello1.NomeCampoSpesePromozione;
            var nomeCampoSpeseInvestimenti = configurazione.Modello1.NomeCampoSpeseInvestimenti;

            for (var i = 0; i < 50; i++)
            {
				var nomeCampoNominativoDittaPartecipanteRow = (i == 0) ? nomeCampoCapofila : nomeCampoNominativoDittaPartecipante.Replace("[n]", i.ToString());
				var nomeCampoSpesePromozioneRow = nomeCampoSpesePromozione.Replace("[n]",(i+1).ToString());
				var nomeCampoSpeseInvestimentiRow = nomeCampoSpeseInvestimenti.Replace("[n]", (i + 1).ToString());
                var nomeImpresa = datiModello1.Valore(nomeCampoNominativoDittaPartecipanteRow);

                if(String.IsNullOrEmpty(nomeImpresa))
                    continue;   // Ok, faccio un po'dicicli a vuoto ma non so esattamente quante imprese possono essere presenti nella domanda e in che modo siano state inserite nel documento

                if (!new SommaCampiInRange(nomeCampoSpesePromozioneRow, nomeCampoSpeseInvestimentiRow, valoreMinSpesaAmmissibileSingolaImpresa, valoreMaxSpesaAmmissibileSingolaImpresa).IsSatisfiedBy(datiModello1))
                {
					yield return String.Format("Impresa {0}: la spesa amissibile dei progetti delle singole imprese devoe essere compresa tra {1:c} e {2:c}", nomeImpresa, valoreMinSpesaAmmissibileSingolaImpresa, valoreMaxSpesaAmmissibileSingolaImpresa);
                }
            }

        }

        public IEnumerable<string> GetAvvertimenti(IDomandaOnlineReadInterface domanda, DatiPdfCompilabile datiModello2)
        {
			var configurazione = this._configurazioneReader.Read();

            // Il valore delle spese per la presentazione di fideiussioni è minoredel 2% del 50% del 50% della spesa di promozione
            var nomeCampoSpeseFideiussioni = configurazione.Modello2.NomeCampoSpeseFideiussioni;
            var nomeCampoSpeseDiPromozione = configurazione.Modello2.NomeCampoSpeseDiPromozione;
            var percentualeSpeseFidejussioni = configurazione.Modello2.PercentualeSpeseFidejussioni;
            var percentualeValoreAnticipo = configurazione.Modello2.PercentualeValoreAnticipo;
            var percentualeValoreContributo = configurazione.Modello2.PercentualeValoreContributo;

            if (!new SommatoriaSpesa1MinoreDiPercentualeSommatoriaSpesa2(nomeCampoSpeseFideiussioni, nomeCampoSpeseDiPromozione, 50, percentualeValoreAnticipo, percentualeValoreContributo, percentualeSpeseFidejussioni).IsSatisfiedBy(datiModello2))
                yield return String.Format("I costi per la presentazione di fideiussioni sono superiori al {0}% dell'importo massimo di anticipo richiedibile",percentualeSpeseFidejussioni);

            // per tuttie le imprese il valore delle spese notarili deve essere minore di € 2.000
            var nomeCampoSpeseNotarili = configurazione.Modello2.NomeCampoSpeseNotarili;
            var importoMassimoSpeseNotarili = configurazione.Modello2.ImportoMassimoSpeseNotarili;

            if (!new SommaListaCampiMinoreDi(nomeCampoSpeseNotarili, 50, importoMassimoSpeseNotarili).IsSatisfiedBy(datiModello2))
                yield return String.Format("Le spese notarili superano l'importo massimo previsto di {0:c}", importoMassimoSpeseNotarili);

            // per singola impresa le spese relative a perizie tecniche devono essere minori dell 8% del valore delle spese per investimenti
			var nomeCampoCapofila = configurazione.Modello2.NomeCampoNominativoDittaCapofila;
            var nomeCampoDittaPartecipante = configurazione.Modello2.NomeCampoNominativoDittaPartecipante;
            var nomeCampoSpesePerizieTecniche = configurazione.Modello2.NomeCampoSpesePerizieTecniche;
            var nomeCampoSpesePerInvestimenti = configurazione.Modello2.NomeCampoSpesePerInvestimenti;
            var percentualeSpeseTecnicheSuInvestimenti = configurazione.Modello2.PercentualeSpeseTecnicheSuInvestimenti;

            for (var i = 0; i < 50; i++)
            {				
                var nomeCampoDitta = (i == 0) ? nomeCampoCapofila : nomeCampoDittaPartecipante.Replace("[n]",i.ToString());
                var nomeCampo = nomeCampoSpesePerizieTecniche.Replace("[n]",(i+1).ToString());
                var nomeCampoConfronto = nomeCampoSpesePerInvestimenti.Replace("[n]",(i+1).ToString());

                var nomeDitta = datiModello2.Valore(nomeCampoDitta);

                if (String.IsNullOrEmpty(nomeDitta))
                {
                    continue;
                }

                if (!new ValoreCampoMinoreDiPercentualeAltroCampo(nomeCampo, nomeCampoConfronto, percentualeSpeseTecnicheSuInvestimenti).IsSatisfiedBy(datiModello2))
                {
                    yield return String.Format("Ditta {0}: Le spese relative a perizie tecniche sono superiori all' {1}% del totale delle spese per investimenti", nomeDitta, percentualeSpeseTecnicheSuInvestimenti);
                }
            }

            // per singola impresa il valore delle spese per fidejussioni deve essere minore del 2% del 50% del 50% della spesa per investimenti
            for (var i = 0; i < 50; i++)
            {
				var nomeCampoDitta = (i == 0) ? nomeCampoCapofila : nomeCampoDittaPartecipante.Replace("[n]", i.ToString());
                var nomeCampo = nomeCampoSpeseFideiussioni.Replace("[n]", (i + 1).ToString());
                var nomeCampoConfronto = nomeCampoSpesePerInvestimenti.Replace("[n]", (i + 1).ToString());

                var nomeDitta = datiModello2.Valore(nomeCampoDitta);

                if (String.IsNullOrEmpty(nomeDitta))
                {
                    continue;
                }

                if (!new SommatoriaSpesa1MinoreDiPercentualeSommatoriaSpesa2(nomeCampo, nomeCampoConfronto, 1, percentualeValoreAnticipo, percentualeValoreContributo, percentualeSpeseFidejussioni).IsSatisfiedBy(datiModello2))
                {
                    yield return String.Format("Ditta {0}: I costi per la presentazione di fideiussioni sono superiori al {1}% dell'importo massimo dell'anticipo richiedibile", nomeDitta, percentualeSpeseTecnicheSuInvestimenti);
                }
            }

			// Verifica degli allegati inseriti
			var datiDomanda = domanda.BandiUmbria.DatiDomanda;

			foreach (var azienda in datiDomanda.Aziende)
			{
				foreach (var allegato in azienda.Allegati)
				{
					if (!allegato.IdAllegato.HasValue)
					{
						yield return String.Format("Ditta {0}: Non è stato caricato l'allegato\"{1}\"", azienda.RagioneSociale, allegato.Descrizione);	
					}
				}
			}
        }
    }
}
