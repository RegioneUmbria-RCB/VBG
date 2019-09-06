using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces.WebControls;
using Init.SIGePro.DatiDinamici.WebControls.CreazioneControlli;
using log4net;

namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{
	internal class CampoDinamicoRenderizzato
	{
		ILog _log = LogManager.GetLogger(typeof(CampoDinamicoRenderizzato));
		IdControlloInput _id;
		CampoDinamicoBase _campo;
		bool _solaLettura;

		public CampoDinamicoRenderizzato(IdControlloInput id,CampoDinamicoBase campo)
		{
			this._campo = campo;
			this._id = id;
		}

		public IDatiDinamiciControl CreaControllo(bool solaLettura, Action<string> callbackErroreAssegnazioneValore = null)
		{
			var indice = this._id.IndiceMolteplicitaValore;
			int numeroRiga = this._id.IndiceRiga;

			IDatiDinamiciControlsFactory factory = solaLettura ?
													(IDatiDinamiciControlsFactory)new ReadOnlyControlsFactory() :
													(IDatiDinamiciControlsFactory)new ReadWriteControlsFactory();

			var ctrl = factory.CreaControllo(this._campo);

			ctrl.ID = this._id.AsString();
			ctrl.Indice = indice;
			ctrl.NumeroRiga = numeroRiga;
			
			// Queste righe di codice vanno spostate e gestite meglio
			// Da qui ...
			if (this._campo is CampoDinamicoTestuale)
			{
				this._campo.ListaValori[indice].Valore = (this._campo as CampoDinamicoTestuale).TestoStatico;
			}
			// ... a qui

			// Debug.WriteLine("Creato controllo con ID {0}\tindice {1}\tnumeroriga {2}\tsolaLettura {3}\ttipo {4}", ctrl.ID, ctrl.Indice, ctrl.NumeroRiga, solaLettura, ctrl.GetType());

			try
			{
				ctrl.Valore = new ValoreCampo(this._campo, solaLettura).AllIndice(indice);
			}
			catch(Exception ex)
			{
				if(callbackErroreAssegnazioneValore != null)
				{
					var errMsg = String.Format("Errore durante l'assegnazione del valore del controllo, Dati tecnici [{0}, errore: {1}]", this._id, ex.Message);

					this._log.Error(errMsg);

					callbackErroreAssegnazioneValore(errMsg);
				}
			}

			return ctrl;
		}

	}
}
