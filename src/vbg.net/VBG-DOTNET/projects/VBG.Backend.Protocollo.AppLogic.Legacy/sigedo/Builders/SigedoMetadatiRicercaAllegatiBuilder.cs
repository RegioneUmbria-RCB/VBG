using System;
using Init.SIGePro.Protocollo.ProtocolloSigedoQueryService;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Builders
{
    public class SigedoMetadatiRicercaBuilder
    {
        public readonly MetadatoRicerca[] MetadatiRicerca;

        int _operatore;
        string _valore = String.Empty;
        string _metadato = String.Empty;

        public SigedoMetadatiRicercaBuilder(string metadato, int operatore, string valore)
        {
            _metadato = metadato;
            _operatore = operatore;
            _valore = valore;

            MetadatiRicerca = CreaMetadato();

        }

        private MetadatoRicerca[] CreaMetadato()
        {
            return new MetadatoRicerca[]
            {
                new MetadatoRicerca
                {
                    metadato = _metadato,
                    operatore = _operatore,
                    valore = _valore
                }
            };
        }
    }
}
