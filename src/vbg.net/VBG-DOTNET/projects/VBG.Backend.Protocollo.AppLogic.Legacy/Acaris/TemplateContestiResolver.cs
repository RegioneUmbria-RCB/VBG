using Init.SIGePro.Manager.Logic.GestioneContesti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris
{
    internal class TemplateContestiResolver
    {
        public string SostituisciTemplate(string template, Dictionary<string, List<Dyn2Dato>> datiContesto)
        {
            if (string.IsNullOrEmpty(template) || datiContesto == null)
            {
                return null;
            }

            var retVal = template;
            if (datiContesto.ContainsKey(Contesti.Categoria))
            {
                retVal = retVal.Replace("[CATEGORIA]", this.EstraiValoreContesto(datiContesto[Contesti.Categoria]));
            }
            if (datiContesto.ContainsKey(Contesti.Codice))
            {
                retVal = retVal.Replace("[CODICE]", this.EstraiValoreContesto(datiContesto[Contesti.Codice]));
            }
            if (datiContesto.ContainsKey(Contesti.CodiceFiscale))
            {
                retVal = retVal.Replace("[CODICEFISCALE]", this.EstraiValoreContesto(datiContesto[Contesti.CodiceFiscale]));
            }
            if (datiContesto.ContainsKey(Contesti.Indirizzo))
            {
                retVal = retVal.Replace("[INDIRIZZO]", this.EstraiValoreDecodificatoContesto(datiContesto[Contesti.Indirizzo]));
            }
            if (datiContesto.ContainsKey(Contesti.Localita))
            {
                retVal = retVal.Replace("[LOCALITA]", this.EstraiValoreDecodificatoContesto(datiContesto[Contesti.Localita]));
            }
            if (datiContesto.ContainsKey(Contesti.PartitaIva))
            {
                retVal = retVal.Replace("[PARTITAIVA]", this.EstraiValoreDecodificatoContesto(datiContesto[Contesti.PartitaIva]));
            }
            if (datiContesto.ContainsKey(Contesti.Soggetto))
            {
                retVal = retVal.Replace("[SOGGETTO]", this.EstraiValoreDecodificatoContesto(datiContesto[Contesti.Soggetto]));
            }
            return retVal;
        }

        private string EstraiValoreContesto(List<Dyn2Dato> dyn2Dati)
        {
            if (dyn2Dati == null || dyn2Dati.Count == 0)
            {
                return null;
            }

            if (dyn2Dati.Any(x => !String.IsNullOrEmpty(x.Valore)))
            {
                return dyn2Dati.First(x => !String.IsNullOrEmpty(x.Valore)).Valore;
            }

            return "";
        }

        private string EstraiValoreDecodificatoContesto(List<Dyn2Dato> dyn2Dati)
        {
            if (dyn2Dati == null || dyn2Dati.Count == 0)
            {
                return null;
            }

            if (dyn2Dati.Any(x => !String.IsNullOrEmpty(x.ValoreDecodificato)))
            {
                return dyn2Dati.First(x => !String.IsNullOrEmpty(x.ValoreDecodificato)).ValoreDecodificato;
            }

            return "";
        }
    }
}
