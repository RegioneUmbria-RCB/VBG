using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi;
using Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.DataAccess;
using Init.SIGePro.Manager.Manager;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

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
        AlberoProcMgr _mgr;
        string _idComune;
        LdpDecodificheMgr _ldpDecodificheRepository;

        public ConfigurazioneAlberoprocLDPService(AlberoProcMgr mgr, string idComune)
        {
            this._mgr = mgr;
            this._idComune = idComune;
            this._ldpDecodificheRepository = new LdpDecodificheMgr(mgr.db, idComune);
        }

        public ConfigurazioneAlberoprocLDP GetConfigurazione(int idIntervento)
        {
            var interventi = this._mgr
                     .GetAlberaturaIntervento(this._idComune, idIntervento)
                     .Select(x => new InterventoReadOnlyLDP(x));

            var enumerator = new InterventiReverseEnumerator<InterventoReadOnlyLDP>(interventi);

            while(enumerator.MoveNext())
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
