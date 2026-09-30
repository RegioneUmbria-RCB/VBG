using Init.Sigepro.FrontEnd.AppLogic.GestioneDatiExtra;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using System.Collections.Generic;
using System.Text.Json;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.SIC
{
    public class LocalizzazioniSICDomandaService : ILocalizzazioniSICDomandaService
    {
        private readonly LocalizzazioniService _localizzazioniService;
        private readonly ISalvataggioDomandaStrategy _salvataggioStrategy;
        private readonly IDatiExtraService _datiExtraService;

        public LocalizzazioniSICDomandaService(LocalizzazioniService localizzazioniService, ISalvataggioDomandaStrategy salvataggioStrategy, IDatiExtraService datiExtraService)
        {
            this._localizzazioniService = localizzazioniService;
            this._salvataggioStrategy = salvataggioStrategy;
            this._datiExtraService = datiExtraService;
        }

        public void AggiungiLocalizzazione(int idDomanda, NuovaLocalizzazione localizzazione, RiferimentiCampoDinamico riferimentiCampoDinamico)
        {
            this._localizzazioniService.AggiungiLocalizzazione(idDomanda, localizzazione);

            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            this.ScriviInfoAggiuntiveSuCampoDinamico(domanda, riferimentiCampoDinamico);

            // TODO: La domanda va salvata???
        }

        public void EliminaLocalizzazione(int idDomanda, int idLocalizzazione, RiferimentiCampoDinamico riferimentiCampoDinamico)
        {
            this._localizzazioniService.EliminaLocalizzazione(idDomanda, idLocalizzazione);

            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            this.ScriviInfoAggiuntiveSuCampoDinamico(domanda, riferimentiCampoDinamico);

            // TODO: La domanda va salvata???
        }

        private string GetChiaveDatiExtra(string uuidLocalizzazione) => $"SIC_{uuidLocalizzazione}_GEOJSON";

        public void SalvaInformazioniAggiuntive(int idDomanda, string uuidLocalizzazione, InformazioniAggiuntive informazioniAggiuntive)
        {
            var chiave = this.GetChiaveDatiExtra(uuidLocalizzazione);

            // Devo serializzare l'oggetto in json altrimenti la serializzazione non funziona perché non riesce a serializzare l'elemento GeoJson
            var valore = JsonSerializer.Serialize(informazioniAggiuntive);

            this._datiExtraService.Set(idDomanda, chiave, valore);
        }

        private void ScriviInfoAggiuntiveSuCampoDinamico(DomandaOnline domanda, RiferimentiCampoDinamico riferimentiCampoDinamico)
        {
            if (riferimentiCampoDinamico == null)
            {
                return;
            }

            //1. Imposto il modello come modificato
            var modello = domanda.ReadInterface.DatiDinamici.GetModelloById(riferimentiCampoDinamico.IdModello);
            if (modello != null)
            {
                domanda.WriteInterface.DatiDinamici.ModificaStatoCompilazioneModello(riferimentiCampoDinamico.IdModello, 0, false);
            }

            //2. Ciclo le localizzazioni per recuperare le info aggiuntive
            var infoAggiuntive = new List<InformazioniAggiuntive>();

            foreach (var indirizzo in domanda.ReadInterface.Localizzazioni.Indirizzi)
            {
                var chiave = this.GetChiaveDatiExtra(indirizzo.Uuid);

                var json = this._datiExtraService.Get<string>(domanda.ReadInterface.AltriDati.IdPresentazione, chiave);

                if (!string.IsNullOrEmpty(json))
                {
                    var info = JsonSerializer.Deserialize<InformazioniAggiuntive>(json);

                    if (info != null)
                    {
                        infoAggiuntive.Add(info);
                    }
                }              
            }

            //3. Aggiorno il campo dinamico
            var jsonInfoAggiuntive = JsonSerializer.Serialize(infoAggiuntive);

            domanda.WriteInterface.DatiDinamici.AggiornaOCrea(riferimentiCampoDinamico.IdCampo, 0, 0, jsonInfoAggiuntive, jsonInfoAggiuntive, riferimentiCampoDinamico.NomeCampo);
        }
    }
}
