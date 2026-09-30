using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi;
using Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.DataAccess;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.ConfigurazioneIntervento
{
    public static class LdpDecodificheExtensions
    {
        public static ConfigurazioneAlberoprocLDP.ConfigurazioneAlberoprocLDPItem ToAlberoprocLDPItem(this LdpDecodifiche ld)
        {
            if (ld == null)
            {
                return null;
            }

            return new ConfigurazioneAlberoprocLDP.ConfigurazioneAlberoprocLDPItem
            {
                Codice = ld.Codice,
                Contesto = ld.Contesto,
                Descrizione = ld.Descrizione,
                Id = ld.Id.GetValueOrDefault(-1),
                IdComune = ld.IdComune
            };
        }
    }


    public class ConfigurazioneAlberoprocLDPService
    {
        private readonly AlberoProcMgr _mgr;
        private readonly string _idComune;
        private readonly LdpDecodificheMgr _ldpDecodificheRepository;

        public ConfigurazioneAlberoprocLDPService(LdpDecodificheMgr decodificheMgr, AlberoProcMgr mgr, string idComune)
        {
            this._mgr = mgr;
            this._idComune = idComune;
            this._ldpDecodificheRepository = decodificheMgr;
        }

        public ConfigurazioneAlberoprocLDP GetConfigurazioneDaCodiceIstanza(int codiceIstanza)
        {
            var idIntervento = this._mgr
                .GetCodiceInterventoProcDaCodiceIstanza(this._idComune, codiceIstanza);

            return this.GetConfigurazione(idIntervento);
        }

        public ConfigurazioneAlberoprocLDP GetConfigurazione(int idIntervento)
        {
            var interventi = this._mgr
                     .GetAlberaturaIntervento(this._idComune, idIntervento)
                     .Select(x => new InterventoReadOnlyLDP(x));

            var enumerator = new InterventiReverseEnumerator<InterventoReadOnlyLDP>(interventi);

            while (enumerator.MoveNext())
            {
                var item = enumerator.Current;

                // I parametri di integrazione con LDP son presenti o assenti del tutto
                if (item.LdpTipoOccupazione.HasValue)
                {
                    var tipoOccupazione = this._ldpDecodificheRepository.GetById(item.LdpTipoOccupazione.Value);
                    var tipoPeriodo = this._ldpDecodificheRepository.GetById(item.LdpTipoPeriodo.Value);
                    var tipoGeometria = this._ldpDecodificheRepository.GetById(item.LdpTipoGeometria.Value);

                    return new ConfigurazioneAlberoprocLDP
                    {
                        TipologiaGeometria = tipoGeometria.ToAlberoprocLDPItem(),
                        TipologiaOccupazione = tipoOccupazione.ToAlberoprocLDPItem(),
                        TipologiaPeriodo = tipoPeriodo.ToAlberoprocLDPItem(),
                        LdpDolQString = item.LdpDolQueryString
                    };
                }
            }

            return null;
        }
    }
}
