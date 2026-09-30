using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.GeProt.Protocollazione
{
    public class ParametriAdapter
    {
        public ParametriAdapter()
        {

        }

        public Parametro[] Adatta(string operatore, string descrizioneTipoDocumento)
        {
            return new Parametro[] 
            { 
                new Parametro 
                { 
                    NomeParametro = new NomeParametro { Text = new string[] { "Note" } }, 
                    ValoreParametro = new ValoreParametro { Text = new string[] { String.Format("Documento protocollato dall'operatore {0}", operatore) } } 
                },
                new Parametro
                { 
                    NomeParametro = new NomeParametro{ Text = new string[]{ "TipoDocumento" } },
                    ValoreParametro = new ValoreParametro{ Text = new string[]{ descrizioneTipoDocumento } }
                }
            };
        }
    }
}
