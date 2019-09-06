using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneMercati
{
    public class DettagliAutorizzazione
    {
        private int _codice;
        private string _numero;
        private DateTime _data;
        private EnteAutorizzazione _ente;
        private int _numeroPresenze;

        public int Codice
        {
            get { return _codice; }
            set { _codice = value; }
        }

        public string Numero
        {
            get { return _numero; }
            set { _numero = value; }
        }

        public DateTime Data
        {
            get { return _data; }
            set { _data = value; }
        }

        public EnteAutorizzazione Ente
        {
            get { return _ente; }
            set { _ente = value; }
        }

        public int NumeroPresenze
        {
            get { return _numeroPresenze; }
            set { _numeroPresenze = value; }
        }
    }
}
