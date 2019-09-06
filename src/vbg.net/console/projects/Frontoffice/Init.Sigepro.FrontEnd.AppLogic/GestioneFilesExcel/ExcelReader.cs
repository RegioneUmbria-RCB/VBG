using SpreadsheetLight;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneFilesExcel
{
    public class ExcelReader
    {
        SLDocument doc;

        public ExcelReader(byte[] contenutoFile)
        {
            var ms = new MemoryStream(contenutoFile);

            doc = new SLDocument(ms);
        }

        public ExcelReader(string path)
            :this(File.ReadAllBytes(path))
        {
        }
        /*
        public string GetValue(string txt)
        {
            return GetValue(new ExcelExpression(txt));
        }
        */
        public bool ContainsWorksheet(string worksheetName)
        {
            var current = doc.GetCurrentWorksheetName();
            var result = doc.SelectWorksheet(worksheetName);

            doc.SelectWorksheet(current);

            return result;
        }

        public string GetValue(ExcelExpression expr)
        {
            if (!doc.SelectWorksheet(expr.Sheet))
            {
                throw new InvalidOperationException(String.Format("Foglio {0} non presente nel documento", expr.Sheet));
            }

            return doc.GetCellValueAsString(expr.Cell);
        }
    }
}
