//using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
//using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
//using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
//using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda.Repositories;
//using log4net;

//namespace Init.Sigepro.FrontEnd.AppLogic.Services.Domanda
//{
//    public class DatiDomandaSalvataggioInterventoService : DatiDomandaService
//    {
//        private readonly WsDatiDomandaServiceCreator _serviceCreator;
//        private readonly ILog _logger = LogManager.GetLogger(typeof(DatiDomandaSalvataggioInterventoService));

//        public DatiDomandaSalvataggioInterventoService(ISalvataggioDomandaStrategy salvataggioStrategy, IResolveDescrizioneIntervento resolveDescrizioneIntervento,
//                                    IWorkflowService workflowService2, WsDatiDomandaServiceCreator serviceCreator) :
//            base(salvataggioStrategy, resolveDescrizioneIntervento, workflowService2)
//        {
//            this._serviceCreator = serviceCreator;
//        }

//        public override void ImpostaIdIntervento(int idDomanda, int idIntervento, int? idAttivitaAtecoSelezionata = null, bool popolaDescrizioneLavoriDaIntervento = true)
//        {
//            base.ImpostaIdIntervento(idDomanda, idIntervento, idAttivitaAtecoSelezionata, popolaDescrizioneLavoriDaIntervento);

//            //using (var ws = this._serviceCreator.CreateClient())
//            //{
//            //    try
//            //    {
//            //        ws.Service.SalvaCodiceInterventoPerStatistica(ws.Token, idDomanda, idIntervento);
//            //    }
//            //    catch (Exception ex)
//            //    {
//            //        ws.Service.Abort();
//            //        this._logger.Error("Errore durante la chiamata al WS AreaRiservata.SalvaCodiceInterventoPerStatistica: " + ex.ToString());
//            //    }
//            //}
//        }
//    }
//}
