using System;
using System.IO;
using System.Text;
using System.Xml.XPath;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{

    public class ByteArrayMenuFile : MenuFile
    {
        private byte[] _binaryContent;

        public override byte[] BinaryContent => this._binaryContent;

        public ByteArrayMenuFile(byte[] menuFile)
            : base(Encoding.UTF8.GetString(menuFile), GetVersione(menuFile))
        {
            this._binaryContent = menuFile;
        }

        private static int GetVersione(byte[] byteContent)
        {
            using (var ms = new MemoryStream(byteContent))
            {
                var doc = new XPathDocument(ms);
                var navigator = doc.CreateNavigator();
                var node = navigator.SelectSingleNode(MenuFile.Constants.XPathVersione);

                if (node == null || String.IsNullOrEmpty(node.Value))
                {
                    return 1;
                }

                return Convert.ToInt32(node.Value);
            }
        }
    }
}
