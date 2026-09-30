using System;

namespace WSAtti.Sicraweb.NumeraDeterminaString
{
    internal class NumeraDeterminaStringWSRequest
    {
        public string IdDocumento { get; internal set; }

        internal static NumeraDeterminaStringWSRequest FromWSAttiNumeraDeterminaRequest(WSAttiNumeraDeterminaRequest request)
        {
            if (request == null)
            {
                throw new Exception("Impossibile utilizzare il metodo FromWSAttiNumeraDeterminaRequest senza passare una request valorizzata");
            }

            return new NumeraDeterminaStringWSRequest
            {
                IdDocumento = request.IdDocumento.ToString()
            };
        }
    }
}
