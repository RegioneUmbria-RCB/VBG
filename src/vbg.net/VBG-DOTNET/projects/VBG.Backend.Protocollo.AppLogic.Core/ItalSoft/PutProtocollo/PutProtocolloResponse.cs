using ProtocolloItalSoftService;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.PutProtocollo
{
    public class PutProtocolloResponse
    {
        public Esito Esito { get; set; }
        public string Id { get; set; }
        public string Anno { get; set; }
        public string Numero { get; set; }
        public string Data { get; set; }
        public string Tipo { get; set; }

        internal static PutProtocolloResponse FromretProtocollo(retProtocollo response, messageResult messageResult)
        {
            if (response == null)
            {
                return new PutProtocolloResponse
                {
                    Esito = Esito.FromProtocolloMessageResult(messageResult)
                };
            }

            return new PutProtocolloResponse
            {
                Esito = Esito.FromProtocolloMessageResult(messageResult),
                Id = response.rowidProtocollo,
                Anno = response.annoProtocollo,
                Numero = response.numeroProtocollo,
                Data = response.dataProtocollo,
                Tipo = response.tipoProtocollo
            };
        }
    }
}
