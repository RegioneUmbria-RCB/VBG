using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca.Parametri;
using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri;
using System;
using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca
{
    public class RicercaFascicoliRequest
    {
        private const string NOME_METODO = "getInterrogazioneFascicolo";
        public int? AnnoFascicolo { get; set; }
        public string Titolario { get; set; }
        public string Oggetto { get; set; }
        public DateTime? DallaData { get; set; }
        public DateTime? AllaData { get; set; }
        public string Numero { get; set; }
        public bool RicercaFascicolo { get; set; }
        public bool EstraiProtocolli { get; set; }
        public string EnableCDATA { get; set; }

        internal NameValueCollection ToParametriRicercaFascicolo()
        {
            return new NameValueCollection
            {
                new WtdkReq(NOME_METODO).Parametro,
                new AnnoFascicolo(this.AnnoFascicolo).Parametro,
                new CodiceTitolario(this.Titolario).Parametro,
                new Oggetto(this.Oggetto).Parametro,
                new DallaData(this.DallaData).Parametro,
                new AllaData(this.AllaData).Parametro,
                new NumeroEsatto(this.Numero).Parametro,
                new TipoRicerca( this.RicercaFascicolo ).Parametro,
                new EstraiProtocolli( this.EstraiProtocolli ).Parametro,
                new EnableCDATA(this.EnableCDATA).Parametro
            };
        }
    }
}