using log4net;
using SIGePro.SIT.QuestioFlorentia;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.ServiceModel;
using System.Xml;

namespace Init.SIGePro.Sit.QuestioFlorentia
{
    public class QuestioFlorentiaWrapper : IDisposable
    {
        public enum TipoCatasto
        {
            CT,
            CF
        }

        private readonly ProxyQuaestioFlorenzia _questioFlorentia;

        private readonly ILog _log = LogManager.GetLogger(typeof(QuestioFlorentiaWrapper));

        public QuestioFlorentiaWrapper(string url, string codiceComune)
        {
            var binding = new BasicHttpBinding
            {
                MaxReceivedMessageSize = 1024 * 1024 * 10,
                MaxBufferSize = 1024 * 1024 * 10,
                ReaderQuotas = new XmlDictionaryReaderQuotas
                {
                    MaxArrayLength = 1024 * 1024,
                    MaxStringContentLength = 1024 * 1024
                }
            };

            if (url.StartsWith("https"))
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            this._questioFlorentia = new ProxyQuaestioFlorenzia(binding, new EndpointAddress(url));


            // this._questioFlorentia.Url = url;
            //this._questioFlorentia.Url = "http://sitaplnew.comune.intranet/webservice/qfservice2/server.php";
            //this.  = codiceComune;

            ServicePointManager.Expect100Continue = true;
            ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls12;
        }

        public string[] ElencoCodiciEDescrizioniDaCodVia(string codVia)
        {


            var rVal = this._questioFlorentia.tpnElencoCodEtDescrCivicoDaCodStrada(codVia);

            if (rVal == null)
                return new string[0];

            return rVal;
        }

        public string[] ElencoCodiciEDescrizioniDaFoglioParticella(string foglio, string particella)
        {
            return this._questioFlorentia.aciElencoCodCivicoEtNomeStradaEtDescrCivicoDaFoglioParticella(Convert.ToInt32(foglio), particella);
        }

        public string[] ElencoCodiciDaCodFabbricato(string fabbricato)
        {
            var rVal = this._questioFlorentia.aciElencoCodCivicoDaCodFabbricato(fabbricato);

            if (rVal == null)
                return new string[0];

            return rVal;
        }

        public string[] aciElencoCodFabbricatoEtFoglioEtParticellaDaCodStrada(string codVia)
        {
            return this._questioFlorentia.aciElencoCodFabbricatoEtFoglioEtParticellaDaCodStrada(codVia);
        }

        public string[] aciElencoCodFabbricatoDaFoglioParticella(string foglio, string particella)
        {
            return this._questioFlorentia.aciElencoCodFabbricatoDaFoglioParticella(Convert.ToInt32(foglio), particella);
        }

        public IEnumerable<string> ElencoFoglioDaFoglioParziale(int parzialeFoglio, TipoCatasto tipoCatasto)
        {
            var rVal = this._questioFlorentia.ctsElencoFoglioDaFoglioParziale(parzialeFoglio, tipoCatasto == TipoCatasto.CT ? "CT" : "CF");

            if (rVal == null)
                rVal = new string[0];

            return rVal.Select(x => x.TrimStart('0')).Where(x => !String.IsNullOrEmpty(x));
        }

        public IEnumerable<RiferimentoCatastaleParsato> ctsElencoParticellaDaFoglio(string foglio, TipoCatasto tipoCatasto)
        {
            var rVal = this._questioFlorentia.ctsElencoParticellaDaFoglio(Convert.ToInt32(foglio), tipoCatasto == TipoCatasto.CT ? "CT" : "CF");

            if (rVal == null)
                rVal = new string[0];

            return rVal.Select(x => new RiferimentoCatastaleParsato(foglio, x));
        }

        public int[] aciElencoIdImmobileDaCodFabbricato(string fabbricato)
        {
            return this._questioFlorentia.aciElencoIdImmobileDaCodFabbricato(fabbricato);
        }

        public string[] ctsElencoSubalternoDaFoglioParticella(string foglio, string particella, TipoCatasto tipoCatasto)
        {
            this._log.DebugFormat("Chiamata a ctsElencoSubalternoDaFoglioParticella con gli argomenti foglio:{0}, particella:{1}, tipoCatasto:{2}", foglio, particella, tipoCatasto);

            return this._questioFlorentia.ctsElencoSubalternoDaFoglioParticella(Convert.ToInt32(foglio), particella, tipoCatasto == TipoCatasto.CT ? "CT" : "CF");
        }

        public int[] aciElencoIdImmobileDaFoglioParticella(string foglio, string particella)
        {
            return this._questioFlorentia.aciElencoIdImmobileDaFoglioParticella(Convert.ToInt32(foglio), particella);
        }

        public string[] tpnCodNumNomeUTOEDaCodCivico(string codCivico)
        {
            return this._questioFlorentia.tpnCodNumNomeUTOEDaCodCivico(codCivico);
        }

        public string[] aciElencoCodCivicoDaCodFabbricato(string codFabbricato)
        {
            return this._questioFlorentia.aciElencoCodCivicoDaCodFabbricato(codFabbricato);
        }

        public string[] aciElencoCodCivicoEtNomeStradaEtDescrCivicoDaFoglioParticella(string foglio, string particella)
        {
            return this._questioFlorentia.aciElencoCodCivicoEtNomeStradaEtDescrCivicoDaFoglioParticella(Convert.ToInt32(foglio), particella);
        }

        public string aciCodFabbricatoDaCodCivico(string codCivico)
        {
            return this._questioFlorentia.aciCodFabbricatoDaCodCivico(codCivico);
        }

        public CodiceQuartiereParsato? CodiceQuartiereDaCodiceCivico(string codCivico)
        {
            var strCodQuartiere = this._questioFlorentia.tpnCodQuartiereDaCodCivico(codCivico);

            if (String.IsNullOrEmpty(strCodQuartiere))
            {
                return null;
            }

            return new CodiceQuartiereParsato(strCodQuartiere);
        }

        public RiferimentoCatastaleParsato aciFoglioParticellaDaCodFabbricato(string codFabbricato)
        {
            var rVal = this._questioFlorentia.aciFoglioParticellaDaCodFabbricato(codFabbricato);

            if (rVal == null)
                return null;

            return new RiferimentoCatastaleParsato(rVal[0], rVal[1]);
        }

        public bool ctsEsisteFoglioParticellaSubalterno(string foglio, string particella, string subalterno, TipoCatasto tipoCatasto)
        {
            return this._questioFlorentia.ctsEsisteFoglioParticellaSubalterno(Convert.ToInt32(foglio), particella, subalterno, tipoCatasto == TipoCatasto.CT ? "CT" : "CF");
        }

        public int aciIdImmobileDaCodCivico(string codCivico)
        {
            return this._questioFlorentia.aciIdImmobileDaCodCivico(codCivico);
        }

        public void Dispose()
        {
            this._questioFlorentia?.Dispose();
        }
    }

}
