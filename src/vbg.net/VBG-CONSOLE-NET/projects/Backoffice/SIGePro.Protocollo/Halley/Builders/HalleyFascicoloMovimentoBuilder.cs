using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Halley.Interfaces;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.Halley.Services;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Halley.Adapters;
using Init.SIGePro.Protocollo.ProtocolloHalleyDizionarioServiceProxy;

namespace Init.SIGePro.Protocollo.Halley.Builders
{
    public class HalleyFascicoloMovimentoBuilder : IFascicoloHalleyBuilder
    {
        string _numeroProtocollo;
        string _annoProtocollo;
        ProtocolloLogs _log;
        HalleyVerticalizzazioneParametriAdapter _vert;
        string _tokenDizionario;
        string _proxy;

        public HalleyFascicoloMovimentoBuilder(string numeroProtocollo, string annoProtocollo, ProtocolloLogs log, HalleyVerticalizzazioneParametriAdapter vert, string tokenDizionario, string proxy)
        {
            _numeroProtocollo = numeroProtocollo;
            _annoProtocollo = annoProtocollo;
            _log = log;
            _vert = vert;
            _tokenDizionario = tokenDizionario;
            _proxy = proxy;
        }

        #region IFascicoloHalleyBuilder Members

        public FascicoliFascicolo GetDatiFascicolo()
        {
            FascicoliFascicolo retVal = null;
            try
            {
                var srv = new HalleyDizionarioService(_log, _vert.UrlWsDizionario, _proxy);
                retVal = srv.GetFascicolo(_vert.Username, _tokenDizionario, _vert.CodiceAoo, _numeroProtocollo, _annoProtocollo);

                /*retVal = new Fascicolo
                {
                    anno = response.anno,
                    numero = response.id,
                    Text = new string[] { response.Nome }
                };*/



                _log.InfoFormat("DATI FASCICOLO RESTITUITO, numero fascicolo: {0}, anno fascicolo: {1}, classifica: {2}, nome fascicolo", retVal.id, retVal.anno, retVal.CodiceTitolario, retVal.Nome);

            }
            catch (Exception ex)
            {
                _log.Warn(ex.Message);
                retVal = null;
            }
            finally
            {
                
            }

            return retVal;

        }

        #endregion
    }
}
