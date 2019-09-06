using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using System.IO;
using System.Xml.Serialization;


namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    [XmlRoot]
    public class MainMenu
    {
        public int Versione { get; set; }

        public string Descrizione { get; set; }

        public List<SezioneMenu> Sezioni { get; set; }
        public List<MenuItem> MenuUtente { get; set; }

        public MainMenu()
        {
            this.Sezioni = new List<SezioneMenu>();
            this.MenuUtente = new List<MenuItem>();
        }

        public static MainMenu FromXmlFile(byte[] xmlFile)
        {
            var menuString = Encoding.Default.GetString(xmlFile);
            var mainMenu = new MainMenu();

            using (var ms = new MemoryStream(xmlFile))
            {
                var xs = new XmlSerializer(typeof(MainMenu));
                return (MainMenu)xs.Deserialize(ms);
            }
        }

        public static void GeneraXmlTest()
        {
            var menu = new MainMenu();

            menu.Versione = 2;
            menu.Descrizione = "Descrizione dell'home page dell'area riservata";
            menu.Sezioni.Add(new SezioneMenu
            {
                Titolo = "Scrivania virtuale",
                Items = new List<MenuItem>{
                    new MenuItem 
                    {
                        Titolo = "Torna alla scrivania virtuale",
                        Descrizione = "Descrizione",
                        Url = "~/Reserved/url.aspx?IdComune={idComune}&Software={software}&Token={token}",
                        MostraInHomePage = true,
                        IdIcona = "generico",
                        UrlIcona = "http://www.google.com/"
                    },

                    new MenuItem 
                    {
                        Titolo = "Nuova domanda",
                        Descrizione = "Permette la presentazione di SCIA, Comunicazioni e Procedimenti ordinari",
                        Url = "~/Reserved/InserimentoIstanza/NuovaIstanza.aspx?IdComune={idComune}&Software={software}&Token={token}",
                        MostraInHomePage = true,
                        IdIcona = "nuova-domanda",
                        UrlIcona = "http://www.google.com/"
                    }
                }
            });

            menu.MenuUtente.Add(new MenuItem
            {
                Titolo = "Esci",
                Descrizione = "Uscita dai servizi on-line",
                Url = "~/Reserved/exit.aspx?IdComune={idComune}&Software={software}&Token={token}",
                MostraInHomePage = true,
                IconaBootstrap = "off"
            });

            File.WriteAllText(@"c:\temp\menu-v2.xml",menu.ToXmlString());
        }

        public IEnumerable<MenuItem> GetVociHomePage()
        {
            return this.Sezioni
                        .SelectMany(x => x.Items)
                        .Where(x => x.MostraInHomePage)
                        .Union(this.MenuUtente.Where(x => x.MostraInHomePage))
                        .ToArray();
        }

        public override string ToString()
        {
            return this.ToXmlString();
        }
    }
}
