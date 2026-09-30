using ProtocolloItalSoftService;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft
{
    public class AllegatoProtocollo
    {
        public string Id { get; set; }
        public string Tipo { get; set; }
        public string Nome { get; set; }
        public string Estensione { get; set; }
        public bool PreCaricato { get; set; }
        public string ImprontaHash { get; set; }
        public bool MettiAllaFirma { get; set; }
        public byte[] Content { get; set; }

        internal static AllegatoProtocollo Fromallegato(allegato response)
        {
            if (response == null)
            {
                return null;
            }

            return new AllegatoProtocollo
            {
                Id = response.id,
                Nome = response.nomeFile,
                Estensione = response.estensione,
                Content = Convert.FromBase64String(response.stream)

            };
        }
    }
}