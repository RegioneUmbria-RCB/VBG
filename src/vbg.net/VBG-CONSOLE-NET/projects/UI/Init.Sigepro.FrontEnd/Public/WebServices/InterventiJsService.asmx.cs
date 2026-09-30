using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Ninject;
using System.Collections.Generic;
using System.Linq;
using System.Web.Script.Services;
using System.Web.Services;

namespace Init.Sigepro.FrontEnd.Public.WebServices
{
    /// <summary>
    /// Summary description for InterventiJsService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    [ScriptService]
    public class InterventiJsService : System.Web.Services.WebService
    {
        [Inject]
        public IInterventiRepository _alberoProcRepository { get; set; }

        public InterventiJsService()
        {
            FoKernelContainer.Inject(this);
        }

        [WebMethod(EnableSession = true)]
        [ScriptMethod()]
        public InterventoDto[] GetNodiFiglio(string aliasComune, string software, int idPadre, int idAteco, bool areaRiservata, bool utenteTester, string codiceComune)
        {
            var ambitoRicerca = areaRiservata ? (IAmbitoRicercaIntervento)new AmbitoRicercaAreaRiservata(utenteTester) :
                                                                     (IAmbitoRicercaIntervento)new AmbitoRicercaFrontofficePubblico();

            if (idAteco != -1)
                return this._alberoProcRepository.GetSottonodiDaIdAteco(aliasComune, software, idPadre, idAteco, ambitoRicerca, codiceComune);

            var tmp = this._alberoProcRepository.GetSottonodi(aliasComune, software, idPadre, ambitoRicerca, codiceComune);

            return tmp;
        }

        [WebMethod(EnableSession = true)]
        [ScriptMethod()]
        public BaseDtoOfInt32String[] RicercaTestuale(string aliasComune, string software, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca, bool areaRiservata, bool utenteTester)
        {
            var ambitoRicerca = areaRiservata ? (IAmbitoRicercaIntervento)new AmbitoRicercaAreaRiservata(utenteTester) :
                                                                     (IAmbitoRicercaIntervento)new AmbitoRicercaFrontofficePubblico();


            if (matchParziale.Length < 2)
                return new InterventoDto[0];

            return this._alberoProcRepository.RicercaTestuale(aliasComune, software, matchParziale, matchCount, modoRicerca, tipoRicerca, ambitoRicerca);
        }

        [WebMethod(EnableSession = true)]
        [ScriptMethod]
        public List<int> CaricaGerarchia(string aliasComune, int id)
        {
            return this._alberoProcRepository.GetIdNodiPadre(aliasComune, id);
        }

        [WebMethod(EnableSession = true)]
        [ScriptMethod]
        public List<InterventoDto> GetNodiPadre(string aliasComune, int id, bool utenteTester)
        {
            var ambitoRicerca = (IAmbitoRicercaIntervento)new AmbitoRicercaAreaRiservata(utenteTester);
            var nodiPadre = this._alberoProcRepository.GetIdNodiPadre(aliasComune, id);

            nodiPadre.Reverse();

            var list = nodiPadre.Select(x => this._alberoProcRepository.GetDettagliIntervento(aliasComune, x, ambitoRicerca)).ToList();

            return list;
        }
    }
}
