using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda.Utils;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda.Upgrader
{
	public class V3ToV4Upgrader : IDatiDomandaUpgrader
	{
		V3DataSetSerializer _origineSerializer = new V3DataSetSerializer();
		V4DataSetSerializer _destinazioneSerializer = new V4DataSetSerializer();

		public byte[] Ugrade(byte[] dati)
		{
			var origine = _origineSerializer.Deserialize(dati);
			var destinazione = MapOrigineToDestinazione(origine);

			return _destinazioneSerializer.Serialize(destinazione);
		}

		protected virtual PresentazioneIstanzaDbV2 MapOrigineToDestinazione(PresentazioneIstanzaDbV2 origine)
		{
			var cloneHelper = new DataSetCloneHelper<PresentazioneIstanzaDbV2, PresentazioneIstanzaDbV2>();

			var destinazione = cloneHelper.CreateFrom(origine);

			UpgradeDatiDinamici(destinazione);

			return destinazione;
		}

		private void UpgradeDatiDinamici(PresentazioneIstanzaDbV2 destinazione)
		{
			var righeDaAggiornare = destinazione.Dyn2Dati.Where(x => x["IndiceScheda"] == DBNull.Value);

			foreach (var riga in righeDaAggiornare)
				riga.IndiceScheda = 0;

			destinazione.AcceptChanges();
		}
	}
}
