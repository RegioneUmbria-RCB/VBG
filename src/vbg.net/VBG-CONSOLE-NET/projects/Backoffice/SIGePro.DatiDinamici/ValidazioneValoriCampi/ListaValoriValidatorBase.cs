using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.ValidazioneValoriCampi
{
	internal abstract class ListaValoriValidatorBase : ICampoDinamicoValidator
	{
		protected CampoDinamicoBase _campo;
		protected string _messaggioErrore;
		protected string _indicatoreRigaErrore;

		public ListaValoriValidatorBase(CampoDinamicoBase campo, string messaggioErrore, string indicatoreRigaErrore)
		{
			this._campo = campo;
			this._messaggioErrore = messaggioErrore;
			this._indicatoreRigaErrore = indicatoreRigaErrore;
		}
		
		protected abstract ICampoDinamicoValidator CreaValidator(int indice);

		internal IEnumerable<ErroreValidazione> OnGetErroriDiValidazione()
		{
			for (int i = 0; i < this._campo.ListaValori.Count; i++)
			{
				var validatore = CreaValidator( i );

				foreach (var ex in validatore.GetErroriDiValidazione())
				{
					var msg = ex.Messaggio;

					if (this._campo.ListaValori.Count > 1)
					{
						msg += " ";
						msg += String.Format(this._indicatoreRigaErrore, (ex.Indice + 1));
					}

					yield return new ErroreValidazione(msg, this._campo.Id, ex.Indice);
				}
			}
		}

		public IEnumerable<ErroreValidazione> GetErroriDiValidazione()
		{
			var res = OnGetErroriDiValidazione();

			return res ?? Enumerable.Empty<ErroreValidazione>();
		}
	}

	internal class CampiMultipliObbligatoriValidator : ListaValoriValidatorBase
	{
		public CampiMultipliObbligatoriValidator(CampoDinamicoBase campo, string messaggioErrore = "E'obbligatorio inserire un valore per il campo \"{0}\"", string indicatoreRiga = "alla riga/blocco {0}")
			: base(campo, messaggioErrore, indicatoreRiga)
		{
		}

		protected override ICampoDinamicoValidator CreaValidator(int indice)
		{
			return new CampoObbligatorioValidator(this._campo, indice, this._messaggioErrore);
		}
	}

	internal class CheckboxesObbligatorieValidator : ListaValoriValidatorBase
	{
		public CheckboxesObbligatorieValidator(CampoDinamicoBase campo, string messaggioErrore = "E'obbligatorio selezionare la casella \"{0}\"", string indicatoreRiga = "alla riga/blocco {0}")
			: base(campo, messaggioErrore, indicatoreRiga)
		{
		}

		protected override ICampoDinamicoValidator CreaValidator(int indice)
		{
			return new CheckboxValidator(this._campo, indice, this._messaggioErrore);
		}
	}

	internal class RegexCampiValidator: ListaValoriValidatorBase
	{
		public RegexCampiValidator(CampoDinamicoBase campo, string messaggioErrore = "Il campo \"{0}\" contiene caratteri non validi per l'espressione di validazione {1}", string indicatoreRiga = "(riga/blocco {0})")
			: base(campo, messaggioErrore, indicatoreRiga)
		{
		}

		protected override ICampoDinamicoValidator CreaValidator(int indice)
		{
			return new RegexCampoValidator(this._campo, indice, this._messaggioErrore);
		}
	}

	internal class ValoriNumericiValidiValidator : ListaValoriValidatorBase
	{
		public ValoriNumericiValidiValidator(CampoDinamicoBase campo, string messaggioErrore = "Il campo \"{0}\" contiene un numero non valido", string indicatoreRiga = "alla riga/blocco {0}")
			:base( campo, messaggioErrore, indicatoreRiga )
		{

		}

		protected override ICampoDinamicoValidator CreaValidator(int indice)
		{
			return new ValidNumberValidator(this._campo, indice, this._messaggioErrore);
		}
	}

	internal class ValoriNumericiMinValueValidator : ListaValoriValidatorBase
	{
		public ValoriNumericiMinValueValidator(CampoDinamicoBase campo, string messaggioErrore = "Il campo \"{0}\" non ammette valori minori di {1}", string indicatoreRiga = "(riga/blocco {0})")
			:base( campo, messaggioErrore, indicatoreRiga )
		{

		}

		protected override ICampoDinamicoValidator CreaValidator(int indice)
		{
			return new MinValueValidator(this._campo, indice, this._messaggioErrore);
		}
	}

	internal class ValoriNumericiMaxValueValidator :  ListaValoriValidatorBase
	{
		public ValoriNumericiMaxValueValidator(CampoDinamicoBase campo, string messaggioErrore = "Il campo \"{0}\" non ammette valori maggiori di {1}", string indicatoreRiga = "(riga/blocco {0})")
			:base( campo, messaggioErrore, indicatoreRiga )
		{

		}

		protected override ICampoDinamicoValidator CreaValidator(int indice)
		{
			return new MaxValueValidator(this._campo, indice, this._messaggioErrore);
		}
	}
}
