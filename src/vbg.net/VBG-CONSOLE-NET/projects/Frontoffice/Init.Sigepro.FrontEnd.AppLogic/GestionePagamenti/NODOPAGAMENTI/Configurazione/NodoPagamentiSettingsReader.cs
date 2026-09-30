using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Configurazione
{
    public class NodoPagamentiSettingsReader : INodoPagamentiSettingsReader
    {
        private readonly IIdPresentazioneResolver _idPresentazioneResolver;
        private readonly IConfigurazioneNodoPagamentiRepository _configurazioneRepository;
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;

        public NodoPagamentiSettingsReader(IIdPresentazioneResolver idPresentazioneResolver, IConfigurazioneNodoPagamentiRepository configurazioneRepository, ISalvataggioDomandaStrategy salvataggioDomandaStrategy)
        {
            _idPresentazioneResolver = idPresentazioneResolver;
            _configurazioneRepository = configurazioneRepository;
            _salvataggioDomandaStrategy = salvataggioDomandaStrategy;
        }

        public NodoPagamentiSettings Read()
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(this._idPresentazioneResolver.IdPresentazione);

            return this._configurazioneRepository.GetConfigurazione(domanda.DataKey.Software, domanda.ReadInterface.AltriDati.CodiceComune);
        }
    }
}
