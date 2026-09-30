using ProtocolloItalSoftService;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.InsertDocumento
{
    public class InsertDocumentoResponse
    {
        public string Id { get; set; }
        public string ImprontaHash { get; set; }
        public string NomeFile { get; set; }
        public string Tipo { get; set; }
        public string Estensione { get; set; }

        internal static InsertDocumentoResponse FromallegatoPrecaricato(allegatoPrecaricato response)
        {
            if (response == null)
            {
                return null;
            }

            return new InsertDocumentoResponse
            {
                Id = response.idunivoco,
                ImprontaHash = response.hashfile,
                NomeFile = response.nomeFile,
                Tipo = response.tipoFile,
                Estensione = response.estensione
            };
        }
    }
}
