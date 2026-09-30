using VBG.Backend.Protocollo.AppLogic.Shared.Managers;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.LeggiProtocollo.Identificativo
{
    public class IdentificativoFactory
    {
        public static IRecordIdentifier Create(string idProtocollo, int numeroProtocollo, int annoProtocollo, ResolveDatiProtocollazioneService datiProto)
        {
            if (String.IsNullOrEmpty(idProtocollo))
            {
                var mgr = new ProtocolloUfficiRegistriMgr(datiProto.Db);
                var listReg = mgr.GetBySoftwareCodiceComune(datiProto.IdComune, datiProto.Software, datiProto.CodiceComune);

                string registro = "";
                string ufficio = "";

                if (listReg.Count() == 1)
                {
                    registro = listReg.ToList()[0].Codiceregistro;
                    ufficio = listReg.ToList()[0].Codiceufficio;
                }

                return new IdentificativoNumeroData(numeroProtocollo, annoProtocollo, registro, ufficio);
            }
            else
                return new IdentificativoId(idProtocollo);
        }
    }
}
