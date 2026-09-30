using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using System;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.Modena
{
    public class LocalizzazioniModenaService : ILocalizzazioniModenaService
    {
        private static class Constants
        {
            public const string CodiceCatastoDefault = "F257";
        }

        [System.Serializable]
        public class ParticellaNonSelezionataException : Exception
        {
            public ParticellaNonSelezionataException() { }
            public ParticellaNonSelezionataException(string message) : base(message) { }
            public ParticellaNonSelezionataException(string message, Exception inner) : base(message, inner) { }
            protected ParticellaNonSelezionataException(
              System.Runtime.Serialization.SerializationInfo info,
              System.Runtime.Serialization.StreamingContext context) : base(info, context) { }
        }


        private readonly LocalizzazioniService _localizzazioniService;
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;

        public LocalizzazioniModenaService(LocalizzazioniService localizzazioniService, ISalvataggioDomandaStrategy salvataggioDomandaStrategy)
        {
            _localizzazioniService = localizzazioniService;
            _salvataggioDomandaStrategy = salvataggioDomandaStrategy;
        }

        public void AggiornaLocalizzazioniModenaDaMappa(int idDomanda, LocalizzazioniMappaModenaSelezionate localizzazioni, OpzioniSalvataggioLocalizzazioniModena opzioni)
        {
            if (localizzazioni == null || !localizzazioni.ParticelleTotali.Any())
            {
                throw new ParticellaNonSelezionataException("Selezionare almeno una particella dalla mappa");
            }

            var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

            if (domanda == null)
            {
                throw new Exception($"Id domanda {idDomanda} non valido");
            }

            var stradario = _localizzazioniService.GetById(opzioni.IdStradarioDefault);

            _localizzazioniService.EliminaLocalizzazioni(domanda);

            foreach (var item in localizzazioni.ParticelleTotali)
            {
                var localizzazione = new NuovaLocalizzazione(stradario, "");
                var riferimentiCatastali = new NuovoRiferimentoCatastale(opzioni.IdCatastoDefault, opzioni.NomeCatastoDefault, item.Foglio, item.Particella);

                domanda.WriteInterface.Localizzazioni.AggiungiLocalizzazioneConRiferimentiCatastali(localizzazione, riferimentiCatastali);
            }

            if (opzioni.IdCampoParticelleManualiPresenti != -1)
            {
                var valore = localizzazioni.ParticelleManuali.Any() ? "1" : "0";
                domanda.WriteInterface.DatiDinamici.AggiornaOCrea(opzioni.IdCampoParticelleManualiPresenti, 0, 0, valore, valore, $"CAMPO_{opzioni.IdCampoParticelleManualiPresenti}");
            }

            _salvataggioDomandaStrategy.Salva(domanda);
        }

        public string GetStringaArrayParticelleSelezionate(int idDomanda, string codiceCatasto)
        {
            var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

            var str = domanda.ReadInterface
                             .Localizzazioni
                             .Indirizzi
                             .Select(x => "\"" +
                                (String.IsNullOrEmpty(codiceCatasto) ? Constants.CodiceCatastoDefault : codiceCatasto) +
                                x.RiferimentiCatastali.First().Foglio.PadLeft(5, ' ') +
                                x.RiferimentiCatastali.First().Particella.PadLeft(5, ' ') +
                                "\""
                             ).ToArray();

            if (!domanda.ReadInterface.Localizzazioni.Indirizzi.Any())
            {
                return "";
            }

            return String.Join(",", str);
        }

        public string GetStringaArrayParticelleSelezionateCore (int idDomanda, string codiceCatasto)
        {
            var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

            var str = domanda.ReadInterface
                             .Localizzazioni
                             .Indirizzi
                             .Select(x => 
                                (String.IsNullOrEmpty(codiceCatasto) ? Constants.CodiceCatastoDefault : codiceCatasto) +
                                x.RiferimentiCatastali.First().Foglio.PadLeft(5, ' ') +
                                x.RiferimentiCatastali.First().Particella.PadLeft(5, ' ') 
                                
                             ).ToArray();

            if (!domanda.ReadInterface.Localizzazioni.Indirizzi.Any())
            {
                return "";
            }

            return String.Join(",", str);
        }
    }
}
