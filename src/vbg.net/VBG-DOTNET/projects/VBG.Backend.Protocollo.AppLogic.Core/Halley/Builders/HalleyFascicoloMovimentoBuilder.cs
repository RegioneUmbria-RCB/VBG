using VBG.Backend.Protocollo.AppLogic.Core.Halley.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.Adapters;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using ProtocolloHalleyDizionarioServiceProxy;
using VBG.Shared.Infrastructure.ServiceModel;


namespace VBG.Backend.Protocollo.AppLogic.Core.Halley.Builders
{
    public class HalleyFascicoloMovimentoBuilder : IFascicoloHalleyBuilder
    {
        string _numeroProtocollo;
        string _annoProtocollo;
        ProtocolloLogs _log;
        HalleyVerticalizzazioneParametriAdapter _vert;
        string _tokenDizionario;
        string _proxy;
        private readonly IBindingFactory _bindingFactory;

        public HalleyFascicoloMovimentoBuilder(string numeroProtocollo, string annoProtocollo, ProtocolloLogs log, HalleyVerticalizzazioneParametriAdapter vert, string tokenDizionario, string proxy, IBindingFactory bindingFactory)
        {
            _numeroProtocollo = numeroProtocollo;
            _annoProtocollo = annoProtocollo;
            _log = log;
            _vert = vert;
            _tokenDizionario = tokenDizionario;
            _proxy = proxy;
            this._bindingFactory = bindingFactory;
        }

        #region IFascicoloHalleyBuilder Members

        public FascicoliFascicolo GetDatiFascicolo()
        {
            FascicoliFascicolo retVal = null;
            try
            {
                var srv = new HalleyDizionarioService(_log, _vert.UrlWsDizionario, _proxy, this._bindingFactory);
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
