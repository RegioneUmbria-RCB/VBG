namespace Init.Utils.CalcoloCF
{
    // InIT.CodiceFiscale
    using System;

    public class CodiceFiscale
    {
        public string CF = string.Empty;

        public string Cognome = string.Empty;

        public string Nome = string.Empty;

        public string Sesso = string.Empty;

        public int Giorno = 0;

        public int Mese = 0;

        public string MeseStr = string.Empty;

        public int Anno2 = 0;

        public string CodiceCatastale = string.Empty;

        public string CarattereControllo = string.Empty;

        public CodiceFiscale(string cognome, string nome, DateTime datanascita, string codcatastale, string sesso)
        {
            this.Cognome = cognome;
            this.Nome = nome;
            if (sesso == "M" || sesso == "F")
            {
                this.Sesso = sesso;
            }
            if (codcatastale.Length == 4)
            {
                this.CodiceCatastale = codcatastale.ToUpper();
            }
            this.Giorno = datanascita.Day;
            this.Mese = datanascita.Month;
            this.MeseStr = this.calcolaLetteraMeseNascita(this.Mese);
            this.Anno2 = Convert.ToInt32(datanascita.Year.ToString().Substring(2, 2));
            if (this.Cognome != string.Empty && this.Nome != string.Empty && this.Sesso != string.Empty && this.CodiceCatastale != string.Empty)
            {
                this.CF = this.calcolaCF(cognome, nome, datanascita, sesso, codcatastale);
                this.CarattereControllo = this.CF.Substring(this.CF.Length - 1, 1);
            }
        }

        public CodiceFiscale()
        {
        }

        private string calcolaLetteraMeseNascita(int Mese)
        {
            string result = string.Empty;
            string[] array = new string[12]
            {
            "A", "B", "C", "D", "E", "H", "L", "M", "P", "R",
            "S", "T"
            };
            if (Mese >= 1 && Mese <= 12)
            {
                result = array[Mese - 1];
            }
            return result;
        }

        private string calcolaCF(string CFparziale)
        {
            string result = string.Empty;
            if (CFparziale.Length == 15)
            {
                result = CFparziale + this.getCarattereControllo(CFparziale);
            }
            return result;
        }

        public string calcolaCF(string cognome, string nome, DateTime datanascita, string sesso, string codicecatastale)
        {
            string empty = string.Empty;
            string text = string.Empty;
            string text2 = string.Empty;
            string text3 = string.Empty;
            codicecatastale = codicecatastale.ToUpper();
            if (cognome != string.Empty || nome != string.Empty)
            {
                text = this.calcolaCognome(cognome);
                text2 = this.calcolaNome(nome);
                text3 = this.EstraiDataNasc(datanascita.Day, datanascita.Month, datanascita.Year, sesso);
            }
            empty = text + text2 + text3 + codicecatastale;
            return this.calcolaCF(empty);
        }

        private string getCarattereControllo(string cf)
        {
            string empty = string.Empty;
            string empty2 = string.Empty;
            int num = 0;
            int num2 = 0;
            for (num = 0; num <= 15; num++)
            {
                empty2 = cf.Substring(num, 1);
                num2 += this.Lettera2Dispari(empty2);
                num++;
                if (num < 15)
                {
                    empty2 = cf.Substring(num, 1);
                    num2 += this.Lettera2Ppari(empty2);
                }
            }
            return this.CodCtrl2Lettera(num2 % 26);
        }

        private int Lettera2Dispari(string carattere)
        {
            int result = 0;
            switch (carattere)
            {
                case "0":
                    result = 1;
                    break;
                case "1":
                    result = 0;
                    break;
                case "2":
                    result = 5;
                    break;
                case "3":
                    result = 7;
                    break;
                case "4":
                    result = 9;
                    break;
                case "5":
                    result = 13;
                    break;
                case "6":
                    result = 15;
                    break;
                case "7":
                    result = 17;
                    break;
                case "8":
                    result = 19;
                    break;
                case "9":
                    result = 21;
                    break;
                case "A":
                    result = 1;
                    break;
                case "B":
                    result = 0;
                    break;
                case "C":
                    result = 5;
                    break;
                case "D":
                    result = 7;
                    break;
                case "E":
                    result = 9;
                    break;
                case "F":
                    result = 13;
                    break;
                case "G":
                    result = 15;
                    break;
                case "H":
                    result = 17;
                    break;
                case "I":
                    result = 19;
                    break;
                case "J":
                    result = 21;
                    break;
                case "K":
                    result = 2;
                    break;
                case "L":
                    result = 4;
                    break;
                case "M":
                    result = 18;
                    break;
                case "N":
                    result = 20;
                    break;
                case "O":
                    result = 11;
                    break;
                case "P":
                    result = 3;
                    break;
                case "Q":
                    result = 6;
                    break;
                case "R":
                    result = 8;
                    break;
                case "S":
                    result = 12;
                    break;
                case "T":
                    result = 14;
                    break;
                case "U":
                    result = 16;
                    break;
                case "V":
                    result = 10;
                    break;
                case "W":
                    result = 22;
                    break;
                case "X":
                    result = 25;
                    break;
                case "Y":
                    result = 24;
                    break;
                case "Z":
                    result = 23;
                    break;
            }
            return result;
        }

        private int Lettera2Ppari(string carattere)
        {
            int result = 0;
            switch (carattere)
            {
                case "0":
                    result = 0;
                    break;
                case "1":
                    result = 1;
                    break;
                case "2":
                    result = 2;
                    break;
                case "3":
                    result = 3;
                    break;
                case "4":
                    result = 4;
                    break;
                case "5":
                    result = 5;
                    break;
                case "6":
                    result = 6;
                    break;
                case "7":
                    result = 7;
                    break;
                case "8":
                    result = 8;
                    break;
                case "9":
                    result = 9;
                    break;
                case "A":
                    result = 0;
                    break;
                case "B":
                    result = 1;
                    break;
                case "C":
                    result = 2;
                    break;
                case "D":
                    result = 3;
                    break;
                case "E":
                    result = 4;
                    break;
                case "F":
                    result = 5;
                    break;
                case "G":
                    result = 6;
                    break;
                case "H":
                    result = 7;
                    break;
                case "I":
                    result = 8;
                    break;
                case "J":
                    result = 9;
                    break;
                case "K":
                    result = 10;
                    break;
                case "L":
                    result = 11;
                    break;
                case "M":
                    result = 12;
                    break;
                case "N":
                    result = 13;
                    break;
                case "O":
                    result = 14;
                    break;
                case "P":
                    result = 15;
                    break;
                case "Q":
                    result = 16;
                    break;
                case "R":
                    result = 17;
                    break;
                case "S":
                    result = 18;
                    break;
                case "T":
                    result = 19;
                    break;
                case "U":
                    result = 20;
                    break;
                case "V":
                    result = 21;
                    break;
                case "W":
                    result = 22;
                    break;
                case "X":
                    result = 23;
                    break;
                case "Y":
                    result = 24;
                    break;
                case "Z":
                    result = 25;
                    break;
            }
            return result;
        }

        private string EstraiVocali(string stringa)
        {
            string text = "AEIOU";
            string text2 = string.Empty;
            string empty = string.Empty;
            if (stringa != string.Empty)
            {
                stringa = stringa.ToUpper();
                for (int i = 0; i < stringa.Length; i++)
                {
                    empty = stringa.Substring(i, 1);
                    if (text.IndexOf(Convert.ToChar(empty)) > -1)
                    {
                        text2 += empty;
                    }
                }
            }
            return text2;
        }

        private string CodCtrl2Lettera(int numero)
        {
            string result = string.Empty;
            string[] array = new string[26]
            {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
            };
            if (numero >= 0 && numero <= 25)
            {
                result = array[numero];
            }
            return result;
        }

        private string calcolaCognome(string cognome)
        {
            string empty = string.Empty;
            empty = this.EstraiConsonanti(cognome);
            if (empty.Length >= 3)
            {
                return empty.Substring(0, 3);
            }
            empty += this.EstraiVocali(cognome);
            if (empty.Length >= 3)
            {
                return empty.Substring(0, 3);
            }
            return empty.PadRight(3, Convert.ToChar("X"));
        }

        private string calcolaNome(string nome)
        {
            string text = "AEIOU";
            string text2 = "AEIOU'-,;:.\\/_ ()[]{}";
            string text3 = string.Empty;
            int length = nome.Length;
            int num = 0;
            string empty = string.Empty;
            int num2 = 0;
            if (nome != string.Empty)
            {
                nome = nome.ToUpper();
                for (num = 0; num < length; num++)
                {
                    empty = nome.Substring(num, 1);
                    if (text2.IndexOf(empty) == -1)
                    {
                        text3 += empty;
                        num2++;
                    }
                    if (num2 == 4)
                    {
                        text3 = text3.Substring(0, 1) + text3.Substring(2, 2);
                        break;
                    }
                }
                if (num2 < 3)
                {
                    for (num = 0; num < length; num++)
                    {
                        empty = nome.Substring(num, 1);
                        if (text.IndexOf(empty) != -1)
                        {
                            text3 += empty;
                            num2++;
                        }
                        if (num2 == 3)
                        {
                            break;
                        }
                    }
                }
                if (num2 < 3)
                {
                    text3 = text3.PadRight(3, Convert.ToChar("X"));
                }
                text3 = text3.ToUpper();
            }
            return text3;
        }

        private string EstraiConsonanti(string parola)
        {
            string text = string.Empty;
            int num = 0;
            string empty = string.Empty;
            string text2 = "BCDFGHJKLMNPQRSTVWXYZ";
            if (parola != string.Empty)
            {
                parola = parola.ToUpper();
                for (num = 0; num < parola.Length; num++)
                {
                    empty = parola.Substring(num, 1);
                    if (text2.IndexOf(Convert.ToChar(empty)) > -1)
                    {
                        text += empty;
                    }
                }
            }
            return text;
        }

        private string EstraiDataNasc(int giorno, int mese, int anno, string sesso)
        {
            string result = string.Empty;
            string empty = string.Empty;
            string empty2 = string.Empty;
            string empty3 = string.Empty;
            if (giorno > 0 && mese > 0 && anno > 0 && sesso != string.Empty)
            {
                empty = anno.ToString().PadLeft(4, '0').Substring(2, 2);
                empty2 = this.calcolaLetteraMeseNascita(mese);
                empty3 = ((!(sesso.ToUpper() == "M")) ? (giorno + 40).ToString() : giorno.ToString());
                if (empty3.Length == 1)
                {
                    empty3 = "0" + empty3;
                }
                result = empty + empty2 + empty3;
            }
            return result;
        }
    }

}
