
//using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
//using Init.Sigepro.FrontEnd.AppLogic.WsInterventiAteco;
//using Init.SIGePro.Manager.DTO.Common;
//using Init.SIGePro.Manager.DTO.Interventi;

//namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
//{
//    internal class WsAtecoRepository : Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces.IAtecoRepository
//    {
//        private readonly AtecoServiceCreator _serviceCreator;

//        public WsAtecoRepository(AtecoServiceCreator serviceCreator)
//        {
//            if (serviceCreator == null)
//                throw new System.ArgumentNullException(nameof(serviceCreator));
//            //Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

//            _serviceCreator = serviceCreator;
//        }


//        public Ateco[] GetNodiFiglio(string aliasComune, int? idPadre)
//        {
//            using (var ws = _serviceCreator.CreateClient())
//            {
//                return ws.Service.GetNodiFiglioAteco(ws.Token, idPadre);
//            }
//        }

//        public Ateco GetDettagli(string aliasComune, int id)
//        {
//            using (var ws = _serviceCreator.CreateClient())
//            {
//                return ws.Service.GetDettagliAteco(ws.Token, id);
//            }
//        }

//        public Ateco[] RicercaAteco(string aliasComune, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca)
//        {
//            using (var ws = _serviceCreator.CreateClient())
//            {
//                return ws.Service.RicercaAteco(ws.Token, matchParziale, matchCount, modoRicerca, tipoRicerca);
//            }
//        }

//        public NodoAlberoInterventiDto GetAlberoProc(string aliasComune, int id, AmbitoRicerca ambitoRicerca)
//        {
//            using (var ws = _serviceCreator.CreateClient())
//            {
//                return ws.Service.GetAlberoProcDaIdAteco(ws.Token, id, ambitoRicerca);
//            }
//        }

//        public int[] CaricaGerarchia(string aliasComune, int id)
//        {
//            using (var ws = _serviceCreator.CreateClient())
//            {
//                return ws.Service.CaricaListaIdGerarchiaAteco(ws.Token, id);
//            }
//        }


//        public bool EsistonoInterventiCollegati(string aliasComune, string software, int idAteco, IAmbitoRicercaIntervento ambitoRicerca)
//        {
//            using (var ws = _serviceCreator.CreateClient())
//            {
//                return ws.Service.VerificaEsistenzaNodiCollegatiAIdAteco(ws.Token, software, idAteco, ambitoRicerca.GetAmbito());
//            }
//        }
//    }
//}
