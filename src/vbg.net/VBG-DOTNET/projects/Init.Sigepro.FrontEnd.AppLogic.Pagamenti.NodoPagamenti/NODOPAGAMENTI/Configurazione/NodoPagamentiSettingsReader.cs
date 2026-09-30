using VBG.Pagamenti.NodoPagamenti;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.Configurazione
{
    public class NodoPagamentiSettingsReader : INodoPagamentiSettingsReader
    {
        private readonly IConfigurazioneNodoPagamentiRepository _configurazioneRepository;

        public NodoPagamentiSettingsReader(IConfigurazioneNodoPagamentiRepository configurazioneRepository)
        {
            this._configurazioneRepository = configurazioneRepository;
        }

        public NodoPagamentiSettings Read(string codiceComune)
        {
            // var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            return this._configurazioneRepository.GetConfigurazione(codiceComune);
        }
    }
}
