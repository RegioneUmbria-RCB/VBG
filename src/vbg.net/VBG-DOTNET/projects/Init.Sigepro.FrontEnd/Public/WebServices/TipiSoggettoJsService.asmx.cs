using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Ninject;
using System;
using System.Web.Script.Services;
using System.Web.Services;

namespace Init.Sigepro.FrontEnd.Public.WebServices
{
    /// <summary>
    /// Summary description for TipiSoggettoService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    [ScriptService]
    public class TipiSoggettoJsService : Ninject.Web.WebServiceBase
    {
        [Inject]
        public ITipiSoggettoService _tipiSoggettoRepository { get; set; }

        [WebMethod(EnableSession = true)]
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        public bool RichiedeDescrizioneEstesa(string idSoggetto)
        {
            if (String.IsNullOrEmpty(idSoggetto))
                return false;

            var sogg = this._tipiSoggettoRepository.GetById(Convert.ToInt32(idSoggetto));

            return (sogg != null && sogg.RichiedeSpecificaDescrizione);
        }

        [WebMethod(EnableSession = true)]
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        public TipoSoggetto GetById(string idSoggetto)
        {
            if (String.IsNullOrEmpty(idSoggetto))
                return new TipoSoggetto();

            return this._tipiSoggettoRepository.GetById(Convert.ToInt32(idSoggetto));
        }
    }
}
