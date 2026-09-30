using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.PresentazionePraticheEdilizieSiena
{
    public class LocalizzazioneInterventoLDP
    {
        public class DatiNuovaLocalizzazione
        {
            public readonly NuovaLocalizzazione Localizzazione;
            public readonly NuovoRiferimentoCatastale RiferimentiCatastali;

            public DatiNuovaLocalizzazione(NuovaLocalizzazione localizzazione, NuovoRiferimentoCatastale riferimentiCatastali)
            {
                this.Localizzazione = localizzazione;
                this.RiferimentiCatastali = riferimentiCatastali;
            }
        }

        private static class NuovaLocalizzazioneHelper
        {
            internal static NuovaLocalizzazione FromCivicoLDP(ILocalizzazioniService localizzazioniService, ComplexTypeCivico c, ComplexTypePoint coordinate)
            {
                 var i = localizzazioniService.GetIndirizzoByCodViario(c.codice_strada);

                if (i == null)
                {
                    throw new InvalidOperationException("Codice via " + c.codice_strada + " non trovato nello stradario comunale");
                }

                var l = new NuovaLocalizzazione(i.CodiceStradario, i.NomeVia, c.numero, c.esponente);
                l.Latitudine = coordinate.y.ToString();
                l.Longitudine = coordinate.x.ToString();

                return l;
            }

            internal static NuovoRiferimentoCatastale FromMappaleLDP(ComplexTypeSubalterno sub)
            {
                return new NuovoRiferimentoCatastale("F", "Fabbricati", sub.foglio, sub.particella, sub.subalterno, sub.sezione);
            }

        }



        List<DatiNuovaLocalizzazione> _datiLocalizzativi;


        public IEnumerable<DatiNuovaLocalizzazione> DatiLocalizzativi { get { return _datiLocalizzativi; } }

        internal LocalizzazioneInterventoLDP(ComplexTypePraticaDatiTerritoriali dati, ILocalizzazioniService localizzazioniService)
        {
            var coordinate = dati.point;
            var localizzazioni = new List<NuovaLocalizzazione>();

            var indirizzi = dati.a_civici.Select(c => NuovaLocalizzazioneHelper.FromCivicoLDP(localizzazioniService, c, coordinate)).ToArray();
            var mappali = dati.a_subalterni.Select(s => NuovaLocalizzazioneHelper.FromMappaleLDP(s)).ToArray();
            var nonDefinito = new NuovaLocalizzazione(localizzazioniService.GetById(0), String.Empty, String.Empty);

            this._datiLocalizzativi = new List<DatiNuovaLocalizzazione>();

            if (dati.a_civici.Length > 0)
            {
                for (int i = 0; i < indirizzi.Length; i++)
                {
                    var ind = indirizzi[i];
                    var map = mappali.Length > i ? mappali[i] : null;

                    this._datiLocalizzativi.Add(new DatiNuovaLocalizzazione(ind, map));
                }

                for (int i = indirizzi.Length; i < mappali.Length; i++)
                {
                    var ind = this._datiLocalizzativi.Last().Localizzazione;
                    var map = mappali[i];

                    this._datiLocalizzativi.Add(new DatiNuovaLocalizzazione(ind, map));
                }
            }
            else
            {
                for (int i = 0; i < mappali.Length; i++)
                {
                    var ind = nonDefinito;
                    var map = mappali[i];

                    this._datiLocalizzativi.Add(new DatiNuovaLocalizzazione(ind, map));
                }
            }


        }
    }
}
