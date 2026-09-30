// -----------------------------------------------------------------------
// <copyright file="SitBaseV2.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using log4net;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using VBG.Backend.SIT.AppLogic.Data;
using VBG.Backend.SIT.AppLogic.Manager;
using VBG.Backend.SIT.AppLogic.Utils;
using VBG.Backend.SIT.AppLogic.ValidazioneFormale;

namespace VBG.Backend.SIT.AppLogic
{

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public abstract class SitBaseV2 : ISitApi
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(SitBaseV2));
        private readonly IValidazioneFormaleService _servizioValidazioneFormale;

        protected string IdComune { get; private set; }
        protected string Alias { get; private set; }
        protected string Software { get; private set; }

        public Data.Sit DataSit { get; set; }

        internal SitBaseV2(IValidazioneFormaleService servizioValidazioneFormale)
        {
            this._servizioValidazioneFormale = servizioValidazioneFormale;
        }

        public virtual RetSit CAPValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit CodiceViaValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit CivicoValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit ColoreValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit EsponenteInternoValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit EsponenteValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit AccessoTipoValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit AccessoNumeroValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit AccessoDescrizioneValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit FabbricatoValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit FoglioValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit InternoValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit KmValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit ParticellaValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit ScalaValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit SezioneValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit SubValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit UIValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit FrazioneValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit CircoscrizioneValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit TipoCatastoValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit PianoValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit QuartiereValidazione()
        {
            return new RetSit(true);
        }



        public virtual RetSit DettaglioFabbricato()
        {
            throw new NotImplementedException();
        }

        public virtual RetSit DettaglioUI()
        {
            throw new NotImplementedException();
        }

        public virtual RetSit ElencoCodVia()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoCivici()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoColori()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoEsponentiInterno()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoEsponenti()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoFabbricati()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoFogli()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoInterni()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoKm()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoParticelle()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoScale()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoSezioni()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoSub()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoUI()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoCAP()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoFrazioni()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoCircoscrizioni()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoVincoli()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoZone()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoSottoZone()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoDatiUrbanistici()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoPiani()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoQuartieri()
        {
            return new RetSit(true);
        }

        public virtual RetSit ElencoAccessoTipo()
        {
            return new RetSit(true);
        }

        public bool ValidaDatiSit(Data.Sit sitClass)
        {
            if (this._log.IsDebugEnabled)
                this._log.DebugFormat("Validazione della classe sit: {0}", sitClass.ToXmlString());
            try
            {
                var esito = this._servizioValidazioneFormale.Valida(sitClass);

                this._log.DebugFormat("Esito validazione: {0}", esito);

                return esito;
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la validazione della classe sit: {0}", ex.ToString());

                throw;
            }
        }

        public virtual DettagliVia[] GetListaVie(FiltroRicercaListaVie filtro, string[] codiciComuni)
        {
            return new DettagliVia[0];
        }

        public abstract void SetupVerticalizzazione(IVerticalizzazioniFactory verticalizzazioniFactory);

        public abstract string[] GetListaCampiGestiti();


        public void InizializzaParametriSigepro(string idComune, string alias, string software)
        {
            this.IdComune = idComune;
            this.Alias = alias;
            this.Software = software;
        }


        public virtual BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniFrontoffice()
        {
            return new BaseDto<SitFeatures.TipoVisualizzazione, string>[0] { };
        }


        public virtual BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniBackoffice()
        {
            return new BaseDto<SitFeatures.TipoVisualizzazione, string>[0] { };
        }
    }
}
