using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneFilesExcel
{
    class RegoleExcel
    {
        IEnumerable<MappaturaExcel> _mappature;

        public RegoleExcel(IEnumerable<MappaturaExcel> mappature)
        {
            this._mappature = mappature;
        }

        public IEnumerable<EsitoMappatura> ApplicaA(BinaryFile documento)
        {
            var xls = new ExcelReader(documento.FileContent);
            var risultatoMappature = new List<EsitoMappatura>();

            foreach(var m in this._mappature)
            {
                var expr = new ExcelExpression(m.Espressione);

                if (xls.ContainsWorksheet(expr.Sheet))
                {
                    var valore = xls.GetValue(expr);

                    if (String.IsNullOrEmpty(valore))
                    {
                        continue;
                    }

                    risultatoMappature.Add(new EsitoMappatura(m.IdCampo, m.NomeCampo, valore));
                }
            }

            return risultatoMappature;
        }

        internal IEnumerable<EsitoMappatura> ApplicaA(FileExcel doc)
        {
            return ApplicaA(doc.GetFile());
        }
    }
}
