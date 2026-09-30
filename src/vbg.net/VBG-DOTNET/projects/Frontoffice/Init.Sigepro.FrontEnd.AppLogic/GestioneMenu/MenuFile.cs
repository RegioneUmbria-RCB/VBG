using System;
using System.Text;
using System.Xml;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    public class MenuFile
    {
        protected static class Constants
        {
            public const string XPathVersione = "//MainMenu/Versione";
        }

        public string TextContent { get; }
        public virtual byte[] BinaryContent => Encoding.UTF8.GetBytes(this.TextContent);

        public int Versione { get; }

        public MenuFile(string menuText)
        {
            this.TextContent = menuText;
            this.Versione = this.EstraiVersione();
        }

        protected MenuFile(string textContent, int versione)
        {
            this.TextContent = textContent;
            this.Versione = versione;
        }

        private int EstraiVersione()
        {
            var xmlDoc = new XmlDocument();
            xmlDoc.LoadXml(this.TextContent);

            var node = xmlDoc.SelectSingleNode(Constants.XPathVersione);

            if (node == null || String.IsNullOrEmpty(node.InnerText))
            {
                return 1;
            }

            return Convert.ToInt32(node.InnerText);
        }
    }
}
