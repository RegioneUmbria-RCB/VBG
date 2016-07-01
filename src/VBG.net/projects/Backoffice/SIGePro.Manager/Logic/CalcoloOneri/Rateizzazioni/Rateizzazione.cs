using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.Utils.Math;

namespace Init.SIGePro.Manager.Logic.CalcoloOneri.Rateizzazioni
{
	public class Rateizzazione
	{
		#region Proprietà

		int m_NumeroRate;
		public int NumeroRate
		{
			get { return m_NumeroRate; }
			set { m_NumeroRate = value; }
		}

		string m_RipartizioneRate;
		public string RipartizioneRate
		{
			get { return m_RipartizioneRate; }
			set { m_RipartizioneRate = value; }
		}

		DateTime m_DataInizioRateizzazione;
		public DateTime DataInizioRateizzazione
		{
			get { return m_DataInizioRateizzazione; }
			set { m_DataInizioRateizzazione = value; }
		}

		string m_FrequenzaRate;
		public string FrequenzaRate
		{
			get { return m_FrequenzaRate; }
			set { m_FrequenzaRate = value; }
		}

		string m_ScadenzaRate;
		public string ScadenzaRate
		{
			get { return m_ScadenzaRate; }
			set { m_ScadenzaRate = value; }
		}

		string m_InteressiRate;
		public string InteressiRate
		{
			get { return m_InteressiRate; }
			set { m_InteressiRate = value; }
		}

		#endregion

		#region Campi

		string[] aRipartizioneRate;
		string[] aFrequenzaRate;
		string[] aInteressiRate;
		double dTotaleRipartizioneRate;
		List<double> listRipartizioniRate;
		// double dTotaleInteressiRate;
		List<double> listInteressiRate;
		double dTotaleImporto;
		double dTotaleImportoIstrut;
		DateTime tLastDataScadenza;
		int iCifreDecimali = 2;

		#endregion

		#region Metodi

        #region Metodi pubblici
        public List<IstanzeOneri> Rateizza(double dImportoOnere, double dImportoIstruttoria)
		{
			List<IstanzeOneri> listRate = new List<IstanzeOneri>();

			aRipartizioneRate = m_RipartizioneRate.Split(new Char[] { ';' });
			aFrequenzaRate = m_FrequenzaRate.Split(new Char[] { ';' });
			aInteressiRate = m_InteressiRate.Split(new Char[] { ';' });

			//Ciclo per il numero totale di rate
			listRipartizioniRate = new List<double>();
			listInteressiRate = new List<double>();
			for (int iCount = 0; iCount < m_NumeroRate; iCount++)
			{
				listRipartizioniRate.Add(CalcolaRipartizioneRata(iCount));
				listInteressiRate.Add(CalcolaInteressiRata(iCount));
			}
			for (int iCount = 0; iCount < m_NumeroRate; iCount++)
			{
				IstanzeOneri io = new IstanzeOneri();
				//Calcolo l'importo della singola rata in base alla ripartizione e agli interessi
				io.PREZZO = CalcolaImportoRata(dImportoOnere, iCount);
				//Calcolo l'importo della singola rata istruttoria in base alla ripartizione e agli interessi
				io.PREZZOISTRUTTORIA = CalcolaImportoRata(dImportoIstruttoria, iCount, true);
				//Calcolo l'importo della scadenza della singola rata
				io.DATASCADENZA = CalcolaScadenzaRata(iCount);
				//Setto il numero della rata
				io.NUMERORATA = Convert.ToString(iCount + 1);

				listRate.Add(io);
			}

			return listRate;
		}

		public double CalcolaRipartizioneRata(int iCount)
		{
			double dRipartizioneRata;

			if (string.IsNullOrEmpty(m_RipartizioneRate))
			{
				if (iCount == m_NumeroRate - 1)
					dRipartizioneRata = 100 - dTotaleRipartizioneRate;
				else
					dRipartizioneRata = System.Math.Round(100.0 / m_NumeroRate, iCifreDecimali);
			}
			else
			{
				if (iCount < aRipartizioneRate.Length)
					dRipartizioneRata = Convert.ToDouble(aRipartizioneRate[iCount]);
				else
				{
					if (iCount == m_NumeroRate - 1)
						dRipartizioneRata = 100 - dTotaleRipartizioneRate;
					else
						dRipartizioneRata = (dTotaleRipartizioneRate + Convert.ToDouble(aRipartizioneRate[aRipartizioneRate.Length - 1]) > 100 ? 100 - dTotaleRipartizioneRate : Convert.ToDouble(aRipartizioneRate[aRipartizioneRate.Length - 1]));
				}
			}

            if (dRipartizioneRata == 0)
                throw new Exception("la rata finale è pari a 0%");

			dTotaleRipartizioneRate += dRipartizioneRata;

			return dRipartizioneRata;
        }
        #endregion

        #region Metodi privati
        private double CalcolaInteressiRata(int iCount)
		{
			double dInteressiRata;

			if (string.IsNullOrEmpty(m_InteressiRate))
				return 0;

            if (iCount < aInteressiRate.Length)
                dInteressiRata = Convert.ToDouble(aInteressiRate[iCount]);
            else
                dInteressiRata = Convert.ToDouble(aInteressiRate[aInteressiRate.Length - 1]);

			return dInteressiRata;
		}

		private double CalcolaImportoRata(double dImporto, int iCount)
		{
			return CalcolaImportoRata(dImporto, iCount, false);
		}


		private double CalcolaImportoRata(double dImporto, int iCount, bool bIstruttoria)
		{
			double dImportoRata = 0;

			//Verifico se esiste l'importo istruttoria da rateizzare
			if (dImporto == 0)
				return dImportoRata;

			dImportoRata = System.Math.Round((dImporto * listRipartizioniRate[iCount]) / 100.0, iCifreDecimali);
            //if (listInteressiRate[iCount] != 0)
            //    dImportoRata = System.Math.Round((dImportoRata * listInteressiRate[iCount]) / 100.0, iCifreDecimali);

			if (bIstruttoria)
			{
				if (iCount == m_NumeroRate - 1)
					dImportoRata = dImporto - dTotaleImportoIstrut;
				else
					dImportoRata = (dTotaleImportoIstrut + dImportoRata > dImporto ? dImporto - dTotaleImportoIstrut : dImportoRata);

				dTotaleImportoIstrut += dImportoRata;
			}
			else
			{
				if (iCount == m_NumeroRate - 1)
					dImportoRata = dImporto - dTotaleImporto;
				else
					dImportoRata = (dTotaleImporto + dImportoRata > dImporto ? dImporto - dTotaleImporto : dImportoRata);

				dTotaleImporto += dImportoRata;
			}

            if (listInteressiRate[iCount] != 0)
                dImportoRata = dImportoRata + System.Math.Round((dImportoRata * listInteressiRate[iCount]) / 100.0, iCifreDecimali);

			return dImportoRata;
		}



		private int CalcolaFrequenzaRata(int iCount)
		{
			int iFrequenzaRata;

			if (string.IsNullOrEmpty(m_FrequenzaRate))
				return 0;

			if (iCount < aFrequenzaRate.Length)
				iFrequenzaRata = Convert.ToInt32(aFrequenzaRate[iCount]);
			else
				iFrequenzaRata = Convert.ToInt32(aFrequenzaRate[aFrequenzaRate.Length - 1]);

			return iFrequenzaRata;
		}

		private DateTime CalcolaScadenzaRata(int iCount)
		{
			//Calcolo il valore della frequenza della singola rata
			int iFrequenzaRata = CalcolaFrequenzaRata(iCount);

			DateTime tDataScadenza;

            if (iCount == 0)
            {
                tDataScadenza = m_DataInizioRateizzazione.AddDays(iFrequenzaRata);
            }
            else
            {
                tDataScadenza = tLastDataScadenza.AddDays(iFrequenzaRata);
            }

			switch (m_ScadenzaRate)
			{
				case "0":
                    tDataScadenza = DateUtilities.GetEndOfCurrentMonth(tDataScadenza);
					break;
				case "1":
					tDataScadenza = DateUtilities.GetHalfOfCurrentMonth(tDataScadenza);
					break;
				case "2":
					if (iCount != 0)
                        tDataScadenza = DateUtilities.GetEndOfCurrentMonth(tDataScadenza);
					break;
				case "3":
					if (iCount != 0)
						tDataScadenza = DateUtilities.GetHalfOfCurrentMonth(tDataScadenza);
					break;
                case "5":
                    tDataScadenza = DateUtilities.GetHalfOfNextMonth(tDataScadenza);
                    break;
                case "6":
                    if (iCount != 0)
                        tDataScadenza = DateUtilities.GetHalfOfNextMonth(tDataScadenza);
                    break;
			}

			tLastDataScadenza = tDataScadenza;
			return tDataScadenza;
        }
        #endregion

        #endregion
    }

	public class DateUtilities
	{
		#region Months
		private static DateTime GetStartOfMonth(int Month, int Year)
		{
			return new DateTime(Year, (int)Month, 1, 0, 0, 0, 0);
		}

		private static DateTime GetHalfOfMonth(int Month, int Year)
		{
			return new DateTime(Year, (int)Month, 15, 0, 0, 0, 0);
		}

		private static DateTime GetEndOfMonth(int Month, int Year)
		{
			return new DateTime(Year, (int)Month,
			   DateTime.DaysInMonth(Year, (int)Month), 0, 0, 0, 0);
        }

        #region Public methods

        #region Last month
        public static DateTime GetStartOfLastMonth(DateTime dt)
        {
            if (dt.Month == 1)
                return GetStartOfMonth(12, dt.Year - 1);
            else
                return GetStartOfMonth(dt.Month - 1, dt.Year);
        }

        public static DateTime GetEndOfLastMonth(DateTime dt)
        {
            if (dt.Month == 1)
                return GetEndOfMonth(12, dt.Year - 1);
            else
                return GetEndOfMonth(dt.Month - 1, dt.Year);
        }
        #endregion

        #region Current month
        public static DateTime GetStartOfCurrentMonth(DateTime dt)
        {
            return GetStartOfMonth(dt.Month, dt.Year);
        }

        public static DateTime GetHalfOfCurrentMonth(DateTime dt)
        {
            if (dt.Month == 12)
            {
                if ((dt.Day >= 1) && (dt.Day <= 15))
                    return GetHalfOfMonth(12, dt.Year);
                else
                    return GetHalfOfMonth(1, dt.Year + 1);
            }
            else
            {
                if ((dt.Day >= 1) && (dt.Day <= 15))
                    return GetHalfOfMonth(dt.Month, dt.Year);
                else
                    return GetHalfOfMonth(dt.Month + 1, dt.Year);
            }
        }

        public static DateTime GetEndOfCurrentMonth(DateTime dt)
        {
            return GetEndOfMonth(dt.Month, dt.Year);
        }
        #endregion

        #region Next month
        public static DateTime GetStartOfNextMonth(DateTime dt)
		{
			if (dt.Month == 12)
				return GetStartOfMonth(1, dt.Year + 1);
			else
				return GetStartOfMonth(dt.Month + 1, dt.Year);
		}

		public static DateTime GetHalfOfNextMonth(DateTime dt)
		{
			if (dt.Month == 12)
			    return GetHalfOfMonth(1, dt.Year + 1);
			else
				return GetHalfOfMonth(dt.Month + 1, dt.Year);
		}

        public static DateTime GetEndOfNextMonth(DateTime dt)
        {
            if (dt.Month == 12)
            {
                if ((dt.Day >= 1) && (dt.Day <= 15))
                    return GetEndOfMonth(12, dt.Year);
                else
                    return GetEndOfMonth(1, dt.Year + 1);
            }
            else
            {
                if ((dt.Day >= 1) && (dt.Day <= 15))
                    return GetEndOfMonth(dt.Month, dt.Year);
                else
                    return GetEndOfMonth(dt.Month + 1, dt.Year);
            }
        }
        #endregion

        #endregion
        #endregion
    }
}
