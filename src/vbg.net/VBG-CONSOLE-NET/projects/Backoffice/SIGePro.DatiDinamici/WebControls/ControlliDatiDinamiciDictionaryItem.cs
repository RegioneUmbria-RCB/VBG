using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;

namespace Init.SIGePro.DatiDinamici.WebControls
{
    /// <summary>
    /// Rappresenta un tipo di controllo utilizzabile nella gestione dei dati dinamici di SIGepro
    /// </summary>
    public class ControlliDatiDinamiciDictionaryItem
    {
        public readonly TipoControlloEnum TipoCampo;
        public readonly string NomeTipoCampo;
        public readonly string Descrizione;
        public readonly bool VisibileInDesigner;
        public readonly Type TipoRuntime;
        public readonly bool HaEtichetta;
        public readonly bool HaDescrizione;

        public string HelpControllo
        {
            get
            {
                var mi = TipoRuntime.GetMethod("HelpControllo", BindingFlags.Static | BindingFlags.Public);

                if (mi == null)
                    return string.Empty;

                return (string)mi.Invoke(null, null) ?? String.Empty;
            }
        }

        public Dictionary<string, EditablePropertyDetails> ProprietaEditabili
        {
            get
            {
                var ret = new Dictionary<string, EditablePropertyDetails>();

                MethodInfo mi = TipoRuntime.GetMethod("GetProprietaDesigner", BindingFlags.Static | BindingFlags.Public);

                if (mi == null)
                {
                    return ret;
                }

                var proprieta = (ProprietaDesigner[])mi.Invoke(null, null);

                foreach (var prop in proprieta)
                {
                    if (!prop.VisibileInDesigner)
                        continue;

                    var id = prop.NomeProprieta;
                    var description = prop.Descrizione;
                    var tipoControllo = prop.TipoControlloEditing;
                    var valoriLista = prop.ValoriLista;
                    var valoreDefault = prop.ValoreDefault;

                    if (String.IsNullOrEmpty(description))
                        description = id;

                    ret.Add(id, new EditablePropertyDetails(id, description, tipoControllo, valoriLista, valoreDefault));
                }

                return ret;
            }
        }

        public ControlliDatiDinamiciDictionaryItem(TipoControlloEnum tipoCampo, string descrizione, bool visibileInDesigner, Type tipoelemento)
            :this(tipoCampo, descrizione, visibileInDesigner, true, true, tipoelemento)
        {

        }

        public ControlliDatiDinamiciDictionaryItem(TipoControlloEnum tipoCampo, string descrizione, bool visibileInDesigner, bool haEtichetta, bool haDescrizione, Type tipoelemento)
        {
            this.TipoCampo = tipoCampo;
            this.Descrizione = descrizione;
            this.VisibileInDesigner = visibileInDesigner;
            this.TipoRuntime = tipoelemento;
            this.HaEtichetta = haEtichetta;
            this.HaDescrizione = haDescrizione;
        }
    }
}
