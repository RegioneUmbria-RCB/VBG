using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sigedo.Proxies;
using Init.SIGePro.Protocollo.Sigedo.Proxies.QueryService;

namespace Init.SIGePro.Protocollo.Sigedo.Builders
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

        /*
        public static class Constants
        {
            public const string METADATO_RIFERIMENTO = "IDRIF";
        }

        int _operatore;
        string _idRiferimento;
        public readonly MetadatoRicerca[] MetadatiRicerca;

        public SigedoMetadatiRicercaBuilder(int operatore, string idRiferimento)
        {
            _operatore = operatore;
            _idRiferimento = idRiferimento;

            MetadatiRicerca = CreaMetadatiRicerca();
        }

        private MetadatoRicerca[] CreaMetadatiRicerca()
        {
            return new MetadatoRicerca[]{
                CreaMetadato(Constants.METADATO_RIFERIMENTO, _idRiferimento),
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
        }*/
    }
}
