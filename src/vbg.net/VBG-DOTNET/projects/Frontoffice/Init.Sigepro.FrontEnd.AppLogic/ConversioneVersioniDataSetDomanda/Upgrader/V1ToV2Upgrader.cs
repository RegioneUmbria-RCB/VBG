using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System.Data;
using System.IO;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda.Utils;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda.Upgrader
{
	public class V1ToV2Upgrader : IDatiDomandaUpgrader
	{
		V1DataSetSerializer _v1Serializer = new V1DataSetSerializer();
		V2DataSetSerializer _v2Serializer = new V2DataSetSerializer();

		public byte[] Ugrade(byte[] dati)
		{
			PresentazioneIstanzaDataSet v1 = _v1Serializer.Deserialize(dati, false);
			PresentazioneIstanzaDbV2 v2 = MapV1ToV2(v1);

			return _v2Serializer.Serialize(v2);
		}

		protected virtual PresentazioneIstanzaDbV2 MapV1ToV2(PresentazioneIstanzaDataSet v1)
		{
			var cloneHelper = new DataSetCloneHelper<PresentazioneIstanzaDataSet,PresentazioneIstanzaDbV2>();

			var v2 = cloneHelper.CreateFrom( v1 );

			UpgradeProcure(v1, v2);

			UpgradeOggetti(v1, v2);

			UpgradeDelegaATrasmettere(v1, v2);

			UpgradeAttestazionePagamento(v1, v2);

			UpgradeRiepiloghiDatiDinamici(v1, v2);

			return v2;
		}

		private void UpgradeRiepiloghiDatiDinamici(PresentazioneIstanzaDataSet v1, PresentazioneIstanzaDbV2 v2)
		{
			foreach (var row in v1.RiepilogoDatiDinamici.Rows.Cast<PresentazioneIstanzaDataSet.RiepilogoDatiDinamiciRow>())
			{
				if (row.IsCodiceOggettoNull())
					continue;

				var codiceOggetto = row.CodiceOggetto;
				var nomeFile = row.NomeFile;
				var md5 = row.MD5Hash;
				var firmatoDigitalmente = IsFirmatoDigitalmente(nomeFile);

				var newAllegatiRow = v2.Allegati.AddAllegatiRow(nomeFile, codiceOggetto, md5, firmatoDigitalmente, String.Empty);

				var riepilogoDDRow = v2.RiepilogoDatiDinamici.FindByIdModelloIndiceMolteplicita( row.IdModello, row.IndiceMolteplicita );

				riepilogoDDRow.IdAllegato = newAllegatiRow.Id;
			}
		}

		private void UpgradeAttestazionePagamento(PresentazioneIstanzaDataSet v1, PresentazioneIstanzaDbV2 v2)
		{
			if (v1.OneriAttestazionePagamento.Count == 0)
				return;

			if (v1.OneriAttestazionePagamento[0].IsCodiceOggettoNull())
				return;

			var row = v1.OneriAttestazionePagamento[0];

			var codiceOggetto = Convert.ToInt32(row.CodiceOggetto);
			var nomeFile = row.NomeFile;
			var firmatoDigitalmente = IsFirmatoDigitalmente(nomeFile);

			var newAllegatiRow = v2.Allegati.AddAllegatiRow(nomeFile, codiceOggetto, String.Empty, firmatoDigitalmente, String.Empty);

			var oneriRow = v2.OneriAttestazionePagamento[0];

			oneriRow.IdAllegato = newAllegatiRow.Id;
		}

		private void UpgradeDelegaATrasmettere(PresentazioneIstanzaDataSet v1, PresentazioneIstanzaDbV2 v2)
		{
			foreach (var row in v1.DelegaATrasmettere.Rows.Cast<PresentazioneIstanzaDataSet.DelegaATrasmettereRow>())
			{
				if (row.IsCodiceOggettoNull())
					continue;

				var codiceOggetto = Convert.ToInt32(row.CodiceOggetto);
				var nomeFile = row.NomeFile;
				var firmatoDigitalmente = IsFirmatoDigitalmente(nomeFile);

				var newAllegatiRow = v2.Allegati.AddAllegatiRow(nomeFile, codiceOggetto, String.Empty, firmatoDigitalmente, String.Empty);

				var delegaRow = v2.DelegaATrasmettere[0];

				delegaRow.IdAllegato = newAllegatiRow.Id;
			}
		}

		private void UpgradeOggetti(PresentazioneIstanzaDataSet v1, PresentazioneIstanzaDbV2 v2)
		{
			foreach (var row in v1.OGGETTI.Rows.Cast<PresentazioneIstanzaDataSet.OGGETTIRow>())
			{
				if (row.IsCODICEOGGETTONull())
					continue;

				var codiceOggetto = Convert.ToInt32(row.CODICEOGGETTO);
				var nomeFile = row.NOMEFILE;
				var md5 = row.MD5Hash;
				var note = row.Note;
				var firmatoDigitalmente = IsFirmatoDigitalmente(nomeFile);

				var newAllegatiRow = v2.Allegati.AddAllegatiRow(nomeFile, codiceOggetto, md5, firmatoDigitalmente, note);

				var oggettiRow = v2.OGGETTI.FindByID(row.ID);

				oggettiRow.IdAllegato = newAllegatiRow.Id;
			}
		}

		private static void UpgradeProcure(PresentazioneIstanzaDataSet v1, PresentazioneIstanzaDbV2 v2)
		{
			foreach (var row in v1.Procure.Rows.Cast<PresentazioneIstanzaDataSet.ProcureRow>())
			{
				if (row.IsCodiceOggettoProcuraNull())
					continue;

				var codiceOggetto = Convert.ToInt32(row.CodiceOggettoProcura);
				var nomeFile = row.NomeFile;
				var firmatoDigitalmente = IsFirmatoDigitalmente(nomeFile);

				var newAllegatiRow = v2.Allegati.AddAllegatiRow(nomeFile, codiceOggetto, String.Empty, firmatoDigitalmente, String.Empty);

				var procureRow = v2.Procure.FindByCodiceAnagrafeCodiceProcuratore(row.CodiceAnagrafe, row.CodiceProcuratore);

				procureRow.IdAllegato = newAllegatiRow.Id;
			}
		}

		private static bool IsFirmatoDigitalmente(string nomeFile)
		{
			return Path.GetExtension(nomeFile).ToUpper() == ".P7M";
		}
	}
}
