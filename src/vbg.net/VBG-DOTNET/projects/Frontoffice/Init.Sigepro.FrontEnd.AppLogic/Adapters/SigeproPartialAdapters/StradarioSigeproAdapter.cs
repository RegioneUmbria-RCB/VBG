using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.SigeproPartialAdapters
{
    public class StradarioSigeproAdapter : IIstanzaSigeproPartialAdapter
    {
        private readonly IStradarioRepository _stradarioRepository;

        public StradarioSigeproAdapter(IStradarioRepository stradarioRepository)
        {
            this._stradarioRepository = stradarioRepository;
        }

        public void Adatta(IDomandaOnlineReadInterface src, Istanze istanza, IstanzaSigeproAdapterFlags flags)
        {
            var aliasComune = src.AltriDati.AliasComune;

            var stradarioList = new List<IstanzeStradario>();

            foreach (var indirizzo in src.Localizzazioni.Indirizzi)
            {
                var codStradario = indirizzo.CodiceStradario;

                var rigaStradario = this._stradarioRepository.GetByCodiceStradario(codStradario);

                if (rigaStradario == null)
                {
                    continue;
                }

                var str = new IstanzeStradario
                {
                    CODICESTRADARIO = codStradario.ToString(),
                    CIVICO = indirizzo.Civico,
                    ESPONENTE = indirizzo.Esponente,
                    COLORE = indirizzo.Colore,
                    SCALA = indirizzo.Scala,
                    INTERNO = indirizzo.Interno,
                    ESPONENTEINTERNO = indirizzo.EsponenteInterno,
                    Piano = indirizzo.Piano,
                    NOTE = indirizzo.Note,
                    Uuid = indirizzo.Uuid,
                    Longitudine = indirizzo.Longitudine,
                    Latitudine = indirizzo.Latitudine,
                    Km = indirizzo.Km,

                    Stradario = new Stradario
                    {
                        PREFISSO = rigaStradario.Prefisso,
                        CODICESTRADARIO = codStradario.ToString(),
                        DESCRIZIONE = indirizzo.Indirizzo,//rigaStradario.DESCRIZIONE,
                        LOCFRAZ = rigaStradario.LocFraz,
                        CAP = rigaStradario.Cap,
                        CODVIARIO = rigaStradario.CodViario
                    }

                };

                if (rigaStradario.ComuneLocalizzazione != null)
                {
                    str.ComuneLocalizzazione = new Comuni
                    {
                        COMUNE = rigaStradario.ComuneLocalizzazione.Comune,
                        PROVINCIA = rigaStradario.ComuneLocalizzazione.Provincia,
                        CODICECOMUNE = rigaStradario.ComuneLocalizzazione.CodiceComune,
                        SIGLAPROVINCIA = rigaStradario.ComuneLocalizzazione.SiglaProvincia
                    };
                }


                stradarioList.Add(str);
            }

            istanza.Stradario = stradarioList.ToArray();
        }
    }
}
