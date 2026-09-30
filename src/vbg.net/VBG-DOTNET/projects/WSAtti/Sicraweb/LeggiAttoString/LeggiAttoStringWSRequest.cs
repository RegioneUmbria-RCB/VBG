using System;

namespace WSAtti.Sicraweb.LeggiAttoString
{
    internal class LeggiAttoStringWSRequest
    {
        public string IdDocumento { get; internal set; }
        public string Tipo { get; internal set; }
        public string Organo { get; internal set; }
        public string Anno { get; internal set; }
        public string Numero { get; internal set; }

        internal static LeggiAttoStringWSRequest FromWSAttiLeggiAttoRequest(WSAttiLeggiDeterminaRequest request)
        {
            if (request == null)
            {
                throw new Exception("Impossibile utilizzare il metodo FromWSAttiLeggiAttoRequest senza passare una request valorizzata");
            }

            return new LeggiAttoStringWSRequest
            {
                Anno = request.Anno,
                IdDocumento = request.IdDocumento,
                Numero = request.Numero
            };
        }
    }
}
