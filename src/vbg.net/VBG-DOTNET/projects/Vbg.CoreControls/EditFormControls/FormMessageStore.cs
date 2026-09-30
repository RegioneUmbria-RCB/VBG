using Microsoft.AspNetCore.Components.Forms;
using Microsoft.VisualBasic;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;

namespace Vbg.CoreControls.EditFormControls
{
    public class FormMessageStore
    {
        public static class Messages
        {
            public const string CAMPO_OBBLIGATORIO = "Compila questo campo.";
            public const string CAMPO_TROPPO_LUNGO = "Il campo ammette al massimo {0} caratteri.";
            public const string SELEZIONA_ELEMENTO = "Seleziona un elemento nell'elenco.";
        }

        private ValidationMessageStore _store;

        public FormMessageStore(ValidationMessageStore store)
        {
            this._store = store;
            this._store.Clear();
        }

        public void Add(Expression<Func<object>> func, string msg)
        {
            this._store.Add(func, msg);
        }
    }
}
