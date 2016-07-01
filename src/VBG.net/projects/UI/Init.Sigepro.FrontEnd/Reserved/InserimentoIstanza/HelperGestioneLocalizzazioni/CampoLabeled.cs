using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.Utils.Web.UI;
using System.Web.UI.WebControls;
using System.Collections;
using log4net;
using System.Globalization;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.HelperGestioneLocalizzazioni
{
	public class CampoLabeled : ICampoLocalizzazioni
	{
		public LabeledControlBase ControlloEdit { get; set; }
		public DataControlField Colonna { get; set; }

		IDictionary _stateBag;
		ILog _log = LogManager.GetLogger(typeof(CampoLabeled));


		public bool Visibile
		{
			get {  return this._stateBag[StateBagVisibile] == null ? true : (bool)this._stateBag[StateBagVisibile]; }
			set
			{
				this._stateBag[StateBagVisibile] = value;
				this.ControlloEdit.Visible = value;
				
				if (this.Colonna != null)
					this.Colonna.Visible = value;
			}
		}
		
		public string Etichetta
		{
			get { return this._stateBag[StateBagIdEtichetta] == null ? String.Empty : this._stateBag[StateBagIdEtichetta].ToString(); }
			set
			{
				this._stateBag[StateBagIdEtichetta] = value;
				AggiornaEtichetta();
			}
		}

		public bool Obbligatorio
		{
			get
			{
				return this._stateBag[StateBagIdObbligatorio] == null ? false : (bool)this._stateBag[StateBagIdObbligatorio];
			}
			set
			{
				this._stateBag[StateBagIdObbligatorio] = value;
				AggiornaEtichetta();
			}
		}

		public string EspressioneRegolare
		{
			private get
			{
				return this._stateBag[StateBagIdRegex] == null ? String.Empty : this._stateBag[StateBagIdRegex].ToString();
			}
			set
			{
				this._stateBag[StateBagIdRegex] = value;
			}
		}

		public string Valore
		{
			get { return ControlloEdit.Value; }
		}

		public string ValoreMax
		{
			get
			{
				return this._stateBag[StateBagIdValoreMax] == null ? String.Empty : this._stateBag[StateBagIdValoreMax].ToString();
			}
			set
			{
				this._stateBag[StateBagIdValoreMax] = value;
			}
		}

		public string ValoreMin
		{
			get
			{
				return this._stateBag[StateBagIdValoreMin] == null ? String.Empty : this._stateBag[StateBagIdValoreMin].ToString();
			}
			set
			{
				this._stateBag[StateBagIdValoreMin] = value;
			}
		}

		public IDictionary StateBag
		{
			set { this._stateBag = value; }
		}
		
		public virtual string Descrizione
		{
			get { return this.Valore; }
		}

		private string StateBagIdEtichetta
		{
			get { return this.ControlloEdit.ClientID + "_Etichetta"; }
		}

		private string StateBagIdObbligatorio
		{
			get { return this.ControlloEdit.ClientID + "_Obbligatorio"; }
		}

		private string StateBagVisibile
		{
			get { return this.ControlloEdit.ClientID + "_Visibile"; }
		}

		private string StateBagIdRegex
		{
			get { return this.ControlloEdit.ClientID + "_Regex"; }
		}

		private string StateBagIdValoreMin
		{
			get { return this.ControlloEdit.ClientID + "_valoremin"; }
		}

		private string StateBagIdValoreMax
		{
			get { return this.ControlloEdit.ClientID + "_valoremax"; }
		}
		
		private void AggiornaEtichetta()
		{
			this.ControlloEdit.Descrizione = this.Etichetta;

			if (this.Obbligatorio)
				this.ControlloEdit.Descrizione += " *";

			if (this.Colonna != null)
				this.Colonna.HeaderText = this.Etichetta;
		}

		public bool RegexVerificata()
		{
			if (!this.Obbligatorio && String.IsNullOrEmpty(this.ControlloEdit.Value.Trim()))
			{
				return true;
			}

			if (String.IsNullOrEmpty(this.EspressioneRegolare))
			{
				return true;
			}

			return System.Text.RegularExpressions.Regex.IsMatch(this.ControlloEdit.Value, this.EspressioneRegolare);
		}
		
		public bool VerificaValoreInRange()
		{
			var min = double.MinValue;
			var max = double.MaxValue;
			var val = 0.0d;

			var formatProvider = CultureInfo.InvariantCulture.NumberFormat;
			var numberStyles = NumberStyles.AllowLeadingWhite | NumberStyles.AllowTrailingWhite | NumberStyles.AllowLeadingSign | NumberStyles.AllowDecimalPoint;

			if (!String.IsNullOrEmpty(this.ValoreMin))
			{
				if (!double.TryParse(this.ValoreMin, numberStyles, formatProvider, out min))
				{
					_log.DebugFormat("Il valore min del campo {0} non è un numero valido (valore={1}), verrà usato double.MinValue come valore min", this.Etichetta, this.ValoreMin);
					min = double.MinValue;
				}
			}

			if (!String.IsNullOrEmpty(this.ValoreMax))
			{
				if (!double.TryParse(this.ValoreMax, numberStyles, formatProvider, out max))
				{
					_log.DebugFormat("Il valore max del campo {0} non è un numero valido (valore={1}), verrà usato double.MaxValue come valore max", this.Etichetta, this.ValoreMin);
					max = double.MaxValue;
				}
			}

			if (String.IsNullOrEmpty(this.ControlloEdit.Value))
			{
				return true;
			}

			this.ControlloEdit.Value = this.ControlloEdit.Value.Replace(",", ".");

			if (!double.TryParse(this.ControlloEdit.Value, numberStyles, formatProvider, out val))
			{
				_log.DebugFormat("Il valore del campo {0} non è un numero valido (valore={1}), non verrà effettuata la verifica di appartenenza ad un range di valori", this.Etichetta, this.ControlloEdit.Value);
				return true;
			}

			return min <= val && val <= max;
		}

		public bool VerificaCompilazione()
		{
			if (this.Visibile && this.Obbligatorio && String.IsNullOrEmpty(this.ControlloEdit.Value))
				return false;

			return true;
		}

		public virtual void SvuotaCampo()
		{
			ControlloEdit.Value = String.Empty;
		}
	}
}