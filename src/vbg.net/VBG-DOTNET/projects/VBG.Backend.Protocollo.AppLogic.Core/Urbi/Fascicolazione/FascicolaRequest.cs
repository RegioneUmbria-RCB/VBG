using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri;
using System.Collections.Generic;
using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione
{
    public class FascicolaRequest
    {
        private const string NOME_METODO = "insDocInProtocollo";
        public int? IdProtocollo { get; set; }
        public bool EseguiSoloFascicolazione { get; set; }
        public string IdAoo { get; set; }
        public int AnnoProtocollo { get; set; }
        public int NumeroProtocollo { get; set; }
        public string TipologiaProtocollo { get; set; }
        public int? IdFascicolo { get; set; }
        public bool? GeneraFascicolo { get; set; }
        public string OggettoFascicolo { get; set; }
        public string IdentificativoFascicolo { get; set; }
        public int? FormaAggregazione { get; set; }
        public string CodiceSoggettoFascicolo { get; set; }
        public int? TipoDiChiusuraFascicolo { get; set; }
        public bool NonAggiungereClasseDocFascicolo { get; set; }
        public IEnumerable<AllegatoFascicolo> Allegati { get; set; }

        internal NameValueCollection ToParametriNuovaFascicolazione()
        {
            if (this.Allegati == null)
            {
                this.Allegati = new List<AllegatoFascicolo>();
            }
            var parametri = new NameValueCollection
            {
                new WtdkReq(NOME_METODO).Parametro,
                new IdProto(this.IdProtocollo).Parametro,
                new EseguiSoloFascicolazione(this.EseguiSoloFascicolazione).Parametro,
                new IdAoo( this.IdAoo ).Parametro,
                new AnnoProtocollo(this.AnnoProtocollo).Parametro,
                new NumeroProtocollo(this.NumeroProtocollo).Parametro,
                new TipologiaProtocollo(this.TipologiaProtocollo).Parametro,
                new OggettoFascicolo(this.OggettoFascicolo).Parametro,
                new FormaAggregazione(this.FormaAggregazione).Parametro,
                new CodiceSoggettoFascicolo(this.CodiceSoggettoFascicolo).Parametro,
                new TipoDiChiusuraFascicolo(this.TipoDiChiusuraFascicolo).Parametro,
                new NonAggiungereClasseDocFascicolo(this.NonAggiungereClasseDocFascicolo).Parametro,
                new Allegati(this.Allegati).Parametro,
            };
            if(this.IdFascicolo.HasValue)
            {
                parametri.Add(new IdFascicolo(this.IdFascicolo).Parametro);
            }
            if (!string.IsNullOrEmpty(this.IdentificativoFascicolo))
            {
               parametri.Add( new IdentificativoFascicolo(this.IdentificativoFascicolo).Parametro);
            }
            if (this.GeneraFascicolo.HasValue)
            {
                parametri.Add(new GeneraFS(this.GeneraFascicolo.Value).Parametro);
            }
            
            return parametri;
        }
    }
}
