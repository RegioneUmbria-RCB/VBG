using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda.Utils;
using System.Data;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda.Upgrader
{
	public class V2ToV3Upgrader : IDatiDomandaUpgrader
	{
		V2DataSetSerializer _origineSerializer = new V2DataSetSerializer();
		V3DataSetSerializer _destinazioneSerializer = new V3DataSetSerializer();

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
			
			UpgradeMappali(destinazione);
			UpgradeStradariSenzaUUID(destinazione);

			// 
			
			return destinazione;
		}

		private void UpgradeStradariSenzaUUID( PresentazioneIstanzaDbV2 destinazione)
		{
			var localizzazioniSenzaUuid = destinazione.ISTANZESTRADARIO.Where(x => x.IsUuidNull() || String.IsNullOrEmpty(x.Uuid));

			foreach (var localizzazione in localizzazioniSenzaUuid)
				localizzazione.Uuid = Guid.NewGuid().ToString();
		}
		
		private void UpgradeMappali( PresentazioneIstanzaDbV2 destinazione)
		{
			if (destinazione.ISTANZESTRADARIO.Count == 0)
			{
				destinazione.DATICATASTALI.Clear();
				return;
			}

			if (destinazione.DATICATASTALI.Count == 0)
				return;

			if (destinazione.ISTANZESTRADARIO.Count >= destinazione.DATICATASTALI.Count)
			{
				for (int i = 0; i < destinazione.DATICATASTALI.Count; i++)
				{
					destinazione.DATICATASTALI[i].IdLocalizzazione = destinazione.ISTANZESTRADARIO[i].ID;
				}
			}

			// Ci sono più dati catastali che mappali
			for (int i = 0; i < destinazione.ISTANZESTRADARIO.Count; i++)
			{
				destinazione.DATICATASTALI[i].IdLocalizzazione = destinazione.ISTANZESTRADARIO[i].ID;
			}

			for (int i = destinazione.ISTANZESTRADARIO.Count; i < destinazione.DATICATASTALI.Count; i++)
			{
				destinazione.DATICATASTALI[i].IdLocalizzazione = destinazione.ISTANZESTRADARIO[destinazione.ISTANZESTRADARIO.Count - 1].ID;
			}
		}
	}
}
