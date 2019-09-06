using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Manager.Verticalizzazioni;
using Init.SIGePro.Sit.Manager;
using Init.SIGePro.Sit.ValidazioneFormale;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit
{
    public class SIT_PISTOIA : SitBaseV2
    {
        string _urlCartografiaDaCivico;
        string _urlCartografiaDaMappale;

        public SIT_PISTOIA()
            : base(new NullValidazioneFormaleService())
        {

        }

        public override void SetupVerticalizzazione()
        {
            var vert = new VerticalizzazioneSitPistoia(this.Alias, this.Software);

            if (!vert.Attiva)
            {
                throw new ConfigurationException("La verticalizzazione SIT_PISTOIA non è attiva");
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
