using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.WebControls
{
    /// <summary>
    /// Rappresenta il dizionario di tutti i tipi controllo utilizzabili nei campi dinamici
    /// </summary>
    public static class ControlliDatiDinamiciDictionary
    {
        public static Dictionary<TipoControlloEnum, ControlliDatiDinamiciDictionaryItem> DesignTimeItems { get; private set; }
        public static Dictionary<TipoControlloEnum, ControlliDatiDinamiciDictionaryItem> Items { get; private set; }

        static ControlliDatiDinamiciDictionary()
        {
            var items = new List<ControlliDatiDinamiciDictionaryItem>
            {
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Checkbox, "Casella di spunta", true, typeof(DatiDinamiciCheckBox)),
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Data, "Data", true, typeof(DatiDinamiciDateTextBox)),
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Label, "Etichetta o descrizione estesa", false, typeof(DatiDinamiciLabel)),
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Lista, "Lista valori", true, typeof(DatiDinamiciListBox)),
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.ListaSIGePro, "Lista valori da db", true, typeof(DatiDinamiciSigeproListBox)),
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.MultiLista, "Multi lista valori", true, typeof(DatiDinamiciMultiListBox)),
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.NumericoDouble, "Numerico decimale", true, typeof(DatiDinamiciDoubleTextBox)),
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.NumericoIntero, "Numero intero", true, typeof(DatiDinamiciIntTextBox)),
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Ricerca, "Ricerca nel db", true, typeof(DatiDinamiciSearch2)),
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Testo, "Testo", true, typeof(DatiDinamiciTextBox)),
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Titolo, "Titolo", false, typeof(DatiDinamiciTitolo)),
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Upload, "Upload", true, typeof(DatiDinamiciUpload)),
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Bottone, "Bottone", true, typeof(DatiDinamiciButton)),
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Localizzazione, "Localizzazione", true, typeof(DatiDinamiciLocalizzazioniListBox)),
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.CampoNascosto, "Campo nascosto", true, false, false, typeof(DatiDinamiciHidden)),
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.TestoInSolaLettura, "Testo in sola lettura", true, false, false, typeof(DatiDinamiciReadOnlyText)),

            };

            DesignTimeItems = items.Where(x => x.VisibileInDesigner).ToDictionary(x => x.TipoCampo);
            Items = items.ToDictionary( x => x.TipoCampo);
        }
        
        /// <summary>
        /// Rappresenta i tipi di elementi che possono essere utilizzati durante la fase di design di un campo dinamico
        /// </summary>
              
    }
}
