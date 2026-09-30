using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici.GestioneLocalizzazioni;
using VBG.DatiDinamici.GestioneLocalizzazioni.StringaFormattazioneIndirizzi;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess
{
    public static class LocalizzazioneIstanzaExtensions
    {
        public static LocalizzazioneIstanza.RiferimentiCatastali ToD2RiferimentiCatastali(this IstanzeMappali rc)
        {
            if (rc == null)
                return null;

            var tipoCatasto = rc.Codicecatasto;
            var foglio = rc.Foglio;
            var particella = rc.Particella;
            var sub = rc.Sub;

            return new LocalizzazioneIstanza.RiferimentiCatastali(tipoCatasto, foglio, particella, sub);
        }

        public static LocalizzazioneIstanza.RiferimentiCatastali ToD2RiferimentiCatastali(this RiferimentoCatastale rc)
        {
            if (rc == null)
                return null;

            var tipoCatasto = rc.TipoCatasto == RiferimentoCatastale.TipoCatastoEnum.Terreni ? "T" : "F";
            var foglio = rc.Foglio;
            var particella = rc.Particella;
            var sub = rc.Sub;

            return new LocalizzazioneIstanza.RiferimentiCatastali(tipoCatasto, foglio, particella, sub);
        }

        public static LocalizzazioneIstanza.RiferimentiCatastali ToD2RiferimentiCatastali(this IEnumerable<RiferimentoCatastale> rcList)
        {
            if (rcList == null)
                return null;

            return rcList.FirstOrDefault().ToD2RiferimentiCatastali();
        }

        public static LocalizzazioneIstanza ToD2LocalizzazioneIstanza(this IndirizzoStradario x)
        {
            return new LocalizzazioneIstanza
            {
                Civico = x.Civico,
                Coordinate = String.IsNullOrEmpty(x.Longitudine) ? null : new LocalizzazioneIstanza.Coordinata(x.Longitudine, x.Latitudine), // TODO: gestire le coordinate
                Esponente = x.Esponente,
                EsponenteInterno = x.EsponenteInterno,
                Indirizzo = x.Indirizzo,
                Interno = x.Interno,
                Km = x.Km,
                Note = x.Note,
                Piano = x.Piano,
                Scala = x.Scala,
                Uuid = x.Uuid,
                Mappali = x.RiferimentiCatastali.ToD2RiferimentiCatastali()
            };
        }

        public static LocalizzazioneIstanza ToD2LocalizzazioneIstanza(this IstanzeStradario x, IstanzeMappali riferimentiCatastali)
        {
            return new LocalizzazioneIstanza
            {
                Civico = x.CIVICO,
                Coordinate = String.IsNullOrEmpty(x.Longitudine) ? null : new LocalizzazioneIstanza.Coordinata(x.Longitudine, x.Latitudine), // TODO: gestire le coordinate
                Esponente = x.ESPONENTE,
                EsponenteInterno = x.ESPONENTEINTERNO,
                Indirizzo = x.Stradario.DESCRIZIONE,
                Interno = x.INTERNO,
                Km = x.Km,
                Note = x.NOTE,
                Piano = x.Piano,
                Scala = x.SCALA,
                Uuid = x.Uuid,
                Mappali = riferimentiCatastali?.ToD2RiferimentiCatastali()
            };
        }
    }


    public class QueryLocalizzazioni : QueryLocalizzazioniBase
    {
        private readonly IDomandaOnlineReadInterface _readInterface;

        public QueryLocalizzazioni(IDomandaOnlineReadInterface readInterface)
        {
            this._readInterface = readInterface;
        }

        public override IEnumerable<LocalizzazioneIstanza> GetLocalizzazioni(string tipoLocalizzazione)
        {
            return this
                    ._readInterface
                    .Localizzazioni
                    .Indirizzi
                    .Where(x => x.TipoLocalizzazione.ToUpperInvariant() == tipoLocalizzazione.ToUpperInvariant())
                    .Select(x => x.ToD2LocalizzazioneIstanza());
        }
    }

    public class QueryLocalizzazioniDaClasseIstanze : QueryLocalizzazioniBase
    {
        private readonly Istanze _istanza;

        public QueryLocalizzazioniDaClasseIstanze(Istanze istanza)
        {
            this._istanza = istanza;
        }

        public override IEnumerable<LocalizzazioneIstanza> GetLocalizzazioni(string tipoLocalizzazione)
        {
            var localizzazioni = new List<LocalizzazioneIstanza>();

            for (var i = 0; i < this._istanza.Stradario.Length; i++)
            {
                var x = this._istanza.Stradario[i];

                if ((x.TipoLocalizzazione?.Id.ToString() ?? "") == tipoLocalizzazione.ToUpperInvariant())
                {
                    if (this._istanza.Mappali.Length > i)
                    {

                    }
                    localizzazioni.Add(x.ToD2LocalizzazioneIstanza(this._istanza.Mappali.Length > i ? this._istanza.Mappali[i] : null));
                }
            }

            return localizzazioni;

        }
    }
}
