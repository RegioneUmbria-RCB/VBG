using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;


namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.LeggiProtocollo
{
    public class LeggiProtocolloFactory
    {
        public static ILeggiProtocollo Create(string idProtocollo, string numero, string anno, string codiceUfficio, string codiceRegistro, ProtocolloService wrapper, ProtocolloLogs logs)
        {
            if (String.IsNullOrEmpty(idProtocollo))
            {
                return new LeggiProtocolloByEstremi(numero, anno, codiceRegistro, codiceUfficio, wrapper, logs);
            }
            else
            {
                return new LeggiProtocolloById(idProtocollo, wrapper);
            }
        }
    }
}
