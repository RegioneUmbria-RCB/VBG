using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces;

namespace Init.SIGePro.DatiDinamici.Interfaces.WebControls
{
	public delegate void OnValueChangedDelegate(object sender, string nuovoValore);

    public interface IDatiDinamiciControl
    {
        string ID { get; set; }
        int IdCampoCollegato { get; }
        string Descrizione { get; }
        int Indice { get; set; }
        int NumeroRiga { get; set; }
        string Software { get; }
        string IdComune { get; }
        string Valore { get; set; }
        string Note { get; }
        // bool GeneraRiferimentoANote { get; set; }
        // int? IdRiferimentoNote { set; get; }
    }
}
