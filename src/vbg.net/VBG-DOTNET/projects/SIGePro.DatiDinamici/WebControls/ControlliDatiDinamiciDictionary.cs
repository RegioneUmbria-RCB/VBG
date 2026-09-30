using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici.Web;

namespace Init.SIGePro.DatiDinamici.WebControls
{
    /// <summary>
    /// Rappresenta il dizionario di tutti i tipi controllo utilizzabili nei campi dinamici
    /// </summary>
    /// <summary>
    /// Rappresenta il dizionario di tutti i tipi controllo utilizzabili nei campi dinamici
    /// </summary>
    public static class ControlliDatiDinamiciDictionary
    {
        private static readonly IEnumerable<ControlliDatiDinamiciDictionaryItem> _allItems;
        private static readonly IEnumerable<ControlliDatiDinamiciDictionaryItem> _designTimeItems;

        static ControlliDatiDinamiciDictionary()
        {
            _allItems = new List<ControlliDatiDinamiciDictionaryItem>
            {
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Checkbox, "Casella di spunta", true, typeof(DatiDinamiciCheckBox)),
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Data, "Data", true, typeof(DatiDinamiciDateTextBox)),
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Label, "Etichetta o descrizione estesa", false, typeof(DatiDinamiciLabel)),
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.Lista, "Lista valori", true, typeof(DatiDinamiciListBox)),
                new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.ListaSIGePro, "Lista valori da db (SOLO BACK)", true, typeof(DatiDinamiciSigeproListBox)),
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

            _designTimeItems = _allItems.Where(x => x.VisibileInDesigner);
            //_items = items
        }

        /// <summary>
        /// Rappresenta i tipi di elementi che possono essere utilizzati durante la fase di design di un campo dinamico
        /// </summary>

        public static IDictionary<TipoControlloEnum, ControlliDatiDinamiciDictionaryItem> GetCampiSupportati(bool verticalizzazioneEagleAttiva)
        {
            var items = _allItems.ToDictionary(x => x.TipoCampo);

            if (verticalizzazioneEagleAttiva)
            {
                items[TipoControlloEnum.LocalizzazioneEagle] = new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.LocalizzazioneEagle, "Localizzazione EAGLE", true, false, true, typeof(DatiDinamiciEagle));
            }

            return items;
        }

        public static IDictionary<TipoControlloEnum, ControlliDatiDinamiciDictionaryItem> GetCampiDesignSupportati(bool verticalizzazioneEagleAttiva)
        {
            var items = _designTimeItems.ToDictionary(x => x.TipoCampo);

            if (verticalizzazioneEagleAttiva)
            {
                items[TipoControlloEnum.LocalizzazioneEagle] = new ControlliDatiDinamiciDictionaryItem(TipoControlloEnum.LocalizzazioneEagle, "Localizzazione EAGLE", true, false, true, typeof(DatiDinamiciEagle));
            }

            return items;
        }
    }
}
