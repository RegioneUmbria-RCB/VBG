using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;

namespace Init.SIGePro.Protocollo.Insiel3
{
    public class ClassificaAdapter
    {
        public ClassificaAdapter()
        {

        }

        public string EstraiClassificaDaRegistro(bool usaLivelliClassifica, RegistroDaClassificaView classificaResponse)
        {
            if (usaLivelliClassifica)
            {
                var arrClassifica = new List<string>();

                if (!String.IsNullOrEmpty(classificaResponse.codiceRegistroLiv1))
                {
                    arrClassifica.Add(classificaResponse.codiceRegistroLiv1.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.codiceRegistroLiv2))
                {
                    arrClassifica.Add(classificaResponse.codiceRegistroLiv2.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.codiceRegistroLiv3))
                {
                    arrClassifica.Add(classificaResponse.codiceRegistroLiv3.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.codiceRegistroLiv4))
                {
                    arrClassifica.Add(classificaResponse.codiceRegistroLiv4.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.codiceRegistroLiv5))
                {
                    arrClassifica.Add(classificaResponse.codiceRegistroLiv5.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.codiceRegistroLiv6))
                {
                    arrClassifica.Add(classificaResponse.codiceRegistroLiv6.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.codiceRegistroLiv7))
                {
                    arrClassifica.Add(classificaResponse.codiceRegistroLiv7.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.codiceRegistroLiv8))
                {
                    arrClassifica.Add(classificaResponse.codiceRegistroLiv8.Trim());
                }

                if (arrClassifica.Count > 0)
                {
                    return String.Join(".", arrClassifica);
                }
            }
            else
            {
                if (!String.IsNullOrEmpty(classificaResponse.codiceRegistro))
                {
                    return classificaResponse.codiceRegistro.Trim();
                }
            }

            return "";
        }

        public string EstraiClassifica(bool usaLivelliClassifica, ClassificaView classificaResponse)
        {
            if (usaLivelliClassifica)
            {
                var arrClassifica = new List<string>();

                if (!String.IsNullOrEmpty(classificaResponse.codiceLiv1))
                {
                    arrClassifica.Add(classificaResponse.codiceLiv1.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.codiceLiv2))
                {
                    arrClassifica.Add(classificaResponse.codiceLiv2.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.codiceLiv3))
                {
                    arrClassifica.Add(classificaResponse.codiceLiv3.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.codiceLiv4))
                {
                    arrClassifica.Add(classificaResponse.codiceLiv4.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.codiceLiv5))
                {
                    arrClassifica.Add(classificaResponse.codiceLiv5.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.codiceLiv6))
                {
                    arrClassifica.Add(classificaResponse.codiceLiv6.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.codiceLiv7))
                {
                    arrClassifica.Add(classificaResponse.codiceLiv7.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.codiceLiv8))
                {
                    arrClassifica.Add(classificaResponse.codiceLiv8.Trim());
                }

                if (arrClassifica.Count > 0)
                {
                    return String.Join(".", arrClassifica);
                }
            }
            else
            {
                if (!String.IsNullOrEmpty(classificaResponse.codice))
                {
                    return classificaResponse.codice.Trim();
                }
            }

            return "";
        }

        public Classifica Adatta(bool usaLivelliClassifica, string classifica)
        {
            if (String.IsNullOrEmpty(classifica))
            {
                throw new Exception("CLASSIFICA NON VALORIZZATA");
            }

            if (!usaLivelliClassifica)
            {
                return new Classifica { Item = classifica.Replace("x", " ") };
            }

            var livelli = classifica.Split('.');
            var classificaLivelli = new ClassificaLivelli { codiceLiv1 = livelli[0] };

            if (livelli.Length > 1)
            {
                classificaLivelli.codiceLiv2 = livelli[1];
            }

            if (livelli.Length > 2)
            {
                classificaLivelli.codiceLiv3 = livelli[2];
            }

            if (livelli.Length > 3)
            {
                classificaLivelli.codiceLiv4 = livelli[3];
            }

            if (livelli.Length > 4)
            {
                classificaLivelli.codiceLiv5 = livelli[4];
            }

            if (livelli.Length > 5)
            {
                classificaLivelli.codiceLiv6 = livelli[5];
            }

            if (livelli.Length > 6)
            {
                classificaLivelli.codiceLiv7 = livelli[6];
            }

            if (livelli.Length > 7)
            {
                classificaLivelli.codiceLiv8 = livelli[7];
            }

            return new Classifica { Item = classificaLivelli };
        }
    }
}
