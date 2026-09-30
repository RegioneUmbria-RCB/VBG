using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System;
using System.Collections.Generic;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4
{
    public class ClassificaAdapter
    {
        public ClassificaAdapter()
        {

        }

        public string EstraiClassificaDaRegistro(bool usaLivelliClassifica, RegistroClassificato classificaResponse)
        {
            if (usaLivelliClassifica)
            {
                var arrClassifica = new List<string>();

                if (!String.IsNullOrEmpty(classificaResponse.CodiceRegistroLiv1))
                {
                    arrClassifica.Add(classificaResponse.CodiceRegistroLiv1.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.CodiceRegistroLiv2))
                {
                    arrClassifica.Add(classificaResponse.CodiceRegistroLiv2.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.CodiceRegistroLiv3))
                {
                    arrClassifica.Add(classificaResponse.CodiceRegistroLiv3.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.CodiceRegistroLiv4))
                {
                    arrClassifica.Add(classificaResponse.CodiceRegistroLiv4.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.CodiceRegistroLiv5))
                {
                    arrClassifica.Add(classificaResponse.CodiceRegistroLiv5.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.CodiceRegistroLiv6))
                {
                    arrClassifica.Add(classificaResponse.CodiceRegistroLiv6.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.CodiceRegistroLiv7))
                {
                    arrClassifica.Add(classificaResponse.CodiceRegistroLiv7.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.CodiceRegistroLiv8))
                {
                    arrClassifica.Add(classificaResponse.CodiceRegistroLiv8.Trim());
                }

                if (arrClassifica.Count > 0)
                {
                    return String.Join(".", arrClassifica);
                }
            }
            else
            {
                if (!String.IsNullOrEmpty(classificaResponse.CodiceRegistro))
                {
                    return classificaResponse.CodiceRegistro.Trim();
                }
            }

            return "";
        }

        public string EstraiClassifica(bool usaLivelliClassifica, ClassificaView classificaResponse)
        {
            if (usaLivelliClassifica)
            {
                var arrClassifica = new List<string>();

                if (!String.IsNullOrEmpty(classificaResponse.CodiceLiv1))
                {
                    arrClassifica.Add(classificaResponse.CodiceLiv1.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.CodiceLiv2))
                {
                    arrClassifica.Add(classificaResponse.CodiceLiv2.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.CodiceLiv3))
                {
                    arrClassifica.Add(classificaResponse.CodiceLiv3.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.CodiceLiv4))
                {
                    arrClassifica.Add(classificaResponse.CodiceLiv4.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.CodiceLiv5))
                {
                    arrClassifica.Add(classificaResponse.CodiceLiv5.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.CodiceLiv6))
                {
                    arrClassifica.Add(classificaResponse.CodiceLiv6.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.CodiceLiv7))
                {
                    arrClassifica.Add(classificaResponse.CodiceLiv7.Trim());
                }

                if (!String.IsNullOrEmpty(classificaResponse.CodiceLiv8))
                {
                    arrClassifica.Add(classificaResponse.CodiceLiv8.Trim());
                }

                if (arrClassifica.Count > 0)
                {
                    return String.Join(".", arrClassifica);
                }
            }
            else
            {
                if (!String.IsNullOrEmpty(classificaResponse.Codice))
                {
                    return classificaResponse.Codice.Trim();
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
                return new Classifica { Codice = classifica.Replace("x", " ") };
            }

            var livelli = classifica.Split('.');
            var classificaLivelli = new ClassificaLivelli { CodiceLiv1 = livelli[0] };

            if (livelli.Length > 1)
            {
                classificaLivelli.CodiceLiv2 = livelli[1];
            }

            if (livelli.Length > 2)
            {
                classificaLivelli.CodiceLiv3 = livelli[2];
            }

            if (livelli.Length > 3)
            {
                classificaLivelli.CodiceLiv4 = livelli[3];
            }

            if (livelli.Length > 4)
            {
                classificaLivelli.CodiceLiv5 = livelli[4];
            }

            if (livelli.Length > 5)
            {
                classificaLivelli.CodiceLiv6 = livelli[5];
            }

            if (livelli.Length > 6)
            {
                classificaLivelli.CodiceLiv7 = livelli[6];
            }

            if (livelli.Length > 7)
            {
                classificaLivelli.CodiceLiv8 = livelli[7];
            }

            return new Classifica { Livelli = classificaLivelli };
        }
    }
}
