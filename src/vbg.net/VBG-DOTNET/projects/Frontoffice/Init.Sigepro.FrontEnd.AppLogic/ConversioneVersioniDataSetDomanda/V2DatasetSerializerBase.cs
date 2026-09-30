using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System;
using System.IO;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda
{
    internal abstract class V2DatasetSerializerBase
    {
        public byte[] Serialize(PresentazioneIstanzaDbV2 dataSet)
        {
            using (var ms = new MemoryStream())
            {
                var header = Encoding.Default.GetBytes(this.GetVersionHeader());
                ms.Write(header, 0, header.Length);

                dataSet.WriteXml(ms);

                return ms.ToArray();
            }
        }

        public PresentazioneIstanzaDbV2 Deserialize(byte[] dati)
        {
            //var lunghezzaHeader = VersionInformationsHelper.DatiDomandaHeader.V2Header.Length;
            var versionHeader = this.GetVersionHeader();
            var lunghezzaHeader = versionHeader.Length;

            var lunghezzaDatiSenzaHeader = dati.Length - lunghezzaHeader;

            var datiSenzaHeader = new byte[lunghezzaDatiSenzaHeader];

            Array.Copy(dati, lunghezzaHeader, datiSenzaHeader, 0, lunghezzaDatiSenzaHeader);

            var ds = new PresentazioneIstanzaDbV2();

            using (var ms = new MemoryStream(datiSenzaHeader))
            {
                //ds.EnforceConstraints = false;
                ds.ReadXml(ms);
                //ds.Tables[7].Clear();
            }

            return ds;
        }

        protected abstract string GetVersionHeader();
    }
}
