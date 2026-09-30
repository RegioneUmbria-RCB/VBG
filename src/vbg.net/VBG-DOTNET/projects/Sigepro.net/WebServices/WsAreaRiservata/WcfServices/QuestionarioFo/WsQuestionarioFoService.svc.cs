using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.GestioneQuestionario;
using PersonalLib2.Data;
using System;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.QuestionarioFo
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsQuestionarioFoService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsQuestionarioFoService.svc or WsQuestionarioFoService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsQuestionarioFoService : WcfServiceBase, IWsQuestionarioFoService
    {
        public bool QuestionarioCompilato(string token, string uuidIstanza)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var codiceIstanza = this.CodiceIstanzaDaUUID(db, authInfo.IdComune, uuidIstanza);
                return new QuestionarioGradimentoService(db, authInfo.IdComune).QuestionarioCompilato(codiceIstanza);
            }
        }

        public void SalvaQuestionario(string token, string uuidIstanza, int valutazione, string note)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var codiceIstanza = this.CodiceIstanzaDaUUID(db, authInfo.IdComune, uuidIstanza);
                new QuestionarioGradimentoService(db, authInfo.IdComune).SalvaQuestionario(codiceIstanza, valutazione, note);
            }
        }

        private int CodiceIstanzaDaUUID(DataBase db, string idComune, string uuid)
        {
            var riferimenti = new IstanzeMgr(db).GetCodiceIstanzaDaUuid(idComune, uuid);

            if (riferimenti == null || riferimenti.IdComune != idComune)
            {
                throw new Exception($"Uuid istanza {uuid} non valido");
            }

            return riferimenti.CodiceIstanza;
        }
    }
}
