using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Manager;

namespace Init.SIGePro.Protocollo.InsielMercato2.LeggiProtocollo.Identificativo
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
