using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.QuestioFlorentia
{
	public class QuestioFlorentiaWrapper
	{
		public enum TipoCatasto
		{
			CT,
			CF
		}

		ProxyQuaestioFlorenzia _questioFlorentia;
		string _codiceComune;
        ILog _log = LogManager.GetLogger(typeof(QuestioFlorentiaWrapper));

		public QuestioFlorentiaWrapper(string url, string codiceComune)
		{
			this._questioFlorentia = new ProxyQuaestioFlorenzia();
			this._questioFlorentia.Url = url;
			this._codiceComune = codiceComune;
		}

		public string[] ElencoCodiciEDescrizioniDaCodVia(string codVia)
		{
			var rVal = _questioFlorentia.tpnElencoCodEtDescrCivicoDaCodStrada(this._codiceComune + codVia.PadLeft(8, '0'));

			if (rVal == null)
				return new string[0];

			return rVal;
		}

		public string[] ElencoCodiciEDescrizioniDaFoglioParticella(string foglio, string particella)
		{
			return _questioFlorentia.aciElencoCodCivicoEtNomeStradaEtDescrCivicoDaFoglioParticella(Convert.ToInt32(foglio), particella);
		}

		public string[] ElencoCodiciDaCodFabbricato(string fabbricato)
		{
			var rVal = _questioFlorentia.aciElencoCodCivicoDaCodFabbricato(fabbricato);

			if (rVal == null)
				return new string[0];

			return rVal;
		}

		public string[] aciElencoCodFabbricatoEtFoglioEtParticellaDaCodStrada(string codVia)
		{
			return _questioFlorentia.aciElencoCodFabbricatoEtFoglioEtParticellaDaCodStrada(this._codiceComune + codVia.PadLeft(8, '0'));
		}

		public string[] aciElencoCodFabbricatoDaFoglioParticella(string foglio, string particella)
		{
			return _questioFlorentia.aciElencoCodFabbricatoDaFoglioParticella(Convert.ToInt32(foglio), particella);
		}

		public IEnumerable<string> ElencoFoglioDaFoglioParziale(int parzialeFoglio, TipoCatasto tipoCatasto)
		{
			var rVal = _questioFlorentia.ctsElencoFoglioDaFoglioParziale(parzialeFoglio, tipoCatasto == TipoCatasto.CT ? "CT" : "CF");

			if (rVal == null)
				rVal = new string[0];

			return rVal.Select(x => x.TrimStart('0')).Where(x => !String.IsNullOrEmpty(x));
		}

		public IEnumerable<RiferimentoCatastaleParsato> ctsElencoParticellaDaFoglio(string foglio, TipoCatasto tipoCatasto)
		{
			var rVal = _questioFlorentia.ctsElencoParticellaDaFoglio(Convert.ToInt32(foglio), tipoCatasto == TipoCatasto.CT ? "CT" : "CF");

			if (rVal == null)
				rVal = new string[0];

			return rVal.Select(x => new RiferimentoCatastaleParsato(foglio, x));
		}

		public int[] aciElencoIdImmobileDaCodFabbricato(string fabbricato)
		{
			return _questioFlorentia.aciElencoIdImmobileDaCodFabbricato(fabbricato);
		}

		public string[] ctsElencoSubalternoDaFoglioParticella(string foglio, string particella, TipoCatasto tipoCatasto)
		{
            _log.DebugFormat("Chiamata a ctsElencoSubalternoDaFoglioParticella con gli argomenti foglio:{0}, particella:{1}, tipoCatasto:{2}", foglio, particella, tipoCatasto);

            return _questioFlorentia.ctsElencoSubalternoDaFoglioParticella(Convert.ToInt32(foglio), particella, tipoCatasto == TipoCatasto.CT ? "CT" : "CF");
		}

		public int[] aciElencoIdImmobileDaFoglioParticella(string foglio, string particella)
		{
			return _questioFlorentia.aciElencoIdImmobileDaFoglioParticella(Convert.ToInt32(foglio), particella);
		}

		public string[] tpnCodNumNomeUTOEDaCodCivico(string codCivico)
		{
			return _questioFlorentia.tpnCodNumNomeUTOEDaCodCivico(codCivico);
		}

		public string[] aciElencoCodCivicoDaCodFabbricato(string codFabbricato)
		{
			return _questioFlorentia.aciElencoCodCivicoDaCodFabbricato(codFabbricato);
		}

		public string[] aciElencoCodCivicoEtNomeStradaEtDescrCivicoDaFoglioParticella(string foglio, string particella)
		{
			return _questioFlorentia.aciElencoCodCivicoEtNomeStradaEtDescrCivicoDaFoglioParticella(Convert.ToInt32(foglio), particella);
		}

		public string aciCodFabbricatoDaCodCivico(string codCivico)
		{
			return _questioFlorentia.aciCodFabbricatoDaCodCivico(codCivico);
		}

		public RiferimentoCatastaleParsato aciFoglioParticellaDaCodFabbricato(string codFabbricato)
		{
			var rVal = _questioFlorentia.aciFoglioParticellaDaCodFabbricato(codFabbricato);

			if (rVal == null)
				return null;

			return new RiferimentoCatastaleParsato(rVal[0], rVal[1]);
		}

		public bool ctsEsisteFoglioParticellaSubalterno(string foglio, string particella, string subalterno, TipoCatasto tipoCatasto)
		{
			return _questioFlorentia.ctsEsisteFoglioParticellaSubalterno(Convert.ToInt32(foglio), particella, subalterno, tipoCatasto == TipoCatasto.CT ? "CT" : "CF");
		}

		public int aciIdImmobileDaCodCivico(string codCivico)
		{
			return _questioFlorentia.aciIdImmobileDaCodCivico(codCivico);
		}
	}

}
