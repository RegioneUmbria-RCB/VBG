using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using VBG.Backend.SIT.AppLogic.Manager;
using VBG.Backend.SIT.AppLogic.ValidazioneFormale;
using VBG.Backend.SIT.Verticalizzazioni;

namespace VBG.Backend.SIT.AppLogic
{
    [SitImplementation("SIT_PISTOIA")]
    public class SitPistoia : SitBaseV2
    {
        private string _urlCartografiaDaCivico;
        private string _urlCartografiaDaMappale;

        public SitPistoia()
            : base(new NullValidazioneFormaleService())
        {
        }

        public override void SetupVerticalizzazione(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            var vert = verticalizzazioniFactory.Create<VerticalizzazioneSitPistoia>(this.Alias, this.Software);

            if (!vert.Attiva)
            {
                throw new Exception("La verticalizzazione SIT_PISTOIA non è attiva");
            }

            this._urlCartografiaDaCivico = vert.UrlCartografiaDaCivico;
            this._urlCartografiaDaMappale = vert.UrlCartografiaDaMappale;
        }

        public override BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniBackoffice()
        {
            var l = new List<BaseDto<SitFeatures.TipoVisualizzazione, string>>();

            if (!String.IsNullOrEmpty(this._urlCartografiaDaCivico))
            {
                l.Add(new BaseDto<SitFeatures.TipoVisualizzazione, string>(SitFeatures.TipoVisualizzazione.PuntoDaIndirizzo, this._urlCartografiaDaCivico));
            }

            if (!String.IsNullOrEmpty(this._urlCartografiaDaMappale))
            {
                l.Add(new BaseDto<SitFeatures.TipoVisualizzazione, string>(SitFeatures.TipoVisualizzazione.PuntoDaMappale, this._urlCartografiaDaMappale));
            }

            return l.ToArray();
        }

        public override string[] GetListaCampiGestiti()
        {
            return new string[0];
            /*return new[]
            { 
                SitIntegrationService.NomiCampiSit.Civico,
            };*/
        }

    }
}
