using Init.SIGePro.Manager.Authentication;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;

namespace VBG.Backend.Protocollo.AppLogic.Shared.MittentiDestinatari
{
    public class QualificaDittaIndividualeService : IQualificaDittaIndividualeService
    {
        private readonly bool _dittaIndividuale;
        public QualificaDittaIndividualeService(AuthenticationInfo authInfo, VerticalizzazioneProtocolloAttivo protocolloAttivo, string fkCodiceTipoSoggettoIstanza)
        {
            var tipoMittDest = (TipoMittenteEnum)protocolloAttivo.TipoMittDestAuto;

            if (tipoMittDest != TipoMittenteEnum.DITTA_INDIVIDUALE)
            {
                this._dittaIndividuale = false;
                return;
            }

            if (String.IsNullOrEmpty(protocolloAttivo.MappaturaDittaIndiduale))
            {
                throw new Exception("Impossibile determinare se si tratta di ditta inviduale senza impostare il parametro MAPPATURA_DITTA_INDIVIDUALE della verticalizzazione PROTOCOLLO_ATTIVO");
            }

            if (String.IsNullOrEmpty(fkCodiceTipoSoggettoIstanza))
            {
                throw new Exception("Impossibile determinare se si tratta di ditta individuale in quanto manca la qualifica del richiedente nell'istanza");
            }

            using (var db = authInfo.CreateDatabase())
            {
                string sql = $@"select 
                                    count(*) 
                                from 
                                    tipisoggettopeople 
                                where 
                                    idcomune = {db.Specifics.QueryParameterName("idComune")} and 
                                    codicetiposoggetto = {db.Specifics.QueryParameterName("idTipoSoggetto")} and 
                                    tiporapprpeople = {db.Specifics.QueryParameterName("tipoRapprPeople")}";
                var count = db.ExecuteScalar<int>(sql, 0,
                    mp =>
                    {
                        mp.AddParameter("idComune", authInfo.IdComune);
                        mp.AddParameter("idTipoSoggetto", Convert.ToInt32(fkCodiceTipoSoggettoIstanza));
                        mp.AddParameter("tipoRapprPeople", protocolloAttivo.MappaturaDittaIndiduale);
                    });

                this._dittaIndividuale = count > 0;
            }

        }

        public bool DittaIndividuale()
        {
            return this._dittaIndividuale;
        }
    }
}
