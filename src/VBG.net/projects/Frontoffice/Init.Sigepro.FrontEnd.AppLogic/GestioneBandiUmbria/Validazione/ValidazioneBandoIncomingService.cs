using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione
{
    public class ValidazioneBandoIncomingService : IValidazioneBandoIncomingService
    {
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


        IConfigurazioneValidazioneReader _configurazioneReader;

        public ValidazioneBandoIncomingService(IConfigurazioneValidazioneReader configurazioneReader)
        {
            this._configurazioneReader = configurazioneReader;
        }

        public IEnumerable<string> GetErrori(IDomandaOnlineReadInterface domanda, DatiPdfCompilabile datiModello1, DatiPdfCompilabile datiModello2, IEnumerable<DatiPdfCompilabile> datiModello3)
        {
            var configurazione = this._configurazioneReader.Read();

            // Almeno 6 imprese che richiedono contributo
            var numeroMinimoAziende = 5;
            var nomeTipoSoggettoAziendaRichiedenteContributo = configurazione.Modello1.NomeTipoSoggettoAziendaRichiedenteContributo;
            var nomeTipoSoggettoAziendaCapofila = configurazione.Modello1.NomeTipoSoggettoAziendaCapofila;

            if (!new NumeroMinimoAziendePresenti(numeroMinimoAziende, nomeTipoSoggettoAziendaRichiedenteContributo, nomeTipoSoggettoAziendaCapofila).IsSatisfiedBy(domanda))
                yield return String.Format("Nella domanda devono essere presenti almeno {0} imprese richiedenti il contributo", numeroMinimoAziende);

            // Le spese ammissibili a contributo devono essere comprese tra 50.000 e 80.000
            var valoreMinTotaleProgetto = 50000;//configurazione.Modello1.ValoreMinSpeseAmmissibili;
            var valoreMaxTotaleProgetto = 80000; //configurazione.Modello1.ValoreMaxSpeseAmmissibili;
            var nomeCampoImportoAmmissibile = "Spesa totale singolo aggregatoTotali";

            if (!new ValoreCampoInRange(nomeCampoImportoAmmissibile, valoreMinTotaleProgetto, valoreMaxTotaleProgetto).IsSatisfiedBy(datiModello1))
            {
                yield return String.Format("File \"{0}\": le spese ammissibili a contributo devono essere comprese tra {1:c} e {2:c}", datiModello1.NomeFile, valoreMinTotaleProgetto, valoreMaxTotaleProgetto);
            }

            var validatori = new List<IValidatoreCampi>
            {
                new ValidatoreCampi(LocalFile ("~/ValidazioneIncoming_allegato1.txt"), datiModello1),
                new ValidatoreCampi(LocalFile ("~/ValidazioneIncoming_allegato2.txt"), datiModello2),
                new ValidatoreConfrontoCampi(LocalFile("~/ValidazioneIncoming_ConfrontoCampi.txt"), datiModello1, datiModello2)
            };

            validatori.AddRange(datiModello3.Select(x => new ValidatoreCampi(LocalFile("~/ValidazioneIncoming_allegato3.txt"), x)));

            var erroriValidazione = validatori.SelectMany( x => x.GetErroriValidazione());

            foreach (var errore in erroriValidazione)
            {
                yield return errore;
            }
        }

        private string LocalFile(string relativeFileName)
        {
            return HttpContext.Current.Server.MapPath(relativeFileName);
        }

        public IEnumerable<string> GetAvvertimenti(IDomandaOnlineReadInterface domanda, DatiPdfCompilabile datiModello2)
        {
            //var configurazione = this._configurazioneReader.Read();

            // Verifica degli allegati inseriti
            var datiDomanda = domanda.BandiUmbria.DatiDomanda;

            foreach (var azienda in datiDomanda.Aziende)
            {
                foreach (var allegato in azienda.Allegati)
                {
                    if (!allegato.IdAllegato.HasValue)
                    {
                        yield return String.Format("Ditta {0}: Non è stato caricato l'allegato \"{1}\"", azienda.RagioneSociale, allegato.Descrizione);
                    }
                }
            }

        }
    }
}
