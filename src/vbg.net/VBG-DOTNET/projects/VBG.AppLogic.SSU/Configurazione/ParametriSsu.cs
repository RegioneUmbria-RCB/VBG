using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;

namespace VBG.AppLogic.SSU.Configurazione
{
    public class ParametriSsu : IParametriConfigurazione
    {
        private readonly string _idNodoDestinatario;
        private readonly string _idEnteDestinatario;
        private readonly string _idSportelloDestinatario;
        private readonly string _baseUrlSsu;
        private readonly string _baseUrlValidator;

        public string BaseUrlSsu => this.Attiva ? this._baseUrlSsu : throw new InvalidOperationException("Verticalizzazione AREA_RISERVATA_SSU non attiva");
        public string BaseUrlValidator => this.Attiva ? this._baseUrlValidator : throw new InvalidOperationException("Verticalizzazione AREA_RISERVATA_SSU non attiva");
        public bool Attiva { get; init; }

        public ParametriSsu(bool attiva, string baseUrlSsu, string idNodoDestinatario, string idEnteDestinatario, string idSportelloDestinatario, string baseUrlValidator)
        {
            this.Attiva = attiva;
            this._baseUrlSsu = baseUrlSsu;
            this._idNodoDestinatario = idNodoDestinatario;
            this._idEnteDestinatario = idEnteDestinatario;
            this._idSportelloDestinatario = idSportelloDestinatario;
            this._baseUrlValidator = baseUrlValidator;
        }

        public SportelloStcDestinatario? GetSportelloDestinatario()
        {
            if (!this.Attiva)
            {
                throw new InvalidOperationException("Verticalizzazione AREA_RISERVATA_SSU non attiva");
            }

            if (!String.IsNullOrEmpty(this._idNodoDestinatario) &&
                !String.IsNullOrEmpty(this._idEnteDestinatario) &&
                !String.IsNullOrEmpty(this._idSportelloDestinatario))
            {
                return new SportelloStcDestinatario
                {
                    idNodo = this._idNodoDestinatario,
                    idEnte = this._idEnteDestinatario,
                    idSportello = this._idSportelloDestinatario
                };
            }

            return null;
        }
    }
}
