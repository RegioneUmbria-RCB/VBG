using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Frontoffice;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
using Init.Sigepro.FrontEnd.AppLogic.WsEndoFrontoffice;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.SIGePro.Manager.DTO;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Script.Services;
using System.Web.Services;

namespace Init.Sigepro.FrontEnd.Public.WebServices
{
    /// <summary>
    /// Summary description for AlberoEndoJsService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    [ScriptService]
    public class AlberoEndoJsService : System.Web.Services.WebService
    {
        private static class Constants
        {
            public const string PrefissoCodiceFamiglia = "F";
            public const string PrefissoCodiceCategoria = "C";

            public const string PrefissoDescrizioneFamiglia = "FAM";
            public const string PrefissoDescrizioneCategoria = "CAT";
            public const string PrefissoDescrizioneEndo = "PROC";
        }

        [Inject]
        public EndoprocedimentiFrontofficeService _endoService { get; set; }

        public AlberoEndoJsService()
        {
            FoKernelContainer.Inject(this);
        }

        [WebMethod(EnableSession = true)]
        [ScriptMethod()]
        public object[] GetNodiFiglio(string aliasComune, string software, string idPadre, int idAteco, bool areaRiservata, bool utenteTester)
        {
            IAmbitoRicercaIntervento ambitoRicerca = areaRiservata ? (IAmbitoRicercaIntervento)new AmbitoRicercaAreaRiservata(utenteTester) :
                                                                     (IAmbitoRicercaIntervento)new AmbitoRicercaFrontofficePubblico();

            if (idPadre == "-1")
            {
                return this.GetListaFamiglie(aliasComune, software);
            }

            if (idPadre[0].ToString() == Constants.PrefissoCodiceFamiglia)
            {
                return this.GetListaCategorie(aliasComune, software, idPadre.Substring(1));
            }

            if (idPadre[0].ToString() == Constants.PrefissoCodiceCategoria)
            {
                return this.GetListaEndo(aliasComune, software, idPadre.Substring(1));
            }

            throw new Exception("Tipo di ricerca endo non supportato " + idPadre);
        }

        public object[] GetListaFamiglie(string aliasComune, string software)
        {
            return this._endoService
                        .GetListaFamiglieFrontoffice(aliasComune, software)
                        .Select(x => new
                        {
                            Codice = Constants.PrefissoCodiceFamiglia + x.Codice.ToString(),
                            Descrizione = x.Descrizione,
                            HaNodiFiglio = true,
                            HaNote = false
                        })
                        .ToArray();
        }

        public object[] GetListaCategorie(string aliasComune, string software, string codiceFamiglia)
        {
            return this._endoService
                        .GetListaCategorieFrontoffice(aliasComune, software, Convert.ToInt32(codiceFamiglia))
                        .Select(x => new
                        {
                            Codice = Constants.PrefissoCodiceCategoria + x.Codice.ToString(),
                            Descrizione = x.Descrizione,
                            HaNodiFiglio = true,
                            HaNote = false
                        })
                        .ToArray();
        }

        public object[] GetListaEndo(string aliasComune, string software, string codiceCategoria)
        {
            return this._endoService
                        .GetListaEndoFrontoffice(aliasComune, software, Convert.ToInt32(codiceCategoria))
                        .Select(x => new
                        {
                            Codice = x.Codice.ToString(),
                            Descrizione = x.Descrizione,
                            HaNodiFiglio = false,
                            HaNote = false
                        })
                        .ToArray();
        }


        [WebMethod(EnableSession = true)]
        [ScriptMethod()]
        public BaseDto<string, string>[] RicercaTestuale(string aliasComune, string software, string matchParziale, int matchCount, string tipoRicerca, bool areaRiservata, bool utenteTester)
        {
            if (matchParziale.Length < 2)
                return new BaseDto<string, string>[0];

            var tipoRicercaEnum = (TipoRicercaEnum)Enum.Parse(typeof(TipoRicercaEnum), tipoRicerca);

            var result = this._endoService.RicercaTestualeFrontoffice(aliasComune, software, matchParziale, tipoRicercaEnum);

            var famiglie = result.Famiglie
                                    .Select(x => new
                                    {
                                        Codice = $"{Constants.PrefissoCodiceFamiglia}{x.Codice}",
                                        Descrizione = $"{Constants.PrefissoDescrizioneFamiglia} - {x.Descrizione}"
                                    });

            var categorie = result.Categorie
                                    .Select(x => new
                                    {
                                        Codice = $"{Constants.PrefissoCodiceCategoria}{x.Codice}",
                                        Descrizione = $"{Constants.PrefissoDescrizioneCategoria} - {x.Descrizione}"
                                    });


            var endo = result.Endoprocedimenti
                                    .Select(x => new
                                    {
                                        Codice = x.Codice.ToString(),
                                        Descrizione = $"{Constants.PrefissoDescrizioneEndo} - {x.Descrizione}"
                                    });

            return famiglie.Concat(categorie)
                           .Concat(endo)
                           .Select(x => new BaseDto<string, string>
                           {
                               Codice = x.Codice,
                               Descrizione = x.Descrizione
                           }).ToArray();
        }



        [WebMethod(EnableSession = true)]
        [ScriptMethod]
        public List<string> CaricaGerarchia(string aliasComune, string id)
        {
            var valore = -1;
            var livello = LivelloCaricamentoGerarchia.Endo;

            switch (id[0].ToString())
            {
                case Constants.PrefissoCodiceCategoria:
                    {
                        valore = Convert.ToInt32(id.Substring(1));
                        livello = LivelloCaricamentoGerarchia.Categoria;
                        break;
                    }
                case Constants.PrefissoCodiceFamiglia:
                    {
                        valore = Convert.ToInt32(id.Substring(1));
                        livello = LivelloCaricamentoGerarchia.Famiglia;
                        break;
                    }

                default:
                    valore = Convert.ToInt32(id);
                    break;
            }

            var result = this._endoService.CaricaGerarchiaFrontoffice(aliasComune, valore, livello);

            if (result == null)
                return null;

            switch (livello)
            {
                case LivelloCaricamentoGerarchia.Famiglia:
                    return new List<string>
                    {
                        Constants.PrefissoCodiceFamiglia + result.Famiglia.ToString()
                    };

                case LivelloCaricamentoGerarchia.Categoria:
                    return new List<string>
                    {
                        Constants.PrefissoCodiceCategoria + result.Categoria.ToString(),
                        Constants.PrefissoCodiceFamiglia + result.Famiglia.ToString()
                    };

            }

            return new List<string>{
                        result.Endo.ToString(),
                        Constants.PrefissoCodiceCategoria + result.Categoria.ToString(),
                        Constants.PrefissoCodiceFamiglia + result.Famiglia.ToString()
                    };
        }
    }
}
