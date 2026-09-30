using Init.SIGePro.Protocollo.ProtocolloSigedoQueryService;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Builders
{
    public class SigedoMetadatiRicercaProtocolloBuilder
    {

        public static class Constants
        {
            public const string METADATO_ANNO = "ANNO";
            public const string METADATO_NUMERO = "NUMERO";
        }

        int _operatore;
        string _numero;
        string _anno;

        public readonly MetadatoRicerca[] MetadatiRicerca;

        public SigedoMetadatiRicercaProtocolloBuilder(int operatore, string numero, string anno)
        {
            _operatore = operatore;
            _numero = numero;
            _anno = anno;

            MetadatiRicerca = CreaMetadatiRicerca();
        }

        private MetadatoRicerca[] CreaMetadatiRicerca()
        {
            return new MetadatoRicerca[]{
                CreaMetadato(Constants.METADATO_ANNO, _anno),
                CreaMetadato(Constants.METADATO_NUMERO, _numero)
            };
        }

        private MetadatoRicerca CreaMetadato(string metadato, string valore)
        {
            return new MetadatoRicerca
            {
                metadato = metadato,
                operatore = this._operatore,
                valore = valore
            };
        }
    }
}
